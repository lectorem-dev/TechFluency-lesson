from io import BytesIO
from pathlib import Path
from typing import Optional
from uuid import UUID

from reportlab.lib import colors
from reportlab.lib.pagesizes import A4, landscape
from reportlab.lib.utils import ImageReader
from reportlab.pdfbase import pdfmetrics
from reportlab.pdfbase.ttfonts import TTFont
from reportlab.pdfgen import canvas
from sqlalchemy.orm import Session

from app.common.exceptions import CourseNotCompletedException, CourseNotFoundException
from app.course.repository import get_course_lessons, get_main_course
from app.progress.repository import count_completed_lessons
from app.users.models import User


ASSETS_DIR = Path(__file__).resolve().parent / "assets"
FONTS_DIR = ASSETS_DIR / "fonts"
IMAGES_DIR = ASSETS_DIR / "images"

BACKGROUND_PATH = IMAGES_DIR / "techfluency.png"

COURSE_TITLE = "TECH FLUENCY: АНГЛИЙСКИЙ ДЛЯ IT-СПЕЦИАЛИСТОВ"
CERTIFICATE_TITLE = "CERTIFICATE"

FONT_CANDIDATES = [
    FONTS_DIR / "Montserrat-Regular.ttf",
    Path("/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf"),
    Path("/usr/share/fonts/dejavu/DejaVuSans.ttf"),
    Path("/Library/Fonts/Arial Unicode.ttf"),
    Path("/System/Library/Fonts/Supplemental/Arial Unicode.ttf"),
    Path("/System/Library/Fonts/Supplemental/Arial.ttf"),
    Path("C:/Windows/Fonts/arial.ttf"),
]


def generate_certificate(db: Session, user: User) -> bytes:
    course = get_main_course(db)
    if course is None:
        raise CourseNotFoundException()

    total_lessons = len(get_course_lessons(db, course))
    completed_lessons = count_completed_lessons(db, user.id, course.id)
    if total_lessons == 0 or completed_lessons < total_lessons:
        raise CourseNotCompletedException()

    return generate_pdf(user.name, user.id)


def generate_pdf(user_name: str, user_id: UUID) -> bytes:
    output = BytesIO()
    page_width, page_height = landscape(A4)

    regular_font = "CertificateRegular"
    semibold_font = "CertificateSemiBold"
    display_font = "CertificateDisplay"

    pdfmetrics.registerFont(TTFont(regular_font, str(resolve_font_path("Montserrat-Regular.ttf"))))
    pdfmetrics.registerFont(TTFont(semibold_font, str(resolve_font_path("Montserrat-SemiBold.ttf"))))
    pdfmetrics.registerFont(TTFont(display_font, str(resolve_font_path("ZenDots-Regular.ttf"))))

    certificate_number = build_certificate_number(user_id, user_name)

    pdf = canvas.Canvas(output, pagesize=landscape(A4))

    draw_background(pdf, page_width, page_height)
    draw_text_panel(
        pdf=pdf,
        page_width=page_width,
        page_height=page_height,
        user_name=user_name,
        certificate_number=certificate_number,
        regular_font=regular_font,
        semibold_font=semibold_font,
        display_font=display_font,
    )

    pdf.showPage()
    pdf.save()

    return output.getvalue()


def draw_background(pdf: canvas.Canvas, page_width: float, page_height: float) -> None:
    if BACKGROUND_PATH.is_file():
        background = ImageReader(str(BACKGROUND_PATH))
        image_width, image_height = background.getSize()

        scale = max(page_width / image_width, page_height / image_height)
        draw_width = image_width * scale
        draw_height = image_height * scale

        x = (page_width - draw_width) / 2
        y = (page_height - draw_height) / 2

        pdf.drawImage(background, x, y, width=draw_width, height=draw_height, mask="auto")
    else:
        pdf.setFillColor(colors.HexColor("#07091f"))
        pdf.rect(0, 0, page_width, page_height, fill=1, stroke=0)


def draw_text_panel(
    pdf: canvas.Canvas,
    page_width: float,
    page_height: float,
    user_name: str,
    certificate_number: str,
    regular_font: str,
    semibold_font: str,
    display_font: str,
) -> None:
    panel_width = 560
    panel_height = 300
    panel_x = 56
    panel_y = page_height - panel_height - 56

    top_margin = 42
    bottom_margin = 28
    left_margin = 34
    right_margin = 34

    text_x = panel_x + left_margin
    text_right_x = panel_x + panel_width - right_margin
    top_text_y = panel_y + panel_height - top_margin
    bottom_text_y = panel_y + bottom_margin

    pdf.saveState()
    pdf.setFillAlpha(0.72)
    pdf.setFillColor(colors.HexColor("#2b2d38"))
    pdf.roundRect(panel_x, panel_y, panel_width, panel_height, 18, fill=1, stroke=0)
    pdf.restoreState()

    pdf.setStrokeColor(colors.Color(1, 1, 1, alpha=0.28))
    pdf.setLineWidth(1)
    pdf.roundRect(panel_x, panel_y, panel_width, panel_height, 18, fill=0, stroke=1)

    pdf.setFillColor(colors.white)
    pdf.setFont(display_font, 26)
    pdf.drawString(text_x, top_text_y, CERTIFICATE_TITLE)

    pdf.setFont(regular_font, 13)
    pdf.drawString(text_x, top_text_y - 44, "Настоящим подтверждается, что")

    pdf.setFont(semibold_font, 28)
    pdf.drawString(text_x, top_text_y - 84, user_name)

    pdf.setFont(regular_font, 13)
    pdf.drawString(text_x, top_text_y - 122, "успешно завершил курс")

    pdf.setFont(semibold_font, 15)
    draw_text_with_right_limit(
        pdf=pdf,
        text=COURSE_TITLE,
        x=text_x,
        y=top_text_y - 152,
        max_right_x=text_right_x,
        font_name=semibold_font,
        font_size=15,
    )

    pdf.setFont(regular_font, 10)
    pdf.setFillColor(colors.HexColor("#d9d9e6"))
    pdf.drawString(text_x, bottom_text_y + 20, "Уникальный номер сертификата:")

    pdf.setFont(semibold_font, 13)
    pdf.setFillColor(colors.white)
    draw_text_with_right_limit(
        pdf=pdf,
        text=certificate_number,
        x=text_x,
        y=bottom_text_y,
        max_right_x=text_right_x,
        font_name=semibold_font,
        font_size=13,
    )


def draw_text_with_right_limit(
    pdf: canvas.Canvas,
    text: str,
    x: float,
    y: float,
    max_right_x: float,
    font_name: str,
    font_size: int,
) -> None:
    available_width = max_right_x - x
    text_width = pdf.stringWidth(text, font_name, font_size)

    if text_width <= available_width:
        pdf.drawString(x, y, text)
        return

    shortened = text
    while shortened and pdf.stringWidth(shortened + "...", font_name, font_size) > available_width:
        shortened = shortened[:-1]

    pdf.drawString(x, y, shortened + "...")


def build_certificate_number(user_id: UUID, user_name: str) -> str:
    uuid_part = str(user_id).replace("-", "").upper()
    initials = "".join(part[0] for part in user_name.split()[:3] if part).upper()
    raw_number = f"{uuid_part[:6]}{uuid_part[-6:]}{initials}"
    return "".join(symbol for symbol in raw_number if symbol.isalnum())


def resolve_font_path(font_filename: Optional[str] = None) -> Path:
    candidates = [FONTS_DIR / font_filename] if font_filename else []
    candidates.extend(FONT_CANDIDATES)

    for path in candidates:
        if path and path.is_file():
            return path

    raise RuntimeError("Не найден TrueType-шрифт для генерации PDF-сертификата")

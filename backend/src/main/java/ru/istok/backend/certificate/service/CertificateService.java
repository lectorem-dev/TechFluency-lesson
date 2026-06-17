package ru.istok.backend.certificate.service;

import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.istok.backend.common.exception.ArchivedUserException;
import ru.istok.backend.common.exception.CourseNotCompletedException;
import ru.istok.backend.common.exception.CourseNotFoundException;
import ru.istok.backend.common.exception.UserNotFoundException;
import ru.istok.backend.course.entity.Course;
import ru.istok.backend.course.repository.CourseRepository;
import ru.istok.backend.course.repository.LessonRepository;
import ru.istok.backend.progress.repository.LessonProgressRepository;
import ru.istok.backend.security.JwtUser;
import ru.istok.backend.user.entity.User;
import ru.istok.backend.user.entity.UserStatus;
import ru.istok.backend.user.repository.UserRepository;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CertificateService {

    private static final String COURSE_TITLE = "Основной курс";
    private static final String CERTIFICATE_FILENAME = "certificate.pdf";
    private static final float LEFT_MARGIN = 40F;
    private static final float TOP_MARGIN = 40F;
    private static final float FONT_SIZE = 22F;
    private static final List<Path> FONT_CANDIDATES = List.of(
            Path.of("/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf"),
            Path.of("/usr/share/fonts/dejavu/DejaVuSans.ttf"),
            Path.of("/Library/Fonts/Arial Unicode.ttf"),
            Path.of("/System/Library/Fonts/Supplemental/Arial Unicode.ttf"),
            Path.of("/System/Library/Fonts/Supplemental/Arial.ttf"),
            Path.of("C:/Windows/Fonts/arial.ttf")
    );

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final LessonProgressRepository lessonProgressRepository;

    @Transactional(readOnly = true)
    public CertificatePdf generateCertificateForCurrentUser() {
        User user = getCurrentUser();
        if (user.getStatus() == UserStatus.ARCHIVED) {
            throw new ArchivedUserException();
        }

        Course course = courseRepository.findByTitle(COURSE_TITLE)
                .orElseThrow(CourseNotFoundException::new);
        long totalLessons = lessonRepository.findByCourseOrderByPositionAsc(course).size();
        long completedLessons = lessonProgressRepository.countByUserIdAndLessonCourseIdAndPassedTrue(
                user.getId(),
                course.getId()
        );

        // Сертификат доступен только после прохождения всех уроков курса.
        if (totalLessons == 0 || completedLessons < totalLessons) {
            throw new CourseNotCompletedException();
        }

        return new CertificatePdf(generatePdf(user.getName()), CERTIFICATE_FILENAME);
    }

    private byte[] generatePdf(String userName) {
        try (PDDocument document = new PDDocument();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDType0Font font = PDType0Font.load(document, resolveFontPath().toFile());
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(font, FONT_SIZE);
                contentStream.setNonStrokingColor(Color.BLACK);
                contentStream.newLineAtOffset(LEFT_MARGIN, page.getMediaBox().getHeight() - TOP_MARGIN - FONT_SIZE);
                contentStream.showText(userName);
                contentStream.endText();
            }

            document.save(outputStream);
            return outputStream.toByteArray();
        } catch (IOException exception) {
            throw new IllegalStateException("Не удалось сформировать PDF-сертификат", exception);
        }
    }

    private Path resolveFontPath() {
        // Ищем системный TrueType-шрифт с поддержкой кириллицы, чтобы имя студента корректно попало в PDF.
        return FONT_CANDIDATES.stream()
                .filter(Files::isRegularFile)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Не найден TrueType-шрифт для генерации PDF-сертификата"));
    }

    private User getCurrentUser() {
        Object principal = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        if (!(principal instanceof JwtUser jwtUser)) {
            throw new UserNotFoundException();
        }

        return userRepository.findById(jwtUser.userId())
                .orElseThrow(UserNotFoundException::new);
    }
}

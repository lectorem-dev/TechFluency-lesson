package ru.istok.backend.certificate.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.istok.backend.certificate.service.CertificatePdf;
import ru.istok.backend.certificate.service.CertificateService;

@RestController
@RequestMapping("/api/certificate")
@RequiredArgsConstructor
@Tag(name = "Certificate")
public class CertificateController {

    private final CertificateService certificateService;

    @GetMapping(produces = MediaType.APPLICATION_PDF_VALUE)
    @Operation(
            summary = "Download course completion certificate",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "PDF certificate",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_PDF_VALUE,
                                    schema = @Schema(type = "string", format = "binary")
                            )
                    ),
                    @ApiResponse(responseCode = "403", description = "Course is not completed")
            }
    )
    public ResponseEntity<byte[]> downloadCertificate() {
        CertificatePdf certificate = certificateService.generateCertificateForCurrentUser();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename(certificate.filename())
                                .build()
                                .toString()
                )
                .body(certificate.content());
    }
}

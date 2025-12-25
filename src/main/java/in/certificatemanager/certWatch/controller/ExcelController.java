package in.certificatemanager.certWatch.controller;

import in.certificatemanager.certWatch.entity.ProfileEntity;
import in.certificatemanager.certWatch.service.CertificateService;
import in.certificatemanager.certWatch.service.EmailService;
import in.certificatemanager.certWatch.service.ExcelService;
import in.certificatemanager.certWatch.service.ProfileService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/excel")
@RequiredArgsConstructor
public class ExcelController {

     private final ExcelService excelService;
     private final CertificateService certificateService;
     private final EmailService emailService;
     private final ProfileService profileService;

     @GetMapping("/download")
     public void downloadExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=certificates.xlsx");
        excelService.writeCertificatesToExcel(response.getOutputStream(), certificateService.getAllCertificatesForCurrentUser());
     }

     @GetMapping("/email")
     public ResponseEntity<String> emailExcel() throws IOException, MessagingException {
        ProfileEntity profile = profileService.getCurrentProfile();
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         excelService.writeCertificatesToExcel(baos, certificateService.getAllCertificatesForCurrentUser());

         emailService.sendEmailWithAttachment(profile.getEmail(),
                  "Your certificate details report",
                 "Please find your attached report with all the certificate details",
                 baos.toByteArray(),
                 "certificate_report.xlsx");

         return ResponseEntity.ok("Email sent successfully.");
     }
}

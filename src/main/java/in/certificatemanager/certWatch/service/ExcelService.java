package in.certificatemanager.certWatch.service;

import in.certificatemanager.certWatch.dto.CertificateDTO;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class ExcelService {

    public void writeCertificatesToExcel(OutputStream os, List<CertificateDTO> certificates) throws IOException {
        try(Workbook workbook = new XSSFWorkbook()){
            Sheet sheet = workbook.createSheet("Certificates");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Sr No.");
            header.createCell(1).setCellValue("Subject");
            header.createCell(2).setCellValue("Issued By");
            header.createCell(3).setCellValue("Category");
            header.createCell(4).setCellValue("Certificate Sr Number");
            header.createCell(5).setCellValue("Issued Date");
            header.createCell(6).setCellValue("Expiry Date");
            header.createCell(7).setCellValue("Version");
            header.createCell(8).setCellValue("Signature Algorithm");
            header.createCell(9).setCellValue("SAN");
            header.createCell(10).setCellValue("Comments");
            header.createCell(11).setCellValue("Archived");

            IntStream.range(0, certificates.size()).forEach((c)-> {
                CertificateDTO certificate = certificates.get(c);
                Row row = sheet.createRow(c+1);
                row.createCell(0).setCellValue(c+1);
                row.createCell(1).setCellValue(certificate.getSubject() != null ? certificate.getSubject() : "N/A");
                row.createCell(2).setCellValue(certificate.getIssuedBy() != null ? certificate.getIssuedBy() : "N/A");
                row.createCell(3).setCellValue(certificate.getCategoryId() != null ? certificate.getCategoryId() : null);
                row.createCell(4).setCellValue(certificate.getSerialNumber() != null ? certificate.getSerialNumber() : "N/A");
                row.createCell(5).setCellValue(certificate.getIssuedDate() != null ? certificate.getIssuedDate().toString() : null);
                row.createCell(6).setCellValue(certificate.getExpiryDate() != null ? certificate.getExpiryDate().toString() : null);
                row.createCell(7).setCellValue(certificate.getVersion() != 0 ? certificate.getVersion() : null);
                row.createCell(8).setCellValue(certificate.getSignatureAlgorithm() != null ? certificate.getSignatureAlgorithm() : "N/A");
                row.createCell(9).setCellValue(certificate.getSubjectAltName() != null ? certificate.getSubjectAltName() : "N/A");
                row.createCell(10).setCellValue(certificate.getComments() != null ? certificate.getComments() : "N/A");
                row.createCell(11).setCellValue(certificate.getIsArchived() != null ? certificate.getIsArchived() : null);

            });
            workbook.write(os);
        }
    }
}

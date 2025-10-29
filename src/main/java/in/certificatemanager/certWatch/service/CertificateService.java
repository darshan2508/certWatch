package in.certificatemanager.certWatch.service;

import in.certificatemanager.certWatch.dto.CertificateDTO;
import in.certificatemanager.certWatch.dto.DetailsDTO;
import in.certificatemanager.certWatch.entity.CategoryEntity;
import in.certificatemanager.certWatch.entity.CertificateEntity;
import in.certificatemanager.certWatch.entity.ProfileEntity;
import in.certificatemanager.certWatch.repository.CategoryRepository;
import in.certificatemanager.certWatch.repository.CertificateRepository;
import in.certificatemanager.certWatch.util.CertParsingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class CertificateService {

    private final CertificateRepository certificateRepository;
    private final CategoryRepository categoryRepository;
    private final ProfileService profileService;

    public DetailsDTO processCertificateFile(MultipartFile file){
        try{
            String fileContent = StreamUtils.copyToString(file.getInputStream(), StandardCharsets.UTF_8);
            System.out.println(fileContent);
            if(fileContent.trim().startsWith("-----BEGIN CERTIFICATE-----")){
                DetailsDTO certDetails = CertParsingUtil.parseCertificate(fileContent);
                return certDetails;
            }
            System.err.println("****Invalid certificate format****");
            return null;
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    // Adds a new certificate to the database
    public CertificateDTO addCertificate(CertificateDTO certDto){
        ProfileEntity profile = profileService.getCurrentProfile();
        CategoryEntity category = categoryRepository.findById(certDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found."));
        CertificateEntity newCert = toEntity(certDto, profile, category);
        newCert = certificateRepository.save(newCert);
        return toDTO(newCert);
    }

    // helper methods
    private CertificateEntity toEntity(CertificateDTO dto, ProfileEntity profile, CategoryEntity category){
        return CertificateEntity.builder()
                .subject(dto.getSubject())
                .validFrom(dto.getValidFrom())
                .validTo(dto.getValidTo())
                .version(dto.getVersion())
                .serialNumber(dto.getSerialNumber())
                .issuer(dto.getIssuer())
                .profile(profile)
                .category(category)
                .build();
    }

    private CertificateDTO toDTO(CertificateEntity entity){
        return CertificateDTO.builder()
                .id(entity.getId())
                .categoryId(entity.getCategory() != null ? entity.getCategory().getId() : null)
                .categoryName(entity.getCategory() != null ? entity.getCategory().getName() : "N/A")
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .subject(entity.getSubject())
                .validFrom(entity.getValidFrom())
                .validTo(entity.getValidTo())
                .version(entity.getVersion())
                .serialNumber(entity.getSerialNumber())
                .issuer(entity.getIssuer())
                .build();

    }
}

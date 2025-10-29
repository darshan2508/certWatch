package in.certificatemanager.certWatch.repository;

import in.certificatemanager.certWatch.entity.CertificateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateRepository extends JpaRepository<CertificateEntity, Long> {

    List<CertificateEntity> findByProfileIdOrderByDateDesc(Long profileId);

    List<CertificateEntity> findTop5ByProfileIdOrderByDateDesc(Long profileID);

}

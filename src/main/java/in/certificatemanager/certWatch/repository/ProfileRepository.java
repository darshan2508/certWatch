package in.certificatemanager.certWatch.repository;

import in.certificatemanager.certWatch.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

    // SELECT * FROM TBL_PROFILES WHERE EMAIL = ?
    // We have used Optional because we may find a user, or we may not,so it would handle the null pointer exception
    Optional<ProfileEntity> findByEmail(String email);

    // SELECT * FROM TBL_PROFILES WHERE ACTIVATION_TOKEN = ?
    Optional<ProfileEntity> findByActivationToken(String activationToken);

}

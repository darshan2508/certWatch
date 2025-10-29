package in.certificatemanager.certWatch.repository;

import in.certificatemanager.certWatch.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    // select * from tbl_categories where profile_id = ?1
    List<CategoryEntity> findByProfileId(Long profileId);

    // select * from tbl_categories where id =?1 and profile_id = ?2
    Optional<CategoryEntity> findByIdAndProfileId(Long id, Long profileId);

//    // select * from tbl_categories where type =?1 and profile_id = ?2
//    List<CategoryEntity> findByTypeAndProfileId(String type, Long profileId);

    Boolean existsByNameAndProfileId(String name, Long profileId);
    // 1 person cannot create multiple categories with the same name, hence this will check if category already exists.
}
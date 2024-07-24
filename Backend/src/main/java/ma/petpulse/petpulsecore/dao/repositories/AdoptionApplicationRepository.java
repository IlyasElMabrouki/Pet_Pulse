package ma.petpulse.petpulsecore.dao.repositories;

import ma.petpulse.petpulsecore.dao.entities.AdoptionApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdoptionApplicationRepository extends JpaRepository<AdoptionApplication, Long>{

    List<AdoptionApplication> findByUserId(Long userId);
    List<AdoptionApplication> findByReportId(Long reportId);

}
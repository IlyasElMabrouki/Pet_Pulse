package ma.petpulse.petpulsecore.dao.repositories;

import ma.petpulse.petpulsecore.dao.entities.AdoptionApplication;
import ma.petpulse.petpulsecore.dao.entities.LostApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.*;
import java.util.List;

public interface LostApplicationRepository extends JpaRepository<LostApplication, Long> {
    List<LostApplication> findByUserId(Long userId);

    List<LostApplication> findByReportId(Long reportId);
}

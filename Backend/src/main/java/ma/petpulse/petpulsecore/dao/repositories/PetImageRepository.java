package ma.petpulse.petpulsecore.dao.repositories;

import ma.petpulse.petpulsecore.dao.entities.PetImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetImageRepository extends JpaRepository<PetImage, Long>{
    List<PetImage> findByPetId(Long petId);
}

package ma.petpulse.petpulsecore.dao.repositories;

import ma.petpulse.petpulsecore.dao.entities.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    Page<Pet> findPetsByOwner_IdAndNameContains(Long userId, String name, Pageable pageable);
}

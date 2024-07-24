package ma.petpulse.petpulsecore.service.services.interfaces;

import ma.petpulse.petpulsecore.dao.entities.Pet;
import ma.petpulse.petpulsecore.dao.entities.User;
import ma.petpulse.petpulsecore.enumerations.Specie;
import ma.petpulse.petpulsecore.service.dtos.PetDto;
import ma.petpulse.petpulsecore.service.dtos.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IPetService {
    PetDto savePet(PetDto pet, List<MultipartFile> images);

    PetDto updatePet(PetDto pet);

    void deletePet(Long id);

    List<PetDto> getAllPets();

    Page<PetDto> getPetsByUserId(Long userId, String name, Pageable pageable);

    PetDto getPetById(Long id);

}

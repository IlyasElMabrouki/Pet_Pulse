package ma.petpulse.petpulsecore.service.mappers;

import lombok.AllArgsConstructor;
import ma.petpulse.petpulsecore.dao.entities.Pet;
import ma.petpulse.petpulsecore.dao.repositories.PetImageRepository;
import ma.petpulse.petpulsecore.enumerations.Specie;
import ma.petpulse.petpulsecore.service.dtos.PetDto;
import ma.petpulse.petpulsecore.service.services.implementations.UserServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PetMapper {

    private UserServiceImpl userService;
    private PetImageRepository petImageRepository;

    public PetDto fromPet(Pet pet){
        PetDto petDTO = new PetDto();
        BeanUtils.copyProperties(pet,petDTO);
        petDTO.setOwnerId(pet.getOwner().getId());
        petDTO.setSpecie(pet.getSpecie().name());
        List<String> images = new ArrayList<>();
        petImageRepository.findByPetId(pet.getId()).forEach(
                petImage -> images.add(petImage.getUrl())
        );
        petDTO.setImages(images);
        return petDTO;
    }

    public Pet fromPetDto(PetDto petDto){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDto,pet);
        pet.setOwner(userService.getUserById(petDto.getOwnerId()));
        pet.setSpecie(Specie.valueOf(petDto.getSpecie()));
        return pet;
    }
}

package ma.petpulse.petpulsecore.service.services.implementations;

import lombok.AllArgsConstructor;
import ma.petpulse.petpulsecore.dao.entities.Pet;
import ma.petpulse.petpulsecore.dao.entities.PetImage;
import ma.petpulse.petpulsecore.dao.entities.User;
import ma.petpulse.petpulsecore.dao.repositories.PetImageRepository;
import ma.petpulse.petpulsecore.dao.repositories.PetRepository;
import ma.petpulse.petpulsecore.enumerations.Specie;
import ma.petpulse.petpulsecore.exceptions.PetNotFoundException;
import ma.petpulse.petpulsecore.exceptions.UserNotFoundException;
import ma.petpulse.petpulsecore.service.dtos.PetDto;
import ma.petpulse.petpulsecore.service.mappers.PetMapper;
import ma.petpulse.petpulsecore.service.services.interfaces.IPetService;
import ma.petpulse.petpulsecore.service.services.interfaces.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PetServiceImpl implements IPetService {
    private final PetRepository petRepository;
    private PetMapper petMapper;
    private final StorageService storageService;
    private final PetImageRepository petImageRepository;

    @Override
    public PetDto getPetById(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(()->new PetNotFoundException("Pet Not Found"));
        return petMapper.fromPet(pet);
    }

    @Override
    public PetDto savePet(PetDto petDto, List<MultipartFile> images) {
        Long ownerId = petDto.getOwnerId();
        if (ownerId == null) {
            throw new UserNotFoundException("Owner not found");
        }
        Pet pet = petMapper.fromPetDto(petDto);
        Pet savedPet = petRepository.save(pet);
        for (MultipartFile img : images) {
            String imgPath = storageService.uploadFile(img);
            PetImage petImage = new PetImage();
            petImage.setPet(savedPet);
            petImage.setUrl(imgPath);
            petImageRepository.save(petImage);
        }
        return petMapper.fromPet(savedPet);
    }

    @Override
    public PetDto updatePet(PetDto petDto) {
        Pet pet = petMapper.fromPetDto(petDto);
        Pet updatedPet = petRepository.save(pet);
        return petMapper.fromPet(updatedPet);
    }

    @Override
    public void deletePet(Long id) {
        this.getPetById(id);
        petRepository.deleteById(id);
    }

    @Override
    public List<PetDto> getAllPets() {
        List<Pet> pets = petRepository.findAll();
        return pets.stream().map(pet -> petMapper.fromPet(pet)).collect(Collectors.toList());
    }

    @Override
    public Page<PetDto> getPetsByUserId(Long userId, String name, Pageable pageable) {
        Page<Pet> pets = petRepository.findPetsByOwner_IdAndNameContains(userId, name, pageable);
        List<PetDto> petList = pets.stream().map(pet -> petMapper.fromPet(pet)).collect(Collectors.toList());
        return new PageImpl<>(petList, pageable, pets.getTotalElements());
    }

}

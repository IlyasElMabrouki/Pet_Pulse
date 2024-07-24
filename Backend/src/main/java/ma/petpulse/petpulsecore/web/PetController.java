package ma.petpulse.petpulsecore.web;

import lombok.RequiredArgsConstructor;
import ma.petpulse.petpulsecore.enumerations.Specie;
import ma.petpulse.petpulsecore.service.dtos.PetDto;
import ma.petpulse.petpulsecore.service.services.interfaces.IPetService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class PetController {
    private final IPetService petService;

    @GetMapping("/pets")
    public List<PetDto> getPets() {
        return petService.getAllPets();
    }

    @GetMapping("/pets/{id}")
    public PetDto getPetById(@PathVariable Long id) {
        return petService.getPetById(id);
    }

    @CrossOrigin("*")
    @GetMapping("/pets/user/{userId}")
    public Page<PetDto> getPetsByUserId(@PathVariable Long userId,
                                        @RequestParam(value = "name", defaultValue = "") String name,
                                        @RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "2") int size) {
        return petService.getPetsByUserId(userId, name, PageRequest.of(page, size));
    }

    @CrossOrigin("*")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE},
            value = "/pets/save")
    public PetDto addPet(
            @RequestParam("name") String name,
            @RequestParam("specie") String specie,
            @RequestParam("breed") String breed,
            @RequestParam("age") int age,
            @RequestParam("ownerId") Long ownerId,
            @RequestParam("image") List<MultipartFile> images) {
        PetDto petDto = new PetDto();
        petDto.setName(name);
        petDto.setSpecie(specie);
        petDto.setBreed(breed);
        petDto.setAge(age);
        petDto.setOwnerId(ownerId);
        // Save the pet entity
        return petService.savePet(petDto, images);
    }

    @PutMapping("/pets/{id}")
    public PetDto updatePet(@PathVariable Long id,
                            @RequestParam("name") String name,
                            @RequestParam("specie") String specie,
                            @RequestParam("breed") String breed,
                            @RequestParam("age") int age,
                            @RequestParam("ownerId") Long ownerId) {
        PetDto petDto = new PetDto();
        petDto.setId(id);
        petDto.setName(name);
        petDto.setSpecie(specie);
        petDto.setBreed(breed);
        petDto.setAge(age);
        petDto.setOwnerId(ownerId);
        return petService.updatePet(petDto);
    }

    @DeleteMapping("/pets/{id}")
    public void deletePet(@PathVariable Long id) {
        petService.deletePet(id);
    }
}

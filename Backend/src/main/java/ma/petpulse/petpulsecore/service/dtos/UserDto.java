package ma.petpulse.petpulsecore.service.dtos;

import lombok.Data;
import ma.petpulse.petpulsecore.dao.entities.Pet;
import ma.petpulse.petpulsecore.enumerations.Role;

import java.util.List;

@Data
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String city;
    private Role role;
    private List<Pet> pets;
}
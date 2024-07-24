package ma.petpulse.petpulsecore.service.services.interfaces;

import ma.petpulse.petpulsecore.dao.entities.Pet;
import ma.petpulse.petpulsecore.dao.entities.User;
import ma.petpulse.petpulsecore.service.dtos.ChangePasswordRequest;
import ma.petpulse.petpulsecore.service.dtos.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface IUserService extends UserDetailsService {

    User loadUserByUsername(String username) throws UsernameNotFoundException;

    UserDto addUser(User User);
    void deleteUserById(long id);
    User updateUser(User User);

    User getUserById(long id);
    List<UserDto> getAllUsers();

    UserDto getUserByEmail(String email);

    List<Pet> getAllPets(User user);

    void changePassword(ChangePasswordRequest request, User authUser);


}

package ma.petpulse.petpulsecore.service.services.implementations;

import lombok.RequiredArgsConstructor;
import ma.petpulse.petpulsecore.dao.entities.Pet;
import ma.petpulse.petpulsecore.dao.entities.User;
import ma.petpulse.petpulsecore.dao.repositories.UserRepository;
import ma.petpulse.petpulsecore.service.mappers.UserMapper;
import ma.petpulse.petpulsecore.service.dtos.UserDto;
import ma.petpulse.petpulsecore.service.dtos.ChangePasswordRequest;
import ma.petpulse.petpulsecore.service.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto addUser(User User) {
        // check if a user with the same email already exists
        if (userRepository.findByEmail(User.getEmail()).isPresent()) {
            throw new IllegalArgumentException("An error occurred during registration");
        }

        // save the user and map it to a userDto
        return userMapper.userToUserDto(userRepository.save(User));
    }

    @Override
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(User User) {
        return userRepository.save(User);
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userMapper.usersToUserDtos(userRepository.findAll());
    }


    @Override
    public UserDto getUserByEmail(String email) {
        return userMapper.userToUserDto(userRepository.findByEmail(email).orElse(null));
    }

    @Override
    public List<Pet> getAllPets(User user) {
        return user.getPets();
    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("reach for user with email: " + email);
        return userRepository.findByEmail(email)
                .orElse(null);
    }

    @Override
    public void changePassword(ChangePasswordRequest request, User authUser) {

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), authUser.getPassword()))
            throw new IllegalStateException("The current password is incorrect");

        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword()))
            throw new IllegalStateException("The new passwords do not match");

        // update the password
        authUser.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        userRepository.save(authUser);
    }
}
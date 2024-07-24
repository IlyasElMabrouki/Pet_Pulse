package ma.petpulse.petpulsecore.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.petpulse.petpulsecore.dao.entities.User;
import ma.petpulse.petpulsecore.exceptions.UserNotFoundException;
import ma.petpulse.petpulsecore.service.dtos.ChangePasswordRequest;
import ma.petpulse.petpulsecore.service.dtos.UserDto;
import ma.petpulse.petpulsecore.service.services.interfaces.IJwtService;
import ma.petpulse.petpulsecore.service.services.interfaces.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    private final IJwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping()
    @PreAuthorize("hasRole('PET_OWNER')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PET_OWNER')")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null)
            throw new UserNotFoundException("User with id " + id + " not found");

        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserDto> addUser(@Valid @RequestBody User user) {
        if(user.getRole() != null)
            user.setRole(null);

        return ResponseEntity.ok(userService.addUser(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        User authUser = jwtService.getAuthenticatedUser();
        if (authUser == null || !isAuthenticated(authUser, id)) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }

        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('PET_OWNER')")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        User authUser = jwtService.getAuthenticatedUser();

        if (authUser == null)
            throw new UserNotFoundException("User with id " + id + " not found");

        if(!isAuthenticated(authUser, id))
            throw new AccessDeniedException("Authenticated user does not have access to modify this user");

        user.setId(id);

        return ResponseEntity.ok(userService.updateUser(user));
    }

    // change the password of the authenticated user
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        User authUser = jwtService.getAuthenticatedUser();
        userService.changePassword(request, authUser);

        return ResponseEntity.ok("Password changed successfully");
    }

    private boolean isAuthenticated(User authUser, Long id) {
        return authUser.getId().equals(id);
    }
}
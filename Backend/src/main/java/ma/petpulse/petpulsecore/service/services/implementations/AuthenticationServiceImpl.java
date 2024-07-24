package ma.petpulse.petpulsecore.service.services.implementations;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.petpulse.petpulsecore.dao.entities.User;
import ma.petpulse.petpulsecore.enumerations.Role;
import ma.petpulse.petpulsecore.service.dtos.AuthenticationRequest;
import ma.petpulse.petpulsecore.service.dtos.AuthenticationResponse;
import ma.petpulse.petpulsecore.service.dtos.DTOUserCredentials;
import ma.petpulse.petpulsecore.service.dtos.RegisterRequest;
import ma.petpulse.petpulsecore.service.services.interfaces.IAuthenticationService;
import ma.petpulse.petpulsecore.service.services.interfaces.IJwtService;
import ma.petpulse.petpulsecore.service.services.interfaces.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final IUserService userService;
    private final IJwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws BadCredentialsException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userService.loadUserByUsername(request.getEmail());

        String jwtToken = jwtService.generateAccessToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_PET_OWNER);

        userService.addUser(user);

        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    @Override
    public AuthenticationResponse updateCredentials(DTOUserCredentials userCredentials) {
        User authUser = jwtService.getAuthenticatedUser();
        if (authUser != null) {
            User user = userService.loadUserByUsername(jwtService.getAuthenticatedUser().getUsername());

            boolean passwordsMatchers = (new BCryptPasswordEncoder()).matches(userCredentials.getOldPassword(), user.getPassword());
            if (!passwordsMatchers)
                throw new BadCredentialsException("Unauthorized");

            user.setEmail(userCredentials.getEmail());
            user.setPassword(userCredentials.getPassword());

            String accessToken = jwtService.generateAccessToken(
                    userService.updateUser(user)
            );
            return new AuthenticationResponse(accessToken);
        }
        return null;
    }
}

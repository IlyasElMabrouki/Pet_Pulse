package ma.petpulse.petpulsecore.service.services.interfaces;

import ma.petpulse.petpulsecore.service.dtos.AuthenticationRequest;
import ma.petpulse.petpulsecore.service.dtos.AuthenticationResponse;
import ma.petpulse.petpulsecore.service.dtos.DTOUserCredentials;
import ma.petpulse.petpulsecore.service.dtos.RegisterRequest;

public interface IAuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);


    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse updateCredentials(DTOUserCredentials userCredentials);
}

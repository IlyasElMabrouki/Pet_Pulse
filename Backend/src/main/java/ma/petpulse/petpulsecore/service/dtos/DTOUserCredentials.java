package ma.petpulse.petpulsecore.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOUserCredentials {
    private String email;
    private String oldPassword;
    private String password;
}
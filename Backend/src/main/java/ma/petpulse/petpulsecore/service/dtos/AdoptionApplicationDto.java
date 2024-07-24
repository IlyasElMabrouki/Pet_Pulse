package ma.petpulse.petpulsecore.service.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdoptionApplicationDto {

    private Long id;

    // for the user id it will be the authenticated user
    private Long userId;

    @NotNull(message = "Report id is required")
    private Long reportId;

    @NotBlank(message = "Reason is required")
    private String reason;

    @NotBlank(message = "Pet experience description is required")
    private String petExperience;

    @NotNull(message = "Number of pets is required")
    private int numberOfPets;
}
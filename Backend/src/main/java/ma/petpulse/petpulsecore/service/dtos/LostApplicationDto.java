package ma.petpulse.petpulsecore.service.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LostApplicationDto {
    private Long id;
    private Long userId;
    private Long reportId;
    String sightingLocation;
    String contactInfo;
    String message;
    String proofImage;
}

package ma.petpulse.petpulsecore.service.dtos;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import ma.petpulse.petpulsecore.enumerations.Status;
import ma.petpulse.petpulsecore.enumerations.Type;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class ReportDto {
    private Long id;

    private String title;
    private String description;
    private double latitude;
    private double longitude;
    private String city;
    private String address;
    private Type type;

    private Status status;

    private String additionalNotes;

    private Date createdAt;

    private Date updatedAt;
    private boolean verified;
    private Long pet_id;
    private Long user_id;
}

package ma.petpulse.petpulsecore.dao.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "lost_applications")
@Data
@EqualsAndHashCode(callSuper = true)
public class LostApplication extends Application {

    String sightingLocation;
    String contactInfo;
    String message;
    String proofImage;
}
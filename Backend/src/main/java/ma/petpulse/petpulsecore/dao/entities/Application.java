package ma.petpulse.petpulsecore.dao.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "applications")
@Data
public abstract class Application {
    // it's not possible to use Identity strategy with InheritanceType.TABLE_PER_CLASS
    // because of <union-subclass> mapping in hibernate
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "report_id")
    private Report report;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
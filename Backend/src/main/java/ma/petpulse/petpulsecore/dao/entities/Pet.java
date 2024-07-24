package ma.petpulse.petpulsecore.dao.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.petpulse.petpulsecore.enumerations.Specie;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pets")
@Data @AllArgsConstructor @NoArgsConstructor
public class Pet {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    //The species to which the pet belongs (e.g., dog, cat, bird, etc.).
    @Enumerated(EnumType.STRING)
    private Specie specie;

    @NotEmpty
    @Size(max = 100, message = "Breed cannot exceed 100 characters")
    private String breed;

    @Min(value = 0, message = "Age must be greater than or equal to 0")
    private int age;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    private List<PetImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Report> reports = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;
}

package ma.petpulse.petpulsecore.service.services.implementations;

import jakarta.persistence.criteria.*;
import ma.petpulse.petpulsecore.dao.entities.Pet;
import ma.petpulse.petpulsecore.dao.entities.Report;
import ma.petpulse.petpulsecore.enumerations.Specie;
import ma.petpulse.petpulsecore.enumerations.Type;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class ReportSpecification implements Specification<Report> {

    private final String city;
    private final String petBreed;
    private final Integer petAgeStart;
    private final Integer petAgeEnd;
    private final Type type;
    private final Specie petSpecie;

    public ReportSpecification(String city, String petBreed, Integer petAgeStart, Integer petAgeEnd, Type type, Specie petSpecie) {
        this.city = city;
        this.petBreed = petBreed;
        this.petAgeStart = petAgeStart;
        this.petAgeEnd = petAgeEnd;
        this.type = type;
        this.petSpecie = petSpecie;
    }

    @Override
    public Predicate toPredicate(Root<Report> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (!city.equalsIgnoreCase("undefined") && !city.isEmpty()) {
            // contains
            predicates.add(criteriaBuilder.like(root.get("city"), "%" + city + "%"));
        }
        if (!petBreed.equalsIgnoreCase("undefined") && !petBreed.isEmpty()) {
            Join<Report, Pet> petJoin = root.join("pet");
            predicates.add(criteriaBuilder.like(petJoin.get("breed"), "%" + petBreed + "%"));
        }
        if (petAgeStart != null && petAgeEnd != null) {
            Join<Report, Pet> petJoin = root.join("pet");
            predicates.add(criteriaBuilder.between(petJoin.get("age"), petAgeStart, petAgeEnd));
        }
        if (type != null) {
            predicates.add(criteriaBuilder.equal(root.get("type"), type));
        }
        if (petSpecie != null) {
            Join<Report, Pet> petJoin = root.join("pet");
            predicates.add(criteriaBuilder.equal(petJoin.get("specie"), petSpecie));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
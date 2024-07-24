package ma.petpulse.petpulsecore.service.mappers;

import ma.petpulse.petpulsecore.dao.entities.AdoptionApplication;
import ma.petpulse.petpulsecore.service.dtos.AdoptionApplicationDto;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface AdoptionMapper {

    AdoptionApplication adoptionDtoToAdoptionApplication(AdoptionApplicationDto adoptionApplicationDto);
    AdoptionApplicationDto adoptionApplicationToAdoptionDto(AdoptionApplication adoptionApplication);

}

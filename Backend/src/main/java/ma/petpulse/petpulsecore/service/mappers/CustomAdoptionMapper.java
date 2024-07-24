package ma.petpulse.petpulsecore.service.mappers;

import lombok.RequiredArgsConstructor;
import ma.petpulse.petpulsecore.dao.entities.AdoptionApplication;
import ma.petpulse.petpulsecore.dao.repositories.ReportRepository;
import ma.petpulse.petpulsecore.exceptions.ReportNotFoundException;
import ma.petpulse.petpulsecore.service.dtos.AdoptionApplicationDto;
import ma.petpulse.petpulsecore.service.services.interfaces.IJwtService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomAdoptionMapper {
    private final AdoptionMapper adoptionMapper;
    private final ReportRepository reportRepository;
    private final IJwtService jwtService;

    public AdoptionApplication adoptionDtoToAdoption(AdoptionApplicationDto adoptionApplicationDto){
        AdoptionApplication application = adoptionMapper.adoptionDtoToAdoptionApplication(adoptionApplicationDto);
        // get report by id
        application.setReport(reportRepository.findById(adoptionApplicationDto.getReportId()).orElseThrow(
                () -> new ReportNotFoundException("Report with id " + adoptionApplicationDto.getReportId() + " not found")
        ));
        // get auth user
        application.setUser(jwtService.getAuthenticatedUser());

        return application;
    }

    public AdoptionApplicationDto adoptionToAdoptionDto(AdoptionApplication adoptionApplication){
        AdoptionApplicationDto applicationDto = adoptionMapper.adoptionApplicationToAdoptionDto(adoptionApplication);
        applicationDto.setReportId(adoptionApplication.getReport().getId());
        applicationDto.setUserId(adoptionApplication.getUser().getId());
        return applicationDto;
    }

    public List<AdoptionApplicationDto> adoptionsToAdoptionDtos(List<AdoptionApplication> adoptionApplications){
        // use the existing mappers
        return adoptionApplications.stream().map(this::adoptionToAdoptionDto).collect(Collectors.toList());
    }

    public List<AdoptionApplication> adoptionDtosToAdoptions(List<AdoptionApplicationDto> adoptionApplicationDtos){
        // use the existing mappers
        return adoptionApplicationDtos.stream().map(this::adoptionDtoToAdoption).collect(Collectors.toList());
    }


}

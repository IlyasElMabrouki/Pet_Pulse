package ma.petpulse.petpulsecore.service.services.implementations;

import lombok.RequiredArgsConstructor;
import ma.petpulse.petpulsecore.dao.entities.AdoptionApplication;
import ma.petpulse.petpulsecore.dao.repositories.AdoptionApplicationRepository;
import ma.petpulse.petpulsecore.dao.repositories.ReportRepository;
import ma.petpulse.petpulsecore.exceptions.ApplicationNotFoundException;
import ma.petpulse.petpulsecore.exceptions.ReportNotFoundException;
import ma.petpulse.petpulsecore.exceptions.RequestBodyNotValid;
import ma.petpulse.petpulsecore.service.dtos.AdoptionApplicationDto;
import ma.petpulse.petpulsecore.service.mappers.CustomAdoptionMapper;
import ma.petpulse.petpulsecore.service.services.interfaces.IAdoptionApplicationService;
import ma.petpulse.petpulsecore.service.services.interfaces.IJwtService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdoptionApplicationService implements IAdoptionApplicationService {
    private final AdoptionApplicationRepository adoptionApplicationRepository;
    private final ReportRepository reportRepository;
    private final CustomAdoptionMapper adoptionMapper;
    private final IJwtService jwtService;

    @Override
    public AdoptionApplicationDto createApplication(AdoptionApplicationDto applicationDto) {

        AdoptionApplication application = adoptionMapper.adoptionDtoToAdoption(applicationDto);

        return adoptionMapper.adoptionToAdoptionDto(adoptionApplicationRepository.save(application));
    }

    @Override
    public AdoptionApplicationDto getApplicationById(Long id) {
        AdoptionApplication application = adoptionApplicationRepository.findById(id).orElseThrow(
                () -> new ApplicationNotFoundException("Application with id " + id + " not found")
        );

        return adoptionMapper.adoptionToAdoptionDto(application);
    }

    @Override
    public AdoptionApplicationDto updateApplication(AdoptionApplicationDto applicationDto) {
        AdoptionApplication existingApplication = adoptionApplicationRepository.findById(applicationDto.getId()).orElseThrow(
                () -> new ApplicationNotFoundException("Application with id " + applicationDto.getId() + " not found")
        );

        if (!jwtService.getAuthenticatedUser().getId().equals(existingApplication.getUser().getId()))
            throw new AccessDeniedException("Authenticated user does not have access to update this application");

        reportRepository.findById(applicationDto.getReportId()).orElseThrow(
                () -> new ReportNotFoundException("Report with id " + applicationDto.getId() + " not found")
        );

        // check if the report has the application associated with it
        List<AdoptionApplication> applications = adoptionApplicationRepository.findByReportId(applicationDto.getReportId());

        boolean hasApplication = applications.stream()
                .anyMatch(application -> application.getId().equals(existingApplication.getId()));

        if (!hasApplication)
            throw new RequestBodyNotValid("The report with id " + applicationDto.getReportId() + " does not have the application with id " + existingApplication.getId() + " associated with it");

        AdoptionApplication application = adoptionMapper.adoptionDtoToAdoption(applicationDto);
        adoptionApplicationRepository.save(application);

        return adoptionMapper.adoptionToAdoptionDto(application);
    }

    @Override
    public void deleteApplication(Long id) {
        AdoptionApplicationDto applicationDto = getApplicationById(id);

        if (!jwtService.getAuthenticatedUser().getId().equals(applicationDto.getUserId()))
            throw new AccessDeniedException("Authenticated user does not have access to delete this application");

        adoptionApplicationRepository.deleteById(id);
    }

    @Override
    public List<AdoptionApplicationDto> getAllApplications () {
        return adoptionMapper.adoptionsToAdoptionDtos(adoptionApplicationRepository.findAll());
    }

    @Override
    public List<AdoptionApplicationDto> getApplicationsByApplicant (Long userId){
        return adoptionMapper.adoptionsToAdoptionDtos(adoptionApplicationRepository.findByUserId(userId));
    }
}
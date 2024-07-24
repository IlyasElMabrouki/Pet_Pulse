package ma.petpulse.petpulsecore.service.services.interfaces;


import ma.petpulse.petpulsecore.service.dtos.AdoptionApplicationDto;

import java.util.List;

public interface IAdoptionApplicationService {
    AdoptionApplicationDto createApplication(AdoptionApplicationDto application);
    AdoptionApplicationDto getApplicationById(Long id);
    AdoptionApplicationDto updateApplication(AdoptionApplicationDto application);
    void deleteApplication(Long id);
    List<AdoptionApplicationDto> getAllApplications();
    List<AdoptionApplicationDto> getApplicationsByApplicant(Long userId);
}
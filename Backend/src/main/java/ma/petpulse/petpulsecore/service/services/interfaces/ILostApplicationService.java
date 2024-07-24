package ma.petpulse.petpulsecore.service.services.interfaces;

import ma.petpulse.petpulsecore.service.dtos.AdoptionApplicationDto;
import ma.petpulse.petpulsecore.service.dtos.LostApplicationDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ILostApplicationService {

    LostApplicationDto createApplication(LostApplicationDto application, MultipartFile image);

    LostApplicationDto getApplicationById(Long id);

    LostApplicationDto updateApplication(LostApplicationDto application);

    void deleteApplication(Long id);

    List<LostApplicationDto> getAllApplications();

    List<LostApplicationDto> getApplicationsByApplicant(Long userId);
}

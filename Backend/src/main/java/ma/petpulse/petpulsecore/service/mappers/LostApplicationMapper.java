package ma.petpulse.petpulsecore.service.mappers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.petpulse.petpulsecore.dao.entities.Application;
import ma.petpulse.petpulsecore.dao.entities.LostApplication;
import ma.petpulse.petpulsecore.dao.repositories.ReportRepository;
import ma.petpulse.petpulsecore.dao.repositories.UserRepository;
import ma.petpulse.petpulsecore.exceptions.ReportNotFoundException;
import ma.petpulse.petpulsecore.service.dtos.AdoptionApplicationDto;
import ma.petpulse.petpulsecore.service.dtos.LostApplicationDto;
import ma.petpulse.petpulsecore.service.services.interfaces.IJwtService;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Data
@AllArgsConstructor
@Service
public class LostApplicationMapper {
    private final AdoptionMapper adoptionMapper;
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final IJwtService jwtService;

    public LostApplication fromLostApplicationDto(LostApplicationDto lostApplicationDto) {
        LostApplication application = new LostApplication();
        application.setUser(jwtService.getAuthenticatedUser());
        System.out.println(jwtService.getAuthenticatedUser());
        application.setReport(reportRepository.findById(lostApplicationDto.getReportId()).orElseThrow(
                () -> new ReportNotFoundException("Report with id " + lostApplicationDto.getReportId() + " not found")
        ));
        application.setId(lostApplicationDto.getId());
        application.setUser(userRepository.findById(lostApplicationDto.getUserId()).orElse(null));
        application.setMessage(lostApplicationDto.getMessage());
        application.setContactInfo(lostApplicationDto.getContactInfo());
        application.setProofImage(lostApplicationDto.getProofImage());
        application.setSightingLocation(lostApplicationDto.getSightingLocation());
        return application;
    }

    public LostApplicationDto fromLostApplication(LostApplication lostApplication) {
        LostApplicationDto applicationDto = new LostApplicationDto();
        applicationDto.setId(lostApplication.getId());
        applicationDto.setReportId(lostApplication.getReport().getId());
        applicationDto.setUserId(lostApplication.getUser().getId());
        applicationDto.setMessage(lostApplication.getMessage());
        applicationDto.setContactInfo(lostApplication.getContactInfo());
        applicationDto.setSightingLocation(lostApplication.getSightingLocation());
        applicationDto.setProofImage(lostApplication.getProofImage());
        return applicationDto;
    }
}

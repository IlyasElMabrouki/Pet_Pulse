package ma.petpulse.petpulsecore.service.services.implementations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.petpulse.petpulsecore.dao.entities.LostApplication;
import ma.petpulse.petpulsecore.dao.entities.PetImage;
import ma.petpulse.petpulsecore.dao.repositories.LostApplicationRepository;
import ma.petpulse.petpulsecore.dao.repositories.ReportRepository;
import ma.petpulse.petpulsecore.exceptions.ApplicationNotFoundException;
import ma.petpulse.petpulsecore.service.dtos.LostApplicationDto;

import ma.petpulse.petpulsecore.service.mappers.LostApplicationMapper;
import ma.petpulse.petpulsecore.service.services.interfaces.IJwtService;
import ma.petpulse.petpulsecore.service.services.interfaces.ILostApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
@AllArgsConstructor

public class LostApplicationService implements ILostApplicationService {
    private final LostApplicationRepository lostApplicationRepository;
    private final ReportRepository reportRepository;
    private final LostApplicationMapper lostApplicationMapper;
    private final IJwtService jwtService;
    private final StorageService storageService;

    @Override
    public LostApplicationDto createApplication(LostApplicationDto application, MultipartFile proofimage) {
        LostApplication lostApplication = lostApplicationMapper.fromLostApplicationDto(application);
        String imgPath = storageService.uploadFile(proofimage);
        lostApplication.setProofImage(imgPath);
        return lostApplicationMapper.fromLostApplication(lostApplicationRepository.save(lostApplication));
    }

    @Override
    public LostApplicationDto getApplicationById(Long id) {
        LostApplication application = lostApplicationRepository.findById(id).orElseThrow(() -> new ApplicationNotFoundException("Application ID: " + id + " Not Found"));
        return lostApplicationMapper.fromLostApplication(lostApplicationRepository.save(application));
    }

    @Override
    public LostApplicationDto updateApplication(LostApplicationDto application) {
        return null;
    }

    @Override
    public void deleteApplication(Long id) {
        lostApplicationRepository.deleteById(id);
    }

    @Override
    public List<LostApplicationDto> getAllApplications() {
        List<LostApplication> applications = lostApplicationRepository.findAll();
        List<LostApplicationDto> applicationDtos = new ArrayList<>();
        for (LostApplication application : applications) {
            applicationDtos.add(lostApplicationMapper.fromLostApplication(application));
        }
        return applicationDtos;
    }

    public List<LostApplicationDto> getApplicationsByReportId(Long reportId) {
        List<LostApplication> applications = lostApplicationRepository.findByReportId(reportId);
        List<LostApplicationDto> applicationDtos = new ArrayList<>();
        for (LostApplication application : applications) {
            applicationDtos.add(lostApplicationMapper.fromLostApplication(application));
        }
        return applicationDtos;
    }

    @Override
    public List<LostApplicationDto> getApplicationsByApplicant(Long userId) {
        return null;
    }
}

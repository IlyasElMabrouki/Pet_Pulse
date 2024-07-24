package ma.petpulse.petpulsecore.service.services.implementations;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.petpulse.petpulsecore.dao.entities.Pet;
import ma.petpulse.petpulsecore.dao.entities.Report;
import ma.petpulse.petpulsecore.dao.entities.User;
import ma.petpulse.petpulsecore.dao.repositories.ReportRepository;
import ma.petpulse.petpulsecore.enumerations.Specie;
import ma.petpulse.petpulsecore.enumerations.Status;
import ma.petpulse.petpulsecore.enumerations.Type;
import ma.petpulse.petpulsecore.exceptions.PetNotFoundException;
import ma.petpulse.petpulsecore.exceptions.ReportNotFoundException;
import ma.petpulse.petpulsecore.exceptions.UserNotFoundException;
import ma.petpulse.petpulsecore.service.dtos.ReportDto;
import ma.petpulse.petpulsecore.service.mappers.PetMapper;
import ma.petpulse.petpulsecore.service.mappers.ReportMapper;
import ma.petpulse.petpulsecore.service.services.interfaces.IReportService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class ReportServiceImpl implements IReportService {
    public ReportRepository reportRepository;
    public UserServiceImpl userService;
    public PetServiceImpl petService;
    public ReportMapper reportMapper;
    private PetMapper petMapper;

    @Override
    public ReportDto saveReport(ReportDto reportDto) throws UserNotFoundException, PetNotFoundException {
        log.info("saving new report");
        Pet pet = petMapper.fromPetDto(petService.getPetById(reportDto.getPet_id()));
        User user = userService.getUserById(reportDto.getUser_id());
        if (user == null)
            throw new UserNotFoundException("User with id " + reportDto.getUser_id() + " not found");
        if (pet == null)
            throw new PetNotFoundException("Pet with id " + reportDto.getPet_id() + " not found");
        Report report = reportMapper.fromReportDto(reportDto, pet, user);
        Report savedReport = reportRepository.save(report);
        return reportMapper.fromReport(savedReport);
    }

    @Override
    public ReportDto updateReport(ReportDto reportDto) {
        Pet pet = petMapper.fromPetDto(petService.getPetById(reportDto.getPet_id()));
        User user = userService.getUserById(reportDto.getUser_id());
        if (user == null)
            throw new UserNotFoundException("User with id " + reportDto.getUser_id() + " not found");
        if (pet == null)
            throw new PetNotFoundException("Pet with id " + reportDto.getPet_id() + " not found");
        Report report = reportMapper.fromReportDto(reportDto, pet, user);
        Report reportSaved = reportRepository.save(report);
        return reportMapper.fromReport(reportSaved);
    }

    @Override
    public void deleteReport(Long reportId) {
        Report report = reportRepository.findById(reportId).orElse(null);
        if (report == null)
            throw new ReportNotFoundException("Report with id" + reportId + " not found");
        reportRepository
                .deleteById(reportId);
    }

    @Override
    public ReportDto getReportById(Long reportId) {
        return reportMapper.fromReport(reportRepository.findById(reportId).orElseThrow(() -> new ReportNotFoundException("Report with id " + reportId + " not found")));
    }

    @Override
    public List<ReportDto> getAllReports() {
        List<Report> reports = reportRepository.findAll();
        List<ReportDto> reportDtos = new ArrayList<>();
        reports.forEach(
                report -> {
                    ReportDto reportDto = reportMapper.fromReport(report);
                    reportDtos.add(reportDto);
                }
        );
        return reportDtos;
    }

    @Override
    public List<ReportDto> getReportsByUserId(Long userId) {
        User user = userService.getUserById(userId);
        if (user == null)
            throw new UserNotFoundException("User with id " + userId + " not found");
        List<Report> reports = reportRepository.findByUserId(userId);
        List<ReportDto> reportDtos = new ArrayList<>();
        reports.forEach(
                report -> {
                    ReportDto reportDto = reportMapper.fromReport(report);
                    reportDtos.add(reportDto);
                }
        );
        return reportDtos;
    }

    @Override
    public List<ReportDto> getReportsByType(Type type) {
        List<Report> reports = reportRepository.findReportByType(type);
        List<ReportDto> reportDtos = new ArrayList<>();
        reports.forEach(
                report -> {
                    ReportDto reportDto = reportMapper.fromReport(report);
                    reportDtos.add(reportDto);
                }
        );
        return reportDtos;
    }

    @Override
    public List<ReportDto> getReportsByCity(String city) {
        List<Report> reports = reportRepository.findByCity(city);
        List<ReportDto> reportDtos = new ArrayList<>();
        reports.forEach(
                report -> {
                    ReportDto reportDto = reportMapper.fromReport(report);
                    reportDtos.add(reportDto);
                }
        );
        return reportDtos;
    }

    @Override
    public Page<ReportDto> getReportsByFilters(Type type, String city, Status status, LocalDate startDate, LocalDate endDate, Boolean verified, Long petId, Long userId, Pageable pageable) {
        Page<Report> reports = reportRepository.findReportsByFilters(type, city, status, startDate, endDate, verified, petId, userId, pageable);
        List<ReportDto> reportDtos = new ArrayList<>();
        reports.forEach(
                report -> {
                    ReportDto reportDto = reportMapper.fromReport(report);
                    reportDtos.add(reportDto);
                }
        );
        return new PageImpl<>(reportDtos, pageable, reports.getTotalElements());
    }

    @Override
    public Page<Report> getAdoptReportsByFilters(String city, Type type, String petBreed, int petAgeStart, int petAgeEnd, Specie petSpecie, Pageable pageable) {
        ReportSpecification spec = new ReportSpecification(city, petBreed, petAgeStart, petAgeEnd, type, petSpecie);

        return reportRepository.findAll(spec, pageable);
    }


}

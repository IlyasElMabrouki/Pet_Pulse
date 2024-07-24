package ma.petpulse.petpulsecore.service.services.interfaces;

import ma.petpulse.petpulsecore.dao.entities.Report;
import ma.petpulse.petpulsecore.enumerations.Specie;
import ma.petpulse.petpulsecore.enumerations.Status;
import ma.petpulse.petpulsecore.enumerations.Type;
import ma.petpulse.petpulsecore.service.dtos.ReportDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface IReportService {
    ReportDto saveReport(ReportDto reportDto);

    ReportDto updateReport(ReportDto report);

    void deleteReport(Long reportId);

    ReportDto getReportById(Long reportId);

    List<ReportDto> getAllReports();

    List<ReportDto> getReportsByUserId(Long userId);

    List<ReportDto> getReportsByType(Type type);

    List<ReportDto> getReportsByCity(String city);

    Page<ReportDto> getReportsByFilters(Type type, String city, Status status, LocalDate startDate, LocalDate endDate, Boolean verified, Long petId, Long userId, Pageable pageable);

    Page<Report> getAdoptReportsByFilters(String city, Type type, String petBreed, int petAgeStart, int petAgeEnd, Specie petSpecie, Pageable pageable);
}

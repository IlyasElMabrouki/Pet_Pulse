package ma.petpulse.petpulsecore.service.mappers;

import com.fasterxml.jackson.databind.util.BeanUtil;
import lombok.AllArgsConstructor;
import ma.petpulse.petpulsecore.dao.entities.Pet;
import ma.petpulse.petpulsecore.dao.entities.Report;
import ma.petpulse.petpulsecore.dao.entities.User;
import ma.petpulse.petpulsecore.service.dtos.ReportDto;
import ma.petpulse.petpulsecore.service.services.implementations.PetServiceImpl;
import ma.petpulse.petpulsecore.service.services.implementations.ReportServiceImpl;
import ma.petpulse.petpulsecore.service.services.implementations.UserServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReportMapper {
    UserServiceImpl userService;
    PetServiceImpl petService;

    public ReportDto fromReport(Report report){
        ReportDto dto = new ReportDto();
        dto.setId(report.getId());
        dto.setTitle(report.getTitle());
        dto.setDescription(report.getDescription());
        dto.setLatitude(report.getLatitude());
        dto.setLongitude(report.getLongitude());
        dto.setCity(report.getCity());
        dto.setAddress(report.getAddress());
        dto.setType(report.getType());
        dto.setStatus(report.getStatus());
        dto.setAdditionalNotes(report.getAdditionalNotes());
        dto.setCreatedAt(report.getCreatedAt());
        dto.setUpdatedAt(report.getUpdatedAt());
        dto.setVerified(report.isVerified());
        dto.setPet_id(report.getPet().getId());
        dto.setUser_id(report.getUser().getId());
        return dto;
    }
    public Report fromReportDto(ReportDto reportDto,Pet pet,User user){
        Report report=new Report();
        report.setId(reportDto.getId());
        report.setTitle(reportDto.getTitle());
        report.setDescription(reportDto.getDescription());
        report.setLatitude(reportDto.getLatitude());
        report.setLongitude(reportDto.getLongitude());
        report.setCity(reportDto.getCity());
        report.setAddress(reportDto.getAddress());
        report.setType(reportDto.getType());
        report.setStatus(reportDto.getStatus());
        report.setAdditionalNotes(reportDto.getAdditionalNotes());
        report.setCreatedAt(reportDto.getCreatedAt());
        report.setUpdatedAt(reportDto.getUpdatedAt());
        report.setVerified(reportDto.isVerified());
        Long userId=reportDto.getUser_id();
        Long petId=reportDto.getPet_id();
        report.setPet(pet);
        report.setUser(user);
        return report;
    }
}

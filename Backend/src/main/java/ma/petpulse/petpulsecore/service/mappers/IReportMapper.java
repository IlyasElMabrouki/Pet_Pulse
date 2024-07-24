package ma.petpulse.petpulsecore.service.mappers;

import ma.petpulse.petpulsecore.dao.entities.Report;
import ma.petpulse.petpulsecore.service.dtos.ReportDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IReportMapper {
     ReportDto reportToReportDto(Report report);
     Report reportDtoToReport(ReportDto reportDto);
     List<ReportDto> reportsToReportDtos(List<Report> reports);
}

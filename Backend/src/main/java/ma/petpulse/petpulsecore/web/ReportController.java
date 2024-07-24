package ma.petpulse.petpulsecore.web;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import ma.petpulse.petpulsecore.dao.entities.Pet;
import ma.petpulse.petpulsecore.dao.entities.Report;
import ma.petpulse.petpulsecore.dao.repositories.ReportRepository;
import ma.petpulse.petpulsecore.enumerations.Specie;
import ma.petpulse.petpulsecore.enumerations.Status;
import ma.petpulse.petpulsecore.enumerations.Type;
import ma.petpulse.petpulsecore.exceptions.InvalidInputException;
import ma.petpulse.petpulsecore.exceptions.PetNotFoundException;
import ma.petpulse.petpulsecore.exceptions.ReportNotFoundException;
import ma.petpulse.petpulsecore.exceptions.UserNotFoundException;
import ma.petpulse.petpulsecore.service.dtos.PetDto;
import ma.petpulse.petpulsecore.service.dtos.ReportDto;
import ma.petpulse.petpulsecore.service.mappers.ReportMapper;
import ma.petpulse.petpulsecore.service.services.implementations.ReportServiceImpl;
import ma.petpulse.petpulsecore.validators.ReportValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.*;

@RestController
@AllArgsConstructor

public class ReportController {
    private ReportServiceImpl reportService;
    private ReportRepository reportRepository;
    private final ReportValidator reportValidator;
    public ReportMapper reportMapper;

    @GetMapping("/reports")
    public List<ReportDto> getReports() {
        return reportService.getAllReports();
    }

    @GetMapping("/reportsFiltred")
    public Page<ReportDto> getReports(
            @RequestParam(value = "type", required = false) Type type,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "status", required = false) Status status,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat LocalDate endDate,
            @RequestParam(value = "verified", required = false) Boolean verified,
            @RequestParam(value = "petId", required = false) Long petId,
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ReportDto> reports = reportService.getReportsByFilters(type, city, status, startDate, endDate, verified, petId, userId, pageable);
        return reports;
    }


    @GetMapping("/reports/{id}")
    public ReportDto getReportById(@PathVariable Long id) {
        return reportService.getReportById(id);
    }

    @GetMapping("/reports/image/{image}")
    public ReportDto getReportByImage(@PathVariable String image) {
        Page<Report> reportPage = reportRepository.findReportsByPetImageURLContaining(image, PageRequest.of(0, 1));
        if (reportPage.hasContent()) {
            return reportMapper.fromReport(reportPage.getContent().get(0));
        } else {
            return null;
        }
    }

    @DeleteMapping("/reports/{id}")
    public void deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
    }

    @GetMapping("/reports/user/{userId}")
    public List<ReportDto> getReportsByUserId(@PathVariable Long userId) {
        return reportService.getReportsByUserId(userId);
    }

    @PostMapping("/reports")
    public ResponseEntity<?> addReport(@Valid @NotBlank @NotNull @RequestBody ReportDto reportDto, BindingResult bindingResult) {
        reportValidator.validate(reportDto, bindingResult);
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        ReportDto savedReportDto = reportService.saveReport(reportDto);
        return new ResponseEntity<>(savedReportDto, HttpStatus.CREATED);
    }

    @PutMapping("/reports")
    public ResponseEntity<?> updateReport(@Valid @RequestBody ReportDto reportDto, BindingResult bindingResult) {
        reportValidator.validate(reportDto, bindingResult);
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        ReportDto updatedReportDto = reportService.updateReport(reportDto);
        return new ResponseEntity<>(updatedReportDto, HttpStatus.CREATED);
    }

    @GetMapping("/reports/adoptFilter")
    public Page<Report> getAdoptReportsByFilters(@RequestParam(value = "city", required = false) String city,
                                                 @RequestParam(value = "type", required = false) Type type,
                                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                                 @RequestParam(value = "size", defaultValue = "10") int size,
                                                 @RequestParam(value = "petBreed", required = false) String petBreed,
                                                 @RequestParam(value = "petAgeStart", required = false) int petAgeStart,
                                                 @RequestParam(value = "petAgeEnd", required = false) int petAgeEnd,
                                                 @RequestParam(value = "petSpecie", required = false) Specie petSpecie) {

        Pageable pageable = PageRequest.of(page, size);
        return reportService.getAdoptReportsByFilters(city, type, petBreed, petAgeStart, petAgeEnd, petSpecie, pageable);
    }


    //====== Exceptions handling
    @ExceptionHandler(ReportNotFoundException.class)
    public ResponseEntity<String> handleReportNotFound(ReportNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("report not found");
    }

    @ExceptionHandler(PetNotFoundException.class)
    public ResponseEntity<String> handlePetNotFound(PetNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet not found");
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String errorMessage = "Invalid JSON payload: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        String errorMessage = "Invalid argument: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
}

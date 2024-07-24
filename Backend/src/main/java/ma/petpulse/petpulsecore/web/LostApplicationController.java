package ma.petpulse.petpulsecore.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import ma.petpulse.petpulsecore.exceptions.ApplicationNotFoundException;
import ma.petpulse.petpulsecore.exceptions.ReportNotFoundException;
import ma.petpulse.petpulsecore.service.dtos.AdoptionApplicationDto;
import ma.petpulse.petpulsecore.service.dtos.LostApplicationDto;
import ma.petpulse.petpulsecore.service.services.implementations.LostApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@Data
@AllArgsConstructor
@RequestMapping("/applications/find")
public class LostApplicationController {
    LostApplicationService lostApplicationService;


    @GetMapping
    public List<LostApplicationDto> getAllApplications() {
        return lostApplicationService.getAllApplications();
    }

    @GetMapping("/{id}")
    public List<LostApplicationDto> getApplicationsByReportId(@PathVariable Long id) {
        return lostApplicationService.getApplicationsByReportId(id);
    }

    @PostMapping
    public LostApplicationDto createApplication(@Valid LostApplicationDto application,
                                                @RequestPart("image") MultipartFile image) throws IOException {
        return lostApplicationService.createApplication(application, image);
    }

    @GetMapping("application/{id}")
    public ResponseEntity<?> getApplicationById(@PathVariable Long id) {
        LostApplicationDto applicationDto = lostApplicationService.getApplicationById(id);
        return ResponseEntity.ok(applicationDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        lostApplicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }


    @ExceptionHandler(ApplicationNotFoundException.class)
    public ResponseEntity<String> handleApplicationNotFoundException(ApplicationNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("application not found");
    }
}

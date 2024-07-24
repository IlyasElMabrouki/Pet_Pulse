package ma.petpulse.petpulsecore.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.petpulse.petpulsecore.service.dtos.AdoptionApplicationDto;
import ma.petpulse.petpulsecore.service.mappers.IReportMapper;
import ma.petpulse.petpulsecore.service.services.interfaces.IAdoptionApplicationService;
import ma.petpulse.petpulsecore.service.services.interfaces.IJwtService;
import ma.petpulse.petpulsecore.service.services.interfaces.IReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications/adopt")
@RequiredArgsConstructor
public class AdoptionApplicationController {
    private final IAdoptionApplicationService adoptionApplicationService;
    private final IReportService reportService;
    private final IJwtService jwtService;
    private final IReportMapper reportMapper;

    @GetMapping
    public ResponseEntity<List<AdoptionApplicationDto>> getAllApplications() {
        return ResponseEntity.ok(adoptionApplicationService.getAllApplications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdoptionApplicationDto> getApplicationById(@PathVariable Long id) {
        AdoptionApplicationDto applicationDto = adoptionApplicationService.getApplicationById(id);

        return ResponseEntity.ok(applicationDto);
    }

    @PostMapping
    public ResponseEntity<AdoptionApplicationDto> createApplication(@RequestBody @Valid AdoptionApplicationDto application) {
        // Create the application
        return ResponseEntity.ok(adoptionApplicationService.createApplication(application));
    }

    // TODO: try to fix the problem with the updateApplication method
    @PutMapping("/{id}")
    public ResponseEntity<AdoptionApplicationDto> updateApplication(@PathVariable Long id, @RequestBody @Valid AdoptionApplicationDto application) {

        application.setId(id);

        AdoptionApplicationDto applicationDto = adoptionApplicationService.updateApplication(application);

        return ResponseEntity.ok(applicationDto);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        adoptionApplicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }
}
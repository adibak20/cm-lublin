package pl.cm.core.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.cm.core.dto.PatientDTO;
import pl.cm.core.services.PatientService;

import java.util.Set;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody PatientDTO patientDTO){
        return ResponseEntity.ok(patientService.save(patientDTO));
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<Long> update(@PathVariable Long id, @RequestBody PatientDTO patientDTO){
        return ResponseEntity.ok(patientService.update(id, patientDTO));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PatientDTO> get(@PathVariable Long id){
        return ResponseEntity.ok(patientService.get(id));
    }

    @GetMapping(path = "/list")
    public ResponseEntity<Set<PatientDTO>> getAll(){
        return ResponseEntity.ok(patientService.getAll());
    }

    @GetMapping(path = "/findAllByKeywords/{keyword}")
    public ResponseEntity<Set<PatientDTO>> findAllByKeywords(@PathVariable String keyword){
        return ResponseEntity.ok(patientService.findAllByKeywords(keyword));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id){
        patientService.delete(id);
        return ResponseEntity.ok(id);
    }
}

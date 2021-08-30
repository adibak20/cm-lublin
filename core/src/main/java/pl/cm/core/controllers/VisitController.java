package pl.cm.core.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.cm.core.dto.VisitDTO;
import pl.cm.core.services.VisitService;

import java.util.Set;

@RestController
@RequestMapping("/visit")
public class VisitController {
    
    private final VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody VisitDTO VisitDTO){
        return ResponseEntity.ok(visitService.save(VisitDTO));
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<Long> update(@PathVariable Long id, @RequestBody VisitDTO VisitDTO){
        return ResponseEntity.ok(visitService.update(id, VisitDTO));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<VisitDTO> get(@PathVariable Long id){
        return ResponseEntity.ok(visitService.get(id));
    }

    @GetMapping(path = "/getAllByPatient/{patientId}")
    public ResponseEntity<Set<VisitDTO>> getAllByPatient(@PathVariable Long patientId){
        return ResponseEntity.ok(visitService.getAllByPatient(patientId));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id){
        visitService.delete(id);
        return ResponseEntity.ok(id);
    }
}

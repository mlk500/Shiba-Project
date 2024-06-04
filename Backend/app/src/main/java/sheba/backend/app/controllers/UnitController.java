package sheba.backend.app.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sheba.backend.app.BL.UnitBL;
import sheba.backend.app.entities.Unit;
import sheba.backend.app.util.Endpoints;

import java.util.List;

@RestController
@RequestMapping(Endpoints.UNIT_ENDPOINT)
@RequiredArgsConstructor
public class UnitController {
    private final UnitBL unitBL;

//    @PostMapping("/create")
//    public ResponseEntity<?> createUnit(@RequestBody Unit unit, @RequestParam long gameId, @RequestParam long taskId, @RequestParam long objectId) {
//        System.out.println("request  " + unit + " "  + gameId + " " + taskId + "  " + objectId);
//        unitBL.createUnit(unit, gameId, taskId, objectId);
//        return ResponseEntity.ok(HttpStatus.CREATED);
//    }

//    @GetMapping("/getAll")
//    public List<Unit> getAllUnits() {
//        return unitBL.getAllUnits();
//    }

    @GetMapping("/get/{id}")
    public Unit getUnitById(@PathVariable long id) {
        return unitBL.getUnitById(id);
    }

    @PutMapping("update/{id}")
    public Unit updateUnit(@PathVariable long id, @RequestBody Unit unit) {
        return unitBL.updateUnit(id, unit);
    }

    @DeleteMapping("delete/{id}")
    public void deleteUnit(@PathVariable long id) {
        unitBL.deleteUnit(id);
    }
}

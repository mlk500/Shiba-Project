package sheba.backend.app.BL;

import org.springframework.stereotype.Service;
import sheba.backend.app.entities.Unit;
import sheba.backend.app.repositories.*;

import java.util.List;

@Service
public class UnitBL {
    private final UnitRepository unitRepository;

    private final GameRepository gameRepository;

    private final TaskRepository taskRepository;

    private final LocationRepository locationRepository;
    private final ObjectLocationRepository objectLocationRepository;

    public UnitBL(UnitRepository unitRepository, GameRepository gameRepository, TaskRepository taskRepository, LocationRepository locationRepository, ObjectLocationRepository objectLocationRepository) {
        this.unitRepository = unitRepository;
        this.gameRepository = gameRepository;
        this.taskRepository = taskRepository;
        this.locationRepository = locationRepository;
        this.objectLocationRepository = objectLocationRepository;
    }

    public List<Unit> getAllUnits() {
        return unitRepository.findAll();
    }

    public Unit getUnitById(long id) {
        return unitRepository.findById(id).orElse(null);
    }

    public Unit createUnit(Unit unit, long gameId) {
        Unit savedUnit = unitRepository.save(unit);
        System.out.println("unit is " + savedUnit);
        savedUnit.setGame(gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game not found")));
        savedUnit.setTask(taskRepository.findById(unit.getTaskID()).orElseThrow(() -> new RuntimeException("Task not found")));
        savedUnit.setLocation(locationRepository.findById(unit.getLocationID()).orElseThrow(() -> new RuntimeException("Location not found")));
        savedUnit.setObject(objectLocationRepository.findById(unit.getObjectID()).orElseThrow(() -> new RuntimeException("Object not found")));
//        unit.setTaskID(taskRepository.findById(unit.getTaskID()).get().getTaskID());
//        unit.setObjectID(objectLocationRepository.findById(objectId).get().getObjectID());
//        unit.setLocationID(objectLocationRepository.findById(objectId).get().getLocation().getLocationID());
        return unitRepository.save(savedUnit);
    }
//    public Unit createUnit(Unit unit, long gameId, long taskId, long objectId) {
//        unit.setGame(gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game not found")));
//        unit.setTask(taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found")));
//        unit.setObject(objectLocationRepository.findById(objectId).orElseThrow(() -> new RuntimeException("Object not found")));
//        unit.setTaskID(taskRepository.findById(taskId).get().getTaskID());
//        unit.setObjectID(objectLocationRepository.findById(objectId).get().getObjectID());
//        unit.setLocationID(objectLocationRepository.findById(objectId).get().getLocation().getLocationID());
//        return unitRepository.save(unit);
//    }

    public Unit updateUnit(long id, Unit updatedUnit) {
        return unitRepository.findById(id).map(unit -> {
            unit.setName(updatedUnit.getName());
            unit.setDescription(updatedUnit.getDescription());
            unit.setHint(updatedUnit.getHint());
            unit.setGame(updatedUnit.getGame());
            unit.setTask(updatedUnit.getTask());
            unit.setObject(updatedUnit.getObject());
            return unitRepository.save(unit);
        }).orElseGet(() -> {
            updatedUnit.setUnitID(id);
            return unitRepository.save(updatedUnit);
        });
    }

    public void deleteUnit(long id) {
        unitRepository.deleteById(id);
    }
}



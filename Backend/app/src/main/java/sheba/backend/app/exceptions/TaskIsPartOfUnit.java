package sheba.backend.app.exceptions;

public class TaskIsPartOfUnit extends Exception{
    public TaskIsPartOfUnit(String message) {
        super(message);
    }
}

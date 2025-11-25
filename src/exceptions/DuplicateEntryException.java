package exceptions;

public class DuplicateEntryException extends Exception {
    public DuplicateEntryException(String message) {
        super(message);
    }
    
    public DuplicateEntryException(int id, String type) {
        super(type + " with ID " + id + " already exists.");
    }
}

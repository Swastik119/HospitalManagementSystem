package interfaces;

import java.util.List;

public interface IPersonOperations<T> {
    void add(T person) throws exceptions.DuplicateEntryException;
    void update(T person) throws exceptions.PatientNotFoundException;
    void delete(int id) throws exceptions.PatientNotFoundException;
    T getById(int id) throws exceptions.PatientNotFoundException;
    List<T> getAll();
    boolean exists(int id);
}

package collections;

import interfaces.IPersonOperations;
import exceptions.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class HospitalRegistry<T> implements IPersonOperations<T> {
    private final Map<Integer, T> registry;
    private final String typeName;
    
    public HospitalRegistry(String typeName) {
        this.registry = new ConcurrentHashMap<>();
        this.typeName = typeName;
    }
    
    @Override
    public void add(T person) throws DuplicateEntryException {
        int id = getPersonId(person);
        if (registry.containsKey(id)) {
            throw new DuplicateEntryException(id, typeName);
        }
        registry.put(id, person);
        System.out.println(typeName + " with ID " + id + " added successfully.");
    }
    
    @Override
    public void update(T person) throws PatientNotFoundException {
        int id = getPersonId(person);
        if (!registry.containsKey(id)) {
            throw new PatientNotFoundException(id);
        }
        registry.put(id, person);
        System.out.println(typeName + " with ID " + id + " updated successfully.");
    }
    
    @Override
    public void delete(int id) throws PatientNotFoundException {
        if (!registry.containsKey(id)) {
            throw new PatientNotFoundException(id);
        }
        registry.remove(id);
        System.out.println(typeName + " with ID " + id + " deleted successfully.");
    }
    
    @Override
    public T getById(int id) throws PatientNotFoundException {
        T person = registry.get(id);
        if (person == null) {
            throw new PatientNotFoundException(id);
        }
        return person;
    }
    
    @Override
    public List<T> getAll() {
        return new ArrayList<>(registry.values());
    }
    
    @Override
    public boolean exists(int id) {
        return registry.containsKey(id);
    }
    
    public List<T> searchByName(String name) {
        return registry.values().stream()
                .filter(person -> getPersonName(person).toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    public int getSize() {
        return registry.size();
    }
    
    public void clear() {
        registry.clear();
        System.out.println(typeName + " registry cleared.");
    }
    
    // Helper methods using reflection-like behavior
    private int getPersonId(T person) {
        try {
            return (int) person.getClass().getMethod("getId").invoke(person);
        } catch (Exception e) {
            throw new RuntimeException("Error getting ID from " + typeName, e);
        }
    }
    
    private String getPersonName(T person) {
        try {
            return (String) person.getClass().getMethod("getName").invoke(person);
        } catch (Exception e) {
            throw new RuntimeException("Error getting name from " + typeName, e);
        }
    }
    
    // Additional utility methods
    public Map<Integer, T> getRegistryMap() {
        return Collections.unmodifiableMap(registry);
    }
    
    public List<T> getSortedByName() {
        return registry.values().stream()
                .sorted(Comparator.comparing(this::getPersonName))
                .collect(Collectors.toList());
    }
    
    public boolean isEmpty() {
        return registry.isEmpty();
    }
}

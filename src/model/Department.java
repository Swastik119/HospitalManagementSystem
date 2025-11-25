package model;

public class Department {
    private int id;
    private String name;
    private String description;
    private int floor;
    
    public Department(int id, String name, String description, int floor) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.floor = floor;
    }
    
    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public int getFloor() { return floor; }
    public void setFloor(int floor) { this.floor = floor; }
    
    @Override
    public String toString() {
        return String.format("Department: %s (Floor: %d) - %s", name, floor, description);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Department that = (Department) obj;
        return id == that.id;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}

package models;

public class AcademicDegree {
    private String id;
    private String name;
    private Number level;
    private boolean isActive;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Number getLevel() {
        return level;
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public String toString() {
        return "AcademicDegree{id=" + id + ", name='" + name + "', level=" + level + ", isActive=" + isActive + "}";
    }
}
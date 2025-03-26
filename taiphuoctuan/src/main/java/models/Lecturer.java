package models;

public class Lecturer {
    private String id;
    private String fullName;
    private String department;

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return "Lecturer{id=" + id + ", fullName='" + fullName + "', department='" + department + "'}";
    }
}
package models;

public class Term {
    private String id;
    private int start_year;
    private int end_year;
    private int max_class;
    private int max_lesson;
    private String start_date;
    private int start_week;
    private boolean status;

    public String getId() {
        return id;
    }

    public int getStartYear() {
        return start_year;
    }

    public int getEndYear() {
        return end_year;
    }

    public int getMaxClass() {
        return max_class;
    }

    public int getMaxLesson() {
        return max_lesson;
    }

    public String getStartDate() {
        return start_date;
    }

    public int getStartWeek() {
        return start_week;
    }

    public boolean isStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", startYear=" + start_year +
                ", endYear=" + end_year +
                ", maxClass=" + max_class +
                ", maxLesson=" + max_lesson +
                ", startDate='" + start_date + '\'' +
                ", startWeek=" + start_week +
                ", status=" + status +
                '}';
    }
}
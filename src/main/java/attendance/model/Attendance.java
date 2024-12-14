package attendance.model;

public class Attendance {

    private final String name;
    private final int year;
    private final int month;
    private final int date;
    private int hour;
    private int minute;
    private String status;

    public Attendance(String name, int year, int month, int date, int hour, int minute, String status) {
        this.name = name;
        this.year = year;
        this.month = month;
        this.date = date;
        this.hour = hour;
        this.minute = minute;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDate() {
        return date;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

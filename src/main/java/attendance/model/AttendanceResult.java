package attendance.model;

public class AttendanceResult {

    private String name;
    private int month;
    private int date;
    private String dayOfWeek;
    private String hour;
    private String minute;
    private String status;

    public AttendanceResult(String name, int month, int date, String dayOfWeek, String hour, String minute,
                            String status) {
        this.name = name;
        this.month = month;
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.hour = hour;
        this.minute = minute;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public int getMonth() {
        return month;
    }

    public int getDate() {
        return date;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getHour() {
        return hour;
    }

    public String getMinute() {
        return minute;
    }

    public String getStatus() {
        return status;
    }
}

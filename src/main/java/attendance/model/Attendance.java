package attendance.model;

public class Attendance {

    private final String name;
    private String dateTime;

    public Attendance(String name, String dateTime) {
        this.name = name;
        this.dateTime = dateTime;
    }

    public String getName() {
        return name;
    }

    public String getDateTime() {
        return dateTime;
    }
}

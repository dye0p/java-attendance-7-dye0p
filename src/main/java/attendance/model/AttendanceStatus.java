package attendance.model;

public enum AttendanceStatus {

    ATTENDANCE("(출석)"),
    LATE("(지각)"),
    ABSENT("(결석)");

    private final String status;

    AttendanceStatus(String status) {
        this.status = status;
    }

    public static String calculateStatus(String dayOfWeek, int hour, int minute) {

        if (dayOfWeek.equals("월요일")) {
            if (hour <= 13 && minute <= 0) {
                return AttendanceStatus.ATTENDANCE.status;
            }

            if (hour <= 13 && minute > 0
                    || hour <= 13 && minute <= 30) {
                return AttendanceStatus.LATE.status;
            }
            return ABSENT.status;
        }

        if (hour <= 10 && minute <= 0) {
            return AttendanceStatus.ATTENDANCE.status;
        }

        if (hour <= 10 && minute > 0
                || hour <= 10 && minute <= 30) {
            return AttendanceStatus.LATE.status;
        }

        return ABSENT.status;

    }

    public String getStatus() {
        return status;
    }
}

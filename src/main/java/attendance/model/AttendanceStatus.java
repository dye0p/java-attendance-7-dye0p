package attendance.model;

public enum AttendanceStatus {

    ATTENDANCE("(출석)"),
    LATE("(지각)"),
    ABSENT("(결석)");

    private final String status;

    AttendanceStatus(String status) {
        this.status = status;
    }

    public static String calculateStatus(String dayOfWeek, String[] goTimeSplit) {

        if (dayOfWeek.equals("월요일")) {
            if (Integer.parseInt(goTimeSplit[0]) <= 13 && Integer.parseInt(goTimeSplit[1]) <= 0) {
                return AttendanceStatus.ATTENDANCE.status;
            }

            if (Integer.parseInt(goTimeSplit[0]) <= 13 && Integer.parseInt(goTimeSplit[1]) > 0
                    || Integer.parseInt(goTimeSplit[0]) <= 13 && Integer.parseInt(goTimeSplit[1]) <= 30) {
                return AttendanceStatus.LATE.status;
            }
            return ABSENT.status;
        }

        if (Integer.parseInt(goTimeSplit[0]) <= 10 && Integer.parseInt(goTimeSplit[1]) <= 0) {
            return AttendanceStatus.ATTENDANCE.status;
        }

        if (Integer.parseInt(goTimeSplit[0]) <= 10 && Integer.parseInt(goTimeSplit[1]) > 0
                || Integer.parseInt(goTimeSplit[0]) <= 10 && Integer.parseInt(goTimeSplit[1]) <= 30) {
            return AttendanceStatus.LATE.status;
        }

        return ABSENT.status;

    }
}

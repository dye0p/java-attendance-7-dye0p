package attendance.model;

import java.time.LocalDate;
import java.util.List;

public class AttendanceResults {

    private List<Attendance> attendancesResult;

    public AttendanceResults(List<Attendance> attendancesResult) {
        this.attendancesResult = attendancesResult;
    }

    public List<Attendance> getAttendancesResult() {
        return attendancesResult;
    }

    public AttendanceResult isContain(String name, int date) {

        LocalDate localDate = LocalDate.of(2024, 12, date);
        int value = localDate.getDayOfWeek().getValue();
        String dayOfWeek = DayOfWeek.of(value);

        if (dayOfWeek.equals("토요일") || dayOfWeek.equals("일요일")) {
            return null;
        }

        for (Attendance attendance : attendancesResult) {
            if (attendance.getDate() == date) {
                String hour = String.valueOf(attendance.getHour());
                String minute = String.valueOf(attendance.getMinute());

                return new AttendanceResult(name, 12, date, dayOfWeek, hour, minute, attendance.getStatus());
            }
        }

        return new AttendanceResult(name, 12, date, dayOfWeek, "--", "--", AttendanceStatus.ABSENT.getStatus());
    }
}

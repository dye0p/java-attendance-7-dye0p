package attendance.model;

import java.util.ArrayList;
import java.util.List;

public class Attendances {

    private final List<Attendance> attendances;

    public Attendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public void isContain(String name) {

        int matchCount = 0;

        for (Attendance attendance : attendances) {
            if (attendance.getName().equals(name)) {
                matchCount++;
            }
        }
        validateContain(matchCount);
    }

    private void validateContain(int matchCount) {
        if (matchCount == 0) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
    }

    public void updateAttendance(Attendance attendance) {
        attendances.add(attendance);
    }

    public Attendance findAttendanceBy(String name, int date) {
        return validateName(name, date);
    }

    public List<Attendance> getAttendanceResultBy(int nowDate, String name) {
        List<Attendance> result = new ArrayList<>();

        for (Attendance attendance : attendances) {
            if (attendance.getName().equals(name)) {
                result.add(attendance);
            }
        }
        return result;
    }

    private Attendance validateName(String name, int date) {
        for (Attendance attendance : attendances) {
            if (attendance.getName().equals(name) && attendance.getDate() == date) {
                return attendance;
            }
        }

        throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
    }
}

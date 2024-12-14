package attendance.model;

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

        if (matchCount == 0) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
    }

    public void updateAttendance(Attendance attendance) {
        attendances.add(attendance);
    }

    public Attendance findAttendanceBy(String name, int date) {
        //해당 닉네임을 찾는다.

        //출석부에 해당 크루가 매개변수의 date에 출석한 기록이 있는지 확인한다.

        for (Attendance attendance : attendances) {
            if (attendance.getName().equals(name) && attendance.getDate() == date) {
                return attendance;
            }
        }

        throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
    }
}

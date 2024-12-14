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
}

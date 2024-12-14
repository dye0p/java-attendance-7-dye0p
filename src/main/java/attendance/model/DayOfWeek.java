package attendance.model;

import java.util.Arrays;

public enum DayOfWeek {

    MON("월요일", 1), TUE("화요일", 2), WED("수요일", 3), THU("목요일", 4), FRI("금요일", 5), SAT("토요일", 6), SUM("일요일", 7);

    private final String value;
    private final int dateValue;

    DayOfWeek(String value, int dateValue) {
        this.value = value;
        this.dateValue = dateValue;
    }

    public static String of(int date) {
        DayOfWeek dayOfWeek = Arrays.stream(values())
                .filter(value -> value.dateValue == date)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 요일 입니다."));

        return dayOfWeek.value;
    }

}

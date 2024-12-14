package attendance.view;

import attendance.model.Attendance;
import attendance.model.DayOfWeek;
import java.time.LocalDate;

public class OutputView {

    private static final String NEXT_LINE = System.lineSeparator();

    public void printToday(int monthValue, int dayOfMonth, String dayOfWeek) {
        String format = String.format("오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요", monthValue, dayOfMonth, dayOfWeek);
        System.out.println(format);
    }

    public void printTodayAttendanceResult(int monthValue, int dayOfMonth, String dayOfWeek, String goTime,
                                           String check) {
        String format = String.format("%d월 %d일 %s %s %s", monthValue, dayOfMonth, dayOfWeek, goTime, check);

        System.out.println(NEXT_LINE + format);

    }

    public void printUpdateResult(Attendance copyAttendance, Attendance attendanceBy) {
        String date = String.valueOf(copyAttendance.getDate());

        if (date.length() == 1) {
            date = "0" + date;
        }

        LocalDate localDate = LocalDate.of(2024, copyAttendance.getMonth(), copyAttendance.getDate());
        int value = localDate.getDayOfWeek().getValue();

        String dayOfWeek = DayOfWeek.of(value);

        String hour = String.valueOf(copyAttendance.getHour());

        if (hour.length() == 1) {
            hour = "0" + hour;
        }

        String minute = String.valueOf(copyAttendance.getMinute());

        if (minute.length() == 1) {
            minute = "0" + minute;
        }

        String status = copyAttendance.getStatus();

        String format = String.format("12월 %s일 %s %s:%s %s",
                date, dayOfWeek, hour, minute, status);

        String hour2 = String.valueOf(attendanceBy.getHour());

        if (hour2.length() == 1) {
            hour2 = "0" + hour2;
        }

        String minute2 = String.valueOf(attendanceBy.getMinute());

        if (minute2.length() == 1) {
            minute2 = "0" + minute2;
        }

        String status2 = attendanceBy.getStatus();

        String format1 = String.format(" -> %s:%s %s 수정완료!",
                hour2, minute2, status2);

        System.out.println(format + format1);
    }
}

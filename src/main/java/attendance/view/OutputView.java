package attendance.view;

import attendance.model.Attendance;
import attendance.model.AttendanceResult;
import attendance.model.AttendanceResults;
import attendance.model.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class OutputView {

    private static final String NEXT_LINE = System.lineSeparator();

    public void printToday(int monthValue, int dayOfMonth, String dayOfWeek) {
        String format = String.format(NEXT_LINE + "오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요", monthValue, dayOfMonth, dayOfWeek);
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

        String format1 = String.format(" -> %s:%s %s 수정 완료!",
                hour2, minute2, status2);

        System.out.println(format + format1);
    }

    public void printAttendanceResult(int nowDayOfMonth, AttendanceResults attendanceResultBy) {
        String name = String.format(NEXT_LINE + "이번 달 %s의 출석 기록입니다.",
                attendanceResultBy.getAttendancesResult().get(0).getName());

        int attendanceCount = 0;
        int lateCount = 0;
        int absentCount = 0;
        System.out.println(name);

        StringJoiner sj = new StringJoiner(NEXT_LINE);

        List<AttendanceResult> resultALl = new ArrayList<>();
        //2일부터 결과를 돌면서 해당 날짜의 출근이 존재하는지 찾는다.
        for (int i = 2; i < nowDayOfMonth; i++) {
            AttendanceResult contain = attendanceResultBy.isContain(name, i);
            if (contain != null) {
                resultALl.add(contain);
            }
        }

        //12월 02일 월요일 13:00 (출석)
        for (AttendanceResult attendance : resultALl) {

            String date = String.valueOf(attendance.getDate());

            if (date.length() == 1) {
                date = "0" + date;
            }

            LocalDate localDate = LocalDate.of(2024, attendance.getMonth(), attendance.getDate());
            int value = localDate.getDayOfWeek().getValue();

            String dayOfWeek = DayOfWeek.of(value);

            String hour = String.valueOf(attendance.getHour());

            if (hour.length() == 1) {
                hour = "0" + hour;
            }

            if (attendance.getHour().equals("--")) {
                hour = "--";
            }

            String minute = String.valueOf(attendance.getMinute());

            if (minute.length() == 1) {
                minute = "0" + minute;
            }

            if (attendance.getMinute().equals("--")) {
                minute = "--";
            }

            String status = attendance.getStatus();

            if (status.equals("(출석)")) {
                attendanceCount++;
            }

            if (status.equals("(지각)")) {
                lateCount++;
                if (lateCount == 3) {
                    absentCount++;
                    lateCount = 0;
                }
            }

            if (status.equals("(결석)")) {
                absentCount++;
            }

            String format = String.format("12월 %s일 %s %s:%s %s",
                    date, dayOfWeek, hour, minute, status);

            sj.add(format);
        }

        String attendanceCountFormat = String.format("출석: %d회", attendanceCount);
        String lateCountFormat = String.format("지각: %d회", lateCount);
        String absentCountFormat = String.format("결석: %d회", absentCount);

        String subjects = "";

        if (absentCount == 2) {
            subjects = "경고 대상자";
        }

        if (absentCount >= 3 && absentCount < 5) {
            subjects = "면담 대상자";
        }

        if (absentCount >= 5) {
            subjects = "제적 대상자";
        }

        //출석부 결과 출력
        System.out.println(NEXT_LINE + sj);

        //현황 출력
        System.out.println(NEXT_LINE + attendanceCountFormat);
        System.out.println(lateCountFormat);
        System.out.println(absentCountFormat);

        System.out.println(NEXT_LINE + subjects + "입니다.");
    }
}

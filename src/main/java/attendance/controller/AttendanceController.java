package attendance.controller;

import attendance.model.Attendance;
import attendance.model.AttendanceResults;
import attendance.model.AttendanceStatus;
import attendance.model.Attendances;
import attendance.model.DayOfWeek;
import attendance.util.CrewFileReader;
import attendance.view.InputView;
import attendance.view.OutputView;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;
    private Attendances attendances;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        initializeAttendance();

        LocalDateTime now = DateTimes.now();
        int monthValue = now.getMonthValue();
        int dayOfMonth = now.getDayOfMonth();
        String dayOfWeek = DayOfWeek.of(now.getDayOfWeek().getValue());

        playAttendance(monthValue, dayOfMonth, dayOfWeek);
    }

    private void playAttendance(int monthValue, int dayOfMonth, String dayOfWeek) {
        while (true) {
            outputView.printToday(monthValue, dayOfMonth, dayOfWeek);
            String option = inputView.readOption();

            if (option.equals("1")) {
                optionOne(dayOfWeek, monthValue, dayOfMonth, attendances);
            }
            if (option.equals("2")) {
                optionTwo();
            }
            if (option.equals("3")) {
                optionThree();
            }
            if (option.equals("Q")) {
                break;
            }
        }
    }

    private void initializeAttendance() {
        CrewFileReader crewFileReader = new CrewFileReader();
        List<Attendance> attendanceList = crewFileReader.attendancesReader();
        attendances = new Attendances(attendanceList);
    }

    private void optionOne(String dayOfWeek, int monthValue, int dayOfMonth, Attendances attendances) {
        validatePossibleSchool(dayOfWeek, monthValue, dayOfMonth);

        String name = inputView.readNickName();
        attendances.isContain(name);

        String goTime = inputView.readGoTime();
        String[] goTimeSplit = validateGoTimm(goTime);

        int hour = Integer.parseInt(goTimeSplit[0]);
        int minute = Integer.parseInt(goTimeSplit[1]);

        String status = AttendanceStatus.calculateStatus(dayOfWeek, hour, minute);
        outputView.printTodayAttendanceResult(monthValue, dayOfMonth, dayOfWeek, goTime, status);

        Attendance attendance = new Attendance(name, 2024, monthValue, dayOfMonth, hour, minute, status);
        attendances.updateAttendance(attendance);
    }

    private void optionTwo() {
        String name = inputView.readUpdateNickName();
        attendances.isContain(name);

        int date = inputView.updateDate();
        validateFuture(date);

        Attendance attendanceBy = attendances.findAttendanceBy(name, date);
        Attendance copyAttendance = createCopyAttendance(attendanceBy);

        //수정할 시간 입력
        String updateTime = inputView.updateTime();

        //시간 수정
        String[] updateTimeSplit = updateTime.split(":");

        int updateHour = Integer.parseInt(updateTimeSplit[0]);
        int updateMinute = Integer.parseInt(updateTimeSplit[1]);

        attendanceBy.setHour(updateHour);
        attendanceBy.setMinute(updateMinute);

        LocalDate localDate = LocalDate.of(2024, attendanceBy.getMonth(), attendanceBy.getDate());
        int value = localDate.getDayOfWeek().getValue();

        String updateDayOfWeek = DayOfWeek.of(value);
        String updateStatus = AttendanceStatus.calculateStatus(updateDayOfWeek, updateMinute, updateMinute);

        attendanceBy.setStatus(updateStatus);

        outputView.printUpdateResult(copyAttendance, attendanceBy);
    }

    private void optionThree() {
        String name = inputView.readNickName();

        attendances.isContain(name);

        LocalDateTime nowDate = DateTimes.now();
        int nowDayOfMonth = nowDate.getDayOfMonth();

        List<Attendance> attendanceResultBy = attendances.getAttendanceResultBy(nowDayOfMonth, name);
        AttendanceResults attendanceResult = new AttendanceResults(attendanceResultBy);

        outputView.printAttendanceResult(nowDayOfMonth, attendanceResult);
    }

    private Attendance createCopyAttendance(Attendance attendanceBy) {
        return new Attendance(attendanceBy.getName(),
                attendanceBy.getYear(),
                attendanceBy.getMonth(),
                attendanceBy.getDate(),
                attendanceBy.getHour(),
                attendanceBy.getMinute(),
                attendanceBy.getStatus());
    }

    private void validatePossibleSchool(String dayOfWeek, int monthValue, int dayOfMonth) {
        if (dayOfWeek.equals("토요일") || dayOfWeek.equals("일요일")) {
            String format = String.format("[ERROR] %d월 %d일 %s은 등교일이 아닙니다.", monthValue, dayOfMonth, dayOfWeek);
            throw new IllegalArgumentException(format);
        }
    }

    private void validateFuture(int date) {
        LocalDateTime now = DateTimes.now();
        int dayOfMonth = now.getDayOfMonth();

        if (date > dayOfMonth) {
            throw new IllegalArgumentException("[ERROR] 아직 수정할 수 없습니다.");
        }
    }

    private String[] validateGoTimm(String goTime) {
        String[] goTimeSplit = goTime.split(":");

        if (goTimeSplit.length != 2) {
            throw new IllegalArgumentException("[ERROR] 잘못된 형식을 입력하였습니다.");
        }

        if (Integer.parseInt(goTimeSplit[0]) > 24 || Integer.parseInt(goTimeSplit[1]) > 60) {
            throw new IllegalArgumentException("[ERROR] 잘못된 형식을 입력하였습니다.");
        }

        if ((Integer.parseInt(goTimeSplit[0]) < 8) ||
                (Integer.parseInt(goTimeSplit[0]) >= 23 && Integer.parseInt(goTimeSplit[1]) > 0)) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간에만 출석이 가능합니다.");
        }
        return goTimeSplit;
    }
}

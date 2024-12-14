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
        CrewFileReader crewFileReader = new CrewFileReader();
        List<Attendance> attendanceList = crewFileReader.attendancesReader();
        attendances = new Attendances(attendanceList);

        //오늘의 날짜를 가져옴.
        LocalDateTime now = DateTimes.now();

        int monthValue = now.getMonthValue(); //월
        int dayOfMonth = now.getDayOfMonth(); //일
        String dayOfWeek = DayOfWeek.of(now.getDayOfWeek().getValue()); //요일

        //기능 선택 입력
        while (true) {
            outputView.printToday(monthValue, dayOfMonth, dayOfWeek);
            String option = inputView.readOption();

            //기능이 1번인 경우
            if (option.equals("1")) {
                optionOne(dayOfWeek, monthValue, dayOfMonth, attendances);
            }

            //기능 2번인 경우
            if (option.equals("2")) {
                optionTwo();
            }

            //기능 3번인 경우
            if (option.equals("3")) {
                optionThree();
            }

            if (option.equals("Q")) {
                break;
            }
        }
    }

    private void optionThree() {
        //출석부를 확인할 닉네임 입력
        String name = inputView.readNickName();

        //해당 닉네임을 찾는다.
        attendances.isContain(name);

        //해당 닉네임에 해당하는 출석 기록을 출력한다.
        //출석 기록을 가져온다. (시작부터 전날까지)

        LocalDateTime nowDate = DateTimes.now();
        int nowDayOfMonth = nowDate.getDayOfMonth();

        List<Attendance> attendanceResultBy = attendances.getAttendanceResultBy(nowDayOfMonth, name);
        AttendanceResults attendanceResult = new AttendanceResults(attendanceResultBy);
        outputView.printAttendanceResult(nowDayOfMonth, attendanceResult);
    }

    private void optionTwo() {
        //출석을 수정하려는 닉네임
        String name = inputView.readUpdateNickName();

        //닉네임이 존재하는지 확인한다.
        attendances.isContain(name);

        //수정하려는 날짜 입력
        int date = inputView.updateDate();

        valdiateFuture(date);

        //해당 닉네임에 대한 일의 출석이 존재하는지 확인한다.
        Attendance attendanceBy = attendances.findAttendanceBy(name, date); //입력한 일자에 출석한

        Attendance copyAttendance = new Attendance(attendanceBy.getName(),
                attendanceBy.getYear(),
                attendanceBy.getMonth(),
                attendanceBy.getDate(),
                attendanceBy.getHour(),
                attendanceBy.getMinute(),
                attendanceBy.getStatus()); //원본

        //수정할 시간 입력
        String updateTime = inputView.updateTime();

        //시간 수정
        String[] updateTimeSplit = updateTime.split(":");

        int updateHour = Integer.parseInt(updateTimeSplit[0]);
        int updateMinute = Integer.parseInt(updateTimeSplit[1]);

        attendanceBy.setHour(updateHour);
        attendanceBy.setMinute(updateMinute);

        //상태 수정
        LocalDate localDate = LocalDate.of(2024, attendanceBy.getMonth(), attendanceBy.getDate());
        int value = localDate.getDayOfWeek().getValue();

        String updateDayOfWeek = DayOfWeek.of(value);
        String updateStatus = AttendanceStatus.calculateStatus(updateDayOfWeek, updateMinute, updateMinute);

        attendanceBy.setStatus(updateStatus);

        //수정 후 출력
        outputView.printUpdateResult(copyAttendance, attendanceBy);

    }

    private void valdiateFuture(int date) {
        LocalDateTime now = DateTimes.now();
        int dayOfMonth = now.getDayOfMonth();

        if (date > dayOfMonth) {
            throw new IllegalArgumentException("[ERROR] 아직 수정할 수 없습니다.");
        }
    }

    private void optionOne(String dayOfWeek, int monthValue, int dayOfMonth, Attendances attendances) {
        //등교 날짜인지 확인
        if (dayOfWeek.equals("토요일") || dayOfWeek.equals("일요일")) {
            String format = String.format("[ERROR] %d월 %d일 %s은 등교일이 아닙니다.", monthValue, dayOfMonth, dayOfWeek);
            throw new IllegalArgumentException(format);
        }

        //닉네임 입력
        String name = inputView.readNickName();

        //닉네임 존재 확인
        attendances.isContain(name);

        //등교 시간 입력
        String goTime = inputView.readGoTime();

        //등교시간 유효성 검사
        String[] goTimeSplit = goTime.split(":");

        if (goTimeSplit.length != 2) {
            throw new IllegalArgumentException("[ERROR] 잘못된 형식을 입력하였습니다.");
        }

        //시간 범위를 초과하면
        if (Integer.parseInt(goTimeSplit[0]) > 24 || Integer.parseInt(goTimeSplit[1]) > 60) {
            throw new IllegalArgumentException("[ERROR] 잘못된 형식을 입력하였습니다.");

        }

        //등교 가능 날짜와 가능 시간이라면 출석처리
        //월요일이라면 13시 이후 부터 지각,초과 처리
        //화~금 이라면 10시 이후 부터 지각,초과 처리
        //13:00 분 안에 출석인지
        int hour = Integer.parseInt(goTimeSplit[0]);
        int minute = Integer.parseInt(goTimeSplit[1]);

        String status = AttendanceStatus.calculateStatus(dayOfWeek, hour, minute);
        outputView.printTodayAttendanceResult(monthValue, dayOfMonth, dayOfWeek, goTime, status);

        //출석이 완료되면
        //출석부에 기록함
        Attendance attendance = new Attendance(name, 2024, monthValue, dayOfMonth, hour, minute, status);
        attendances.updateAttendance(attendance);

    }
}

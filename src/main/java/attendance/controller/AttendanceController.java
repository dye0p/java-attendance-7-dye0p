package attendance.controller;

import attendance.model.Attendance;
import attendance.model.AttendanceStatus;
import attendance.model.Attendances;
import attendance.model.DayOfWeek;
import attendance.util.CrewFileReader;
import attendance.view.InputView;
import attendance.view.OutputView;
import camp.nextstep.edu.missionutils.DateTimes;
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
        outputView.printToday(monthValue, dayOfMonth, dayOfWeek);
        String option = inputView.readOption();

        //기능이 1번인 경우
        if (option.equals("1")) {
            optionOne(dayOfWeek, monthValue, dayOfMonth, attendances);
        }

        //기능 2번인 경우
        if (option.equals("2")) {

            //출석을 수정하려는 닉네임
            String name = inputView.readUpdateNickName();

            //닉네임이 존재하는지 확인한다.
            attendances.isContain(name);

            //수정하려는 날짜 입력
            String date = inputView.updateDate();

            //해당 닉네임에 대한 일의 출석이 존재하는지 확인한다.
            Attendance attendanceBy = attendances.findAttendanceBy(name, date); //입력한 일자에 출석한 크루

            //수정할 시간 입력
            String updateTime = inputView.updateTime();

        }
    }

    private void optionOne(String dayOfWeek, int monthValue, int dayOfMonth, Attendances attendances) {
        //등교 날짜인지 확인
        if (dayOfWeek.equals("토요일") || dayOfWeek.equals("일요일")) {
            String format = String.format("[ERROR] %d월 %d일 %s은 등교일이 아닙니다.", monthValue, dayOfMonth, dayOfWeek);
            throw new IllegalArgumentException(format);
        }

        //등교 날짜라면

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
        String status = AttendanceStatus.calculateStatus(dayOfWeek, goTimeSplit);
        outputView.printTodayAttendanceResult(monthValue, dayOfMonth, dayOfWeek, goTime, status);

        //출석이 완료되면
        //출석부에 기록함
        int hour = Integer.parseInt(goTimeSplit[0]);
        int minute = Integer.parseInt(goTimeSplit[1]);

        Attendance attendance = new Attendance(name, 2024, monthValue, dayOfMonth, hour, minute);
        attendances.updateAttendance(attendance);
    }
}

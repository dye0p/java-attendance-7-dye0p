package attendance.controller;

import attendance.model.Attendance;
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

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        CrewFileReader crewFileReader = new CrewFileReader();
        List<Attendance> attendanceList = crewFileReader.attendancesReader();
        Attendances attendances = new Attendances(attendanceList);

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

            //등교 날짜인지 확인
            if (dayOfWeek.equals("토요일") || dayOfWeek.equals("일요일")) {
                String format = String.format("[ERROR] %d월 %d일 %s은 등교일이 아닙니다.", monthValue, dayOfMonth, dayOfWeek);
                throw new IllegalArgumentException(format);
            }

            //닉네임 입력
            String name = inputView.readNickName();

            //닉네임 존재 확인
            attendances.isContain(name);
        }
    }
}

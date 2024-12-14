package attendance.controller;

import attendance.model.DayOfWeek;
import attendance.view.InputView;
import attendance.view.OutputView;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        //오늘의 날짜를 가져옴.
        LocalDateTime now = DateTimes.now();

        int monthValue = now.getMonthValue(); //월
        int dayOfMonth = now.getDayOfMonth(); //일
        String dayOfWeek = DayOfWeek.of(now.getDayOfWeek().getValue()); //요일

        outputView.printToday(monthValue, dayOfMonth, dayOfWeek);
        String option = inputView.readOption();
    }
}

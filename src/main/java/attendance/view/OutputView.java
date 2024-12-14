package attendance.view;

public class OutputView {

    private static final String NEXT_LINE = System.lineSeparator();

    public void printToday(int monthValue, int dayOfMonth, String dayOfWeek) {
        String format = String.format("오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요", monthValue, dayOfMonth, dayOfWeek);
        System.out.println(format);
    }

    public void printTodayAttendanceResult(int monthValue, int dayOfMonth, String dayOfWeek, String goTime,
                                           String check) {
        String format = String.format("%d월 %d일 %s %s %s", monthValue, dayOfMonth, dayOfWeek, goTime,
                check);

        System.out.println(NEXT_LINE + format);

    }
}

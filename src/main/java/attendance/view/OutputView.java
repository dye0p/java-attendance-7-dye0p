package attendance.view;

public class OutputView {

    public void printToday(int monthValue, int dayOfMonth, String dayOfWeek) {
        String format = String.format("오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요", monthValue, dayOfMonth, dayOfWeek);
        System.out.println(format);
    }
}

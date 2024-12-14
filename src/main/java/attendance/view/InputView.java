package attendance.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    public String readOption() {
        System.out.println("1. 출석 확인");
        System.out.println("2. 출석 수정");
        System.out.println("3. 크루별 출석 기록 확인");
        System.out.println("4. 제적 위험자 확인");
        System.out.println("Q. 종료");

        String option = Console.readLine().trim();

        if (!option.equals("1") && !option.equals("2") && !option.equals("3") && !option.equals("4")
                && !option.equals("Q")) {
            throw new IllegalArgumentException("[ERROR] 잘못된 형식을 입력하였습니다.");
        }

        return option;
    }
}

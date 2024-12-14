package attendance.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    private static final String NEXT_LINE = System.lineSeparator();

    public String readOption() {
        System.out.println("1. 출석 확인");
        System.out.println("2. 출석 수정");
        System.out.println("3. 크루별 출석 기록 확인");
        System.out.println("4. 제적 위험자 확인");
        System.out.println("Q. 종료");

        String option = Console.readLine().trim();

        if (!option.equals("1") && !option.equals("2") && !option.equals("3") && !option.equals("4") && !option.equals(
                "Q")) {
            throw new IllegalArgumentException("[ERROR] 잘못된 형식을 입력하였습니다.");
        }

        return option;
    }

    public String readNickName() {
        System.out.println(NEXT_LINE + "닉네임을 입력해 주세요.");

        return Console.readLine().trim();
    }

    public String readGoTime() {
        System.out.println("등교 시간을 입력해 주세요.");

        return Console.readLine().trim();
    }

    public String readUpdateNickName() {
        System.out.println(NEXT_LINE + "출석을 수정하려는 크루의 닉네임을 입력해 주세요.");

        return Console.readLine().trim();
    }

    public int updateDate() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");

        String date = Console.readLine();

        return Integer.parseInt(date);

    }

    public String updateTime() {
        System.out.println("언제로 변경하겠습니까?");

        return Console.readLine().trim();

    }
}

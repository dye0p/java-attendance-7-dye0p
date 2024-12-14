package attendance.util;

import attendance.exception.ErrorMessage;
import attendance.model.Attendance;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CrewFileReader {

    public List<Attendance> attendancesReader() {

        File file = new File("src/main/resources/attendances.csv");

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            line = br.readLine(); //한줄 띄기

            List<Attendance> attendances = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                String name = split[0];

                String dateTime = split[1];
                String[] dateTimeSplit = dateTime.split(" ");

                String yearMonthDate = dateTimeSplit[0];
                String[] yearMonthDateSplit = yearMonthDate.split("-");

                int year = Integer.parseInt(yearMonthDateSplit[0]);
                int month = Integer.parseInt(yearMonthDateSplit[1]);
                int date = Integer.parseInt(yearMonthDateSplit[2]);

                String time = dateTimeSplit[1];
                String[] timeSplit = time.split(":");

                int hour = Integer.parseInt(timeSplit[0]);
                int minute = Integer.parseInt(timeSplit[1]);

                Attendance crew = new Attendance(name, year, month, date, hour, minute);

                attendances.add(crew);
            }
            return attendances;

        } catch (IOException exception) {
            throw new IllegalArgumentException(ErrorMessage.NOT_FOUND_FILE.getErrorMessage());
        }
    }
}

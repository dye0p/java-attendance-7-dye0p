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

                Attendance crew = new Attendance(name, dateTime);

                attendances.add(crew);
            }
            return attendances;

        } catch (IOException exception) {
            throw new IllegalArgumentException(ErrorMessage.NOT_FOUND_FILE.getErrorMessage());
        }
    }
}

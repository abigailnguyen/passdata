package seek;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class FileInput implements Input {

    private final String fileName;

    public FileInput(String fileName) {
        this.fileName = fileName;
    }

    private Map<String, Integer> halfHourCarRecord = new LinkedHashMap<>();

    @Override
    public Map<String, Integer> read() throws ErrorReadingInput {
        try {
            File file = new File(fileName);
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                validateInput(data);
            }
            myReader.close();
            return halfHourCarRecord;
        } catch (FileNotFoundException e) {
            throw new ErrorReadingInput("File not found.");
        } catch (NumberFormatException e) {
            throw new ErrorReadingInput("Invalid input format. Number of cars should be a number.");
        } catch (DateTimeParseException e) {
            throw new ErrorReadingInput("Invalid input format. Date time string should be in correct format.");
        }
    }

    void validateInput(String input) throws ErrorReadingInput {
        try {
            var result = input.split(" ");
            int numberOfCars = Integer.parseInt(result[1]);
            if (numberOfCars < 0) {
                throw new ErrorReadingInput("Invalid input format. Number of cars should be positive number.");
            }
            LocalDateTime.parse(result[0]);
            halfHourCarRecord.put(result[0], numberOfCars);
        } catch (NumberFormatException e) {
            throw new ErrorReadingInput("Invalid input format. Number of cars should be a number.");
        } catch (DateTimeParseException e) {
            throw new ErrorReadingInput("Invalid input format. Date time string should be in correct format.");
        }
    }

    class ErrorReadingInput extends RuntimeException {
        private final String message;

        public ErrorReadingInput(String message) {
            this.message = message;
        }

        @Override
        public String getMessage() {
            return this.message;
        }
    }
}

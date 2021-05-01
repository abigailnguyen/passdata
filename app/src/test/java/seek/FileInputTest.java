package seek;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

import seek.FileInput.ErrorReadingInput;

public class FileInputTest {

    @Test
    public void Test_WrongInputDateFormat() throws IOException {
        File file = File.createTempFile("tmp", null);
        FileWriter writer = new FileWriter(file.getAbsolutePath());
        writer.append("2016-12-099T00:00:0000 4");
        writer.close();

        var input = new FileInput(file.getAbsolutePath());
        Exception exception = assertThrows(ErrorReadingInput.class, () -> {
            input.read();
        });

        String expectedMessage = "Invalid input format. Date time string should be in correct format.";
        String actualMessage = exception.getMessage();
        System.out.println(actualMessage);
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void Test_WrongInputNumberFormat() throws IOException {
        File file = File.createTempFile("tmp", null);
        FileWriter writer = new FileWriter(file.getAbsolutePath());
        writer.append("2016-12-099T00:00:0000 a4");
        writer.close();

        var input = new FileInput(file.getAbsolutePath());
        Exception exception = assertThrows(ErrorReadingInput.class, () -> {
            input.read();
        });

        String expectedMessage = "Invalid input format. Number of cars should be a number.";
        String actualMessage = exception.getMessage();
        System.out.println(actualMessage);
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void Test_NegativeInputNumberFormat() throws IOException {
        File file = File.createTempFile("tmp", null);
        FileWriter writer = new FileWriter(file.getAbsolutePath());
        writer.append("2016-12-099T00:00:0000 -5");
        writer.close();

        var input = new FileInput(file.getAbsolutePath());
        Exception exception = assertThrows(ErrorReadingInput.class, () -> {
            input.read();
        });

        String expectedMessage = "Invalid input format. Number of cars should be positive number.";
        String actualMessage = exception.getMessage();
        System.out.println(actualMessage);
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void Test_FileDoesNotExist() {
        var input = new FileInput("not-existing.txt");
        Exception exception = assertThrows(ErrorReadingInput.class, () -> {
            input.read();
        });
        String expectedMessage = "File not found.";
        String actualMessage = exception.getMessage();
        System.out.println(actualMessage);
        assertTrue(actualMessage.contains(expectedMessage));
    }
}

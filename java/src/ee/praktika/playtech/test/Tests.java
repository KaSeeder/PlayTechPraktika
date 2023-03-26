package ee.praktika.playtech.test;

import ee.praktika.playtech.Analyzer;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.*;

public class Tests {

    @Test
    public void testCompareGameData0Files() throws IOException {
        String fileToReadFrom= "game_data_0.txt";
        String file1Path = "resources/analyzer_output_0.txt";
        String file2Path = "analyzer_results.txt";

        Analyzer analyzer = new Analyzer();
        analyzer.analyzeGameData(fileToReadFrom);


        BufferedReader reader1 = new BufferedReader(new FileReader(file1Path));
        BufferedReader reader2 = new BufferedReader(new FileReader(file2Path));

        String line1 = reader1.readLine();
        String line2 = reader2.readLine();
        while (line1 != null && line2 != null) {
            assertEquals(line1, line2);
            line1 = reader1.readLine();
            line2 = reader2.readLine();
        }

        assertNull("file1 and file2 don't have the same number of lines", line1);
        assertNull("file1 and file2 don't have the same number of lines", line2);

        reader1.close();
        reader2.close();
    }


    @Test
    public void testCompareGameData1Files() throws IOException {
        String fileToReadFrom= "game_data_1.txt";
        String file1Path = "resources/analyzer_output_1.txt";
        String file2Path = "analyzer_results.txt";

        Analyzer analyzer = new Analyzer();
        analyzer.analyzeGameData(fileToReadFrom);


        BufferedReader reader1 = new BufferedReader(new FileReader(file1Path));
        BufferedReader reader2 = new BufferedReader(new FileReader(file2Path));

        String line1 = reader1.readLine();
        String line2 = reader2.readLine();
        while (line1 != null && line2 != null) {
            assertEquals(line1, line2);
            line1 = reader1.readLine();
            line2 = reader2.readLine();
        }

        assertNull("file1 and file2 don't have the same number of lines", line1);
        assertNull("file1 and file2 don't have the same number of lines", line2);

        reader1.close();
        reader2.close();
    }

    @Test
    public void testCompareGameData2Files() throws IOException {
        String fileToReadFrom= "game_data_2.txt";
        String file1Path = "resources/analyzer_output_2.txt";
        String file2Path = "analyzer_results.txt";

        Analyzer analyzer = new Analyzer();
        analyzer.analyzeGameData(fileToReadFrom);


        BufferedReader reader1 = new BufferedReader(new FileReader(file1Path));
        BufferedReader reader2 = new BufferedReader(new FileReader(file2Path));

        String line1 = reader1.readLine();
        String line2 = reader2.readLine();
        while (line1 != null && line2 != null) {
            assertEquals(line1, line2);
            line1 = reader1.readLine();
            line2 = reader2.readLine();
        }

        assertNull("file1 and file2 don't have the same number of lines", line1);
        assertNull("file1 and file2 don't have the same number of lines", line2);

        reader1.close();
        reader2.close();
    }
}
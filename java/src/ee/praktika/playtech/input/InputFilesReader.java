package ee.praktika.playtech.input;

import ee.praktika.playtech.model.Game;
import ee.praktika.playtech.model.GameMove;

import java.util.List;
import java.util.Map;

/**
 * interface InputFiles
 */
public interface InputFilesReader {
    /**
     * List where every element is a line from a text file
     *
     * @param filename
     * @return list of strings
     */
    Map<Integer, Game> readTextFromFile(String filename);
}

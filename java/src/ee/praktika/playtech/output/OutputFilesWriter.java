package ee.praktika.playtech.output;

import ee.praktika.playtech.model.GameMove;

import java.util.List;

/**
 * Class outputfileswriter
 */
public interface OutputFilesWriter {

    public boolean writeListIntoFile(List<GameMove> gameMoves);
}
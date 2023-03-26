package ee.praktika.playtech.output.outputFilesWriterImpl;

import ee.praktika.playtech.model.GameMove;
import ee.praktika.playtech.output.OutputFilesWriter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class OutputFilesWriterImpl implements OutputFilesWriter {


    @Override
    public boolean writeListIntoFile(List<GameMove> gameMoves) {
        Path path = Path.of("analyzer_results.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (GameMove move: gameMoves) {
                writer.write(move.toString() + "\n");
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}

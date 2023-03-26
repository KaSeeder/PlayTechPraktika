package ee.praktika.playtech.input.InputFilesReaderImpl;

import ee.praktika.playtech.exceptions.FileReaderException;
import ee.praktika.playtech.input.InputFilesReader;
import ee.praktika.playtech.model.Game;
import ee.praktika.playtech.model.GameMove;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class InputFilesBufferReader
 */
public class InputFilesBufferReader implements InputFilesReader {

    /**
     *
     * @param filename where to read game_data
     * @return Map of Games, which are each sorted by game id and time
     */
    @Override
    public Map<Integer, Game> readTextFromFile(String filename) {
        Path path = Path.of("resources/" + filename);

        Map<Integer, Game> games = new HashMap<>(); // key value, Integer is gameNum
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line = reader.readLine();

            while (line != null) {
                String[] values = line.split(",");
                if (values.length != 6 || values[0].equals("Error")) {
                    line = reader.readLine();
                    continue;
                }
                int timeStamp = Integer.parseInt(values[0]);
                int gameNum = Integer.parseInt(values[1]);
                long playerID = Long.parseLong(values[2]);
                String moveType = values[3];
                String[] dealerCards = values[4].split("-");
                String[] playerCards = values[5].split("-");
                GameMove move = new GameMove(timeStamp, gameNum, playerID, moveType, dealerCards, playerCards);
                if (!games.containsKey(gameNum)) {
                    Game gameObject = new Game();
                    gameObject.addMoveToGame(move);
                    games.put(gameNum, gameObject);
                } else {
                    games.get(gameNum).addMoveToGame(move);
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new FileReaderException("No such file", e);
        }
        for (Game game:games.values()) {
            game.finishInitialization();
        }
        return games;
    }
}
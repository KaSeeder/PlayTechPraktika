package ee.praktika.playtech;

import ee.praktika.playtech.input.InputFilesReaderImpl.InputFilesBufferReader;
import ee.praktika.playtech.model.Game;
import ee.praktika.playtech.model.GameMove;
import ee.praktika.playtech.model.GameMoveSorter;
import ee.praktika.playtech.output.OutputFilesWriter;
import ee.praktika.playtech.output.outputFilesWriterImpl.OutputFilesWriterImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class Analyzer
 */
public class Analyzer {

    private static final Integer DEALER_HAND_VALUE_TO_HIT_FROM = 17;
    private static final Integer MAX_VALUE_TO_WIN_GAME = 21;
    private static final Integer ILLEGAL_TO_HIT = 20;
    private static final Integer OWNS_ILLEGAL_CARD = -1;


    private InputFilesBufferReader inputFilesBufferReader;
    private GameMoveSorter gameMoveSorter;
    private Map<Integer, Game> games;
    private OutputFilesWriter outputFilesWriter;
    private List<GameMove> faultyGames;

    /**
     * Constructor for analyzer
     */
    public Analyzer() {
        this.inputFilesBufferReader = new InputFilesBufferReader();
        this.gameMoveSorter = new GameMoveSorter();
        this.outputFilesWriter = new OutputFilesWriterImpl();
        this.faultyGames = new ArrayList<>();
    }

    /**
     * reads every single game and looks for faulty games, writes them into lists
     * @param filename to analyze all game data from
     */
    public void analyzeGameData(String filename) {
        this.games = inputFilesBufferReader.readTextFromFile(filename);
        for (Game game:games.values()) {
            analyzeGameMoves(game);
        }
        gameMoveSorter.sortByGameID(faultyGames);
        outputFilesWriter.writeListIntoFile(faultyGames);
    }

    private void analyzeGameMoves(Game game) {
        for (GameMove gameMove:game.getListOfGameMoves()) {
            //TODO: use switch case and use Enums instead of strings and ifs
            if (gameMove.getMoveType().equals("P Joined")) {
                if (!checkAfterJoined(gameMove)) {
                    faultyGames.add(gameMove);
                    break;
                }
            }
            if (gameMove.getMoveType().equals("P Hit")) {
                if (!checkAfterPlayerHit(gameMove)) {
                    faultyGames.add(gameMove);
                    break;
                }
            }
            if (gameMove.getMoveType().equals("D Redeal")) {
                if (!checkAfterJoined(gameMove)) {
                    faultyGames.add(gameMove);
                    break;
                }
            }
            if (gameMove.getMoveType().equals("D Hit")) {
                if (!checkAfterDealerHit(gameMove)) {
                    faultyGames.add(gameMove);
                    break;
                }
            }
            if (gameMove.getMoveType().equals("P Lose")) {
                if (!checkAfterPLose(gameMove)) {
                    faultyGames.add(gameMove);
                    break;
                }
            }
            if (gameMove.getMoveType().equals("P Win")) {
                if (!checkAfterPWin(gameMove)) {
                    faultyGames.add(gameMove);
                    break;
                }
            }
            if (gameMove.getMoveType().equals("P Stand")) {
                if (!checkAfterStand(gameMove)) {
                    faultyGames.add(gameMove);
                    break;
                }
            }
        }
    }

    private boolean checkAfterPWin(GameMove gamemove) {
        int dealerHandValue = gamemove.dealerHandValue;
        int playerHandValue = gamemove.playerHandValue;
        if (dealerHandValue < DEALER_HAND_VALUE_TO_HIT_FROM) {
            return false;
        }
        for (String playerCard:gamemove.getPlayerCards()) {
            for (String dealerCard:gamemove.getDealerCards()) {
                if (playerCard.equalsIgnoreCase(dealerCard)) {
                    return false;
                }
            }
        }
        return dealerHandValue <= playerHandValue && playerHandValue < MAX_VALUE_TO_WIN_GAME;
    }

    private boolean checkAfterPLose(GameMove gamemove) {
        int dealerHandValue = gamemove.dealerHandValue;
        int playerHandValue = gamemove.playerHandValue;
        return playerHandValue <= MAX_VALUE_TO_WIN_GAME && dealerHandValue <= MAX_VALUE_TO_WIN_GAME && dealerHandValue > playerHandValue;
    }

    private boolean checkAfterPlayerHit(GameMove gamemove) {
        int playerHandValue = gamemove.playerHandValue;
        return playerHandValue < ILLEGAL_TO_HIT;
    }

    private boolean checkAfterDealerHit(GameMove gamemove) {
        int dealerHandValue = gamemove.dealerHandValue;
        return dealerHandValue < DEALER_HAND_VALUE_TO_HIT_FROM;
    }

    private boolean checkAfterJoined(GameMove gamemove) {
        int hidden = 0;
        for (String card:gamemove.getDealerCards()) {
            if (card.length() > 2 && (Integer.parseInt(String.valueOf(card.charAt(0))) > 1 || Integer.parseInt(String.valueOf(card.charAt(1))) > 1)) {
                return false;
            }
            if (card.equals("?")) {
                hidden += 1;
            }
        }
        if (hidden != 1) {
            return false;
        }
        for (String card:gamemove.getPlayerCards()) {
            if (card.length() > 2 && (Integer.parseInt(String.valueOf(card.charAt(0))) > 1 || Integer.parseInt(String.valueOf(card.charAt(1))) > 1)) {
                return false;
            }
            if (card.equals("?")) {
                return false;
            }
        }
        return true;
    }

    private boolean checkAfterStand(GameMove gameMove) {
        int playerHandValue = gameMove.playerHandValue;
        if (playerHandValue > MAX_VALUE_TO_WIN_GAME || playerHandValue == OWNS_ILLEGAL_CARD) { // >= 21 ??
            return false;
        }

        return true;
    }
}

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

public class Analyzer {

    private InputFilesBufferReader inputFilesBufferReader;
    private String filename = "game_data_1";
    private GameMoveSorter gameMoveSorter;
    private Map<Integer, Game> games;
    private OutputFilesWriter outputFilesWriter;
    private List<GameMove> faultyGames;

    public Analyzer() {
        this.inputFilesBufferReader = new InputFilesBufferReader();
        this.gameMoveSorter = new GameMoveSorter();
        this.outputFilesWriter = new OutputFilesWriterImpl();
        this.faultyGames = new ArrayList<>();
    }

    // D Hit, P Lose, P Stand, P Joined, P Win, P Hit, D redeal,
    public void AnalyzeGameData(String filename) {
        this.games = inputFilesBufferReader.readTextFromFile(filename);
        for (Game game:games.values()) {
            for (GameMove gameMove:game.getListOfGameMoves()) {
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
        gameMoveSorter.sortByGameID(faultyGames);
        outputFilesWriter.writeListIntoFile(faultyGames);
    }

    public boolean checkAfterPWin(GameMove gamemove) {
        int dealerHandValue = gamemove.dealerHandValue;
        int playerHandValue = gamemove.playerHandValue;
        if (dealerHandValue < 17) {
            return false;
        }
        for (String playerCard:gamemove.getPlayerCards()) {
            for (String dealerCard:gamemove.getDealerCards()) {
                if (playerCard.equals(dealerCard)) {
                    return false;
                }
            }
        }
        return dealerHandValue <= playerHandValue && playerHandValue < 21;
    }

    public boolean checkAfterPLose(GameMove gamemove) {
        int dealerHandValue = gamemove.dealerHandValue;
        int playerHandValue = gamemove.playerHandValue;
        return playerHandValue <= 21 && dealerHandValue <= 21 && dealerHandValue > playerHandValue;
    }

    public boolean checkAfterPlayerHit(GameMove gamemove) {
        int playerHandValue = gamemove.playerHandValue;
        return playerHandValue < 20;
    }

    public boolean checkAfterDealerHit(GameMove gamemove) {
        int dealerHandValue = gamemove.dealerHandValue;
        return dealerHandValue < 17;
    }

    public boolean checkAfterJoined(GameMove gamemove) {
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

    public boolean checkAfterStand(GameMove gameMove) {
        int playerHandValue = gameMove.playerHandValue;
        if (playerHandValue > 21 || playerHandValue == -1) { // >= 21 ??
            return false;
        }

        return true;
    }
}

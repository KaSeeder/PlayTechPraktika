package ee.praktika.playtech.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Game Class
 */
public class Game {

    private List<GameMove> listOfGameMoves;
    private boolean initialized;
    private boolean faulty;
    private GameMoveSorter gameMoveSorter;


    /**
     * Constructor for Game
     */
    public Game() {
        this.listOfGameMoves = new ArrayList<>();
        this.initialized = false;
    }

    /**
     *
     * @param gameMove to be added into the Game List of GameMoves
     */
    public void addMoveToGame(GameMove gameMove) {
        listOfGameMoves.add(gameMove);
    }

    public List<GameMove> getListOfGameMoves() {
        return listOfGameMoves;
    }

    /**
     * Sorts the game so it is easier to look through
     */
    public void finishInitialization() {
        gameMoveSorter = new GameMoveSorter();
        gameMoveSorter.sortMoves(listOfGameMoves);
    }


}

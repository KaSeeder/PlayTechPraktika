package ee.praktika.playtech.model;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private List<GameMove> listOfGameMoves;
    private boolean initialized;
    private boolean faulty;
    private GameMoveSorter gameMoveSorter;



    public Game() {
        this.listOfGameMoves = new ArrayList<>();
        this.initialized = false;
    }

    public void addMoveToGame(GameMove gameMove) {
        listOfGameMoves.add(gameMove);
    }

    public List<GameMove> getListOfGameMoves() {
        return listOfGameMoves;
    }

    public void finishInitialization() {
        gameMoveSorter = new GameMoveSorter();
        gameMoveSorter.sortMoves(listOfGameMoves);
    }


}

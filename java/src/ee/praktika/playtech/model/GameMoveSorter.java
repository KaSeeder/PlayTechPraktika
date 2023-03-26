package ee.praktika.playtech.model;

import java.util.Comparator;
import java.util.List;

public class GameMoveSorter {

    public void sortMoves(List<GameMove> moves) {
        moves.sort(Comparator.comparingInt(GameMove::getGameNum)
                .thenComparingInt(GameMove::getTimeStamp));
    }

    public void sortByGameID(List<GameMove> moves) {
        moves.sort(Comparator.comparingInt(GameMove::getGameNum));
    }
}
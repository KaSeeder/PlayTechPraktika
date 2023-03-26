package ee.praktika.playtech.model;

import java.util.Comparator;
import java.util.List;

/**
 * Class GameMoveSorter
 */
public class GameMoveSorter {

    /**
     *
     * @param moves to be sorted by game number and then time stamp
     */
    public void sortMoves(List<GameMove> moves) {
        moves.sort(Comparator.comparingInt(GameMove::getGameNum)
                .thenComparingInt(GameMove::getTimeStamp));
    }

    /**
     *
     * @param moves to be sorted by game number
     */
    public void sortByGameID(List<GameMove> moves) {
        moves.sort(Comparator.comparingInt(GameMove::getGameNum));
    }
}
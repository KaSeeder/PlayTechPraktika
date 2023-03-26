package ee.praktika.playtech.model;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * timeStamp (integer): Timestamp of the turn in UNIX format
 * gameNum (integer): the game number
 * playerID (long): the unique identifier of the player making the move
 * moveType (string): the type of move (e.g., "play", "draw", "discard")
 * cards (array of strings): the cards involved in the move
 */
public class GameMove {
    private final int timeStamp;
    private final int gameNum;
    private final long playerID;
    private final String moveType; // Joined, Hit, Stand, Win, Lose, Left
    private final String[] dealerCards;
    private final String[] playerCards;
    public int dealerHandValue;
    public int playerHandValue;
    private final Map<String, Integer> cardCostValue;

    public GameMove(int timeStamp, int gameNum, long playerID, String moveType, String[] dealerCards, String[] playerCards) {
        this.timeStamp = timeStamp;
        this.gameNum = gameNum;
        this.playerID = playerID;
        this.moveType = moveType;
        this.dealerCards = dealerCards;
        this.playerCards = playerCards;
        this.cardCostValue = getCardCostValueMap();
        this.dealerHandValue = getHandValue(this.dealerCards);
        this.playerHandValue = getHandValue(this.playerCards);
    }

    /**
     * Fills the map with Hearts, Spades, Clubs and Diamond
     *  2 - 10  and Jacks, Queens, Kings and Aces
     * @return Map of card cost values
     */
    public Map<String, Integer> getCardCostValueMap() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        String[] cardSuits = {"S", "H", "C", "D"};
        String[] specialCards = {"J", "Q", "K", "A"};
        for(String cardSuit:cardSuits) {
            for (int i = 2; i<11; i++) {
                String fullName = String.valueOf(i) + cardSuit;
                map.put(fullName, i);
                // 2S, 3S ... 10S
            }

            for (String specialCard:specialCards){
                String fullName = specialCard + cardSuit;
                if (specialCard.equals("A")) {
                    map.put(fullName, 11);
                    continue;
                }
                map.put(fullName, 10);
                // JS, QS, KS, AS
            }
            map.put("?", 0);
        }
        return map;
    }

    /**
     * @param hand of cards
     * @return total value of hand
     */
    public int getHandValue(String[] hand) {
        int handValue = 0;
        for (String card:hand) {
            card = card.toUpperCase();
            if (!cardCostValue.containsKey(card)) {
                return -1;
            }
            int cardValue = cardCostValue.get(card);
            handValue += cardValue;
        }
        return handValue;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public int getGameNum() {
        return gameNum;
    }

    public long getPlayerID() {
        return playerID;
    }

    public String getMoveType() {
        return moveType;
    }

    public String[] getDealerCards() {
        return dealerCards;
    }

    public String[] getPlayerCards() {
        return playerCards;
    }

    public int getDealerHandValue() {
        return dealerHandValue;
    }

    public int getPlayerHandValue() {
        return playerHandValue;
    }

    @Override
    public String toString() {
        return timeStamp +
                "," + gameNum +
                "," + playerID +
                "," + moveType +
                "," + String.join("-", dealerCards) +
                "," + String.join("-", playerCards);
    }
}
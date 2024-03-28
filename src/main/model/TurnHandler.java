package model;

import java.util.List;
import ui.MessagePrinter;

public class TurnHandler {
    private MessagePrinter msgPrinter;

    public TurnHandler() {
        this.msgPrinter = new MessagePrinter();
    }

    // EFFECTS: does the player's turn checks to see whether the card selected by player exists in hand and
    //          whether selNum > lastCardPlayed.getIntNum()
    public Card doPlayerAndCompTurn(List<Card> playerCards, List<Card> compCards, String selectedNum,
                                     String selectedSuit, Card lastCardPlayed) {
        for (Card card : playerCards) {
            boolean isNumMatch = card.getNum().equals(selectedNum);
            boolean isSuitMatch = card.getSuit().equals(selectedSuit);
            if (isNumMatch && isSuitMatch) {
                if (lastCardPlayed == null) {
                    playerCards.remove(card);
                    return doCompTurn(compCards, card);
                }
                if (Integer.parseInt(selectedNum) > lastCardPlayed.getIntNum()) {
                    playerCards.remove(card);
                    return doCompTurn(compCards, card);
                }
                msgPrinter.printMessage("Please select a card number greater than last played card!");
                return lastCardPlayed;
            }
        }
        msgPrinter.printMessage("Please select a card in your hand!");
        return lastCardPlayed;
    }

    // EFFECTS: does the computer turn by checking to see if there is a larger card than the last played card
    //          and plays that card, otherwise opponent will print out "pass"
    private Card doCompTurn(List<Card> compCards, Card lastCardPlayed) {
        if (lastCardPlayed == null) {
            return null;
        } else {
            for (Card card : compCards) {
                if (card.getIntNum() > lastCardPlayed.getIntNum()) {
                    compCards.remove(card);
                    return card;
                }
            }
        }
        msgPrinter.printMessage("Opponent: Pass :(");
        return null;
    }

    public MessagePrinter getMsgPrinter() {
        return this.msgPrinter;
    }
}

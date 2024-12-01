package data_access;

import entity.BlackjackPlayerRecord;
import entity.Deck;
import entity.room.BlackjackRoom;
import use_case.BlackjackRoomDataAccessInterface;

/**
 * In-memory implementation of the DAI of the Blackjack game room.
 */
public class BlackjackRoomDataAccessObject implements BlackjackRoomDataAccessInterface {
    private BlackjackRoom blackjackRoom;
    private BlackjackPlayerRecord blackjackPlayerRecord;

    @Override
    public void createRoom(Deck deck, int bankroll, int minimum_bet) {
        blackjackRoom = new BlackjackRoom(deck, bankroll, minimum_bet);
    }

    @Override
    public BlackjackRoom getRoom() {
        return blackjackRoom;
    }

    @Override
    public void newRound() {
        blackjackRoom.newRound();
    }

    @Override
    public void exitRoom() {
        blackjackRoom = null;
    }

    @Override
    public void createBlackjackPlayerRecord() {
        blackjackPlayerRecord = new BlackjackPlayerRecord();
    }

    @Override
    public BlackjackPlayerRecord getBlackjackPlayerRecord() {
        return blackjackPlayerRecord;
    }

    @Override
    public void recordRound(int won) {
        blackjackPlayerRecord.recordRound(won);
    }

    @Override
    public void updateHandValueRecord(int curHandValue) {
        blackjackPlayerRecord.updateHandValueRecord(curHandValue);
    }

}

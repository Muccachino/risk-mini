import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int soldiers;
    private int cards;

    public Player(String name) {
        this.name = name;
        //TODO: Set Soldiers to correct amount
        this.soldiers = 5;
        this.cards = 3;
    }

    public String getName() {
        return this.name;
    }

    public int getSoldiers() {
        return this.soldiers;
    }

    public void addSoldiers(int soldiers) {
        this.soldiers += soldiers;
    }

    public void removeSoldiers(int soldiers) {
        this.soldiers -= soldiers;
    }

    public int getCards() {
        return this.cards;
    }

    public void addCards(int cards) {
        this.cards += cards;
    }

    public void removeCards(int cards) {
        this.cards -= cards;
    }

    public void cardsToSoldiers() {
        removeCards(3);
        addSoldiers(5);
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Country implements MouseListener {
    private String name;
    private int soldiersInside;
    private Player owner;
    private String continent;
    JPanel panel;
    JLabel soldierLabel;

    public Country(String name, String continent) {
        this.name = name;
        this.soldiersInside = 0;
        this.continent = continent;
    }

    public JPanel draw() {
        panel = new JPanel();
        panel.add(new JLabel(this.name));
        soldierLabel = new JLabel("Soldiers: " + this.soldiersInside);
        panel.add(soldierLabel);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.setOpaque(true);
        panel.setBackground(Color.GREEN);
        panel.addMouseListener(this);
        return panel;
    }

    public void updateCountryPanel() {
        soldierLabel.setText("Soldiers: " + this.soldiersInside);
        if(this.owner == Board.playerOne) {
            panel.setBackground(Color.YELLOW);
        } else {
            panel.setBackground(Color.PINK);
        }
    }

    public String getName() {
        return this.name;
    }

    public int getSoldiersInside() {
        return this.soldiersInside;
    }

    public void addSoldiersInside(int soldiers) {
        this.soldiersInside += soldiers;
    }

    public void removeSoldiersInside(int soldiers) {
        this.soldiersInside -= soldiers;
    }

    public Player getOwner() {
        return this.owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public String getContinent() {
        return this.continent;
    }


    public void placeSoldiers() {
        if(Board.turn.equals("Player One's Turn")){
            this.owner = Board.playerOne;
            Board.playerOne.removeSoldiers(1);
            this.soldiersInside++;
            soldierLabel.setText("Soldiers: " + this.soldiersInside);
            this.panel.setBackground(Color.YELLOW);
        } else {
            this.owner = Board.playerTwo;
            Board.playerTwo.removeSoldiers(1);
            this.soldiersInside++;
            soldierLabel.setText("Soldiers: " + this.soldiersInside);
            this.panel.setBackground(Color.PINK);
        }
        Board.turn = Board.turn.equals("Player One's Turn") ? "Player Two's Turn" : "Player One's Turn";
        Board.playerTurn.setText(Board.turn);
        Board.currentPlayer = Board.currentPlayer == Board.playerOne ? Board.playerTwo : Board.playerOne;

        if(Board.playerTwo.getSoldiers() == 0) {
            Board.phase = "Game Phase";
            Board.currentPhase.setText(Board.phase);
        }
    }


    public void mouseClicked(MouseEvent e) {

        if(Board.phase.equals("Set Soldiers") && (this.owner == null || this.owner == Board.currentPlayer)) {
            placeSoldiers();
        }

        else if(Board.phase.equals("Game Phase")){
            if(Board.attackingCountry == null) {
                Board.attackingCountry = this;
                this.panel.setBackground(Color.CYAN);
            } else if (Board.attackingCountry.getName().equals(this.getName())) {
                Board.attackingCountry = null;
                if (Board.currentPlayer == Board.playerOne) {
                    this.panel.setBackground(Color.YELLOW);
                } else {
                    this.panel.setBackground(Color.PINK);
                }
                Board.attackButton.setEnabled(false);
            } else if (Board.defendingCountry == null) {
                Board.defendingCountry = this;
                this.panel.setBackground(Color.RED);
                Board.attackButton.setEnabled(true);
            } else if (Board.defendingCountry.getName().equals(this.getName())) {
                Board.defendingCountry = null;
                if (Board.currentPlayer == Board.playerOne) {
                    this.panel.setBackground(Color.PINK);
                } else {
                    this.panel.setBackground(Color.YELLOW);
                }
                Board.attackButton.setEnabled(false);
            }

            System.out.println("Attacking Country: " + Board.attackingCountry + ", Defending Country: " + Board.defendingCountry);
        }

        System.out.println("Country: " + this.name + " Soldiers: " + this.soldiersInside + " Owner: " + this.owner);
    }
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}

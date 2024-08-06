import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Country implements MouseListener {
    private String name;
    private int soldiersInside;
    private Player owner;
    private String continent;
    private int soldiersSend;
    private Board parent;
    JPanel panel;
    JLabel soldierLabel;
    JLabel soldierIconLabel;
    ImageIcon soldierIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\soldier_icon.png");


    public Country(String name, String continent, Board parent) {
        this.name = name;
        this.soldiersInside = 0;
        this.continent = continent;
        this.soldiersSend = 0;
        this.parent = parent;
    }

    public JPanel draw() {
        panel = new JPanel(new GridLayout(3,1));
        panel.add(new JLabel(this.name, JLabel.CENTER));

        soldierLabel = new JLabel("Soldiers: " + this.soldiersInside);
        soldierLabel.setHorizontalAlignment(SwingConstants.CENTER);
        soldierLabel.setVerticalAlignment(SwingConstants.TOP);
        panel.add(soldierLabel);

        soldierIconLabel = new JLabel();
        soldierIconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        soldierIconLabel.setVerticalAlignment(SwingConstants.TOP);
        panel.add(soldierIconLabel);

        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.setOpaque(true);
        //panel.setBackground(Color.GREEN);
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
        setSoldierIcons();
    }

    public void removeSoldiersInside(int soldiers) {
        this.soldiersInside -= soldiers;
        setSoldierIcons();
    }
    public void setSoldiersInside(int soldiers) {
        this.soldiersInside = soldiers;
        setSoldierIcons();
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

    public int getSoldiersSend() {
        return this.soldiersSend;
    }

    public void setSoldiersSend(int soldiers) {
        this.soldiersSend = soldiers;
    }

    public void resetSoldiersSend() {
        this.soldiersSend = 0;
    }

    public void setSoldierIcons() {
        panel.remove(soldierIconLabel);
        soldierIconLabel = new JLabel();
        soldierIconLabel.setLayout(new GridLayout(2,10));
        for (int i = 0; i < this.soldiersInside; i++) {
            JLabel icon = new JLabel(soldierIcon);
            soldierIconLabel.add(icon);
        }
        panel.add(soldierIconLabel);
    }

    //TODO: Einfügen, dass erst alle Länder besetzt sein müssen, bevor man mehr Truppen drauf legen darf
    public void placeSoldiers() {
        if(Board.turn.equals("Player One's Turn") && (this.getSoldiersInside() == 0 || parent.allCountriesFilled())){
            this.owner = Board.playerOne;
            Board.playerOne.removeSoldiers(1);
            this.addSoldiersInside(1);
            soldierLabel.setText("Soldiers: " + this.soldiersInside);
            this.panel.setBackground(Color.YELLOW);
            Board.turn = Board.turn.equals("Player One's Turn") ? "Player Two's Turn" : "Player One's Turn";
            Board.playerTurn.setText(Board.turn);
            Board.currentPlayer = Board.currentPlayer == Board.playerOne ? Board.playerTwo : Board.playerOne;

        } else if (Board.turn.equals("Player Two's Turn") && (this.getSoldiersInside() == 0 || parent.allCountriesFilled())) {
            this.owner = Board.playerTwo;
            Board.playerTwo.removeSoldiers(1);
            this.addSoldiersInside(1);
            soldierLabel.setText("Soldiers: " + this.soldiersInside);
            this.panel.setBackground(Color.PINK);
            Board.turn = Board.turn.equals("Player One's Turn") ? "Player Two's Turn" : "Player One's Turn";
            Board.playerTurn.setText(Board.turn);
            Board.currentPlayer = Board.currentPlayer == Board.playerOne ? Board.playerTwo : Board.playerOne;

        }


        if(Board.playerTwo.getSoldiers() == 0) {
            Board.phase = "Attack Phase";
            Board.currentPhase.setText(Board.phase);
            Board.endTurnButton.setEnabled(true);
        }
    }

    public void attackPhase() {
        if(Board.attackingCountry == null && this.getOwner() == Board.currentPlayer && this.getSoldiersInside() > 1) {
            Board.attackingCountry = this;
            this.panel.setBackground(Color.CYAN);
        } else if (Board.attackingCountry != null && Board.attackingCountry.getName().equals(this.getName())) {
            Board.attackingCountry = null;
            if (Board.currentPlayer == Board.playerOne) {
                this.panel.setBackground(Color.YELLOW);
            } else {
                this.panel.setBackground(Color.PINK);
            }
            Board.attackButton.setEnabled(false);
        } else if (Board.attackingCountry != null && Board.defendingCountry == null && parent.checkIfNeighbor(Board.attackingCountry.getName(), this.getName())) {
            Board.defendingCountry = this;
            this.panel.setBackground(Color.RED);
            Board.attackButton.setEnabled(true);
        } else if (Board.defendingCountry != null && Board.defendingCountry.getName().equals(this.getName())) {
            Board.defendingCountry = null;
            if (Board.currentPlayer == Board.playerOne) {
                this.panel.setBackground(Color.PINK);
            } else {
                this.panel.setBackground(Color.YELLOW);
            }
            Board.attackButton.setEnabled(false);
        }
    }

    public void playerOneSetCardTroops() {
        Board.playerOne.removeSoldiers(1);
        this.addSoldiersInside(1);
        soldierLabel.setText("Soldiers: " + this.soldiersInside);
        if(Board.playerOne.getSoldiers() == 0) {
            Board.phase = "Attack Phase";
            Board.currentPhase.setText(Board.phase);
        } else {
            Board.currentPhase.setText("Player One: Set " + Board.playerOne.getSoldiers() + " Soldier(s)");
        }
    }

    public void playerTwoSetCardTroops() {
        Board.playerTwo.removeSoldiers(1);
        this.addSoldiersInside(1);
        soldierLabel.setText("Soldiers: " + this.soldiersInside);
        if(Board.playerTwo.getSoldiers() == 0) {
            Board.phase = "Attack Phase";
            Board.currentPhase.setText(Board.phase);
        } else {
            Board.currentPhase.setText("Player Two: Set " + Board.playerTwo.getSoldiers() + " Soldier(s)");
        }
    }

    public void fortification() {
        if(Board.sendingCountry == null && this.getSoldiersInside() > 1) {
            Board.sendingCountry = this;
            this.panel.setBackground(Color.MAGENTA);
        } else if (Board.sendingCountry.getName().equals(this.getName())) {
            Board.sendingCountry = null;
            if (Board.currentPlayer == Board.playerOne) {
                this.panel.setBackground(Color.YELLOW);
            } else {
                this.panel.setBackground(Color.PINK);
            }
        } else if (Board.receivingCountry == null &&
                    parent.checkIfNeighbor(Board.sendingCountry.getName(), this.getName()) &&
                    Board.sendingCountry.getOwner() == this.getOwner()) {
            Board.receivingCountry = this;
            parent.openSendArmiesWindow();
        }
    }


    public void mouseClicked(MouseEvent e) {

        if(Board.phase.equals("Set Soldiers") && (this.owner == null || this.owner == Board.currentPlayer)) {
            placeSoldiers();
        }
        else if(Board.phase.equals("Attack Phase")){
            attackPhase();
        }
        else if (Board.phase.equals("Player One: Set Soldiers") && this.getOwner() == Board.playerOne) {
            playerOneSetCardTroops();
        }
        else if (Board.phase.equals("Player Two: Set Soldiers") && this.getOwner() == Board.playerTwo) {
           playerTwoSetCardTroops();
        }
        else if (Board.phase.equals("Fortification Phase")) {
            fortification();
        }
        else if (Board.phase.equals("New Troops Phase")) {
            if(this.owner == Board.currentPlayer) {
                this.addSoldiersInside(1);
                Board.currentPlayer.removeSoldiers(1);
                soldierLabel.setText("Soldiers: " + this.soldiersInside);
                Board.currentPhase.setText(Board.phase + " " + Board.currentPlayer.getName() + " " + Board.currentPlayer.getSoldiers() + " Soldier(s)");

                if(Board.currentPlayer == Board.playerOne && Board.playerOne.getSoldiers() == 0 && Board.playerTwo.getSoldiers() != 0) {
                    Board.currentPlayer = Board.playerTwo;
                    Board.turn = Board.currentPlayer.getName() + "'s Turn";
                    Board.playerTurn.setText(Board.turn);
                    Board.currentPhase.setText(Board.phase + " " + Board.currentPlayer.getName() + " " + Board.currentPlayer.getSoldiers() + " Soldier(s)");
                }
                else if(Board.currentPlayer == Board.playerTwo && Board.playerTwo.getSoldiers() == 0 && Board.playerOne.getSoldiers() != 0) {
                    Board.currentPlayer = Board.playerOne;
                    Board.turn = Board.currentPlayer.getName() + "'s Turn";
                    Board.playerTurn.setText(Board.turn);
                    Board.currentPhase.setText(Board.phase + " " + Board.currentPlayer.getName() + " " + Board.currentPlayer.getSoldiers() + " Soldier(s)");
                }
                else if(Board.playerOne.getSoldiers() == 0 && Board.playerTwo.getSoldiers() == 0) {
                    Board.currentPlayer = Board.currentPlayer == Board.playerOne ? Board.playerTwo : Board.playerOne;
                    Board.turn = Board.currentPlayer.getName() + "'s Turn";
                    Board.playerTurn.setText(Board.turn);
                    Board.phase = "Attack Phase";
                    Board.currentPhase.setText(Board.phase);
                }
            }
        }

    }
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}

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

    public JPanel createCountry(Color borderColor) {
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

        panel.setBorder(BorderFactory.createLineBorder(borderColor, 3));
        panel.setOpaque(true);

        panel.addMouseListener(this);
        return panel;
    }

    // Sets correct border color for and after highlighting it
    public void setHighlight(boolean on) {
        if(on) {
            panel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 4));
        } else {
            switch (this.continent) {
                case "A" -> panel.setBorder(BorderFactory.createLineBorder(new Color(249,225,68), 3));
                case "B" -> panel.setBorder(BorderFactory.createLineBorder(new Color(241,115,115),3));
                case "C" -> panel.setBorder(BorderFactory.createLineBorder(new Color(99,189,89),3));
                case "D" -> panel.setBorder(BorderFactory.createLineBorder(new Color(67,80,156),3));
                default -> panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            };
        }
    }


    public void updateCountryPanel() {
        soldierLabel.setText("Soldiers: " + this.soldiersInside);
        if(this.owner == parent.playerOne) {
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

    // Places a soldier and claims the country for the current player at the beginning of the game
    public void placeSoldiers() {
        if(parent.turn.equals("Player One's Turn") && (this.getSoldiersInside() == 0 || parent.allCountriesFilled())){
            this.owner = parent.playerOne;
            parent.playerOne.removeSoldiers(1);
            this.addSoldiersInside(1);
            soldierLabel.setText("Soldiers: " + this.soldiersInside);
            this.panel.setBackground(Color.YELLOW);
            parent.turn = parent.turn.equals("Player One's Turn") ? "Player Two's Turn" : "Player One's Turn";
            parent.playerTurn.setText(parent.turn);
            parent.currentPlayer = parent.currentPlayer == parent.playerOne ? parent.playerTwo : parent.playerOne;

        } else if (parent.turn.equals("Player Two's Turn") && (this.getSoldiersInside() == 0 || parent.allCountriesFilled())) {
            this.owner = parent.playerTwo;
            parent.playerTwo.removeSoldiers(1);
            this.addSoldiersInside(1);
            soldierLabel.setText("Soldiers: " + this.soldiersInside);
            this.panel.setBackground(Color.PINK);
            parent.turn = parent.turn.equals("Player One's Turn") ? "Player Two's Turn" : "Player One's Turn";
            parent.playerTurn.setText(parent.turn);
            parent.currentPlayer = parent.currentPlayer == parent.playerOne ? parent.playerTwo : parent.playerOne;

        }

        // Switches to next phase when the players have no more soldiers to place
        if(parent.playerTwo.getSoldiers() == 0) {
            parent.phase = "Attack Phase";
            parent.currentPhase.setText(parent.phase);
            parent.endTurnButton.setEnabled(true);
        }
    }

    // Setting / Unsetting an attacking and defending country for the Attack Phase
    public void attackPhase() {
        if(parent.attackingCountry == null && this.getOwner() == parent.currentPlayer && this.getSoldiersInside() > 1) {
            parent.attackingCountry = this;
            this.panel.setBackground(Color.CYAN);
        } else if (parent.attackingCountry != null && parent.attackingCountry.getName().equals(this.getName())) {
            parent.attackingCountry = null;
            if (parent.currentPlayer == parent.playerOne) {
                this.panel.setBackground(Color.YELLOW);
            } else {
                this.panel.setBackground(Color.PINK);
            }
            parent.attackButton.setEnabled(false);
        } else if (parent.attackingCountry != null && parent.defendingCountry == null && parent.checkIfNeighbor(parent.attackingCountry.getName(), this.getName())) {
            parent.defendingCountry = this;
            this.panel.setBackground(Color.RED);
            parent.attackButton.setEnabled(true);
        } else if (parent.defendingCountry != null && parent.defendingCountry.getName().equals(this.getName())) {
            parent.defendingCountry = null;
            if (parent.currentPlayer == parent.playerOne) {
                this.panel.setBackground(Color.PINK);
            } else {
                this.panel.setBackground(Color.YELLOW);
            }
            parent.attackButton.setEnabled(false);
        }
    }

    // Updates country when player uses soldiers he gained from cards
    public void playerOneSetCardTroops() {
        parent.playerOne.removeSoldiers(1);
        this.addSoldiersInside(1);
        soldierLabel.setText("Soldiers: " + this.soldiersInside);
        if(parent.playerOne.getSoldiers() == 0) {
            parent.phase = "Attack Phase";
            parent.currentPhase.setText(parent.phase);
        } else {
            parent.currentPhase.setText("Player One: Set " + parent.playerOne.getSoldiers() + " Soldier(s)");
        }
    }

    public void playerTwoSetCardTroops() {
        parent.playerTwo.removeSoldiers(1);
        this.addSoldiersInside(1);
        soldierLabel.setText("Soldiers: " + this.soldiersInside);
        if(parent.playerTwo.getSoldiers() == 0) {
            parent.phase = "Attack Phase";
            parent.currentPhase.setText(parent.phase);
        } else {
            parent.currentPhase.setText("Player Two: Set " + parent.playerTwo.getSoldiers() + " Soldier(s)");
        }
    }

    // Setting / Unsetting a sending and receiving country and opening the Send Armies Window
    public void fortification() {
        if(parent.sendingCountry == null && this.getSoldiersInside() > 1) {
            parent.sendingCountry = this;
            this.panel.setBackground(Color.MAGENTA);
        } else if (parent.sendingCountry.getName().equals(this.getName())) {
            parent.sendingCountry = null;
            if (parent.currentPlayer == parent.playerOne) {
                this.panel.setBackground(Color.YELLOW);
            } else {
                this.panel.setBackground(Color.PINK);
            }
        } else if (parent.receivingCountry == null &&
                    parent.checkIfNeighbor(parent.sendingCountry.getName(), this.getName()) &&
                parent.sendingCountry.getOwner() == this.getOwner()) {
            parent.receivingCountry = this;
            parent.openSendArmiesWindow();
        }
    }

    // Sets new Soldiers at beginning of turn and switches to other player, when first one is done
    public void setNewTroops() {
        if(this.owner == parent.currentPlayer) {
            this.addSoldiersInside(1);
            parent.currentPlayer.removeSoldiers(1);
            soldierLabel.setText("Soldiers: " + this.soldiersInside);
            parent.currentPhase.setText(parent.phase + " " + parent.currentPlayer.getName() + " " + parent.currentPlayer.getSoldiers() + " Soldier(s)");

            if(parent.currentPlayer == parent.playerOne && parent.playerOne.getSoldiers() == 0 && parent.playerTwo.getSoldiers() != 0) {
                parent.currentPlayer = parent.playerTwo;
                parent.turn = parent.currentPlayer.getName() + "'s Turn";
                parent.playerTurn.setText(parent.turn);
                parent.currentPhase.setText(parent.phase + " " + parent.currentPlayer.getName() + " " + parent.currentPlayer.getSoldiers() + " Soldier(s)");
            }
            else if(parent.currentPlayer == parent.playerTwo && parent.playerTwo.getSoldiers() == 0 && parent.playerOne.getSoldiers() != 0) {
                parent.currentPlayer = parent.playerOne;
                parent.turn = parent.currentPlayer.getName() + "'s Turn";
                parent.playerTurn.setText(parent.turn);
                parent.currentPhase.setText(parent.phase + " " + parent.currentPlayer.getName() + " " + parent.currentPlayer.getSoldiers() + " Soldier(s)");
            }
            else if(parent.playerOne.getSoldiers() == 0 && parent.playerTwo.getSoldiers() == 0) {
                parent.currentPlayer = parent.currentPlayer == parent.playerOne ? parent.playerTwo : parent.playerOne;
                parent.turn = parent.currentPlayer.getName() + "'s Turn";
                parent.playerTurn.setText(parent.turn);
                parent.phase = "Attack Phase";
                parent.currentPhase.setText(parent.phase);
            }
        }
    }


    public void mouseClicked(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e)) {

            if(parent.phase.equals("Set Soldiers") && (this.owner == null || this.owner == parent.currentPlayer)) {
                placeSoldiers();
            }
            else if(parent.phase.equals("Attack Phase")){
                attackPhase();
            }
            else if (parent.phase.equals("Player One: Set Soldiers") && this.getOwner() == parent.playerOne) {
                playerOneSetCardTroops();
            }
            else if (parent.phase.equals("Player Two: Set Soldiers") && this.getOwner() == parent.playerTwo) {
                playerTwoSetCardTroops();
            }
            else if (parent.phase.equals("Fortification Phase")) {
                fortification();
            }
            else if (parent.phase.equals("New Troops Phase")) {
                setNewTroops();
            }
        }


    }
    public void mouseEntered(MouseEvent e) {
        //parent.showHideNeighbors(this.getName(), true);
    }
    public void mouseExited(MouseEvent e) {
        //parent.showHideNeighbors(this.getName(), false);
    }
    public void mousePressed(MouseEvent e) {
        if(SwingUtilities.isRightMouseButton(e)){
            parent.showHideNeighbors(this.getName(), true);
        }
    }
    public void mouseReleased(MouseEvent e) {
        if(SwingUtilities.isRightMouseButton(e)){
            parent.showHideNeighbors(this.getName(), false);
        }
    }
}

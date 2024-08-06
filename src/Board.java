import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class Board extends JFrame implements ActionListener {
    public static final int DICE_ROW_HEIGHT = 50;
    public static final int ROW_WIDTH_OUTSIDE = 250;
    public static final int ROW_WIDTH_INSIDE = 500;
    public static final int FIELD_WIDTH = 1000;
    public static final int FIELD_HEIGHT = 500;
    public static final int STAT_ROW_HEIGHT = 50;
    public static String turn = "Player One's Turn";
    public static String phase = "Set Soldiers";
    public static Player playerOne = new Player("Player One");
    public static Player playerTwo = new Player("Player Two");
    public static Player currentPlayer = playerOne;
    public static JLabel playerTurn;
    public static JLabel currentPhase;
    public static JButton attackButton;
    public static JButton endTurnButton;

    JButton playerOneCards;
    JButton playerTwoCards;
    int fortifications = 3;


    public static Country attackingCountry;
    public static Country defendingCountry;

    public static Country sendingCountry;
    public static Country receivingCountry;
    GridBagLayout boardLayout = new GridBagLayout();
    GridBagConstraints boardConstraints = new GridBagConstraints();
    JPanel boardPanel = new JPanel(boardLayout);

    public static Map<String, Country> allCountries = new HashMap<>();
    public static Map<String, String[]> countryNeighbors = new HashMap<>();


    public Board() {
        super("Risk");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        boardLayout.columnWidths = new int[] {ROW_WIDTH_OUTSIDE, ROW_WIDTH_INSIDE, ROW_WIDTH_OUTSIDE};
        boardLayout.rowHeights = new int[] {DICE_ROW_HEIGHT, FIELD_HEIGHT, STAT_ROW_HEIGHT};



        playerTurn = new JLabel(turn, JLabel.CENTER);
        playerTurn.setOpaque(true);
        playerTurn.setBackground(Color.BLUE);
        playerTurn.setForeground(Color.WHITE);
        currentPhase = new JLabel(phase, JLabel.CENTER);
        currentPhase.setOpaque(true);
        currentPhase.setBackground(Color.WHITE);
        endTurnButton = new JButton("End Phase");
        endTurnButton.addActionListener(this);
        endTurnButton.setActionCommand("End Phase");
        endTurnButton.setEnabled(false);
        JPanel allContinents = new JPanel(new GridLayout(2, 2));
        JPanel continentOne = createContinent("A");
        JPanel continentTwo = createContinent("B");
        JPanel continentThree = createContinent("C");
        JPanel continentFour = createContinent("D");
        allContinents.add(continentOne);
        allContinents.add(continentTwo);
        allContinents.add(continentThree);
        allContinents.add(continentFour);

        playerOneCards = new JButton("Player One Cards: " + playerOne.getCards());
        playerOneCards.setBackground(Color.ORANGE);
        playerOneCards.setOpaque(true);
        playerOneCards.addActionListener(this);
        playerOneCards.setActionCommand("playerOneCards");

        playerTwoCards = new JButton("Player Two Cards: " + playerTwo.getCards());
        playerTwoCards.setOpaque(true);
        playerTwoCards.setBackground(Color.ORANGE);
        playerTwoCards.addActionListener(this);
        playerTwoCards.setActionCommand("playerTwoCards");

        attackButton = new JButton("Attack");
        attackButton.addActionListener(this);
        attackButton.setActionCommand("fight");
        attackButton.setEnabled(false);


        boardPanel.add(playerTurn, buildBoardConstraints(0,0,1,1));
        boardPanel.add(currentPhase, buildBoardConstraints(0,1,1,1));
        boardPanel.add(endTurnButton, buildBoardConstraints(0,2,1,1));
        boardPanel.add(allContinents, buildBoardConstraints(1,0,1,3));
        boardPanel.add(playerOneCards, buildBoardConstraints(2,0,1,1));
        boardPanel.add(playerTwoCards, buildBoardConstraints(2,2,1,1));
        boardPanel.add(attackButton, buildBoardConstraints(2,1,1,1));

        boardPanel.setPreferredSize(new Dimension(FIELD_WIDTH, DICE_ROW_HEIGHT + FIELD_HEIGHT + STAT_ROW_HEIGHT));
        setContentPane(boardPanel);

        pack();

        addCountryNeighbors();
    }

    private GridBagConstraints buildBoardConstraints(int row, int col, int rowspan, int colspan) {
        boardConstraints.fill = GridBagConstraints.BOTH;
        boardConstraints.gridy = row;
        boardConstraints.gridx = col;
        boardConstraints.gridwidth = colspan;
        boardConstraints.gridheight = rowspan;
        return boardConstraints;
    };

    private void addCountryNeighbors() {
        countryNeighbors.put("A1", new String[] {"A2", "A4"});
        countryNeighbors.put("A2", new String[] {"A1", "A3", "A5"});
        countryNeighbors.put("A3", new String[] {"A2", "A6", "B1"});
        countryNeighbors.put("A4", new String[] {"A1", "A5", "C1"});
        countryNeighbors.put("A5", new String[] {"A2", "A4", "A6", "C2"});
        countryNeighbors.put("A6", new String[] {"A3", "A5", "B4", "C3"});

        countryNeighbors.put("B1", new String[] {"A3", "B2", "B4"});
        countryNeighbors.put("B2", new String[] {"B1", "B3", "B5"});
        countryNeighbors.put("B3", new String[] {"B2", "B6"});
        countryNeighbors.put("B4", new String[] {"B1", "B5", "A6", "D1"});
        countryNeighbors.put("B5", new String[] {"B2", "B4", "B6", "D2"});
        countryNeighbors.put("B6", new String[] {"B3", "B5", "D3"});

        countryNeighbors.put("C1", new String[] {"C2", "C4", "A4"});
        countryNeighbors.put("C2", new String[] {"C1", "C3", "C5", "A5"});
        countryNeighbors.put("C3", new String[] {"C2", "C6", "A6", "D1"});
        countryNeighbors.put("C4", new String[] {"C1", "C5"});
        countryNeighbors.put("C5", new String[] {"C2", "C4", "C6"});
        countryNeighbors.put("C6", new String[] {"C3", "C5", "D4"});

        countryNeighbors.put("D1", new String[] {"D2", "D4", "B4", "C3"});
        countryNeighbors.put("D2", new String[] {"D1", "D3", "D5", "B5"});
        countryNeighbors.put("D3", new String[] {"D2", "D6", "B6"});
        countryNeighbors.put("D4", new String[] {"D1", "D5", "C6"});
        countryNeighbors.put("D5", new String[] {"D2", "D4", "D6"});
        countryNeighbors.put("D6", new String[] {"D3", "D5"});
    }

    private JPanel createContinent(String name) {
        JPanel continent = new JPanel(new GridLayout(2, 3));

        for (int i = 1; i <= 6; i++) {
            Country c = new Country(name + i, name, this);
            allCountries.put(c.getName(), c);
            JPanel country = c.draw();
            continent.add(country, CENTER_ALIGNMENT);
        }

        return continent;
    }

    public boolean checkIfNeighbor(String countryName, String potentialNeighbor) {
        boolean neighborCheck = false;
        String[] neighbors = countryNeighbors.get(countryName);
        for (String neighbor : neighbors) {
            if(neighbor.equals(potentialNeighbor)) {
                neighborCheck = true;
                break;
            }
        }
        return neighborCheck;
    }

    public int checkOwningContinents(Player player) {
        int continentsOwned = 0;
        int contA = 0;
        int contB = 0;
        int contC = 0;
        int contD = 0;

        for (Country c : allCountries.values()) {
            if(c.getOwner() == player) {
                if(c.getContinent().equals("A")) {
                    contA++;
                    if(contA == 6) continentsOwned++;
                }
                if(c.getContinent().equals("B")) {
                    contB++;
                    if(contB == 6) continentsOwned++;
                }
                if(c.getContinent().equals("C")) {
                    contC++;
                    if(contC == 6) continentsOwned++;
                }
                if(c.getContinent().equals("D")) {
                    contD++;
                    if(contD == 6) continentsOwned++;
                }
            }
        }
        return continentsOwned;
    }

    public void updatePanels() {
        if(allCountries.get(defendingCountry.getName()).getSoldiersInside() == 0) {
            allCountries.get(defendingCountry.getName()).setOwner(attackingCountry.getOwner());
            allCountries.get(defendingCountry.getName()).addSoldiersInside(attackingCountry.getSoldiersInside() - 1);
            allCountries.get(attackingCountry.getName()).setSoldiersInside(1);
            currentPlayer.addCards(1);
        }

        allCountries.get(attackingCountry.getName()).updateCountryPanel();
        allCountries.get(defendingCountry.getName()).updateCountryPanel();

        checkWin();

        if(currentPlayer.getCards() > 5) {
            if(currentPlayer == playerOne) {
                playerOne.cardsToSoldiers();
                playerOneCards.setText("Player One Cards: " + playerOne.getCards());
                phase = "Player One: Set Soldiers";
                currentPhase.setText("Player One: Set " + playerOne.getSoldiers() + " Soldier(s)");
            }
            if(currentPlayer == playerTwo) {
                playerTwo.cardsToSoldiers();
                playerTwoCards.setText("Player Two Cards: " + playerTwo.getCards());
                phase = "Player Two: Set Soldiers";
                currentPhase.setText("Player Two: Set " + playerOne.getSoldiers() + " Soldier(s)");
            }
        }

        attackingCountry = null;
        defendingCountry = null;
    }

    public void openSendArmiesWindow() {
        SendArmies sendArmies = new SendArmies(sendingCountry, receivingCountry, this);
        sendArmies.createSendWindow();
    }

    public void fortificateCountry() {
        allCountries.get(receivingCountry.getName()).addSoldiersInside(sendingCountry.getSoldiersSend());
        allCountries.get(sendingCountry.getName()).removeSoldiersInside(sendingCountry.getSoldiersSend());
        allCountries.get(sendingCountry.getName()).resetSoldiersSend();

        allCountries.get(sendingCountry.getName()).updateCountryPanel();
        allCountries.get(receivingCountry.getName()).updateCountryPanel();

        fortifications--;
        if(fortifications == 0) {
            endTurn();
        } else {
            currentPhase.setText("Fortifications: " + fortifications + " Left");
        }
        sendingCountry = null;
        receivingCountry = null;
    }

    public void getNewTroops(Player player) {
        int newTroops = 0;
        int countriesOwned = 0;
        int continentsOwned = checkOwningContinents(player);

        for (Country c : allCountries.values()) {
            if(c.getOwner() == player) countriesOwned++;
        }
        if((countriesOwned / 3) < 3) {
            newTroops += 3;
        } else {
            newTroops += (int)Math.floor(countriesOwned / 3);
        }
        newTroops += continentsOwned * 3;

        player.addSoldiers(newTroops);
    }

    public void endTurn() {
        currentPlayer = currentPlayer == playerOne ? playerTwo : playerOne;
        turn = currentPlayer.getName() + "'s Turn";
        playerTurn.setText(turn);
        phase = "New Troops Phase";
        getNewTroops(playerOne);
        getNewTroops(playerTwo);
        currentPhase.setText(phase + " " + currentPlayer.getName() + " " + currentPlayer.getSoldiers() + " Soldier(s)");
    }

    public void checkWin() {
        boolean win = true;
        for(Country country : allCountries.values()) {
            if (country.getOwner() != currentPlayer) {
                win = false;
                break;
            }
        }
        if(win) {
            JOptionPane.showMessageDialog(this, currentPlayer.getName() + " has won the game!");
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("fight")) {
            FightWindow fightWindow = new FightWindow(attackingCountry, defendingCountry, this);
            fightWindow.createFightWindow();
        }
        if(currentPlayer == playerOne && e.getActionCommand().equals("playerOneCards") && playerOne.getCards() >= 3 && (phase.equals("Attack Phase") || phase.equals("Fortification Phase"))) {
            playerOne.cardsToSoldiers();
            playerOneCards.setText("Player One Cards: " + playerOne.getCards());
            phase = "Player One: Set Soldiers";
            currentPhase.setText("Player One: Set " + playerOne.getSoldiers() + " Soldier(s)");
        }
        if(currentPlayer == playerTwo && e.getActionCommand().equals("playerTwoCards") && playerTwo.getCards() >= 3 && (phase.equals("Attack Phase") || phase.equals("Fortification Phase"))) {
            playerTwo.cardsToSoldiers();
            playerTwoCards.setText("Player Two Cards: " + playerTwo.getCards());
            phase = "Player Two: Set Soldiers";
            currentPhase.setText("Player Two: Set " + playerOne.getSoldiers() + " Soldier(s)");
        }

        if(e.getActionCommand().equals("End Phase")) {
            if(phase.equals("Attack Phase")) {
                phase = "Fortification Phase";
                currentPhase.setText("Fortifications: " + fortifications + " Left");
                endTurnButton.setText("End Turn");
            }
            else if(phase.equals("Fortification Phase")) {
                endTurn();
            }
        }
    }

    public static void main(String[] args) {
        Board board = new Board();
        board.setVisible(true);

    }

}
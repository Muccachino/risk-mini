import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class Board extends JFrame implements ActionListener {
    public static final int DICE_ROW_HEIGHT = 50;
    public static final int ROW_WIDTH_OUTSIDE = 250;
    public static final int ROW_WIDTH_INSIDE = 700;
    public static final int FIELD_WIDTH = 1200;
    public static final int FIELD_HEIGHT = 800;
    public static final int STAT_ROW_HEIGHT = 50;

    public String turn = "Player One's Turn";
    public JLabel playerTurn;

    public String phase = "Set Soldiers";
    public JLabel currentPhase;

    public Player playerOne = new Player("Player One");
    public Player playerTwo = new Player("Player Two");
    public Player currentPlayer = playerOne;

    public JButton attackButton;
    public JButton endTurnButton;

    JButton playerOneCards;
    JButton playerTwoCards;
    int fortifications = 3;

    public Country attackingCountry;
    public Country defendingCountry;

    public Country sendingCountry;
    public Country receivingCountry;

    public Map<String, Country> allCountries = new HashMap<>();
    public Map<String, String[]> countryNeighbors = new HashMap<>();

    GridBagLayout boardLayout = new GridBagLayout();
    GridBagConstraints boardConstraints = new GridBagConstraints();
    JPanel boardPanel = new JPanel(boardLayout);

    private String boardChoice;


    public Board(String boardChoice) {
        super("Risk");
        this.boardChoice = boardChoice;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        boardLayout.columnWidths = new int[] {ROW_WIDTH_OUTSIDE, ROW_WIDTH_INSIDE, ROW_WIDTH_OUTSIDE};
        boardLayout.rowHeights = new int[] {DICE_ROW_HEIGHT, FIELD_HEIGHT, STAT_ROW_HEIGHT};

        // Top row of game board
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

        // Game Board creation depending on choice in Start Window
        JPanel allContinents = new JPanel();
        switch (this.boardChoice) {
            case "board1" -> {
                GameBoard1 gameBoard1 = new GameBoard1();
                allContinents = new JPanel(new GridLayout(2, 2));
                JPanel continentOne = gameBoard1.createContinent("A", allCountries, this);
                JPanel continentTwo = gameBoard1.createContinent("B", allCountries, this);
                JPanel continentThree = gameBoard1.createContinent("C", allCountries, this);
                JPanel continentFour = gameBoard1.createContinent("D", allCountries, this);

                allContinents.add(continentOne);
                allContinents.add(continentTwo);
                allContinents.add(continentThree);
                allContinents.add(continentFour);
            }
            case "board2" -> allContinents = new GameBoard2().createContinents2(allCountries, this);
            case "board3" -> allContinents = new GameBoard3().createContinents3(allCountries, this);
        }
        allContinents.setBackground(new Color(153,204,255));

        // Bottom row of game board
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


        boardPanel.add(playerTurn, buildBoardConstraints(boardConstraints,0,0,1,1));
        boardPanel.add(currentPhase, buildBoardConstraints(boardConstraints,0,1,1,1));
        boardPanel.add(endTurnButton, buildBoardConstraints(boardConstraints,0,2,1,1));
        boardPanel.add(allContinents, buildBoardConstraints(boardConstraints,1,0,1,3));
        boardPanel.add(playerOneCards, buildBoardConstraints(boardConstraints,2,0,1,1));
        boardPanel.add(playerTwoCards, buildBoardConstraints(boardConstraints,2,2,1,1));
        boardPanel.add(attackButton, buildBoardConstraints(boardConstraints,2,1,1,1));

        boardPanel.setPreferredSize(new Dimension(FIELD_WIDTH, DICE_ROW_HEIGHT + FIELD_HEIGHT + STAT_ROW_HEIGHT));
        setContentPane(boardPanel);

        pack();

        // Choosing correct neighbor references for game board
        switch (boardChoice) {
            case "board1" -> GameBoard1.addCountryNeighbors(countryNeighbors);
            case "board2" -> GameBoard2.addCountryNeighbors2(countryNeighbors);
            case "board3" -> GameBoard3.addCountryNeighbors3(countryNeighbors);
        }

    }

    private GridBagConstraints buildBoardConstraints(GridBagConstraints constraints, int row, int col, int rowspan, int colspan) {
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = row;
        constraints.gridx = col;
        constraints.gridwidth = colspan;
        constraints.gridheight = rowspan;
        return constraints;
    };


    // Highlights a country's neighbors, which can be attacked
    public void showHideNeighbors(String countryName, boolean show) {
        String[] allNeighbors = countryNeighbors.get(countryName);

        for (String neighbor : allNeighbors) {
            allCountries.get(neighbor).setHighlight(show);
        }
    }

    // Checks if all available countries have been chosen at beginning of game
    public boolean allCountriesFilled() {
        boolean allFilled = true;
        for (Country c : allCountries.values()) {
            if (c.getOwner() == null) {
                allFilled = false;
                break;
            }
        }
        return allFilled;
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

    // Checks if a player owns one or more whole continent (for New Troops Phase)
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

    // Updates the state of the countries after an attack
    public void updatePanels() {
        // Check if an attack was successful
        if(allCountries.get(defendingCountry.getName()).getSoldiersInside() == 0) {
            allCountries.get(defendingCountry.getName()).setOwner(attackingCountry.getOwner());
            allCountries.get(defendingCountry.getName()).addSoldiersInside(attackingCountry.getSoldiersInside() - 1);
            allCountries.get(attackingCountry.getName()).setSoldiersInside(1);

            currentPlayer.addCards(1);
            playerOneCards.setText("Player One Cards: " + playerOne.getCards());
            playerTwoCards.setText("Player Two Cards: " + playerTwo.getCards());
        }

        // Colors of countries will be set to their owners color
        allCountries.get(attackingCountry.getName()).updateCountryPanel();
        allCountries.get(defendingCountry.getName()).updateCountryPanel();

        checkWin();

        // If player has more than 5 cards, he is forced to use them
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

    // Opens the Send Armies Window in the Fortification Phase
    public void openSendArmiesWindow() {
        SendArmies sendArmies = new SendArmies(sendingCountry, receivingCountry, this);
        sendArmies.createSendWindow();
    }

    // Updates the countries after soldiers have been sent around
    public void fortifyCountry() {
        allCountries.get(receivingCountry.getName()).addSoldiersInside(sendingCountry.getSoldiersSend());
        allCountries.get(sendingCountry.getName()).removeSoldiersInside(sendingCountry.getSoldiersSend());
        allCountries.get(sendingCountry.getName()).resetSoldiersSend();

        allCountries.get(sendingCountry.getName()).updateCountryPanel();
        allCountries.get(receivingCountry.getName()).updateCountryPanel();

        // After the set amount of sending, the players turn end automatically
        fortifications--;
        if(fortifications == 0) {
            endTurn();
        } else {
            currentPhase.setText("Fortifications: " + fortifications + " Left");
        }
        sendingCountry = null;
        receivingCountry = null;
    }

    // Calculates the new Troops at the beginning of a turn, depending on owned countries and continents
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

}
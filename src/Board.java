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




    public static Country attackingCountry;
    public static Country defendingCountry;
    GridBagLayout boardLayout = new GridBagLayout();
    GridBagConstraints boardConstraints = new GridBagConstraints();
    JPanel boardPanel = new JPanel(boardLayout);

    public static Map<String, Country> allCountries = new HashMap<>();
    //Map<String, JPanel> allCountryPanels = new HashMap<>();


    public Board() {
        super("Risk");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);


        boardLayout.columnWidths = new int[] {ROW_WIDTH_OUTSIDE, ROW_WIDTH_INSIDE, ROW_WIDTH_OUTSIDE};
        boardLayout.rowHeights = new int[] {DICE_ROW_HEIGHT, FIELD_HEIGHT, STAT_ROW_HEIGHT};



        playerTurn = new JLabel(turn, JLabel.CENTER);
        playerTurn.setOpaque(true);
        playerTurn.setBackground(Color.BLUE);
        currentPhase = new JLabel(phase, JLabel.CENTER);
        currentPhase.setOpaque(true);
        currentPhase.setBackground(Color.WHITE);
        JButton endTurnButton = new JButton("End Turn");
        JPanel allContinents = new JPanel(new GridLayout(2, 2));
        JPanel continentOne = createContinent("A");
        JPanel continentTwo = createContinent("B");
        JPanel continentThree = createContinent("C");
        JPanel continentFour = createContinent("D");
        allContinents.add(continentOne);
        allContinents.add(continentTwo);
        allContinents.add(continentThree);
        allContinents.add(continentFour);

        JLabel playerOneCards = new JLabel("Player One Cards: " + playerOne.getCards(), JLabel.CENTER);
        playerOneCards.setBackground(Color.ORANGE);
        playerOneCards.setOpaque(true);
        JLabel playerTwoCards = new JLabel("Player Two Cards: " + playerTwo.getCards(), JLabel.CENTER);
        playerTwoCards.setOpaque(true);
        playerTwoCards.setBackground(Color.ORANGE);

        attackButton = new JButton("Attack");
        attackButton.addActionListener(this);
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

    }


    private GridBagConstraints buildBoardConstraints(int row, int col, int rowspan, int colspan) {
        boardConstraints.fill = GridBagConstraints.BOTH;
        boardConstraints.gridy = row;
        boardConstraints.gridx = col;
        boardConstraints.gridwidth = colspan;
        boardConstraints.gridheight = rowspan;
        return boardConstraints;
    };


    private JPanel createContinent(String name) {
        JPanel continent = new JPanel(new GridLayout(2, 3));

        for (int i = 1; i <= 6; i++) {
            Country c = new Country(name + i, name);
            allCountries.put(c.getName(), c);
            JPanel country = c.draw();
            //allCountryPanels.put(c.getName(), country);
            continent.add(country, CENTER_ALIGNMENT);
        }

        return continent;
    }

    public void updatePanels() {
        if(allCountries.get(defendingCountry.getName()).getSoldiersInside() == 0) {
            allCountries.get(defendingCountry.getName()).setOwner(attackingCountry.getOwner());
            allCountries.get(defendingCountry.getName()).addSoldiersInside(attackingCountry.getSoldiersInside() - 1);
        }

        allCountries.get(attackingCountry.getName()).updateCountryPanel();
        allCountries.get(defendingCountry.getName()).updateCountryPanel();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        FightWindow fightWindow = new FightWindow(attackingCountry, defendingCountry, this);
        fightWindow.createFightWindow();
    }

    public static void main(String[] args) {
        Board board = new Board();
        board.setVisible(true);

    }

}
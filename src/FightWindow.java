import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;

public class FightWindow implements ActionListener {
    public static final int ATTACK_WIDTH = 300;
    public static final int ATTACK_HEIGHT = 300;
    public static final int DICE_HEIGHT = 150;
    public static final int BUTTON_HEIGHT = 50;
    GridBagLayout fightLayout = new GridBagLayout();
    GridBagConstraints fightConstraints = new GridBagConstraints();
    JPanel fightPanel = new JPanel(fightLayout);

    private Board parent;
    private Country attackingCountry;
    private Country defendingCountry;
    private int attackingSoldiers;
    private int defendingSoldiers;
    JDialog frame;
    JLabel a_soldiers;
    JLabel a_attackers;
    JLabel d_soldiers;
    JLabel d_defenders;
    JLabel attackerDice;
    JLabel defenderDice;


    public FightWindow(Country attackingCountry, Country defendingCountry, Board parent) {
        this.attackingCountry = attackingCountry;
        this.defendingCountry = defendingCountry;
        this.parent = parent;
    }

    public void createFightWindow() {
        frame = new JDialog(this.parent, "Fight", true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);

        fightLayout.rowHeights = new int[] {ATTACK_HEIGHT, DICE_HEIGHT, BUTTON_HEIGHT};
        fightLayout.columnWidths = new int[] {ATTACK_WIDTH, ATTACK_WIDTH};

        JPanel attackingPanel = new JPanel(new GridLayout(4,1));
        attackingPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel a_countryName = new JLabel("Country: " + this.attackingCountry.getName());
        a_soldiers = new JLabel("Soldiers in Country: " + this.attackingCountry.getSoldiersInside());
        a_attackers = new JLabel("Attackers: 0");
        JPanel a_buttons = new JPanel();
        JButton a_one = new JButton("One");
        a_one.addActionListener(this);
        a_one.setActionCommand("a_one");
        JButton a_two = new JButton("Two");
        a_two.addActionListener(this);
        a_two.setActionCommand("a_two");
        JButton a_three = new JButton("Three");
        a_three.addActionListener(this);
        a_three.setActionCommand("a_three");
        a_buttons.add(a_one);
        a_buttons.add(a_two);
        a_buttons.add(a_three);
        attackingPanel.add(a_countryName);
        attackingPanel.add(a_soldiers);
        attackingPanel.add(a_attackers);
        attackingPanel.add(a_buttons);

        JPanel defendingPanel = new JPanel(new GridLayout(4,1));
        defendingPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel d_countryName = new JLabel("Country: " + this.defendingCountry.getName());
        d_soldiers = new JLabel("Soldiers in Country: " + this.defendingCountry.getSoldiersInside());
        d_defenders = new JLabel("Defenders: 0");
        JPanel d_buttons = new JPanel();
        JButton d_one = new JButton("One");
        d_one.addActionListener(this);
        d_one.setActionCommand("d_one");
        JButton d_two = new JButton("Two");
        d_two.addActionListener(this);
        d_two.setActionCommand("d_two");
        d_buttons.add(d_one);
        d_buttons.add(d_two);
        defendingPanel.add(d_countryName);
        defendingPanel.add(d_soldiers);
        defendingPanel.add(d_defenders);
        defendingPanel.add(d_buttons);

        JPanel a_dicePanel = new JPanel();
        a_dicePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        attackerDice = new JLabel("Attacker Roll:");
        a_dicePanel.add(attackerDice);

        JPanel d_dicePanel = new JPanel();
        d_dicePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        defenderDice = new JLabel("Defender Roll:");
        d_dicePanel.add(defenderDice);

        JPanel buttonPanel = new JPanel();
        JButton rollDice = new JButton("Roll Dice");
        rollDice.addActionListener(this);
        rollDice.setActionCommand("roll_dice");
        JButton exitFight = new JButton("Exit Fight");
        exitFight.addActionListener(this);
        exitFight.setActionCommand("exit_fight");
        buttonPanel.add(rollDice);
        buttonPanel.add(exitFight);

        fightPanel.add(attackingPanel, buildFightConstraints(0,0,1,1));
        fightPanel.add(defendingPanel, buildFightConstraints(0,1,1,1));
        fightPanel.add(a_dicePanel, buildFightConstraints(1,0,1,1));
        fightPanel.add(d_dicePanel, buildFightConstraints(1,1,1,1));
        fightPanel.add(buttonPanel, buildFightConstraints(2,0,1,2));

        frame.setContentPane(fightPanel);
        frame.pack();
        frame.setVisible(true);

    }

    private GridBagConstraints buildFightConstraints(int row, int col, int rowspan, int colspan) {
        fightConstraints.fill = GridBagConstraints.BOTH;
        fightConstraints.gridy = row;
        fightConstraints.gridx = col;
        fightConstraints.gridwidth = colspan;
        fightConstraints.gridheight = rowspan;
        return fightConstraints;
    };

    public boolean checkEnoughAttackers(int attackers) {
        return (attackingCountry.getSoldiersInside() - attackers) >= 1;
    }

    public boolean checkEnoughDefenders(int defenders) {
        return defendingCountry.getSoldiersInside() - defenders >= 0;
    }

    public void fight() {
        Integer[] a_dices = new Integer[attackingSoldiers];
        for (int i = 0; i < attackingSoldiers; i++) {
            a_dices[i] = rollDice();
        }
        Arrays.sort(a_dices, Collections.reverseOrder());
        attackerDice.setText("Attacker Roll: " + setLabelContent(a_dices));

        Integer[] d_dices = new Integer[defendingSoldiers];
        for (int i = 0; i < defendingSoldiers; i++) {
            d_dices[i] = rollDice();
        }
        Arrays.sort(d_dices, Collections.reverseOrder());
        defenderDice.setText("Defender Roll: " + setLabelContent(d_dices));

        resolveDiceRolls(a_dices, d_dices);
    }


    public int rollDice() {
        return (int)(Math.random() * 6) + 1;
    }

    public String setLabelContent(Integer[] rolls) {
        StringBuilder labelContent = new StringBuilder();
        for (int i = 0; i < rolls.length; i++) {
            if(i == rolls.length - 1){
                labelContent.append(rolls[i]);
            }
            else {
                labelContent.append(rolls[i]).append(" | ");
            }
        }
        return labelContent.toString();
    }

    public void resolveDiceRolls(Integer[] attackerDices, Integer[] defenderDices) {
        int attackerLosses = 0;
        int defenderLosses = 0;
        for (int i = 0; i < defenderDices.length; i++) {
            if(attackerDices[i] > defenderDices[i]){
                defenderLosses++;
            } else {
                attackerLosses++;
            }
        }
        a_attackers.setText("Attackers: 0");
        d_defenders.setText("Defenders: 0");

        attackingSoldiers = 0;
        defendingSoldiers = 0;

        attackingCountry.removeSoldiersInside(attackerLosses);
        defendingCountry.removeSoldiersInside(defenderLosses);

        a_soldiers.setText("Soldiers in Country: " + attackingCountry.getSoldiersInside());
        d_soldiers.setText("Soldiers in Country: " + defendingCountry.getSoldiersInside());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("a_one") && checkEnoughAttackers(1)) {
            attackingSoldiers = 1;
            a_attackers.setText("Attackers: " + attackingSoldiers);
        }
        if(e.getActionCommand().equals("a_two") && checkEnoughAttackers(2)) {
            attackingSoldiers = 2;
            a_attackers.setText("Attackers: " + attackingSoldiers);
        }
        if(e.getActionCommand().equals("a_three") && checkEnoughAttackers(3)) {
            attackingSoldiers = 3;
            a_attackers.setText("Attackers: " + attackingSoldiers);
        }
        if(e.getActionCommand().equals("d_one") && checkEnoughDefenders(1)) {
            defendingSoldiers = 1;
            d_defenders.setText("Defenders: " + defendingSoldiers);
        }
        if(e.getActionCommand().equals("d_two") && checkEnoughDefenders(2)) {
            defendingSoldiers = 2;
            d_defenders.setText("Defenders: " + defendingSoldiers);
        }

        if(e.getActionCommand().equals("roll_dice") && (attackingSoldiers != 0 && defendingSoldiers != 0)) {
            fight();
        }

        if (e.getActionCommand().equals("exit_fight")) {
            parent.updatePanels();
            frame.dispose();
        }
    }
}

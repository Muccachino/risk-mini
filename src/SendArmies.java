import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SendArmies implements ActionListener {
    public static final int SEND_ARMIES_WIDTH = 300;
    public static final int QUESTION_HEIGHT = 100;
    public static final int INPUT_HEIGHT = 100;
    public static final int BUTTON_HEIGHT = 50;
    GridBagLayout sendLayout = new GridBagLayout();
    GridBagConstraints sendConstraints = new GridBagConstraints();
    JPanel sendPanel = new JPanel(sendLayout);

    JDialog dialog;
    JSpinner soldiersSend;
    JButton sendButton;
    JButton cancelButton;
    int soldiers = 0;

    private Board parent;
    private Country sendingCountry;
    private Country receivingCountry;

    public SendArmies(Country sendingCountry, Country receivingCountry, Board parent) {
        this.parent = parent;
        this.sendingCountry = sendingCountry;
        this.receivingCountry = receivingCountry;
    }

    public void createSendWindow() {
        dialog = new JDialog(this.parent, "Send Armies", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setResizable(false);

        sendLayout.columnWidths = new int[] {SEND_ARMIES_WIDTH};
        sendLayout.rowHeights = new int[] {QUESTION_HEIGHT, INPUT_HEIGHT, BUTTON_HEIGHT};

        JLabel question = new JLabel("How many soldiers do you want to send from " + this.sendingCountry.getName() + " to " + this.receivingCountry.getName() + " ?");
        JPanel soldiersPanel = new JPanel(new FlowLayout());
        soldiersSend = new JSpinner(new SpinnerNumberModel(1, 1, sendingCountry.getSoldiersInside() - 1, 1));
        soldiersSend.setSize(100, 100);
        //soldiersSend.addChangeListener(e -> soldiers = (int) soldiersSend.getValue());
        soldiersPanel.add(soldiersSend);
        JPanel buttonPanel = new JPanel();
        sendButton = new JButton("Send");
        sendButton.addActionListener(this);
        sendButton.setActionCommand("send");
        buttonPanel.add(sendButton);
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        cancelButton.setActionCommand("cancel");
        buttonPanel.add(cancelButton);

        sendPanel.add(question, buildSendConstraints(0,0,1,1));
        sendPanel.add(soldiersPanel, buildSendConstraints(1,0,1,1));
        sendPanel.add(buttonPanel, buildSendConstraints(2,0,1,1));

        dialog.setContentPane(sendPanel);
        dialog.pack();
        dialog.setVisible(true);
    }

    private GridBagConstraints buildSendConstraints(int row, int col, int rowspan, int colspan) {
        sendConstraints.fill = GridBagConstraints.BOTH;
        sendConstraints.gridy = row;
        sendConstraints.gridx = col;
        sendConstraints.gridwidth = colspan;
        sendConstraints.gridheight = rowspan;
        return sendConstraints;
    };

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("send")) {
            sendingCountry.setSoldiersSend((int)soldiersSend.getValue());
            System.out.println("Soldiers Send: " + sendingCountry.getSoldiersSend());
            parent.fortificateCountry();
            dialog.dispose();
        }
        else if(e.getActionCommand().equals("cancel")) {
            dialog.dispose();
        }
    }
}

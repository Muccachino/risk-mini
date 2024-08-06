import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;

public class Board2 {

    GridBagLayout continentLayout2 = new GridBagLayout();
    GridBagConstraints continentConstraints2 = new GridBagConstraints();
    JPanel continentPanel2 = new JPanel(continentLayout2);

    public void createContinents2() {
        continentLayout2.columnWidths = new int[] {200,200,200,200,200,200};
        continentLayout2.rowHeights = new int[] {200,200,200,200,200,200};

        
    }

    private GridBagConstraints buildBoardConstraints(int row, int col) {
        continentConstraints2.fill = GridBagConstraints.BOTH;
        continentConstraints2.gridy = row;
        continentConstraints2.gridx = col;
        continentConstraints2.gridwidth = 1;
        continentConstraints2.gridheight = 1;
        return continentConstraints2;
    };
}

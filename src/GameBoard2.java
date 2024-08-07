import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class GameBoard2 {

    GridBagLayout continentLayout2 = new GridBagLayout();
    GridBagConstraints continentConstraints2 = new GridBagConstraints();
    JPanel continentPanel2 = new JPanel(continentLayout2);

    public JPanel createContinents2(Map<String, Country> allCountries, Board board) {
        continentLayout2.columnWidths = new int[] {100,100,100,100,100,100,100,100};
        continentLayout2.rowHeights = new int[] {100,100,100,100,100,100,100};

        Country a1 = new Country("A1", "A", board);
        allCountries.put(a1.getName(), a1);
        JPanel countryA1Panel = a1.createCountry(new Color(249,225,68));
        continentPanel2.add(countryA1Panel, buildBoardConstraints(continentConstraints2, 0,0, 1, 1));

        Country a2 = new Country("A2", "A", board);
        allCountries.put(a2.getName(), a2);
        JPanel countryA2Panel = a2.createCountry(new Color(249,225,68));
        continentPanel2.add(countryA2Panel, buildBoardConstraints(continentConstraints2, 0,2, 1, 1));

        Country a3 = new Country("A3", "A", board);
        allCountries.put(a3.getName(), a3);
        JPanel countryA3Panel = a3.createCountry(new Color(249,225,68));
        continentPanel2.add(countryA3Panel, buildBoardConstraints(continentConstraints2, 1,1, 1, 1));

        Country a4 = new Country("A4", "A", board);
        allCountries.put(a4.getName(), a4);
        JPanel countryA4Panel = a4.createCountry(new Color(249,225,68));
        continentPanel2.add(countryA4Panel, buildBoardConstraints(continentConstraints2, 1,3, 1, 1));

        Country a5 = new Country("A5", "A", board);
        allCountries.put(a5.getName(), a5);
        JPanel countryA5Panel = a5.createCountry(new Color(249,225,68));
        continentPanel2.add(countryA5Panel, buildBoardConstraints(continentConstraints2, 2,0, 1, 1));

        Country a6 = new Country("A6", "A", board);
        allCountries.put(a6.getName(), a6);
        JPanel countryA6Panel = a6.createCountry(new Color(249,225,68));
        continentPanel2.add(countryA6Panel, buildBoardConstraints(continentConstraints2, 2,2, 1, 1));

        Country b1 = new Country("B1", "B", board);
        allCountries.put(b1.getName(), b1);
        JPanel countryB1Panel = b1.createCountry(new Color(241,115,115));
        continentPanel2.add(countryB1Panel, buildBoardConstraints(continentConstraints2, 0,5, 1, 1));

        Country b2 = new Country("B2", "B", board);
        allCountries.put(b2.getName(), b2);
        JPanel countryB2Panel = b2.createCountry(new Color(241,115,115));
        continentPanel2.add(countryB2Panel, buildBoardConstraints(continentConstraints2, 0,6, 1, 1));

        Country b3 = new Country("B3", "B", board);
        allCountries.put(b3.getName(), b3);
        JPanel countryB3Panel = b3.createCountry(new Color(241,115,115));
        continentPanel2.add(countryB3Panel, buildBoardConstraints(continentConstraints2, 1,4, 1, 1));

        Country b4 = new Country("B4", "B", board);
        allCountries.put(b4.getName(), b4);
        JPanel countryB4Panel = b4.createCountry(new Color(241,115,115));
        continentPanel2.add(countryB4Panel, buildBoardConstraints(continentConstraints2, 1,7, 1, 1));

        Country b5 = new Country("B5", "B", board);
        allCountries.put(b5.getName(), b5);
        JPanel countryB5Panel = b5.createCountry(new Color(241,115,115));
        continentPanel2.add(countryB5Panel, buildBoardConstraints(continentConstraints2, 2,5, 1, 1));

        Country b6 = new Country("B6", "B", board);
        allCountries.put(b6.getName(), b6);
        JPanel countryB6Panel = b6.createCountry(new Color(241,115,115));
        continentPanel2.add(countryB6Panel, buildBoardConstraints(continentConstraints2, 2,6, 1, 1));

        Country c1 = new Country("C1", "C", board);
        allCountries.put(c1.getName(), c1);
        JPanel countryC1Panel = c1.createCountry(new Color(99,189,89));
        continentPanel2.add(countryC1Panel, buildBoardConstraints(continentConstraints2, 3,2, 1, 1));

        Country c2 = new Country("C2", "C", board);
        allCountries.put(c2.getName(), c2);
        JPanel countryC2Panel = c2.createCountry(new Color(99,189,89));
        continentPanel2.add(countryC2Panel, buildBoardConstraints(continentConstraints2, 4,2, 1, 1));

        Country c3 = new Country("C3", "C", board);
        allCountries.put(c3.getName(), c3);
        JPanel countryC3Panel = c3.createCountry(new Color(99,189,89));
        continentPanel2.add(countryC3Panel, buildBoardConstraints(continentConstraints2, 5,0, 1, 1));

        Country c4 = new Country("C4", "C", board);
        allCountries.put(c4.getName(), c4);
        JPanel countryC4Panel = c4.createCountry(new Color(99,189,89));
        continentPanel2.add(countryC4Panel, buildBoardConstraints(continentConstraints2, 5,1, 1, 1));

        Country c5 = new Country("C5", "C", board);
        allCountries.put(c5.getName(), c5);
        JPanel countryC5Panel = c5.createCountry(new Color(99,189,89));
        continentPanel2.add(countryC5Panel, buildBoardConstraints(continentConstraints2, 5,3, 1, 1));

        Country c6 = new Country("C6", "C", board);
        allCountries.put(c6.getName(), c6);
        JPanel countryC6Panel = c6.createCountry(new Color(99,189,89));
        continentPanel2.add(countryC6Panel, buildBoardConstraints(continentConstraints2, 6,2, 1, 1));

        Country d1 = new Country("D1", "D", board);
        allCountries.put(d1.getName(), d1);
        JPanel countryD1Panel = d1.createCountry(new Color(67,80,156));
        continentPanel2.add(countryD1Panel, buildBoardConstraints(continentConstraints2, 3,4, 1, 1));

        Country d2 = new Country("D2", "D", board);
        allCountries.put(d2.getName(), d2);
        JPanel countryD2Panel = d2.createCountry(new Color(67,80,156));
        continentPanel2.add(countryD2Panel, buildBoardConstraints(continentConstraints2, 3,7, 1, 1));

        Country d3 = new Country("D3", "D", board);
        allCountries.put(d3.getName(), d3);
        JPanel countryD3Panel = d3.createCountry(new Color(67,80,156));
        continentPanel2.add(countryD3Panel, buildBoardConstraints(continentConstraints2, 4,5, 1, 1));

        Country d4 = new Country("D4", "D", board);
        allCountries.put(d4.getName(), d4);
        JPanel countryD4Panel = d4.createCountry(new Color(67,80,156));
        continentPanel2.add(countryD4Panel, buildBoardConstraints(continentConstraints2, 4,6, 1, 1));

        Country d5 = new Country("D5", "D", board);
        allCountries.put(d5.getName(), d5);
        JPanel countryD5Panel = d5.createCountry(new Color(67,80,156));
        continentPanel2.add(countryD5Panel, buildBoardConstraints(continentConstraints2, 5,5, 1, 1));

        Country d6 = new Country("D6", "D", board);
        allCountries.put(d6.getName(), d6);
        JPanel countryD6Panel = d6.createCountry(new Color(67,80,156));
        continentPanel2.add(countryD6Panel, buildBoardConstraints(continentConstraints2, 5,6, 1, 1));

        return continentPanel2;
    }

    private GridBagConstraints buildBoardConstraints(GridBagConstraints constraints, int row, int col,  int rowspan, int colspan) {
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = row;
        constraints.gridx = col;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        return constraints;
    };

    public static void addCountryNeighbors2(Map<String, String[]> countryNeighbors) {
        countryNeighbors.put("A1", new String[] {"A3"});
        countryNeighbors.put("A2", new String[] {"A3", "A4"});
        countryNeighbors.put("A3", new String[] {"A1", "A2", "A5", "A6"});
        countryNeighbors.put("A4", new String[] {"A2", "A6", "B3"});
        countryNeighbors.put("A5", new String[] {"A3"});
        countryNeighbors.put("A6", new String[] {"A3", "A4","C1"});

        countryNeighbors.put("B1", new String[] {"B2", "B3"});
        countryNeighbors.put("B2", new String[] {"B1", "B4"});
        countryNeighbors.put("B3", new String[] {"B1", "B5", "A4"});
        countryNeighbors.put("B4", new String[] {"B2", "B6"});
        countryNeighbors.put("B5", new String[] {"B3", "B6", "D1"});
        countryNeighbors.put("B6", new String[] {"B4", "B5", "D2"});

        countryNeighbors.put("C1", new String[] {"C2", "A6", "D1"});
        countryNeighbors.put("C2", new String[] {"C1", "C4", "C5"});
        countryNeighbors.put("C3", new String[] {"C4"});
        countryNeighbors.put("C4", new String[] {"C2", "C3", "C6"});
        countryNeighbors.put("C5", new String[] {"C2", "C6", "D5"});
        countryNeighbors.put("C6", new String[] {"C4", "C5"});

        countryNeighbors.put("D1", new String[] {"D3", "B5", "C1"});
        countryNeighbors.put("D2", new String[] {"D4", "B6"});
        countryNeighbors.put("D3", new String[] {"D1", "D4", "D5"});
        countryNeighbors.put("D4", new String[] {"D2", "D3", "D6"});
        countryNeighbors.put("D5", new String[] {"D3", "D6", "C5"});
        countryNeighbors.put("D6", new String[] {"D4", "D5"});
    }
}

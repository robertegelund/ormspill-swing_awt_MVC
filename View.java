import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class View {
    private Controller controller;
    int antRader, antKolonner;
    JLabel[][] brett;
    private JFrame vindu;
    private JPanel hovedpanel, statusPanel, styrePanel, brettPanel;
    private JLabel lengde = new JLabel("", SwingConstants.CENTER);

    View(Controller controller, int antRader, int antKolonner) {
        this.controller = controller;
        this.antRader = antRader; this.antKolonner = antKolonner;
        brett = new JLabel[antRader][antKolonner];

        try {
            UIManager.setLookAndFeel(
                UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) { System.exit(1); }

        vindu = new JFrame("Slangespillet");
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        settOppPaneler();
        settOppStyreKnapper();
        settOppBrett();
        
        vindu.pack(); vindu.setVisible(true);
    }

    private void settOppBrett() {
        for(int rad = 0; rad < antRader; rad++) {
            for(int kolonne = 0; kolonne < antKolonner; kolonne++) {
                JLabel rute = new JLabel("", SwingConstants.CENTER);
                brett[rad][kolonne] = rute;
                rute.setPreferredSize(new Dimension(35, 35));
                rute.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                brettPanel.add(rute);
            }
        }
    }
    
    public void tegnSkatt(int rad, int kolonne) {
        JLabel rute = brett[rad][kolonne];
        rute.setText("$");
        rute.setForeground(Color.RED);
    }

    public void tegnSlangedel(String del, int rad, int kolonne) {
        JLabel rute = brett[rad][kolonne];
        rute.setText(del);
        rute.setBackground(Color.GREEN);
        rute.setForeground(Color.BLACK);
        rute.setOpaque(true);
    }

    public void fjernSlangedel(int rad, int kolonne) {
        brett[rad][kolonne].setText("");
        brett[rad][kolonne].setBackground(Color.WHITE);
    }

    private void settOppStyreKnapper() {
        JButton opp = new JButton("Opp");
        JButton ned = new JButton("Ned");
        JButton venstre = new JButton("Venstre");
        JButton hoyre = new JButton("Hoyre");
        JButton slutt = new JButton("Slutt");

        opp.addActionListener(new GaaNord());
        ned.addActionListener(new GaaSor());
        venstre.addActionListener(new GaaVest());
        hoyre.addActionListener(new GaaOst());
        slutt.addActionListener(new Slutt());
        
        styrePanel.add(opp, BorderLayout.NORTH); styrePanel.add(ned, BorderLayout.SOUTH); 
        styrePanel.add(venstre, BorderLayout.WEST); styrePanel.add(hoyre, BorderLayout.EAST);
        statusPanel.add(slutt);
    }

    private void settOppPaneler() {
        hovedpanel = new JPanel();
        hovedpanel.setLayout(new BorderLayout());
        vindu.add(hovedpanel); 
       
        statusPanel = new JPanel();
        statusPanel.setLayout(new GridLayout(1, 3));
        hovedpanel.add(statusPanel, BorderLayout.NORTH);

        statusPanel.add(lengde);

        styrePanel = new JPanel();
        styrePanel.setLayout(new BorderLayout());
        statusPanel.add(styrePanel);
        
        brettPanel = new JPanel();
        brettPanel.setLayout(new GridLayout(antRader, antKolonner));
        brettPanel.setBackground(Color.WHITE);
        brettPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        hovedpanel.add(brettPanel, BorderLayout.SOUTH);
    }

    public void visSlangeLengde(int slangeLengde) {
        String stoerrelse = "Lengde: " + slangeLengde;
        lengde.setText(stoerrelse);
    }

    public void gameOver() {
        brettPanel.setBackground(Color.BLACK);
        lengde.setText("GAME OVER");
    }

    class GaaSor implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            controller.endreRetning("SOR");
        }
    }

    class GaaNord implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            controller.endreRetning("NORD");
        }
    }

    class GaaVest implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            controller.endreRetning("VEST");
        }
    }

    class GaaOst implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            controller.endreRetning("OST");
        }
    }

    class Slutt implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            System.exit(0);
        }
    }
}

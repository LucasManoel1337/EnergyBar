package energy.bar;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

class TelaAnotacoes extends JPanel {
    public TelaAnotacoes() {
        setLayout(new BorderLayout());
        
        JLabel EnergyBar = new JLabel("ENERGY BAR", SwingConstants.CENTER);
        EnergyBar.setFont(new Font("Arial", Font.BOLD, 32));
        add(EnergyBar, BorderLayout.PAGE_START);
        
        JLabel label = new JLabel("Tela Anotações", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        add(label, BorderLayout.CENTER);
    }
}
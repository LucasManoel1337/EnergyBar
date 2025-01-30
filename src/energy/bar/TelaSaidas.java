package energy.bar;

import energy.bar.bancoDeDados.Diretorios;
import energy.bar.support.LabelEnergyBar;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

class TelaSaidas extends JPanel {

    Diretorios dir = new Diretorios();
    LabelEnergyBar labelEnergyBar = new LabelEnergyBar();

    public TelaSaidas() {
        setLayout(new BorderLayout());

        // Criando e adicionando a label EnergyBar
        JLabel energyBarLabel = labelEnergyBar.criarLabelEnergyBar(dir);
        add(energyBarLabel);

        JLabel label = new JLabel("Tela Saídas", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        add(label, BorderLayout.CENTER);
    }
}

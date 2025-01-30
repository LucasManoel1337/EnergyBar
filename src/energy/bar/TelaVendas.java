package energy.bar;

import energy.bar.bancoDeDados.Diretorios;
import energy.bar.support.LabelEnergyBar;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

class TelaVendas extends JPanel {

    Diretorios dir = new Diretorios();
    LabelEnergyBar labelEnergyBar = new LabelEnergyBar();

    public TelaVendas() {
        setLayout(new BorderLayout());

        // Criando e adicionando a label EnergyBar
        JLabel energyBarLabel = labelEnergyBar.criarLabelEnergyBar(dir);
        add(energyBarLabel);

        JLabel label = new JLabel("Tela Vendas", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        add(label, BorderLayout.CENTER);
    }
}

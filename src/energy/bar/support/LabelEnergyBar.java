package energy.bar.support;

import energy.bar.bancoDeDados.Diretorios;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class LabelEnergyBar {

    public static JLabel criarLabelEnergyBar(Diretorios dir) {
        JLabel EnergyBar = new JLabel("ENERGY BAR", JLabel.CENTER);
        try {
            Font minhaFonte = Font.createFont(Font.TRUETYPE_FONT, new File(dir.getDirFonteEnergyBar()));
            minhaFonte = minhaFonte.deriveFont(Font.BOLD, 32);
            EnergyBar.setFont(minhaFonte);
            EnergyBar.setForeground(Color.RED);
        } catch (Exception e) {
            System.err.println("Erro ao carregar a fonte: " + e.getMessage());
            // Use uma fonte padrão caso ocorra algum erro
            EnergyBar.setFont(new Font("Arial", Font.BOLD, 32));
        }
        EnergyBar.setBounds(225, 10, 350, 40); // Define posição e tamanho
        return EnergyBar;
    }
}

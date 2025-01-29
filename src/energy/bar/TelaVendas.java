/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package energy.bar;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

class TelaVendas extends JPanel {
    public TelaVendas() {
        setLayout(new BorderLayout());
        
        JLabel EnergyBar = new JLabel("ENERGY BAR", SwingConstants.CENTER);
        EnergyBar.setFont(new Font("Arial", Font.BOLD, 32));
        add(EnergyBar, BorderLayout.PAGE_START);
        
        JLabel label = new JLabel("Tela Vendas", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        add(label, BorderLayout.CENTER);
    }
}
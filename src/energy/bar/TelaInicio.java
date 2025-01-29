package energy.bar;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

class TelaInicio extends JPanel {

    private double valorDoEstoque;
    private JLabel lValorDeEstoque;

    public TelaInicio() throws IOException {
        setLayout(null);

        // Título principal
        JLabel EnergyBar = new JLabel("ENERGY BAR", JLabel.CENTER);
        EnergyBar.setFont(new Font("Arial", Font.BOLD, 32));
        EnergyBar.setBounds(250, 20, 300, 40); // Define posição e tamanho
        add(EnergyBar, SwingConstants.CENTER);
        
        JLabel lPesquisaVazio = new JLabel("Valor total de estoque");
        lPesquisaVazio.setFont(new Font("Arial", Font.BOLD, 16));
        lPesquisaVazio.setBounds(75, 75, 550, 40); // Define posição e tamanho
        lPesquisaVazio.setForeground(Color.BLACK);
        lPesquisaVazio.setVisible(true);
        add(lPesquisaVazio);
        valorDoEstoque = calcularValorTotalEstoque("Estoque de produtos");

        // Label for stock value
        lValorDeEstoque = new JLabel("R$ " + valorDoEstoque, JLabel.CENTER);
        lValorDeEstoque.setFont(new Font("Arial", Font.BOLD, 32));
        lValorDeEstoque.setBounds(10, 100, 300, 40); // Define posição e tamanho
        add(lValorDeEstoque, SwingConstants.CENTER);
        
        JButton bAtualizar = new JButton("Atualizar dados");
        bAtualizar.setFont(new Font("Arial", Font.BOLD, 14));
        bAtualizar.setBounds(590, 460, 160, 30);
        bAtualizar.setBackground(new Color(32, 3, 3));
        bAtualizar.setForeground(Color.WHITE);
        bAtualizar.setFocusPainted(false);
        bAtualizar.setBorderPainted(false);
        bAtualizar.setVisible(true);
        bAtualizar.addActionListener(e -> {
    try {
        double novoValorTotal = calcularValorTotalEstoque("Estoque de produtos");
        // Verificar se o valor calculado é válido
        if (novoValorTotal >= 0) {
            lValorDeEstoque.setText("R$ " + novoValorTotal);
        } else {
            System.err.println("Valor total do estoque é negativo ou inválido.");
        }
    } catch (IOException ex) {
        System.err.println("Erro ao atualizar o valor do estoque: " + ex.getMessage());
    }
});
        add(bAtualizar);
    }

    public static double calcularValorTotalEstoque(String caminhoPasta) throws IOException {
        BigDecimal valorTotal = BigDecimal.ZERO;
        File pasta = new File(caminhoPasta);
        File[] arquivos = pasta.listFiles();

        if (arquivos != null) {
            for (File arquivo : arquivos) {
                if (arquivo.isFile() && arquivo.getName().endsWith(".txt")) {
                    try (Scanner scanner = new Scanner(new FileReader(arquivo))) {
                        while (scanner.hasNextLine()) {
                            String linha = scanner.nextLine();
                            if (linha.startsWith("Valor de Custo: ")) {
                                String valorCustoStr = linha.substring(14).trim();
                                // Remover todos os caracteres não numéricos e vírgulas
                                valorCustoStr = valorCustoStr.replaceAll("[^0-9,.]", "");
                                try {
                                    // Converter para BigDecimal para garantir precisão
                                    BigDecimal valor = new BigDecimal(valorCustoStr.replace(",", "."));
                                    valorTotal = valorTotal.add(valor);
                                } catch (NumberFormatException e) {
                                    System.err.println("Erro ao converter valor de custo para número: " + valorCustoStr + " no arquivo " + arquivo.getName());
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }

        return valorTotal.doubleValue();
    }
}
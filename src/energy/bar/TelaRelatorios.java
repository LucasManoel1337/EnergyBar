package energy.bar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

class TelaRelatorios extends JPanel {

    private String valorDoEstoque; // Alterado para String
    private JLabel lValorDeEstoque;
    private JLabel lLucroLiquido30;
    private JLabel lLucroLiquido7;
    private JLabel lLucroLiquidoD;

    public TelaRelatorios() throws IOException {
        setLayout(null);

        // Título principal
        JLabel EnergyBar = new JLabel("ENERGY BAR", JLabel.CENTER);
        try {
            Font minhaFonte = Font.createFont(Font.TRUETYPE_FONT, new File("DelaGothicOne-Regular.ttf"));
            minhaFonte = minhaFonte.deriveFont(Font.BOLD, 32);
            EnergyBar.setFont(minhaFonte);
            EnergyBar.setForeground(Color.red);
        } catch (Exception e) {
            System.err.println("Erro ao carregar a fonte: " + e.getMessage());
            // Use uma fonte padrão caso ocorra algum erro
            Font fontPadrao = new Font("Arial", Font.BOLD, 32);
            EnergyBar.setFont(fontPadrao);
        }
        EnergyBar.setBounds(225, 10, 350, 40); // Define posição e tamanho
        add(EnergyBar, SwingConstants.CENTER);
        
        JLabel lTValorDeEstoque = new JLabel("Valor total de estoque");
        lTValorDeEstoque.setFont(new Font("Arial", Font.BOLD, 16));
        lTValorDeEstoque.setBounds(75, 75, 550, 40); // Define posição e tamanho
        lTValorDeEstoque.setForeground(Color.BLACK);
        lTValorDeEstoque.setVisible(true);
        add(lTValorDeEstoque);
        
        valorDoEstoque = calcularValorTotalEstoque("Estoque de produtos"); // Agora retorna String
        // Label for stock value
        lValorDeEstoque = new JLabel("R$ " + valorDoEstoque, JLabel.CENTER);
        lValorDeEstoque.setFont(new Font("Arial", Font.BOLD, 32));
        lValorDeEstoque.setBounds(10, 100, 300, 40); // Define posição e tamanho
        add(lValorDeEstoque, SwingConstants.CENTER);
        
        JPanel painelFundo1 = new JPanel();
        painelFundo1.setBackground(Color.LIGHT_GRAY); // Cinza claro
        painelFundo1.setBounds(10, 60, 300, 100); // Ajuste as dimensões conforme necessário
        painelFundo1.setVisible(true);
        add(painelFundo1);
        
        JLabel lTLucroLiquido30 = new JLabel("Lucro Liquido (30 dias)");
        lTLucroLiquido30.setFont(new Font("Arial", Font.BOLD, 16));
        lTLucroLiquido30.setBounds(75, 180, 550, 40); // Define posição e tamanho
        lTLucroLiquido30.setForeground(Color.BLACK);
        lTLucroLiquido30.setVisible(true);
        add(lTLucroLiquido30);
        
        lLucroLiquido30 = new JLabel("R$ " + valorDoEstoque, JLabel.CENTER);
        lLucroLiquido30.setFont(new Font("Arial", Font.BOLD, 32));
        lLucroLiquido30.setBounds(10, 210, 300, 40); // Define posição e tamanho
        lLucroLiquido30.setForeground(Color.GREEN);
        add(lLucroLiquido30, SwingConstants.CENTER);
        
        JPanel painelFundo2 = new JPanel();
        painelFundo2.setBackground(Color.LIGHT_GRAY); // Cinza claro
        painelFundo2.setBounds(10, 170, 300, 100); // Ajuste as dimensões conforme necessário
        painelFundo2.setVisible(true);
        add(painelFundo2);
        
        JLabel lTLucroLiquido7 = new JLabel("Lucro Liquido (7 dias)");
        lTLucroLiquido7.setFont(new Font("Arial", Font.BOLD, 16));
        lTLucroLiquido7.setBounds(75, 290, 550, 40); // Define posição e tamanho
        lTLucroLiquido7.setForeground(Color.BLACK);
        lTLucroLiquido7.setVisible(true);
        add(lTLucroLiquido7);
        
        lLucroLiquido7 = new JLabel("R$ " + valorDoEstoque, JLabel.CENTER);
        lLucroLiquido7.setFont(new Font("Arial", Font.BOLD, 32));
        lLucroLiquido7.setBounds(10, 320, 300, 40); // Define posição e tamanho
        lLucroLiquido7.setForeground(Color.GREEN);
        add(lLucroLiquido7, SwingConstants.CENTER);
        
        JPanel painelFundo3 = new JPanel();
        painelFundo3.setBackground(Color.LIGHT_GRAY); // Cinza claro
        painelFundo3.setBounds(10, 280, 300, 100); // Ajuste as dimensões conforme necessário
        painelFundo3.setVisible(true);
        add(painelFundo3);
        
        JLabel lTLucroLiquidoD = new JLabel("Lucro Liquido (Diario)");
        lTLucroLiquidoD.setFont(new Font("Arial", Font.BOLD, 16));
        lTLucroLiquidoD.setBounds(75, 400, 550, 40); // Define posição e tamanho
        lTLucroLiquidoD.setForeground(Color.BLACK);
        lTLucroLiquidoD.setVisible(true);
        add(lTLucroLiquidoD);
        
        lLucroLiquidoD = new JLabel("R$ " + valorDoEstoque, JLabel.CENTER);
        lLucroLiquidoD.setFont(new Font("Arial", Font.BOLD, 32));
        lLucroLiquidoD.setBounds(10, 430, 300, 40); // Define posição e tamanho
        lLucroLiquidoD.setForeground(Color.GREEN);
        add(lLucroLiquidoD, SwingConstants.CENTER);
        
        JPanel painelFundo4 = new JPanel();
        painelFundo4.setBackground(Color.LIGHT_GRAY); // Cinza claro
        painelFundo4.setBounds(10, 390, 300, 100); // Ajuste as dimensões conforme necessário
        painelFundo4.setVisible(true);
        add(painelFundo4);
        
        JButton bAtualizar = new JButton("Atualizar dados");
        bAtualizar.setFont(new Font("Arial", Font.BOLD, 14));
        bAtualizar.setBounds(590, 520, 160, 30);
        bAtualizar.setBackground(new Color(32, 3, 3));
        bAtualizar.setForeground(Color.WHITE);
        bAtualizar.setFocusPainted(false);
        bAtualizar.setBorderPainted(false);
        bAtualizar.setVisible(true);
        bAtualizar.addActionListener(e -> {
            try {
                // Chama o método que retorna o valor formatado como String
                String novoValorTotalFormatado = calcularValorTotalEstoque("Estoque de produtos");
                
                // Verifica se o valor calculado não é nulo ou vazio
                if (novoValorTotalFormatado != null && !novoValorTotalFormatado.isEmpty()) {
                    lValorDeEstoque.setText("R$ " + novoValorTotalFormatado);
                    lLucroLiquido30.setText("R$ " + novoValorTotalFormatado);
                    lLucroLiquido7.setText("R$ " + novoValorTotalFormatado);
                    lLucroLiquidoD.setText("R$ " + novoValorTotalFormatado);
                } else {
                    System.err.println("Valor total do estoque é inválido.");
                }
            } catch (IOException ex) {
                System.err.println("Erro ao atualizar o valor do estoque: " + ex.getMessage());
            }
        });

        add(bAtualizar);
    }

    public static String calcularValorTotalEstoque(String caminhoPasta) throws IOException {
        BigDecimal valorTotal = BigDecimal.ZERO;
        File pasta = new File(caminhoPasta);
        File[] arquivos = pasta.listFiles();

        if (arquivos != null) {
            for (File arquivo : arquivos) {
                if (arquivo.isFile() && arquivo.getName().endsWith(".txt")) {
                    try (Scanner scanner = new Scanner(new FileReader(arquivo))) {
                        BigDecimal valorCusto = BigDecimal.ZERO;
                        int estoqueTotal = 0;
                        
                        while (scanner.hasNextLine()) {
                            String linha = scanner.nextLine();
                            if (linha.startsWith("Valor de Custo: ")) {
                                String valorCustoStr = linha.substring(14).trim();
                                // Remover todos os caracteres não numéricos e vírgulas
                                valorCustoStr = valorCustoStr.replaceAll("[^0-9,.]", "");
                                try {
                                    // Converter para BigDecimal para garantir precisão
                                    valorCusto = new BigDecimal(valorCustoStr.replace(",", "."));
                                } catch (NumberFormatException e) {
                                    System.err.println("Erro ao converter valor de custo para número: " + valorCustoStr + " no arquivo " + arquivo.getName());
                                }
                            }
                            if (linha.startsWith("Estoque: ")) {
                                try {
                                    estoqueTotal += Integer.parseInt(linha.substring(9).trim()); // Somando os estoques de todos os lotes
                                } catch (NumberFormatException e) {
                                    System.err.println("Erro ao converter quantidade de estoque para número: " + linha + " no arquivo " + arquivo.getName());
                                }
                            }
                        }
                        
                        // Agora, calcular o valor total para todos os lotes do produto
                        BigDecimal valorProduto = valorCusto.multiply(BigDecimal.valueOf(estoqueTotal));
                        valorTotal = valorTotal.add(valorProduto);
                    }
                }
            }
        }

        // Formatação para garantir que o valor tenha ponto como separador de milhar e vírgula como separador decimal
        DecimalFormat formato = new DecimalFormat("#,##0.00"); // Formato desejado
        String valorFormatado = formato.format(valorTotal);
        
        // Substituindo a vírgula por ponto, se necessário
        valorFormatado = valorFormatado.replace(",", ".");
        
        return valorFormatado;
    }
}
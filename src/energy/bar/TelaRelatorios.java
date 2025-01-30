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

import energy.bar.bancoDeDados.Diretorios;
import energy.bar.support.LabelEnergyBar;
import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

class TelaRelatorios extends JPanel {

    Diretorios dir = new Diretorios();
    LabelEnergyBar labelEnergyBar = new LabelEnergyBar();

    private String valorDoEstoque; // Alterado para String
    private JLabel lValorDeEstoque;
    private JLabel lLucroBruto30;
    private JLabel lLucroBruto7;
    private JLabel lLucroBrutoD;

    public TelaRelatorios() throws IOException {
        setLayout(null);

        // Criando e adicionando a label EnergyBar
        JLabel energyBarLabel = labelEnergyBar.criarLabelEnergyBar(dir);
        add(energyBarLabel);

        JLabel lTValorDeEstoque = new JLabel("Valor total de estoque");
        lTValorDeEstoque.setFont(new Font("Arial", Font.BOLD, 16));
        lTValorDeEstoque.setBounds(75, 75, 550, 40); // Define posição e tamanho
        lTValorDeEstoque.setForeground(Color.BLACK);
        lTValorDeEstoque.setVisible(true);
        add(lTValorDeEstoque);

        valorDoEstoque = calcularValorTotalEstoque(dir.getDirEstoque());
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

        JLabel lTLucroBruto30 = new JLabel("Lucro bruto (30 dias)");
        lTLucroBruto30.setFont(new Font("Arial", Font.BOLD, 16));
        lTLucroBruto30.setBounds(75, 180, 550, 40); // Define posição e tamanho
        lTLucroBruto30.setForeground(Color.BLACK);
        lTLucroBruto30.setVisible(true);
        add(lTLucroBruto30);

        lLucroBruto30 = new JLabel("R$ " + somarComprasUltimos30Dias(), JLabel.CENTER);
        lLucroBruto30.setFont(new Font("Arial", Font.BOLD, 32));
        lLucroBruto30.setBounds(10, 210, 300, 40); // Define posição e tamanho
        lLucroBruto30.setForeground(Color.GREEN);
        add(lLucroBruto30, SwingConstants.CENTER);

        JPanel painelFundo2 = new JPanel();
        painelFundo2.setBackground(Color.LIGHT_GRAY); // Cinza claro
        painelFundo2.setBounds(10, 170, 300, 100); // Ajuste as dimensões conforme necessário
        painelFundo2.setVisible(true);
        add(painelFundo2);

        JLabel lTLucroBruto7 = new JLabel("Lucro bruto (7 dias)");
        lTLucroBruto7.setFont(new Font("Arial", Font.BOLD, 16));
        lTLucroBruto7.setBounds(75, 290, 550, 40); // Define posição e tamanho
        lTLucroBruto7.setForeground(Color.BLACK);
        lTLucroBruto7.setVisible(true);
        add(lTLucroBruto7);

        lLucroBruto7 = new JLabel("R$ " + somarComprasUltimos7Dias(), JLabel.CENTER);
        lLucroBruto7.setFont(new Font("Arial", Font.BOLD, 32));
        lLucroBruto7.setBounds(10, 320, 300, 40); // Define posição e tamanho
        lLucroBruto7.setForeground(Color.GREEN);
        add(lLucroBruto7, SwingConstants.CENTER);

        JPanel painelFundo3 = new JPanel();
        painelFundo3.setBackground(Color.LIGHT_GRAY); // Cinza claro
        painelFundo3.setBounds(10, 280, 300, 100); // Ajuste as dimensões conforme necessário
        painelFundo3.setVisible(true);
        add(painelFundo3);

        JLabel lTLucroBrutoD = new JLabel("Lucro bruto (Diario)");
        lTLucroBrutoD.setFont(new Font("Arial", Font.BOLD, 16));
        lTLucroBrutoD.setBounds(75, 400, 550, 40); // Define posição e tamanho
        lTLucroBrutoD.setForeground(Color.BLACK);
        lTLucroBrutoD.setVisible(true);
        add(lTLucroBrutoD);

        lLucroBrutoD = new JLabel("R$ " + somarComprasDoDia(), JLabel.CENTER);
        lLucroBrutoD.setFont(new Font("Arial", Font.BOLD, 32));
        lLucroBrutoD.setBounds(10, 430, 300, 40); // Define posição e tamanho
        lLucroBrutoD.setForeground(Color.GREEN);
        add(lLucroBrutoD, SwingConstants.CENTER);

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
                String novoValorTotalFormatado = calcularValorTotalEstoque(dir.getDirEstoque());

                // Verifica se o valor calculado não é nulo ou vazio
                if (novoValorTotalFormatado != null && !novoValorTotalFormatado.isEmpty()) {
                    lValorDeEstoque.setText("R$ " + novoValorTotalFormatado);
                    lLucroBruto30.setText("R$ " + somarComprasUltimos30Dias());
                    lLucroBruto7.setText("R$ " + somarComprasUltimos7Dias());
                    lLucroBrutoD.setText("R$ " + somarComprasDoDia());
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

    public static double somarComprasDoDia() throws IOException {
        // Obter a data do sistema no formato yyyyMMdd
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dataAtual = sdf.format(new Date());

        // Diretorio onde os arquivos de compras estão armazenados
        File diretorio = new File("Dados/Compras");

        // Verificar se o diretório existe
        if (!diretorio.exists() || !diretorio.isDirectory()) {
            throw new IOException("Diretório 'Dados/Compras' não encontrado.");
        }

        // Variável para acumular o valor total das compras
        double total = 0;

        // Listar todos os arquivos no diretório
        File[] arquivos = diretorio.listFiles();
        if (arquivos != null) {
            for (File arquivo : arquivos) {
                // Verificar se o arquivo é um arquivo com nome no formato yyyyMMdd hhmmss
                String nomeArquivo = arquivo.getName();
                if (nomeArquivo.length() >= 15 && nomeArquivo.substring(0, 8).equals(dataAtual)) {
                    // Ler o arquivo e procurar a linha que contém o valor
                    try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                        String linha;
                        while ((linha = br.readLine()) != null) {
                            if (linha.startsWith("Valor Total da Compra: R$")) {
                                // Extrair o valor e somar ao total
                                String valorStr = linha.substring(25).trim().replace(",", ".");
                                total += Double.parseDouble(valorStr);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return total;
    }

    public static double somarComprasUltimos7Dias() throws IOException {
        // Obter a data do sistema no formato yyyyMMdd
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dataAtual = sdf.format(new Date());

        // Obter a data do sistema e calcular a data dos últimos 7 dias
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        String dataLimite = sdf.format(calendar.getTime());

        // Diretorio onde os arquivos de compras estão armazenados
        File diretorio = new File("Dados/Compras");

        // Verificar se o diretório existe
        if (!diretorio.exists() || !diretorio.isDirectory()) {
            throw new IOException("Diretório 'Dados/Compras' não encontrado.");
        }

        // Variável para acumular o valor total das compras
        double total = 0;

        // Listar todos os arquivos no diretório
        File[] arquivos = diretorio.listFiles();
        if (arquivos != null) {
            for (File arquivo : arquivos) {
                // Verificar se o arquivo é um arquivo com nome no formato yyyyMMdd hhmmss
                String nomeArquivo = arquivo.getName();
                if (nomeArquivo.length() >= 15) {
                    // Pegar a data do arquivo (formato yyyyMMdd) e comparar com o intervalo dos últimos 7 dias
                    String dataArquivo = nomeArquivo.substring(0, 8);
                    if (dataArquivo.compareTo(dataLimite) >= 0 && dataArquivo.compareTo(dataAtual) <= 0) {
                        // Ler o arquivo e procurar a linha que contém o valor
                        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                            String linha;
                            while ((linha = br.readLine()) != null) {
                                if (linha.startsWith("Valor Total da Compra: R$")) {
                                    // Extrair o valor e somar ao total
                                    String valorStr = linha.substring(25).trim().replace(",", ".");
                                    total += Double.parseDouble(valorStr);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        return total;
    }

    public static double somarComprasUltimos30Dias() throws IOException {
        // Obter a data do sistema no formato yyyyMMdd
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dataAtual = sdf.format(new Date());

        // Obter a data do sistema e calcular a data dos últimos 30 dias
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -30);
        String dataLimite = sdf.format(calendar.getTime());

        // Diretorio onde os arquivos de compras estão armazenados
        File diretorio = new File("Dados/Compras");

        // Verificar se o diretório existe
        if (!diretorio.exists() || !diretorio.isDirectory()) {
            throw new IOException("Diretório 'Dados/Compras' não encontrado.");
        }

        // Variável para acumular o valor total das compras
        double total = 0;

        // Listar todos os arquivos no diretório
        File[] arquivos = diretorio.listFiles();
        if (arquivos != null) {
            for (File arquivo : arquivos) {
                // Verificar se o arquivo é um arquivo com nome no formato yyyyMMdd hhmmss
                String nomeArquivo = arquivo.getName();
                if (nomeArquivo.length() >= 15) {
                    // Pegar a data do arquivo (formato yyyyMMdd) e comparar com o intervalo dos últimos 30 dias
                    String dataArquivo = nomeArquivo.substring(0, 8);
                    if (dataArquivo.compareTo(dataLimite) >= 0 && dataArquivo.compareTo(dataAtual) <= 0) {
                        // Ler o arquivo e procurar a linha que contém o valor
                        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                            String linha;
                            while ((linha = br.readLine()) != null) {
                                if (linha.startsWith("Valor Total da Compra: R$")) {
                                    // Extrair o valor e somar ao total
                                    String valorStr = linha.substring(25).trim().replace(",", ".");
                                    total += Double.parseDouble(valorStr);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        return total;
    }

}

package energy.bar;

import energy.bar.bancoDeDados.Diretorios;
import energy.bar.support.IdFilter;
import energy.bar.support.IdVerifier;
import energy.bar.support.LabelEnergyBar;
import energy.bar.support.TimerAvisosLabels;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.AbstractDocument;

class TelaVendas extends JPanel {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss - dd-MM-yyyy");
    String dataHoraAtual = LocalDateTime.now().format(formatter);
    public String dataHora = new java.text.SimpleDateFormat(dataHoraAtual).format(new java.util.Date());

    private List<File> arquivosEstoque;

    String[] colunas = {"ID", "Produto", "Qtn", "Valor", "Lote"};
    DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);

    public JButton bRemover = new JButton();
    public JTable tabelaProdutos = new JTable(modeloTabela);

    public JTextField campoQtn = new JTextField("000");
    public JTextField campoId = new JTextField("000");
    public JLabel lProdutoAdicionado = new JLabel("Produto com ID " + campoId.getText() + " adicionado no carrinho");
    public JLabel lProdutoSemEstoque = new JLabel("Produto com ID " + campoId.getText() + " está sem estoque no lote mais velho");
    public JLabel lDescontoAplicado = new JLabel("- Desconto ja aplicado!");

    Diretorios dir = new Diretorios();
    TimerAvisosLabels tir = new TimerAvisosLabels();
    LabelEnergyBar labelEnergyBar = new LabelEnergyBar();
    public double valorTotalDeCompra = 0.0;

    public JLabel lfaltaDeDados = new JLabel("Campos obrigatorios devem ser preenchidos!");
    public JLabel lCompraFinalizada = new JLabel("Compra feita e cadastrada com sucesso!");
    public JLabel lCarrinhoVazio = new JLabel("Carrinho está vazio!");
    public JLabel lIdOuQtnVazio = new JLabel("ID ou Quantidade está vazio!");
    public JLabel lProdutoNaoExiste = new JLabel("Esse ID de produto não existe ou não foi localizado!");

    String[] responsaveis = {"", "Cliente", "Funcionario"};
    public JComboBox<String> campoTipocliente = new JComboBox<>(responsaveis);

    public JTextField campoTotalDaCompra = new JTextField();
    public JTextField campoDesconto = new JTextField();

    String[] formaDePagamento = {"", "Dinheiro", "Pix", "Débito", "Crédito"};
    public JComboBox<String> campoFormaDePagamento = new JComboBox<>(formaDePagamento);

    String[] funcionarios = {"", "Pamela", "Lucas", "Fellipe"};
    public JComboBox<String> campoFuncionario = new JComboBox<>(funcionarios);

    public String descontoStr = campoDesconto.getText().trim();
    public int desconto = 0; // valor padrão para desconto

    public TelaVendas() throws IOException {
        setLayout(null);

        // Criando e adicionando a label EnergyBar
        JLabel energyBarLabel = labelEnergyBar.criarLabelEnergyBar(dir);
        add(energyBarLabel);

        tabelaProdutos.setRowHeight(30);
        tabelaProdutos.setFont(new Font("Arial", Font.PLAIN, 14)); // Fonte da tabela
        tabelaProdutos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16)); // Fonte do cabeçalho
        tabelaProdutos.getTableHeader().setBackground(new Color(32, 3, 3)); // Cor de fundo do cabeçalho
        tabelaProdutos.getTableHeader().setForeground(Color.WHITE); // Cor do texto do cabeçalho
        tabelaProdutos.setBackground(new Color(245, 245, 245)); // Cor de fundo da tabela
        tabelaProdutos.setForeground(new Color(0, 0, 0)); // Cor do texto
        tabelaProdutos.setGridColor(new Color(200, 200, 200)); // Cor das linhas de grade
        tabelaProdutos.setSelectionBackground(new Color(52, 152, 219)); // Cor de fundo ao selecionar
        tabelaProdutos.setSelectionForeground(Color.WHITE); // Cor do texto ao selecionar
        tabelaProdutos.setRowHeight(30); // Ajustando a altura das linhas
        tabelaProdutos.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1)); // Bordas da tabela

        // Usando TableRowSorter para ordenar numericamente a coluna ID
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(modeloTabela);
        tabelaProdutos.setRowSorter(sorter);

        // Definir o tipo de comparação para a coluna ID (coluna 0)
        sorter.setComparator(0, (o1, o2) -> {
            try {
                int id1 = Integer.parseInt(o1.toString());
                int id2 = Integer.parseInt(o2.toString());
                return Integer.compare(id1, id2); // Comparação numérica
            } catch (NumberFormatException e) {
                return 0; // Em caso de erro de conversão
            }
        });

        tabelaProdutos.getColumnModel().getColumn(0).setPreferredWidth(5);
        tabelaProdutos.getColumnModel().getColumn(1).setPreferredWidth(80);
        tabelaProdutos.getColumnModel().getColumn(2).setPreferredWidth(10);

        JScrollPane scrollPane = new JScrollPane(tabelaProdutos);
        scrollPane.setVisible(true);
        scrollPane.setBounds(10, 70, 400, 400); // Definindo o tamanho e posição
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 2)); // Bordas do JScrollPane
        add(scrollPane);
        
        lProdutoNaoExiste.setFont(new Font("Arial", Font.BOLD, 16));
        lProdutoNaoExiste.setBounds(200, 465, 400, 40); // Define posição e tamanho
        lProdutoNaoExiste.setForeground(Color.RED);
        lProdutoNaoExiste.setVisible(false);
        add(lProdutoNaoExiste);

        lfaltaDeDados.setFont(new Font("Arial", Font.BOLD, 16));
        lfaltaDeDados.setBounds(220, 465, 350, 40); // Define posição e tamanho
        lfaltaDeDados.setForeground(Color.RED);
        lfaltaDeDados.setVisible(false);
        add(lfaltaDeDados);

        lCompraFinalizada.setFont(new Font("Arial", Font.BOLD, 16));
        lCompraFinalizada.setBounds(220, 465, 350, 40); // Define posição e tamanho
        lCompraFinalizada.setForeground(Color.GREEN);
        lCompraFinalizada.setVisible(false);
        add(lCompraFinalizada);

        lCarrinhoVazio.setFont(new Font("Arial", Font.BOLD, 16));
        lCarrinhoVazio.setBounds(290, 465, 350, 40); // Define posição e tamanho
        lCarrinhoVazio.setForeground(Color.RED);
        lCarrinhoVazio.setVisible(false);
        add(lCarrinhoVazio);

        lIdOuQtnVazio.setFont(new Font("Arial", Font.BOLD, 16));
        lIdOuQtnVazio.setBounds(260, 465, 350, 40); // Define posição e tamanho
        lIdOuQtnVazio.setForeground(Color.RED);
        lIdOuQtnVazio.setVisible(false);
        add(lIdOuQtnVazio);

        lDescontoAplicado.setFont(new Font("Arial", Font.BOLD, 16));
        lDescontoAplicado.setBounds(135, 480, 350, 40); // Define posição e tamanho
        lDescontoAplicado.setForeground(Color.GREEN);
        lDescontoAplicado.setVisible(false);
        add(lDescontoAplicado);

        // Label e TextField ID
        JLabel lId = new JLabel("ID do produto");
        lId.setFont(new Font("Arial", Font.BOLD, 16));
        lId.setBounds(420, 60, 300, 40); // Define posição e tamanho
        lId.setVisible(true);
        add(lId);
        campoId.setBounds(420, 90, 330, 30);
        campoId.setBackground(Color.LIGHT_GRAY);
        campoId.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        campoId.setFont(new Font("Arial", Font.BOLD, 16));
        IdVerifier verifier = new IdVerifier();
        campoId.setInputVerifier(verifier);
        IdFilter idFilter = new IdFilter();
        ((AbstractDocument) campoId.getDocument()).setDocumentFilter(idFilter);
        campoId.setText("000");
        campoId.setVisible(true);
        add(campoId);
        campoId.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField textField = (JTextField) input;
                String text = textField.getText().trim();

                // Permite campo vazio para que o usuário possa sair dele
                if (text.isEmpty()) {
                    campoId.setText("000");
                    return true;
                }

                return true; // Permite a mudança de foco
            }
        });

        lProdutoAdicionado.setFont(new Font("Arial", Font.BOLD, 16));
        lProdutoAdicionado.setBounds(220, 465, 350, 40); // Define posição e tamanho
        lProdutoAdicionado.setForeground(Color.GREEN);
        lProdutoAdicionado.setVisible(false);
        add(lProdutoAdicionado);

        lProdutoSemEstoque.setFont(new Font("Arial", Font.BOLD, 16));
        lProdutoSemEstoque.setBounds(150, 465, 450, 40); // Define posição e tamanho
        lProdutoSemEstoque.setForeground(Color.RED);
        lProdutoSemEstoque.setVisible(false);
        add(lProdutoSemEstoque);

        // Label e TextField ID
        JLabel lQnt = new JLabel("Quantidade");
        lQnt.setFont(new Font("Arial", Font.BOLD, 16));
        lQnt.setBounds(420, 120, 300, 40); // Define posição e tamanho
        lQnt.setVisible(true);
        add(lQnt);
        campoQtn.setBounds(420, 150, 330, 30);
        campoQtn.setBackground(Color.LIGHT_GRAY);
        campoQtn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        campoQtn.setFont(new Font("Arial", Font.BOLD, 16));
        campoQtn.setInputVerifier(verifier);
        ((AbstractDocument) campoQtn.getDocument()).setDocumentFilter(idFilter);
        campoQtn.setText("000");
        campoQtn.setVisible(true);
        add(campoQtn);
        campoQtn.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField textField = (JTextField) input;
                String text = textField.getText().trim();

                // Permite campo vazio para que o usuário possa sair dele
                if (text.isEmpty()) {
                    campoQtn.setText("000");
                    return true;
                }

                return true; // Permite a mudança de foco
            }
        });

        JButton bRemover = new JButton();
        bRemover.setText("Remover");
        bRemover.setFont(new Font("Arial", Font.BOLD, 14));
        bRemover.setBounds(420, 190, 120, 30);
        bRemover.setBackground(Color.RED);
        bRemover.setForeground(Color.WHITE);
        bRemover.setFocusPainted(false);
        bRemover.setBorderPainted(false);
        bRemover.setVisible(true);
        add(bRemover);

        JButton bAdicionar = new JButton();
        bAdicionar.setText("Adicionar");
        bAdicionar.setFont(new Font("Arial", Font.BOLD, 14));
        bAdicionar.setBounds(630, 190, 120, 30);
        bAdicionar.setBackground(Color.GREEN);
        bAdicionar.setForeground(Color.WHITE);
        bAdicionar.setFocusPainted(false);
        bAdicionar.setBorderPainted(false);
        bAdicionar.setVisible(true);
        add(bAdicionar);

        JLabel lTipoCliente = new JLabel("Tipo de cliente");
        lTipoCliente.setFont(new Font("Arial", Font.BOLD, 16));
        lTipoCliente.setBounds(420, 230, 330, 30);
        lTipoCliente.setVisible(true);
        add(lTipoCliente);
        campoTipocliente.setBounds(420, 260, 330, 30);
        campoTipocliente.setBackground(Color.LIGHT_GRAY);
        campoTipocliente.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        campoTipocliente.setFocusable(false);
        campoTipocliente.setFont(new Font("Arial", Font.BOLD, 16));
        campoTipocliente.setVisible(true);
        add(campoTipocliente);
        campoTipocliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selecionado = (String) campoTipocliente.getSelectedItem();

                if ("Funcionario".equals(selecionado)) {
                    campoDesconto.setText("020");
                    campoTotalDaCompra.setText("R$ " + calcularTotalCompra());
                } else {
                    campoDesconto.setText("000");
                    campoTotalDaCompra.setText("R$ " + calcularTotalCompra());
                }

            }
        });

        JLabel lFormaDePagamento = new JLabel("Forma de pagamento");
        lFormaDePagamento.setFont(new Font("Arial", Font.BOLD, 16));
        lFormaDePagamento.setBounds(420, 290, 330, 30);
        lFormaDePagamento.setVisible(true);
        add(lFormaDePagamento);
        campoFormaDePagamento.setBounds(420, 320, 330, 30);
        campoFormaDePagamento.setBackground(Color.LIGHT_GRAY);
        campoFormaDePagamento.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        campoFormaDePagamento.setFocusable(false);
        campoFormaDePagamento.setFont(new Font("Arial", Font.BOLD, 16));
        campoFormaDePagamento.setVisible(true);
        add(campoFormaDePagamento);

        // Label e TextField ID
        JLabel lDesconto = new JLabel("Desconto");
        lDesconto.setFont(new Font("Arial", Font.BOLD, 16));
        lDesconto.setBounds(420, 350, 330, 30);
        lDesconto.setVisible(true);
        add(lDesconto);

        campoDesconto.setBounds(420, 380, 220, 30);
        campoDesconto.setBackground(Color.LIGHT_GRAY);
        campoDesconto.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        campoDesconto.setFont(new Font("Arial", Font.BOLD, 16));
        campoDesconto.setInputVerifier(verifier);
        ((AbstractDocument) campoDesconto.getDocument()).setDocumentFilter(idFilter);
        campoDesconto.setText("000");
        campoDesconto.setVisible(true);
        add(campoDesconto);
        campoDesconto.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                return true; // Permite a mudança de foco
            }
        });

        JButton bAplicarDesconto = new JButton();
        bAplicarDesconto.setText("Aplicar");
        bAplicarDesconto.setFont(new Font("Arial", Font.BOLD, 14));
        bAplicarDesconto.setBounds(650, 380, 100, 30);
        bAplicarDesconto.setBackground(new Color(32, 3, 3));
        bAplicarDesconto.setForeground(Color.WHITE);
        bAplicarDesconto.setFocusPainted(false);
        bAplicarDesconto.setBorderPainted(false);
        bAplicarDesconto.setVisible(true);
        add(bAplicarDesconto);
        bAplicarDesconto.addActionListener(e -> {
            campoTotalDaCompra.setText("R$ " + calcularTotalCompra());
        });

        JLabel lFuncionario = new JLabel("Funcionario");
        lFuncionario.setFont(new Font("Arial", Font.BOLD, 16));
        lFuncionario.setBounds(420, 410, 330, 30);
        lFuncionario.setVisible(true);
        add(lFuncionario);
        campoFuncionario.setBounds(420, 440, 330, 30);
        campoFuncionario.setBackground(Color.LIGHT_GRAY);
        campoFuncionario.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        campoFuncionario.setFocusable(false);
        campoFuncionario.setFont(new Font("Arial", Font.BOLD, 16));
        campoFuncionario.setVisible(true);
        add(campoFuncionario);

        JButton bFinalizarCompra = new JButton();
        bFinalizarCompra.setText("Finalizar Compra");
        bFinalizarCompra.setFont(new Font("Arial", Font.BOLD, 14));
        bFinalizarCompra.setBounds(590, 510, 160, 30);
        bFinalizarCompra.setBackground(Color.GREEN);
        bFinalizarCompra.setForeground(Color.WHITE);
        bFinalizarCompra.setFocusPainted(false);
        bFinalizarCompra.setBorderPainted(false);
        bFinalizarCompra.setVisible(true);
        add(bFinalizarCompra);

        JButton bCancelarCompra = new JButton();
        bCancelarCompra.setText("Cancelar Compra");
        bCancelarCompra.setFont(new Font("Arial", Font.BOLD, 14));
        bCancelarCompra.setBounds(420, 510, 160, 30);
        bCancelarCompra.setBackground(Color.RED);
        bCancelarCompra.setForeground(Color.WHITE);
        bCancelarCompra.setFocusPainted(false);
        bCancelarCompra.setBorderPainted(false);
        bCancelarCompra.setVisible(true);
        add(bCancelarCompra);

        JLabel lTotalDaCompra = new JLabel("Total da compra");
        lTotalDaCompra.setFont(new Font("Arial", Font.BOLD, 16));
        lTotalDaCompra.setBounds(10, 480, 300, 40); // Define posição e tamanho
        lTotalDaCompra.setVisible(true);
        add(lTotalDaCompra);
        campoTotalDaCompra.setBounds(10, 510, 330, 30);
        campoTotalDaCompra.setBackground(Color.LIGHT_GRAY);
        campoTotalDaCompra.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        campoTotalDaCompra.setFont(new Font("Arial", Font.BOLD, 16));
        campoTotalDaCompra.setVisible(true);
        campoTotalDaCompra.setEnabled(false);
        campoTotalDaCompra.setText("R$ 00.00");
        add(campoTotalDaCompra);

        bAdicionar.addActionListener(e -> {
            relerArquivosDeEstoque();

            lfaltaDeDados.setVisible(false);
            lProdutoNaoExiste.setVisible(false);
            lCarrinhoVazio.setVisible(false);
            lCompraFinalizada.setVisible(false);
            lProdutoAdicionado.setVisible(false);
            lIdOuQtnVazio.setVisible(false);

            // Verifica se os campos de ID e Quantidade foram preenchidos
            String id = campoId.getText().trim();
            String qtnStr = campoQtn.getText().trim();

            if (id.isEmpty() || qtnStr.isEmpty() || id.equals("000") || qtnStr.equals("000")) {
                // Exibir mensagem de erro se algum campo estiver vazio ou for "000"
                tir.exibirAvisoTemporario(lIdOuQtnVazio);
                return;
            }

            int quantidadeDesejada = Integer.parseInt(qtnStr);

            try {
                // Encontre o arquivo do produto baseado no ID
                File arquivoProduto = new File(dir.getDirEstoque(), id + ".txt");

                if (!arquivoProduto.exists()) {
                    // Se o arquivo não existir, exibe um erro
                    System.out.println("[" + dataHora + "] - [TelaVendas.java] - Produto com ID: " + id + " não existe ou não foi localizado!");
                    campoId.setText("000");
                    campoQtn.setText("000");
                    tir.exibirAvisoTemporario(lProdutoNaoExiste);                              
                    return;
                }

                // Lê os dados do arquivo de produto
                BufferedReader reader = new BufferedReader(new FileReader(arquivoProduto));
                String linha;
                String nomeProduto = "";
                int quantidadeAtual = 0;
                double valorDeVenda = 0.0;
                String loteAntigo = "";
                int quantidadeLoteAntigo = 0;

                // Lê cada linha e captura os dados relevantes
                while ((linha = reader.readLine()) != null) {
                    if (linha.startsWith("Nome do Produto:")) {
                        nomeProduto = linha.substring("Nome do Produto:".length()).trim();
                    }
                    if (linha.startsWith("Estoque:")) {
                        quantidadeAtual = Integer.parseInt(linha.substring("Estoque:".length()).trim());
                    }
                    if (linha.startsWith("Valor de Venda:")) {
                        valorDeVenda = Double.parseDouble(linha.substring("Valor de Venda:".length()).trim());
                    }
                    if (linha.startsWith("Lote:")) {
                        // Guardar o primeiro lote encontrado como o lote mais antigo
                        if (loteAntigo.isEmpty()) {
                            loteAntigo = linha.substring("Lote:".length()).trim();
                            quantidadeLoteAntigo = quantidadeAtual; // Assume a quantidade do primeiro lote como a quantidade atual
                        }
                    }
                }
                reader.close();

                // Verifica se há quantidade suficiente no estoque
                if (quantidadeDesejada > quantidadeLoteAntigo) {
                    lProdutoSemEstoque.setText("Produto com ID " + id + " está sem estoque no lote mais velho");
                    tir.exibirAvisoTemporario(lProdutoSemEstoque);
                    return;
                }

                // Atualiza a tabela com o ID, Nome, Quantidade, Valor de Venda e Lote
                modeloTabela.addRow(new Object[]{id, nomeProduto, quantidadeDesejada, valorDeVenda, loteAntigo});

                // Atualiza o valor total da compra, somando o valor da venda do produto
                valorTotalDeCompra += quantidadeDesejada * valorDeVenda;

                // Atualiza o campo de total da compra, formatando para 2 casas decimais
                campoTotalDaCompra.setText(String.format("R$ " + "%.2f", valorTotalDeCompra));

                // Atualiza a quantidade no arquivo, removendo a quantidade desejada do lote mais antigo
                atualizarQuantidadeNoArquivo(arquivoProduto, quantidadeLoteAntigo - quantidadeDesejada);

                // Limpa os campos após a operação
                campoId.setText("000");
                campoQtn.setText("000");

                // Atualiza a label para mostrar que o produto foi adicionado ao carrinho
                lProdutoAdicionado.setText("Produto com ID " + id + " adicionado no carrinho");
                campoTotalDaCompra.setText("R$ " + calcularTotalCompra());

                // Usa o TimerAvisosLabels para esconder a label após alguns segundos
                tir.exibirAvisoTemporario(lProdutoAdicionado);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        bRemover.addActionListener(e -> {
            lfaltaDeDados.setVisible(false);
            lProdutoNaoExiste.setVisible(false);
            lCarrinhoVazio.setVisible(false);
            lCompraFinalizada.setVisible(false);
            lProdutoAdicionado.setVisible(false);
            lIdOuQtnVazio.setVisible(false);
            try {
                removerProduto();
                campoTotalDaCompra.setText("R$ " + calcularTotalCompra());
            } catch (IOException ex) {
                Logger.getLogger(TelaVendas.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        bFinalizarCompra.addActionListener(e -> {
    lfaltaDeDados.setVisible(false);
    lProdutoNaoExiste.setVisible(false);
    lCarrinhoVazio.setVisible(false);
    lCompraFinalizada.setVisible(false);
    lProdutoAdicionado.setVisible(false);
    lIdOuQtnVazio.setVisible(false);

    // Verifica se os campos ComboBox e a tabela estão preenchidos
    if (campoTipocliente.getSelectedItem() == null || campoFormaDePagamento.getSelectedItem() == null || campoFuncionario.getSelectedItem() == null
            || campoTipocliente.getSelectedItem().equals("") || campoFormaDePagamento.getSelectedItem().equals("") || campoFuncionario.getSelectedItem().equals("")) {
        tir.exibirAvisoTemporario(lfaltaDeDados);
        return;
    }

    if (modeloTabela.getRowCount() == 0) {
        tir.exibirAvisoTemporario(lCarrinhoVazio);
        return;
    }

    try {
        // Pega a data e hora atual para nome do arquivo
        String dataHora = new SimpleDateFormat("HH-mm-ss - dd-MM-yyyy").format(new Date());

        // Cria o arquivo com o nome baseado na data e hora
        File dirHistorico = new File(dir.getDirHistoricoDeCompras());
        if (!dirHistorico.exists()) {
            dirHistorico.mkdirs();
        }
        File arquivoHistorico = new File(dirHistorico, dataHora + ".txt");

        // Cria o BufferedWriter para escrever no arquivo
        BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoHistorico));

        // Escreve as informações dos campos ComboBox
        writer.write("Tipo de Cliente: " + campoTipocliente.getSelectedItem().toString() + "\n");
        writer.write("Forma de Pagamento: " + campoFormaDePagamento.getSelectedItem().toString() + "\n");
        writer.write("Funcionário: " + campoFuncionario.getSelectedItem().toString() + "\n");

        // Escreve o valor total da compra na primeira linha
        writer.write("Valor Total da Compra: " + campoTotalDaCompra.getText() + "\n");

        // Escreve o valor do desconto (se houver) - considerando o valor do campoDesconto
        String descontoStr = campoDesconto.getText().trim();
        int desconto = Integer.parseInt(descontoStr);
        writer.write("Desconto Aplicado: " + desconto + "%\n");

        // Escreve os dados da tabela
        for (int i = 0; i < modeloTabela.getRowCount(); i++) {
            String idProduto = modeloTabela.getValueAt(i, 0).toString();
            String nomeProduto = modeloTabela.getValueAt(i, 1).toString();
            String quantidade = modeloTabela.getValueAt(i, 2).toString();
            String valorDeVenda = modeloTabela.getValueAt(i, 3).toString();

            writer.write(idProduto + " | " + nomeProduto + " | " + quantidade + " | " + valorDeVenda + "\n");
        }

        // Lê os arquivos de estoque para remover lotes com estoque 0
        File dirEstoque = new File(dir.getDirEstoque());
        File[] arquivosProdutos = dirEstoque.listFiles((dir, name) -> name.endsWith(".txt"));

        if (arquivosProdutos != null) {
            for (File arquivoProduto : arquivosProdutos) {
                BufferedReader reader = new BufferedReader(new FileReader(arquivoProduto));
                StringBuilder conteudoArquivo = new StringBuilder();
                StringBuilder loteAtual = new StringBuilder();
                String linha;
                boolean loteComEstoqueZero = false;

                while ((linha = reader.readLine()) != null) {
                    if (linha.startsWith("Estoque:")) {
                        int quantidadeAtual = Integer.parseInt(linha.substring("Estoque:".length()).trim());
                        loteComEstoqueZero = (quantidadeAtual == 0);
                    }

                    // Apenas adiciona linhas se o lote não estiver marcado como estoque zero
                    if (!loteComEstoqueZero) {
                        loteAtual.append(linha).append("\n");
                    }

                    // Quando encontramos o final do lote, decidimos se ele será mantido ou não
                    if (linha.startsWith("Data e Hora de Cadastro:")) {
                        if (!loteComEstoqueZero) {
                            conteudoArquivo.append(loteAtual);
                        }
                        loteAtual.setLength(0); // Limpa para o próximo lote
                        loteComEstoqueZero = false; // Reseta para o próximo lote
                    }
                }
                reader.close();

                // Sobrescreve o arquivo com o novo conteúdo, removendo lotes inválidos
                BufferedWriter writerProduto = new BufferedWriter(new FileWriter(arquivoProduto));
                writerProduto.write(conteudoArquivo.toString());
                writerProduto.close();

                System.out.println("[" + dataHora + "] - [TelaVendas.java] - Lotes com estoque zero removidos do produto: " + arquivoProduto.getName());
            }
        }

        // Fecha o arquivo do histórico de compras
        writer.close();

        // Limpa a tabela após registrar
        modeloTabela.setRowCount(0);
        campoTipocliente.setSelectedItem("");
        campoFuncionario.setSelectedItem("");
        campoTotalDaCompra.setText("R$ 00.00");
        campoFormaDePagamento.setSelectedItem("");
        campoDesconto.setText("000");
        campoId.setText("000");
        campoQtn.setText("000");

        lDescontoAplicado.setVisible(false);

        System.out.println("[" + dataHora + "] - [TelaVendas.java] - Venda cadastrada em " + arquivoHistorico.getName());
        System.out.println("[" + dataHora + "] - [TelaVendas.java] - Venda feita e cadastrada com sucesso!");

        tir.exibirAvisoTemporario(lCompraFinalizada);
    } catch (IOException ex) {
        ex.printStackTrace();
    }
});

        bCancelarCompra.addActionListener(e -> {
            try {
                cancelarCompra();
            } catch (IOException ex) {
                Logger.getLogger(TelaVendas.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Método para atualizar a quantidade no arquivo do produto
    private void atualizarQuantidadeNoArquivo(File arquivo, int novaQuantidade) throws IOException {
        // Lê o conteúdo do arquivo
        List<String> linhas = Files.readAllLines(arquivo.toPath(), StandardCharsets.UTF_8);

        // Atualiza a linha de estoque com a nova quantidade
        boolean estoqueAtualizado = false;
        for (int i = 0; i < linhas.size(); i++) {
            if (linhas.get(i).startsWith("Estoque:")) {
                // Substitui a linha de estoque com a nova quantidade
                linhas.set(i, "Estoque: " + novaQuantidade);
                estoqueAtualizado = true;
                break; // Sai do loop após encontrar e atualizar a linha
            }
        }

        // Se o estoque foi atualizado, escreve de volta para o arquivo
        if (estoqueAtualizado) {
            Files.write(arquivo.toPath(), linhas, StandardCharsets.UTF_8);
        } else {
        }
    }

    private void removerProduto() throws IOException {
        relerArquivosDeEstoque();

        // 1. Valida se o campo de ID e quantidade está vazio ou em "000"
        String id = campoId.getText().trim();
        String qtnStr = campoQtn.getText().trim();

        if (id.isEmpty() || qtnStr.isEmpty() || id.equals("000") || qtnStr.equals("000")) {
            System.out.println("[" + dataHora + "] - [TelaVendas.java] - ERRO: O campo ID ou Quantidade está vazio ou contém '000'.");
            return; // Não deixa prosseguir
        }

        // 2. Procura o ID na tabela
        int linhaEncontrada = -1;
        String idTabela = "";
        int quantidadeNaTabela = 0;
        int quantidadeRemover = Integer.parseInt(qtnStr);

        for (int i = 0; i < modeloTabela.getRowCount(); i++) {
            idTabela = modeloTabela.getValueAt(i, 0).toString(); // Obtém o ID da tabela (índice 0)
            if (idTabela.equals(id)) {
                linhaEncontrada = i;
                String quantidadeNaTabelaStr = modeloTabela.getValueAt(i, 2).toString(); // Coluna da quantidade (índice 2)
                if (isNumeric(quantidadeNaTabelaStr)) {
                    quantidadeNaTabela = Integer.parseInt(quantidadeNaTabelaStr);
                } else {
                    System.out.println("[" + dataHora + "] - [TelaVendas.java] - ERRO: Quantidade inválida na tabela.");
                    return;
                }
                break;
            }
        }

        if (linhaEncontrada == -1) {
            System.out.println("[" + dataHora + "] - [TelaVendas.java] - ERRO: Produto com ID " + id + " não encontrado na tabela.");
            return; // Não deixa prosseguir
        } else if (quantidadeRemover > quantidadeNaTabela) {
            System.out.println("[" + dataHora + "] - [TelaVendas.java] - ERRO: Quantidade a remover é maior do que a quantidade na tabela.");
            return; // Não deixa prosseguir
        } else {
            System.out.println("[" + dataHora + "] - [TelaVendas.java] - Produto com ID " + id + " foi localizado o seu arquivo");
        }

        // 3. Puxa os dados da tabela (ID e quantidade)
        //System.out.println("Dados puxados da tabela:");
        //System.out.println("ID: " + idTabela);
        //System.out.println("Quantidade na Tabela: " + quantidadeNaTabela);

        // 4. Entra no arquivo do produto e utiliza a linha 3 para alterar o estoque
        File arquivoProduto = new File(dir.getDirEstoque(), id + ".txt");

        if (!arquivoProduto.exists()) {
            System.out.println("[" + dataHora + "] - [TelaVendas.java] - ERRO: Arquivo do produto não encontrado: "+ id);
            return;
        }

        List<String> linhasArquivo = Files.readAllLines(arquivoProduto.toPath(), StandardCharsets.UTF_8);

        if (linhasArquivo.size() < 3) {
            System.out.println("[" + dataHora + "] - [TelaVendas.java] - ERRO: Arquivo do produto não possui linhas suficientes para alterar o estoque.");
            return;
        }

        // Atualiza o estoque na linha 3 do arquivo
        String linhaEstoque = linhasArquivo.get(2);
        if (linhaEstoque.startsWith("Estoque:")) {
            int estoqueAtualNoArquivo = Integer.parseInt(linhaEstoque.substring("Estoque:".length()).trim());
            int novaQuantidade = estoqueAtualNoArquivo + quantidadeRemover; // Atualiza o estoque
            linhasArquivo.set(2, "Estoque: " + novaQuantidade);
            System.out.println("[" + dataHora + "] - [TelaVendas.java] - Estoque atualizado na linha 3 do arquivo do produto com ID "+id);
        } else {
            System.out.println("[" + dataHora + "] - [TelaVendas.java] - ERRO: Linha 3 do arquivo não contém a palavra 'Estoque:'.");
            return;
        }

        // Escreve as linhas atualizadas de volta no arquivo
        Files.write(arquivoProduto.toPath(), linhasArquivo, StandardCharsets.UTF_8);
        System.out.println("[" + dataHora + "] - [TelaVendas.java] - Estoque do produto removido atualizado com sucesso!");

        // 5. Atualizar a quantidade na tabela ou remover a linha se a quantidade for zero
        int quantidadeRestante = quantidadeNaTabela - quantidadeRemover;
        if (quantidadeRestante > 0) {
            modeloTabela.setValueAt(quantidadeRestante, linhaEncontrada, 2); // Atualiza a quantidade na tabela
            //System.out.println("Quantidade atualizada na tabela para o produto com ID " + id + ".");
        } else {
            modeloTabela.removeRow(linhaEncontrada); // Remove a linha da tabela se a quantidade for zero
            //System.out.println("Produto removido da tabela.");
        }

        // Zera os campos de ID e quantidade
        campoId.setText("000");
        campoQtn.setText("000");

        System.out.println("[" + dataHora + "] - [TelaVendas.java] - Produto removido do carrinho com sucesso! Realizando contagem do valor total novamente!");
        campoTotalDaCompra.setText("R$ " + calcularTotalCompra());
    }

// Método para verificar se uma string é numérica
    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str); // Tenta converter para número
            return true;
        } catch (NumberFormatException e) {
            return false; // Se der erro, não é numérico
        }
    }

    public void relerArquivosDeEstoque() {
        // Obtém o diretório de estoque a partir da classe Diretorios
        String dirEstoque = dir.getDirEstoque(); // 'dir' é a sua instância da classe Diretorios

        // Cria o objeto File para o diretório de estoque
        File dirEstoqueFile = new File(dirEstoque);

        // Verifica se o diretório existe
        if (dirEstoqueFile.exists() && dirEstoqueFile.isDirectory()) {

            // Lista os arquivos com extensão ".txt" no diretório
            File[] arquivosEstoque = dirEstoqueFile.listFiles((d, name) -> name.endsWith(".txt"));

            // Verifica se existem arquivos de estoque
            if (arquivosEstoque != null && arquivosEstoque.length > 0) {
                // Para cada arquivo de estoque encontrado, você pode ler ou processar conforme necessário
                for (File arquivo : arquivosEstoque) {
                    // Aqui você pode abrir o arquivo para leitura ou processamento
                    try {
                        // Exemplo de leitura do arquivo (ajuste conforme a necessidade)
                        BufferedReader reader = new BufferedReader(new FileReader(arquivo));
                        String linha;
                        while ((linha = reader.readLine()) != null) {
                        }
                        reader.close();
                    } catch (IOException e) {
                        System.out.println("[" + dataHora + "] - [TelaVendas.java] - Erro ao ler o arquivo: " + arquivo.getName());
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("[" + dataHora + "] - [TelaVendas.java] - Arquivo do produto no estoque não foi encontrado!");
            }
        }
    }

    public double calcularTotalCompra() {
        lDescontoAplicado.setVisible(false);
        double total = 0.0;

        // Percorre todas as linhas da tabela para calcular o total
        for (int i = 0; i < tabelaProdutos.getRowCount(); i++) {
            Object valorVenda = tabelaProdutos.getValueAt(i, 3); // Coluna 3: Valor de venda
            Object quantidade = tabelaProdutos.getValueAt(i, 2); // Coluna 2: Quantidade

            if (valorVenda != null && quantidade != null) {
                try {
                    double valor = Double.parseDouble(valorVenda.toString());
                    int quantidadeItem = Integer.parseInt(quantidade.toString());

                    // Calcula o total (valor * quantidade)
                    total += valor * quantidadeItem;
                } catch (NumberFormatException e) {
                    System.out.println("[" + dataHora + "] - [TelaVendas.java] - Erro ao converter os valores: " + e.getMessage());
                    return 0.0;
                }
            }
        }

        // Obtendo o valor do desconto no formato 3 caracteres
        String descontoStr = campoDesconto.getText().trim();
        int desconto = Integer.parseInt(descontoStr); // Remove os zeros à esquerda automaticamente

        // Se o desconto for "000", significa que não deve aplicar o desconto
        if (desconto > 0) {
            total -= (total * desconto / 100); // Aplica o desconto
            lDescontoAplicado.setVisible(true);
            DecimalFormat df = new DecimalFormat("###,##0.00");
        }

        DecimalFormat df = new DecimalFormat("###,##0.00");

        // Atualiza o texto do JLabel com o valor do desconto aplicado
        lDescontoAplicado.setText("- Desconto de " + desconto + "% aplicado!");

        return Double.parseDouble(df.format(total).replace(",", "."));
    }

    private void cancelarCompra() throws IOException {

        relerArquivosDeEstoque();

        for (int i = 0; i < modeloTabela.getRowCount(); i++) {
            String idTabela = modeloTabela.getValueAt(i, 0).toString(); // Obtém o ID da tabela (índice 0)
            int quantidadeNaTabela = 0;
            String quantidadeNaTabelaStr = modeloTabela.getValueAt(i, 2).toString(); // Coluna da quantidade (índice 2)
            if (isNumeric(quantidadeNaTabelaStr)) {
                quantidadeNaTabela = Integer.parseInt(quantidadeNaTabelaStr);
            } else {
                System.out.println("[" + dataHora + "] - [TelaVendas.java] - ERRO: Quantidade inválida na tabela para ID " + idTabela);
                continue;
            }

            // Entra no arquivo do produto para atualizar o estoque
            File arquivoProduto = new File(dir.getDirEstoque(), idTabela + ".txt");

            if (!arquivoProduto.exists()) {
                System.out.println("[" + dataHora + "] - [TelaVendas.java] - ERRO: Arquivo do produto não encontrado: " + idTabela);
                continue;
            }

            List<String> linhasArquivo = Files.readAllLines(arquivoProduto.toPath(), StandardCharsets.UTF_8);
            boolean estoqueAtualizado = false;

            // Atualiza o estoque na linha 3 do arquivo
            if (linhasArquivo.size() >= 3) {
                String linhaEstoque = linhasArquivo.get(2);
                if (linhaEstoque.startsWith("Estoque:")) {
                    int estoqueAtualNoArquivo = Integer.parseInt(linhaEstoque.substring("Estoque:".length()).trim());
                    int novaQuantidade = estoqueAtualNoArquivo + quantidadeNaTabela; // Atualiza o estoque
                    linhasArquivo.set(2, "Estoque: " + novaQuantidade);
                    estoqueAtualizado = true;
                } else {
                }
            } else {
            }

            if (estoqueAtualizado) {
                // Escreve as linhas atualizadas de volta no arquivo
                Files.write(arquivoProduto.toPath(), linhasArquivo, StandardCharsets.UTF_8);
                System.out.println("Estoque atualizado com sucesso para ID " + idTabela);
            }
        }

        // Remove todas as linhas da tabela após atualizar o estoque
        modeloTabela.setRowCount(0);
        System.out.println("[" + dataHora + "] - [TelaVendas.java] - Todos os produtos do carrinho foram retirados!");

        // Zera os campos de ID e quantidade
        campoId.setText("000");
        campoQtn.setText("000");
        campoTipocliente.setSelectedItem("");
        campoFuncionario.setSelectedItem("");
        campoFormaDePagamento.setSelectedItem("");
        campoDesconto.setText("000");

        campoTotalDaCompra.setText("R$ " + calcularTotalCompra());
    }
}

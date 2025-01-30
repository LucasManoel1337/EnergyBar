package energy.bar;

import energy.bar.bancoDeDados.Diretorios;
import energy.bar.support.IdFilter;
import energy.bar.support.IdVerifier;
import energy.bar.support.LabelEnergyBar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.AbstractDocument;

class TelaVendas extends JPanel {

    Diretorios dir = new Diretorios();
    LabelEnergyBar labelEnergyBar = new LabelEnergyBar();
    public double valorTotalDeCompra = 0.0;

    private String idProdutoComprado = "";
    private String loteComprado = "";
    private int quantidadeRemovida = 0;
    private String nomeProdutoComprado = "";
    private String valorDeVendaComprado = "";

    public TelaVendas() {
        setLayout(null);

        // Criando e adicionando a label EnergyBar
        JLabel energyBarLabel = labelEnergyBar.criarLabelEnergyBar(dir);
        add(energyBarLabel);

        String[] colunas = {"ID", "Produto", "Qtn", "Valor", "Lote"};
        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);

        JTable tabelaProdutos = new JTable(modeloTabela);

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

        // Label e TextField ID
        JLabel lId = new JLabel("ID do produto");
        lId.setFont(new Font("Arial", Font.BOLD, 16));
        lId.setBounds(420, 60, 300, 40); // Define posição e tamanho
        lId.setVisible(true);
        add(lId);
        JTextField campoId = new JTextField();
        campoId.setBounds(420, 90, 330, 30);
        campoId.setBackground(Color.LIGHT_GRAY);
        campoId.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        campoId.setFont(new Font("Arial", Font.BOLD, 16));
        IdVerifier verifier = new IdVerifier();
        campoId.setInputVerifier(verifier);
        IdFilter idFilter = new IdFilter();
        ((AbstractDocument) campoId.getDocument()).setDocumentFilter(idFilter);
        campoId.setVisible(true);
        add(campoId);

        // Label e TextField ID
        JLabel lQnt = new JLabel("Quantidade");
        lQnt.setFont(new Font("Arial", Font.BOLD, 16));
        lQnt.setBounds(420, 120, 300, 40); // Define posição e tamanho
        lQnt.setVisible(true);
        add(lQnt);
        JTextField campoQtn = new JTextField();
        campoQtn.setBounds(420, 150, 330, 30);
        campoQtn.setBackground(Color.LIGHT_GRAY);
        campoQtn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        campoQtn.setFont(new Font("Arial", Font.BOLD, 16));
        campoQtn.setInputVerifier(verifier);
        ((AbstractDocument) campoQtn.getDocument()).setDocumentFilter(idFilter);
        campoQtn.setVisible(true);
        add(campoQtn);

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
        String[] responsaveis = {"", "Cliente", "Funcionario"};
        JComboBox<String> campoResponsavel = new JComboBox<>(responsaveis);
        campoResponsavel.setBounds(420, 260, 330, 30);
        campoResponsavel.setBackground(Color.LIGHT_GRAY);
        campoResponsavel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        campoResponsavel.setFocusable(false);
        campoResponsavel.setFont(new Font("Arial", Font.BOLD, 16));
        campoResponsavel.setVisible(true);
        add(campoResponsavel);

        JLabel lFormaDePagamento = new JLabel("Forma de pagamento");
        lFormaDePagamento.setFont(new Font("Arial", Font.BOLD, 16));
        lFormaDePagamento.setBounds(420, 290, 330, 30);
        lFormaDePagamento.setVisible(true);
        add(lFormaDePagamento);
        String[] formaDePagamento = {"", "Dinheiro", "Pix", "Débito", "Crédito"};
        JComboBox<String> campoFormaDePagamento = new JComboBox<>(formaDePagamento);
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
        JTextField campoDesconto = new JTextField();
        campoDesconto.setBounds(420, 380, 220, 30);
        campoDesconto.setBackground(Color.LIGHT_GRAY);
        campoDesconto.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        campoDesconto.setFont(new Font("Arial", Font.BOLD, 16));
        campoDesconto.setInputVerifier(verifier);
        ((AbstractDocument) campoDesconto.getDocument()).setDocumentFilter(idFilter);
        campoDesconto.setVisible(true);
        add(campoDesconto);

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

        JLabel lFuncionario = new JLabel("Funcionario");
        lFuncionario.setFont(new Font("Arial", Font.BOLD, 16));
        lFuncionario.setBounds(420, 410, 330, 30);
        lFuncionario.setVisible(true);
        add(lFuncionario);
        String[] funcionarios = {"", "Pamela", "Lucas", "Fellipe"};
        JComboBox<String> campoFuncionario = new JComboBox<>(funcionarios);
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
        JTextField campoTotalDaCompra = new JTextField();
        campoTotalDaCompra.setBounds(10, 510, 330, 30);
        campoTotalDaCompra.setBackground(Color.LIGHT_GRAY);
        campoTotalDaCompra.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        campoTotalDaCompra.setFont(new Font("Arial", Font.BOLD, 16));
        campoTotalDaCompra.setVisible(true);
        campoTotalDaCompra.setEnabled(false);
        campoTotalDaCompra.setText("R$ 00,00");
        add(campoTotalDaCompra);

        bAdicionar.addActionListener(e -> {
            // Verifica se os campos de ID e Quantidade foram preenchidos
            String id = campoId.getText().trim();
            String qtnStr = campoQtn.getText().trim();

            if (id.isEmpty() || qtnStr.isEmpty()) {
                // Exibir mensagem de erro se algum campo estiver vazio
                System.out.println("CAMPOS VAZIOS");
                return;
            }

            int quantidadeDesejada = Integer.parseInt(qtnStr);

            try {
                // Encontre o arquivo do produto baseado no ID
                File arquivoProduto = new File(dir.getDirEstoque(), id + ".txt");

                if (!arquivoProduto.exists()) {
                    // Se o arquivo não existir, exibe um erro
                    System.out.println("ID NAO EXISTE: " + id);
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

                // Exibe no console qual lote está sendo utilizado
                System.out.println("Lote utilizado: " + loteAntigo);
                System.out.println("Quantidade no lote mais antigo: " + quantidadeLoteAntigo);

                // Verifica se há quantidade suficiente no estoque
                if (quantidadeDesejada > quantidadeLoteAntigo) {
                    System.out.println("QUANTIDADE NAO HA NO LOTE MAIS ANTIGO");
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
                campoId.setText("");
                campoQtn.setText("");

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        bFinalizarCompra.addActionListener(e -> {
            // Verifica se os campos ComboBox e a tabela estão preenchidos
            if (campoResponsavel.getSelectedItem() == null || campoFormaDePagamento.getSelectedItem() == null || campoFuncionario.getSelectedItem() == null) {
                System.out.println("Campos não preenchidos.");
                return;
            }

            if (modeloTabela.getRowCount() == 0) {
                System.out.println("Tabela vazia.");
                return;
            }

            try {
                // Pega a data e hora atual para nome do arquivo
                String dataHora = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());

                // Cria o arquivo com o nome baseado na data e hora
                File arquivoHistorico = new File(dir.getDirHistoricoDeCompras(), dataHora + ".txt");

                // Cria o BufferedWriter para escrever no arquivo
                BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoHistorico));

                // Escreve as informações dos campos ComboBox
                writer.write("Tipo de Cliente: " + campoResponsavel.getSelectedItem().toString() + "\n");
                writer.write("Forma de Pagamento: " + campoFormaDePagamento.getSelectedItem().toString() + "\n");
                writer.write("Funcionário: " + campoFuncionario.getSelectedItem().toString() + "\n");

                // Escreve o valor total da compra na primeira linha
                writer.write("Valor Total da Compra: " + campoTotalDaCompra.getText() + "\n");

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

                        System.out.println("Lotes com estoque zero removidos do produto: " + arquivoProduto.getName());
                    }
                }

                // Fecha o arquivo do histórico de compras
                writer.close();

                // Mensagem de confirmação
                System.out.println("Compra registrada no arquivo: " + arquivoHistorico.getName());
                System.out.println("Compra finalizada. Lotes com estoque zero foram removidos.");

                // Limpa a tabela após registrar
                modeloTabela.setRowCount(0);
                campoResponsavel.setSelectedItem("");
                campoFuncionario.setSelectedItem("");
                campoTotalDaCompra.setText("R$ 00,00");
                campoFormaDePagamento.setSelectedItem("");

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        bCancelarCompra.addActionListener(e -> {
            // Percorrer as linhas da tabela e processar cada item
            for (int i = 0; i < modeloTabela.getRowCount(); i++) {
                String idProduto = modeloTabela.getValueAt(i, 0).toString();
                String nomeProduto = modeloTabela.getValueAt(i, 1).toString();
                int quantidadeProduto = Integer.parseInt(modeloTabela.getValueAt(i, 2).toString());
                double valorDeVenda = Double.parseDouble(modeloTabela.getValueAt(i, 3).toString());
                String loteProduto = modeloTabela.getValueAt(i, 4).toString();

                // Encontre o arquivo do produto baseado no ID
                File arquivoProduto = new File(dir.getDirEstoque(), idProduto + ".txt");

                try {
                    if (!arquivoProduto.exists()) {
                        // Se o arquivo não existir, exibe um erro
                        System.out.println("ID NAO EXISTE: " + idProduto);
                        continue;
                    }

                    // Lê os dados do arquivo de produto
                    BufferedReader reader = new BufferedReader(new FileReader(arquivoProduto));
                    String linha;
                    String nomeProdutoArquivo = "";
                    int quantidadeAtual = 0;
                    String loteAtual = "";
                    // Variáveis para armazenar os dados que precisam ser revertidos
                    int quantidadeLoteAtual = 0;

                    // Lê cada linha e captura os dados relevantes
                    while ((linha = reader.readLine()) != null) {
                        if (linha.startsWith("Nome do Produto:")) {
                            nomeProdutoArquivo = linha.substring("Nome do Produto:".length()).trim();
                        }
                        if (linha.startsWith("Estoque:")) {
                            quantidadeAtual = Integer.parseInt(linha.substring("Estoque:".length()).trim());
                        }
                        if (linha.startsWith("Lote:")) {
                            loteAtual = linha.substring("Lote:".length()).trim();
                            if (loteProduto.equals(loteAtual)) {
                                quantidadeLoteAtual = quantidadeAtual;
                            }
                        }
                    }
                    reader.close();

                    // Verifica se encontrou o lote e o produto
                    if (!loteProduto.equals(loteAtual)) {
                        System.out.println("Lote não encontrado ou não corresponde.");
                        continue;
                    }

                    // Atualiza a quantidade no arquivo, adicionando de volta a quantidade que foi retirada
                    atualizarQuantidadeNoArquivo(arquivoProduto, quantidadeLoteAtual + quantidadeProduto);

                    // Atualiza o valor total da compra, subtraindo o valor do item removido
                    valorTotalDeCompra -= quantidadeProduto * valorDeVenda;

                    // Atualiza o campo de total da compra
                    campoTotalDaCompra.setText(String.format("R$ " + "%.2f", valorTotalDeCompra));

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            // Remove todos os itens da tabela
            modeloTabela.setRowCount(0);

            // Limpa o campo de total da compra após cancelar
            campoTotalDaCompra.setText("R$ 0.00");

            System.out.println("Compra cancelada e estoques atualizados.");
        });
    }

    // Método para atualizar a quantidade no arquivo do produto
    private void atualizarQuantidadeNoArquivo(File arquivo, int novaQuantidade) throws IOException {
        // Lê o conteúdo do arquivo
        List<String> linhas = Files.readAllLines(arquivo.toPath(), StandardCharsets.UTF_8);

        // Mostra as linhas do arquivo para ver o que estamos lendo
        System.out.println("Linhas lidas do arquivo:");
        for (String l : linhas) {
            System.out.println(l);
        }

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
            System.out.println("Atualizando o estoque no arquivo...");
            Files.write(arquivo.toPath(), linhas, StandardCharsets.UTF_8);
            System.out.println("Estoque atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar o estoque: estoque não encontrado no arquivo.");
        }
    }
}

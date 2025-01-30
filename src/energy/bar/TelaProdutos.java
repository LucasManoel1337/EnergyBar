package energy.bar;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import energy.bar.bancoDeDados.Diretorios;
import energy.bar.support.LabelEnergyBar;

public class TelaProdutos extends JPanel {

    Diretorios dir = new Diretorios();
    LabelEnergyBar labelEnergyBar = new LabelEnergyBar();

    private EnergyBarApp mainApp;

    // Tabela de produtos
    String[] colunas = {"ID", "Produto", "Resp.", "Qnt", "Validade", "Custo", "Venda", "Lote"};
    DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);

    JTable tabelaProdutos = new JTable(modeloTabela);

    public TelaProdutos(EnergyBarApp mainApp) throws ParseException {
        setLayout(null); // Define layout manual (absoluto)
        this.mainApp = mainApp;

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
        tabelaProdutos.getColumnModel().getColumn(0).setPreferredWidth(10);
        tabelaProdutos.getColumnModel().getColumn(5).setPreferredWidth(20);
        tabelaProdutos.getColumnModel().getColumn(6).setPreferredWidth(20);
        tabelaProdutos.getColumnModel().getColumn(3).setPreferredWidth(8);

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

        tabelaProdutos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                cellComponent.setFont(new Font("Arial", Font.BOLD, 14)); // Define a fonte desejada
                // Regra para a coluna "Estoque" (índice 3)
                if (column == 3) {
                    try {
                        int estoque = Integer.parseInt(value.toString());

                        // Se o estoque for menor ou igual a 5, colorir a célula de vermelho
                        if (estoque <= 5) {
                            cellComponent.setBackground(Color.RED);
                            cellComponent.setForeground(Color.WHITE); // Texto em branco
                        } else {
                            cellComponent.setBackground(Color.LIGHT_GRAY);
                            cellComponent.setForeground(Color.BLACK); // Texto em preto
                        }
                    } catch (Exception e) {
                        // Caso ocorra algum erro (valor inválido), mantemos a célula com a cor padrão
                        cellComponent.setBackground(Color.LIGHT_GRAY);
                        cellComponent.setForeground(Color.BLACK); // Texto em preto
                    }
                } // Regra para a coluna "Validade" (índice 4)
                else if (column == 4) {
                    try {
                        String validadeStr = value.toString();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date validadeDate = dateFormat.parse(validadeStr);

                        // Obtendo a data atual sem horas, minutos e segundos
                        Calendar hoje = Calendar.getInstance();
                        hoje.set(Calendar.HOUR_OF_DAY, 0);
                        hoje.set(Calendar.MINUTE, 0);
                        hoje.set(Calendar.SECOND, 0);
                        hoje.set(Calendar.MILLISECOND, 0);
                        Date currentDate = hoje.getTime();

                        // Calculando a diferença em dias entre a validade e a data atual
                        long diffInMillies = validadeDate.getTime() - currentDate.getTime();
                        long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

                        if (validadeDate.before(currentDate) || diffInDays == 0) {
                            // Se já venceu ou vence hoje, cor preta com texto branco
                            cellComponent.setBackground(Color.BLACK);
                            cellComponent.setForeground(Color.WHITE);
                        } else if (diffInDays < 30) {
                            // Se a validade for menor que 30 dias, cor vermelha com texto branco
                            cellComponent.setBackground(Color.RED);
                            cellComponent.setForeground(Color.WHITE);
                        } else {
                            // Caso contrário, mantém a cor padrão
                            cellComponent.setBackground(Color.LIGHT_GRAY);
                            cellComponent.setForeground(Color.BLACK);
                        }

                    } catch (Exception e) {
                        // Caso ocorra erro no formato da data, mantém a cor padrão
                        cellComponent.setBackground(Color.LIGHT_GRAY);
                        cellComponent.setForeground(Color.BLACK);
                    }
                } else {
                    // Para as outras células, mantém a cor padrão
                    cellComponent.setBackground(Color.LIGHT_GRAY);
                    cellComponent.setForeground(Color.BLACK);
                }

                // Garantir que a cor de fundo seja mantida, mesmo ao selecionar a célula
                if (isSelected) {
                    cellComponent.setBackground(cellComponent.getBackground().darker());
                }

                return cellComponent;
            }
        });

        JScrollPane scrollPane = new JScrollPane(tabelaProdutos);
        scrollPane.setVisible(true);
        scrollPane.setBounds(10, 150, 740, 300); // Definindo o tamanho e posição
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 2)); // Bordas do JScrollPane
        add(scrollPane);

        // Carregar os produtos da pasta
        modeloTabela.setRowCount(0);
        carregarProdutos(modeloTabela);

        // Label inicial
        JLabel label = new JLabel("Pesquisar ID ou nome do produto");
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setBounds(10, 70, 300, 40); // Define posição e tamanho
        add(label);

        // Botão Cadastrar Produto
        JButton bCadastrarProduto = new JButton("Cadastrar Produto");
        bCadastrarProduto.setFont(new Font("Arial", Font.BOLD, 16));
        bCadastrarProduto.setBounds(550, 80, 200, 50); // Define posição e tamanho
        bCadastrarProduto.setBackground(new Color(32, 3, 3));
        bCadastrarProduto.setForeground(Color.WHITE);
        bCadastrarProduto.setFocusPainted(false);
        bCadastrarProduto.setBorderPainted(false);
        add(bCadastrarProduto);

        // Label pesquisa
        JLabel lIdNaoExistente = new JLabel("Não existe produto cadastrado com esse ID!");
        lIdNaoExistente.setFont(new Font("Arial", Font.BOLD, 16));
        lIdNaoExistente.setBounds(200, 510, 400, 40); // Define posição e tamanho
        lIdNaoExistente.setForeground(Color.RED);
        lIdNaoExistente.setVisible(false);
        add(lIdNaoExistente);

        // Label pesquisa
        JLabel lPesquisaVazio = new JLabel("Não foi possivel pesquisar o produto com o campo de pesquisa vazio!");
        lPesquisaVazio.setFont(new Font("Arial", Font.BOLD, 16));
        lPesquisaVazio.setBounds(110, 510, 550, 40); // Define posição e tamanho
        lPesquisaVazio.setForeground(Color.RED);
        lPesquisaVazio.setVisible(false);
        add(lPesquisaVazio);

        JTextField campoPesquisa = new JTextField();
        campoPesquisa.setBounds(10, 100, 300, 30);
        campoPesquisa.setBackground(Color.LIGHT_GRAY);
        campoPesquisa.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        campoPesquisa.setFont(new Font("Arial", Font.BOLD, 16));
        add(campoPesquisa);

        // Botão Pesquisar
        JButton bPesquisar = new JButton("Pesquisar");
        bPesquisar.setFont(new Font("Arial", Font.BOLD, 14));
        bPesquisar.setBounds(315, 100, 110, 30); // Mesmo tamanho do botão anterior
        bPesquisar.setBackground(new Color(32, 3, 3));
        bPesquisar.setForeground(Color.WHITE);
        bPesquisar.setFocusPainted(false);
        bPesquisar.setBorderPainted(false);
        bPesquisar.setVisible(true); // Inicialmente invisível
        add(bPesquisar);

        JButton bAtualizar = new JButton("Atualizar");
        bAtualizar.setFont(new Font("Arial", Font.BOLD, 14));
        bAtualizar.setBounds(590, 460, 160, 30);
        bAtualizar.setBackground(new Color(32, 3, 3));
        bAtualizar.setForeground(Color.WHITE);
        bAtualizar.setFocusPainted(false);
        bAtualizar.setBorderPainted(false);
        bAtualizar.setVisible(true);
        bAtualizar.addActionListener(e -> {
            campoPesquisa.setText("");
            lPesquisaVazio.setVisible(false);
            lIdNaoExistente.setVisible(false);
            modeloTabela.setRowCount(0); // Limpa a tabela
            carregarProdutos(modeloTabela); // Recarrega os dados
        });
        add(bAtualizar);

        bPesquisar.addActionListener(e -> {
            lIdNaoExistente.setVisible(false);
            String pesquisa = campoPesquisa.getText().trim().toLowerCase(); // Normaliza para minúsculas

            if (pesquisa.isEmpty()) {
                lPesquisaVazio.setVisible(true);
            } else {
                lPesquisaVazio.setVisible(false);
                modeloTabela.setRowCount(0); // Limpa a tabela antes de preencher

                File diretorio = new File(dir.getDirEstoque());
                if (!diretorio.exists()) {
                    JOptionPane.showMessageDialog(this, "Diretório de estoque não encontrado.");
                    return;
                }

                File[] arquivos = diretorio.listFiles((dir, name) -> name.endsWith(".txt"));
                boolean produtoEncontrado = false;

                for (File arquivo : arquivos) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
                        String linha;
                        String id = arquivo.getName().replace(".txt", ""); // ID = Nome do arquivo sem ".txt"
                        String nome = "", responsavel = "", estoque = "";
                        String validade = "", valorCusto = "", valorVenda = "", lote = "";

                        while ((linha = reader.readLine()) != null) {
                            linha = linha.trim();

                            if (linha.startsWith("Nome do Produto: ")) {
                                nome = linha.replace("Nome do Produto: ", "").trim();
                            } else if (linha.startsWith("Responsável: ")) {
                                responsavel = linha.replace("Responsável: ", "").trim();
                            } else if (linha.startsWith("Estoque: ")) {
                                estoque = linha.replace("Estoque: ", "").trim();
                            } else if (linha.startsWith("Validade: ")) {
                                // Se já havia uma validade lida, significa que um novo lote começou,
                                // então adicionamos o lote anterior antes de sobrescrever as variáveis.
                                if (!validade.isEmpty()) {
                                    if (id.toLowerCase().contains(pesquisa) || nome.toLowerCase().contains(pesquisa)) {
                                        produtoEncontrado = true;
                                        lIdNaoExistente.setVisible(false);

                                        // Adiciona o lote anterior antes de sobrescrever as variáveis
                                        modeloTabela.addRow(new Object[]{id, nome, responsavel, estoque, validade, valorCusto, valorVenda, lote});
                                    }
                                }
                                validade = linha.replace("Validade: ", "").trim(); // Atualiza a validade
                                valorCusto = ""; // Reseta os valores para garantir que o próximo lote não herde o anterior
                                valorVenda = "";
                            } else if (linha.startsWith("Valor de Custo: ")) {
                                valorCusto = linha.replace("Valor de Custo: ", "").trim();
                            } else if (linha.startsWith("Valor de Venda: ")) {
                                valorVenda = linha.replace("Valor de Venda: ", "").trim();
                            } else if (linha.startsWith("Lote: ")) {
                                lote = linha.replace("Lote: ", "").trim();
                            }
                        }

                        // Após a leitura do arquivo, adicionamos o último lote (se houver um válido)
                        if (!validade.isEmpty()) {
                            if (id.toLowerCase().contains(pesquisa) || nome.toLowerCase().contains(pesquisa)) {
                                produtoEncontrado = true;
                                lIdNaoExistente.setVisible(false);

                                modeloTabela.addRow(new Object[]{id, nome, responsavel, estoque, validade, valorCusto, valorVenda, lote});
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                if (!produtoEncontrado) {
                    lIdNaoExistente.setVisible(true);
                }
            }
        });

        // Ação do botão "Cadastrar Produto"
        bCadastrarProduto.addActionListener(e -> {
            TelaCadastrarProduto telaCadastro = mainApp.getTelaCadastrarProduto(); // Usando o getter
            mainApp.exibirTela(telaCadastro);
        });
    }

    public void carregarProdutos(DefaultTableModel modeloTabela) {
        File diretorio = new File(dir.getDirEstoque());
        if (diretorio.exists() && diretorio.isDirectory()) {
            File[] arquivos = diretorio.listFiles((dir, name) -> name.endsWith(".txt"));
            if (arquivos != null) {
                for (File arquivo : arquivos) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
                        String nomeProduto = "", responsavel = "", estoque = "", validade = "", custo = "", venda = "";
                        String lote = "", dataCadastro = "";
                        String linha;

                        while ((linha = reader.readLine()) != null) {
                            // Verifica os dados do produto
                            if (linha.startsWith("Nome do Produto: ")) {
                                nomeProduto = linha.replace("Nome do Produto: ", "").trim();
                            } else if (linha.startsWith("Responsável: ")) {
                                responsavel = linha.replace("Responsável: ", "").trim();
                            } else if (linha.startsWith("Estoque: ")) {
                                estoque = linha.replace("Estoque: ", "").trim();
                            } else if (linha.startsWith("Validade: ")) {
                                validade = linha.replace("Validade: ", "").trim();
                            } else if (linha.startsWith("Valor de Custo: ")) {
                                custo = linha.replace("Valor de Custo: ", "").trim();
                            } else if (linha.startsWith("Valor de Venda: ")) {
                                venda = linha.replace("Valor de Venda: ", "").trim();
                            } else if (linha.startsWith("Lote: ")) {
                                lote = linha.replace("Lote: ", "").trim();
                            } else if (linha.startsWith("Data e Hora de Cadastro: ")) {
                                dataCadastro = linha.replace("Data e Hora de Cadastro: ", "").trim();

                                // Adiciona uma nova linha para cada lote encontrado
                                modeloTabela.addRow(new Object[]{
                                    arquivo.getName().replace(".txt", ""), // ID (nome do arquivo sem a extensão)
                                    nomeProduto,
                                    responsavel,
                                    estoque,
                                    validade,
                                    custo,
                                    venda,
                                    lote,
                                    dataCadastro
                                });
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

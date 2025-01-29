package energy.bar;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.ParseException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.AbstractDocument;
import javax.swing.text.MaskFormatter;

public class TelaCadastrarProduto extends JPanel {
    
    private EnergyBarApp mainApp; // Adicione o campo para a referência
    
    public TelaCadastrarProduto(EnergyBarApp mainApp) throws ParseException {
        this.mainApp = mainApp; // Inicialize a referência
        setVisible(true);
        setLayout(null); // Define layout manual (absoluto)
        
        // Título principal
        JLabel EnergyBar = new JLabel("ENERGY BAR", JLabel.CENTER);
        EnergyBar.setFont(new Font("Arial", Font.BOLD, 32));
        EnergyBar.setBounds(250, 20, 300, 40); // Define posição e tamanho
        EnergyBar.setVisible(true);
        add(EnergyBar, SwingConstants.CENTER);
        
        // Label falta de dados
        JLabel lfaltaDeDados = new JLabel("Todos os campos devem ser preenchidos!");
        lfaltaDeDados.setFont(new Font("Arial", Font.BOLD, 16));
        lfaltaDeDados.setBounds(220, 510, 350, 40); // Define posição e tamanho
        lfaltaDeDados.setForeground(Color.RED);
        lfaltaDeDados.setVisible(false);
        add(lfaltaDeDados);
        
        // Label falta de dados
        JLabel lCadastroFeito = new JLabel("Produto cadastrado com sucesso!");
        lCadastroFeito.setFont(new Font("Arial", Font.BOLD, 16));
        lCadastroFeito.setBounds(250, 510, 350, 40); // Define posição e tamanho
        lCadastroFeito.setForeground(Color.GREEN);
        lCadastroFeito.setVisible(false);
        add(lCadastroFeito);
        
        // Label falta de dados
        JLabel lCampoIdVazio = new JLabel("Não foi possivel buscar ID com o campo do ID vazio!");
        lCampoIdVazio.setFont(new Font("Arial", Font.BOLD, 16));
        lCampoIdVazio.setBounds(180, 510, 500, 40); // Define posição e tamanho
        lCampoIdVazio.setForeground(Color.RED);
        lCampoIdVazio.setVisible(false);
        add(lCampoIdVazio);
        
        // Label e TextField ID
        JLabel lId = new JLabel("ID do produto");
        lId.setFont(new Font("Arial", Font.BOLD, 16));
        lId.setBounds(50, 70, 300, 40); // Define posição e tamanho
        lId.setVisible(true);
        add(lId);
        JTextField campoId = new JTextField();
        campoId.setBounds(50, 100, 540, 30);
        campoId.setBackground(Color.LIGHT_GRAY);
        campoId.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        campoId.setFont(new Font("Arial", Font.BOLD, 16));
        IdVerifier verifier = new IdVerifier();
        campoId.setInputVerifier(verifier);
        IdFilter idFilter = new IdFilter();
        ((AbstractDocument) campoId.getDocument()).setDocumentFilter(idFilter);
        campoId.setVisible(true);
        add(campoId);
        
        // Botão Buscar
        JButton bBuscar = new JButton("Buscar ID");
        bBuscar.setFont(new Font("Arial", Font.BOLD, 14));
        bBuscar.setBounds(600, 100, 110, 30); // Mesmo tamanho do botão anterior
        bBuscar.setBackground(new Color(32, 3, 3));
        bBuscar.setForeground(Color.WHITE);
        bBuscar.setFocusPainted(false);
        bBuscar.setBorderPainted(false);
        bBuscar.setVisible(true);
        add(bBuscar);
        
        // Label e TextField Nome do produto
        JLabel lNomeProduto = new JLabel("Nome do Produto");
        lNomeProduto.setFont(new Font("Arial", Font.BOLD, 16));
        lNomeProduto.setBounds(50, 125, 300, 40); // Define posição e tamanho
        lNomeProduto.setVisible(true);
        add(lNomeProduto);
        JTextField campoNomeProduto = new JTextField();
        campoNomeProduto.setBounds(50, 155, 660, 30);
        campoNomeProduto.setBackground(Color.LIGHT_GRAY);
        campoNomeProduto.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        campoNomeProduto.setFont(new Font("Arial", Font.BOLD, 16));
        campoNomeProduto.setVisible(true);
        add(campoNomeProduto);
        
        // Label e TextField Responsavel
        JLabel lResponsavel = new JLabel("Responsável");
        lResponsavel.setFont(new Font("Arial", Font.BOLD, 16));
        lResponsavel.setBounds(50, 180, 300, 40); // Define posição e tamanho
        lResponsavel.setVisible(true);
        add(lResponsavel);
        String[] responsaveis = {"", "Pamela", "Lucas", "Fellipe"};
        JComboBox<String> campoResponsavel = new JComboBox<>(responsaveis);
        campoResponsavel.setBounds(50, 210, 660, 30);
        campoResponsavel.setBackground(Color.LIGHT_GRAY);
        campoResponsavel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        campoResponsavel.setFocusable(false);
        campoResponsavel.setFont(new Font("Arial", Font.BOLD, 16));
        campoResponsavel.setVisible(true);
        add(campoResponsavel);
        
        // Label e TextField Estoque
        JLabel lEstoque = new JLabel("Estoque");
        lEstoque.setFont(new Font("Arial", Font.BOLD, 16));
        lEstoque.setBounds(50, 235, 300, 40); // Define posição e tamanho
        lEstoque.setVisible(true);
        add(lEstoque);
        JTextField campoEstoque = new JTextField();
        campoEstoque.setBounds(50, 265, 660, 30);
        campoEstoque.setBackground(Color.LIGHT_GRAY);
        campoEstoque.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        campoEstoque.setFont(new Font("Arial", Font.BOLD, 16));
        NumberFilter numberFilter = new NumberFilter();
        ((AbstractDocument) campoEstoque.getDocument()).setDocumentFilter(numberFilter);
        EstoqueVerifier estoqueVerifier = new EstoqueVerifier();
        campoEstoque.setInputVerifier(estoqueVerifier);
        campoEstoque.setVisible(true);
        add(campoEstoque);
        
        // Label e TextField Validade
        JLabel lValidade = new JLabel("Validade");
        lValidade.setFont(new Font("Arial", Font.BOLD, 16));
        lValidade.setBounds(50, 290, 300, 40); // Define posição e tamanho
        lValidade.setVisible(true);
        add(lValidade);
        MaskFormatter mascaraData = new MaskFormatter("##/##/####");
        mascaraData.setPlaceholderCharacter('_');
        JFormattedTextField campoValidade = new JFormattedTextField(mascaraData);
        campoValidade.setBounds(50, 320, 660, 30);
        campoValidade.setBackground(Color.LIGHT_GRAY);
        campoValidade.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        campoValidade.setFont(new Font("Arial", Font.BOLD, 16));
        campoValidade.setVisible(true);
        add(campoValidade);
        
        // Label e TextField Valor de custo
        JLabel lValorDeCusto = new JLabel("Valor de custo");
        lValorDeCusto.setFont(new Font("Arial", Font.BOLD, 16));
        lValorDeCusto.setBounds(50, 345, 300, 40); // Define posição e tamanho
        lValorDeCusto.setVisible(true);
        add(lValorDeCusto);
        JTextField campoValorDeCusto = new JTextField();
        campoValorDeCusto.setBounds(50, 375, 660, 30);
        campoValorDeCusto.setBackground(Color.LIGHT_GRAY);
        campoValorDeCusto.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        campoValorDeCusto.setFont(new Font("Arial", Font.BOLD, 16));
        ValueFilter valueFilterCusto = new ValueFilter();
        ((AbstractDocument) campoValorDeCusto.getDocument()).setDocumentFilter(valueFilterCusto);
        ValueVerifier valueVerifierCusto = new ValueVerifier();
        campoValorDeCusto.setInputVerifier(valueVerifierCusto);
        campoValorDeCusto.setVisible(true);
        add(campoValorDeCusto);
        
        // Label e TextField Valor de venda
        JLabel lValorDeVenda = new JLabel("Valor de venda");
        lValorDeVenda.setFont(new Font("Arial", Font.BOLD, 16));
        lValorDeVenda.setBounds(50, 400, 300, 40); // Define posição e tamanho
        lValorDeVenda.setVisible(true);
        add(lValorDeVenda);
        JTextField campoValorDeVenda = new JTextField();
        campoValorDeVenda.setBounds(50, 430, 660, 30);
        campoValorDeVenda.setBackground(Color.LIGHT_GRAY);
        campoValorDeVenda.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        campoValorDeVenda.setFont(new Font("Arial", Font.BOLD, 16));
        ValueFilter valueFilterVenda = new ValueFilter();
        ((AbstractDocument) campoValorDeVenda.getDocument()).setDocumentFilter(valueFilterVenda);
        ValueVerifier valueVerifierVenda = new ValueVerifier();
        campoValorDeVenda.setInputVerifier(valueVerifierVenda);
        campoValorDeVenda.setVisible(true);
        add(campoValorDeVenda);

        // Botão Cancelar
        JButton bCancelar = new JButton("Cancelar");
        bCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        bCancelar.setBounds(50, 510, 100, 40); // Mesmo tamanho do botão anterior
        bCancelar.setBackground(Color.RED);
        bCancelar.setForeground(Color.WHITE);
        bCancelar.setFocusPainted(false);
        bCancelar.setBorderPainted(false);
        bCancelar.setVisible(true);
        add(bCancelar);
        
        // Botão Cadastrar
        JButton bCadastrar = new JButton("Cadastrar");
        bCadastrar.setFont(new Font("Arial", Font.BOLD, 14));
        bCadastrar.setBounds(600, 510, 110, 40); // Mesmo tamanho do botão anterior
        bCadastrar.setBackground(Color.GREEN);
        bCadastrar.setForeground(Color.WHITE);
        bCadastrar.setFocusPainted(false);
        bCadastrar.setBorderPainted(false);
        bCadastrar.setVisible(true);
        add(bCadastrar);
        
        // Label pesquisa
        JLabel lIdNaoExistente = new JLabel("Não existe produto cadastrado com esse ID!");
        lIdNaoExistente.setFont(new Font("Arial", Font.BOLD, 16));
        lIdNaoExistente.setBounds(200, 510, 400, 40); // Define posição e tamanho
        lIdNaoExistente.setForeground(Color.RED);
        lIdNaoExistente.setVisible(false);
        add(lIdNaoExistente);
        
        bCadastrar.addActionListener(e -> {
            lCampoIdVazio.setVisible(false);
            lfaltaDeDados.setVisible(false);
            lCadastroFeito.setVisible(false);
            lIdNaoExistente.setVisible(false);
            
            String id = campoId.getText().trim();
            String nomeDoProduto = campoNomeProduto.getText().trim();
            String responsavel = (String) campoResponsavel.getSelectedItem();
            String estoque = campoEstoque.getText().trim();
            String validade = campoValidade.getText().trim();
            String valorDeCusto = campoValorDeCusto.getText().trim();
            String valorDeVenda = campoValorDeVenda.getText().trim();

            if (id.isEmpty() || nomeDoProduto.isEmpty() || responsavel.isEmpty() || estoque.isEmpty() ||
                validade.isEmpty() || valorDeCusto.isEmpty() || valorDeVenda.isEmpty()) {
                lfaltaDeDados.setVisible(true);
                lCadastroFeito.setVisible(false);
                return;
            }
            
            File diretorio = new File("Estoque de produtos");
            if (!diretorio.exists()) {
                diretorio.mkdirs(); // Cria o diretório e quaisquer diretórios pais que não existam
            } 

            try {
                File arquivo = new File(diretorio, id + ".txt");
                BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo));
                writer.write("Nome do Produto: " + nomeDoProduto + "\n");
                writer.write("Responsável: " + responsavel + "\n");
                writer.write("Estoque: " + estoque + "\n");
                writer.write("Validade: " + validade + "\n");
                writer.write("Valor de Custo: " + valorDeCusto + "\n");
                writer.write("Valor de Venda: " + valorDeVenda + "\n");
                writer.close();

                campoId.setText("");
                campoNomeProduto.setText("");
                campoEstoque.setText("");
                campoResponsavel.setSelectedItem("");
                campoValidade.setText("");
                campoValorDeCusto.setText("");
                campoValorDeVenda.setText("");
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
                lCampoIdVazio.setVisible(false);
                lfaltaDeDados.setVisible(false);
                lCadastroFeito.setVisible(true);
                lIdNaoExistente.setVisible(false);
            });

        // Ação do botão "Cancelar"
        bCancelar.addActionListener(e -> {
            lCampoIdVazio.setVisible(false);
            lfaltaDeDados.setVisible(false);
            lCadastroFeito.setVisible(false);
            lIdNaoExistente.setVisible(false);
            
            mainApp.exibirTela(mainApp.getTelaProdutos()); // Voltar para a tela de produtos
            // Limpar os campos (opcional)
            campoId.setText("");
            campoNomeProduto.setText("");
            campoResponsavel.setSelectedItem("");
            campoEstoque.setText("");
            campoValidade.setText("");
            campoValorDeCusto.setText("");
            campoValorDeVenda.setText("");
        });
        
        // Ação do botão "Cadastrar Produto"
        bBuscar.addActionListener(e -> {
    lCampoIdVazio.setVisible(false);
    lfaltaDeDados.setVisible(false);
    lCadastroFeito.setVisible(false);
    lIdNaoExistente.setVisible(false);

    String id = campoId.getText().trim();

    if (id.isEmpty()) {
        lCampoIdVazio.setVisible(true);
        return;
    }
    
    File diretorio = new File("Estoque de produtos");
            if (!diretorio.exists()) {
                diretorio.mkdirs(); // Cria o diretório e quaisquer diretórios pais que não existam
            } 

    File arquivo = new File(diretorio, id + ".txt");
    if (!arquivo.exists()) {
        campoNomeProduto.setText("");
        campoResponsavel.setSelectedItem("");
        campoEstoque.setText("");
        campoValidade.setText("");
        campoValorDeCusto.setText("");
        campoValorDeVenda.setText("");
        
        lIdNaoExistente.setVisible(true);
        return;
    }

    try {
        BufferedReader reader = new BufferedReader(new FileReader(arquivo));
        String linha;
        while ((linha = reader.readLine()) != null) {
            if (linha.startsWith("Nome do Produto: ")) {
                campoNomeProduto.setText(linha.replace("Nome do Produto: ", "").trim());
            } else if (linha.startsWith("Responsável: ")) {
                campoResponsavel.setSelectedItem(linha.replace("Responsável: ", "").trim());
            } else if (linha.startsWith("Estoque: ")) {
                campoEstoque.setText(linha.replace("Estoque: ", "").trim());
            } else if (linha.startsWith("Validade: ")) {
                campoValidade.setText(linha.replace("Validade: ", "").trim());
            } else if (linha.startsWith("Valor de Custo: ")) {
                campoValorDeCusto.setText(linha.replace("Valor de Custo: ", "").trim());
            } else if (linha.startsWith("Valor de Venda: ")) {
                campoValorDeVenda.setText(linha.replace("Valor de Venda: ", "").trim());
            }
        }
        reader.close();
        lCampoIdVazio.setVisible(false);
        lfaltaDeDados.setVisible(false);
        lCadastroFeito.setVisible(false);
        lIdNaoExistente.setVisible(false);
    } catch (Exception ex) {
        ex.printStackTrace();
    }
});
    }
    }

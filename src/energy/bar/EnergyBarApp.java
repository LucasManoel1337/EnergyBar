package energy.bar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class EnergyBarApp {
    
    private TelaCadastrarProduto telaCadastrarProduto;
    private TelaProdutos telaProdutos; // Adicione o campo para a tela de produtos

    public TelaCadastrarProduto getTelaCadastrarProduto() {
        return telaCadastrarProduto;
    }
    
    public TelaProdutos getTelaProdutos() {
        return telaProdutos;
    }

    private String versaoPrograma = "0.2.0";
    private JLabel labelVersao;

    private JFrame janela;
    private JPanel painelPrincipal;
    private JPanel painelConteudo;
    private PainelFaixa painelFaixa;

    private JLabel labelDataHora;

    private BotaoPersonalizado bInicio, bProdutos, bVendas, bEntradas, bSaidas, bAnotacoes, bCadastrarProduto; // Adicionado botão de cadastro
    private Font fontePadrao = new Font("Arial", Font.BOLD, 20);
    private Color corPadrao = Color.WHITE;
    private Color corSelecionada = new Color(180, 155, 183);

    private TelaInicio telaInicio;
    private TelaVendas telaVendas;
    private TelaEntradas telaEntradas;
    private TelaSaidas telaSaidas;
    private TelaAnotacoes telaAnotacoes;

    public EnergyBarApp() throws ParseException {
        telaCadastrarProduto = new TelaCadastrarProduto(this); // Passe "this" para TelaCadastrarProduto
        telaProdutos = new TelaProdutos(this); // Inicialize a tela de produtos
        
        configurarJanela();
        configurarPainelPrincipal();
        configurarPainelFaixa();
        configurarTelas(); // Inicializa as telas
        configurarBotoes();
        configurarPainelConteudo();
        adicionarComponentes();
    }
    
    public void exibirTela(JPanel tela) {
        painelConteudo.removeAll();
        painelConteudo.add(tela, BorderLayout.CENTER);
        painelConteudo.revalidate();
        painelConteudo.repaint();
    }

    private void configurarJanela() {
        janela = new JFrame("Energy Bar");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setBounds(0, 0, 1000, 600);
        janela.setLocationRelativeTo(null);
        janela.setResizable(false);
    }

    private void configurarPainelPrincipal() {
        painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BorderLayout());
    }

    private void configurarPainelFaixa() {
        painelFaixa = new PainelFaixa();
        painelFaixa.setLayout(new GridLayout(8, 1, 5, 10));

        javax.swing.Timer timer = new javax.swing.Timer(1000, e -> atualizarDataHora());
        timer.start();
    }

    private String obterDataHoraAtual() {
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return agora.format(formatter);
    }

    private void atualizarDataHora() {
        labelDataHora.setText(obterDataHoraAtual());
    }

    private void configurarTelas() throws ParseException {
        telaInicio = new TelaInicio();
        telaProdutos = new TelaProdutos(this); // Passa "this" para TelaProdutos
        telaVendas = new TelaVendas();
        telaEntradas = new TelaEntradas();
        telaSaidas = new TelaSaidas();
        telaAnotacoes = new TelaAnotacoes();
    }


    private void configurarBotoes() throws ParseException {
        bInicio = criarBotao("INICIO", telaInicio);
        bProdutos = criarBotao("PRODUTOS", telaProdutos);
        bVendas = criarBotao("VENDAS", telaVendas);
        bEntradas = criarBotao("ENTRADAS", telaEntradas);
        bSaidas = criarBotao("SAIDAS", telaSaidas);
        bAnotacoes = criarBotao("ANOTAÇÕES", telaAnotacoes);

        painelFaixa.add(bInicio);
        painelFaixa.add(bProdutos);
        painelFaixa.add(bVendas);
        painelFaixa.add(bEntradas);
        painelFaixa.add(bSaidas);
        painelFaixa.add(bAnotacoes);

        labelDataHora = new JLabel(obterDataHoraAtual(), SwingConstants.CENTER);
        labelDataHora.setFont(new Font("Arial", Font.PLAIN, 14));
        labelDataHora.setForeground(Color.WHITE);
        painelFaixa.add(labelDataHora);

        labelVersao = new JLabel("Versão: " + versaoPrograma, SwingConstants.CENTER);
        labelVersao.setFont(new Font("Arial", Font.PLAIN, 14));
        labelVersao.setForeground(Color.WHITE);
        painelFaixa.add(labelVersao);
    }

    private BotaoPersonalizado criarBotao(String texto, JPanel tela) {
        BotaoPersonalizado botao = new BotaoPersonalizado(texto, corPadrao, corSelecionada, fontePadrao);
        botao.addActionListener(e -> {
            resetarBotoes();
            botao.selecionar();
            atualizarTela(tela);
        });
        return botao;
    }

    private void resetarBotoes() {
        bInicio.desmarcar();
        bProdutos.desmarcar();
        bVendas.desmarcar();
        bEntradas.desmarcar();
        bSaidas.desmarcar();
        bAnotacoes.desmarcar();
    }

    private void atualizarTela(JPanel novaTela) {
        painelConteudo.removeAll();
        painelConteudo.add(novaTela, BorderLayout.CENTER);
        painelConteudo.revalidate();
        painelConteudo.repaint();
    }

    private void configurarPainelConteudo() {
        painelConteudo = new JPanel();
        painelConteudo.setLayout(new BorderLayout());
        atualizarTela(telaInicio); // Tela inicial padrão
    }

    private void adicionarComponentes() {
        painelPrincipal.add(painelFaixa, BorderLayout.WEST);
        painelPrincipal.add(painelConteudo, BorderLayout.CENTER);
        janela.add(painelPrincipal);
        janela.setVisible(true);
    }

    public static void main(String[] args) throws ParseException {
        new EnergyBarApp();
    }
}
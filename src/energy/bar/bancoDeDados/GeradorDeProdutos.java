package energy.bar.bancoDeDados;

import energy.bar.bancoDeDados.Diretorios;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class GeradorDeProdutos {

    private static Random random = new Random();

    // Método para gerar produtos para testes com valores aleatórios
    public static void gerarProdutosDeTeste() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

        List<String[]> produtos = Arrays.asList(
                new String[]{"001", "Agua S/gás", gerarResponsavel(), String.valueOf(gerarEstoque()), String.valueOf(gerarDataVencimento()), gerarValorCusto() + "", gerarValorVenda(gerarValorCusto()) + "", LocalDateTime.now().format(formatter)},
                new String[]{"002", "Agua C/gás", gerarResponsavel(), String.valueOf(gerarEstoque()), String.valueOf(gerarDataVencimento()), gerarValorCusto() + "", gerarValorVenda(gerarValorCusto()) + "", LocalDateTime.now().format(formatter)},
                new String[]{"003", "Getorate Morango", gerarResponsavel(), String.valueOf(gerarEstoque()), String.valueOf(gerarDataVencimento()), gerarValorCusto() + "", gerarValorVenda(gerarValorCusto()) + "", LocalDateTime.now().format(formatter)},
                new String[]{"004", "Getorate Limão", gerarResponsavel(), String.valueOf(gerarEstoque()), String.valueOf(gerarDataVencimento()), gerarValorCusto() + "", gerarValorVenda(gerarValorCusto()) + "", LocalDateTime.now().format(formatter)},
                new String[]{"005", "Getorate Maracuja", gerarResponsavel(), String.valueOf(gerarEstoque()), String.valueOf(gerarDataVencimento()), gerarValorCusto() + "", gerarValorVenda(gerarValorCusto()) + "", LocalDateTime.now().format(formatter)},
                new String[]{"006", "Shake Proteico Chocolate", gerarResponsavel(), String.valueOf(gerarEstoque()), String.valueOf(gerarDataVencimento()), gerarValorCusto() + "", gerarValorVenda(gerarValorCusto()) + "", LocalDateTime.now().format(formatter)},
                new String[]{"007", "Shake Proteico Baunilha", gerarResponsavel(), String.valueOf(gerarEstoque()), String.valueOf(gerarDataVencimento()), gerarValorCusto() + "", gerarValorVenda(gerarValorCusto()) + "", LocalDateTime.now().format(formatter)},
                new String[]{"008", "Barra de Proteína Morango", gerarResponsavel(), String.valueOf(gerarEstoque()), String.valueOf(gerarDataVencimento()), gerarValorCusto() + "", gerarValorVenda(gerarValorCusto()) + "", LocalDateTime.now().format(formatter)},
                new String[]{"009", "Barra de Proteína Chocolate", gerarResponsavel(), String.valueOf(gerarEstoque()), String.valueOf(gerarDataVencimento()), gerarValorCusto() + "", gerarValorVenda(gerarValorCusto()) + "", LocalDateTime.now().format(formatter)},
                new String[]{"010", "Energético Red Bull", gerarResponsavel(), String.valueOf(gerarEstoque()), String.valueOf(gerarDataVencimento()), gerarValorCusto() + "", gerarValorVenda(gerarValorCusto()) + "", LocalDateTime.now().format(formatter)},
                new String[]{"011", "Energético Monster", gerarResponsavel(), String.valueOf(gerarEstoque()), String.valueOf(gerarDataVencimento()), gerarValorCusto() + "", gerarValorVenda(gerarValorCusto()) + "", LocalDateTime.now().format(formatter)},
                new String[]{"012", "Suplemento Pré-Treino", gerarResponsavel(), String.valueOf(gerarEstoque()), String.valueOf(gerarDataVencimento()), gerarValorCusto() + "", gerarValorVenda(gerarValorCusto()) + "", LocalDateTime.now().format(formatter)},
                new String[]{"013", "Suplemento Pós-Treino", gerarResponsavel(), String.valueOf(gerarEstoque()), String.valueOf(gerarDataVencimento()), gerarValorCusto() + "", gerarValorVenda(gerarValorCusto()) + "", LocalDateTime.now().format(formatter)},
                new String[]{"014", "Goma de Colágeno", gerarResponsavel(), String.valueOf(gerarEstoque()), String.valueOf(gerarDataVencimento()), gerarValorCusto() + "", gerarValorVenda(gerarValorCusto()) + "", LocalDateTime.now().format(formatter)},
                new String[]{"015", "Isotônico Powerade", gerarResponsavel(), String.valueOf(gerarEstoque()), String.valueOf(gerarDataVencimento()), gerarValorCusto() + "", gerarValorVenda(gerarValorCusto()) + "", LocalDateTime.now().format(formatter)},
                new String[]{"016", "Água de Coco", gerarResponsavel(), String.valueOf(gerarEstoque()), String.valueOf(gerarDataVencimento()), gerarValorCusto() + "", gerarValorVenda(gerarValorCusto()) + "", LocalDateTime.now().format(formatter)},
                new String[]{"017", "Café Expresso", gerarResponsavel(), String.valueOf(gerarEstoque()), String.valueOf(gerarDataVencimento()), gerarValorCusto() + "", gerarValorVenda(gerarValorCusto()) + "", LocalDateTime.now().format(formatter)},
                new String[]{"018", "Café com Leite", gerarResponsavel(), String.valueOf(gerarEstoque()), String.valueOf(gerarDataVencimento()), gerarValorCusto() + "", gerarValorVenda(gerarValorCusto()) + "", LocalDateTime.now().format(formatter)},
                new String[]{"019", "Biscoito Integral", gerarResponsavel(), String.valueOf(gerarEstoque()), String.valueOf(gerarDataVencimento()), gerarValorCusto() + "", gerarValorVenda(gerarValorCusto()) + "", LocalDateTime.now().format(formatter)},
                new String[]{"020", "Frutas Frescas (Maçã)", gerarResponsavel(), String.valueOf(gerarEstoque()), String.valueOf(gerarDataVencimento()), gerarValorCusto() + "", gerarValorVenda(gerarValorCusto()) + "", LocalDateTime.now().format(formatter)}
        );

        // Criar os produtos e escrever no arquivo
        for (String[] produto : produtos) {
            cadastrarProduto(produto);
        }
    }

    private static int gerarEstoque() {
        return random.nextInt(12) + 1;  // Gera um número aleatório de 1 a 12
    }

    private static String gerarResponsavel() {
        // Lista de nomes de responsáveis
        List<String> responsaveis = Arrays.asList("Lucas", "Pamela", "Fellipe");

        // Escolher um nome aleatório da lista
        int index = random.nextInt(responsaveis.size());
        return responsaveis.get(index);
    }

    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    private static double gerarValorCusto() {
        double valorCusto = 2.50 + (random.nextDouble() * (6.00 - 2.50));
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat df = new DecimalFormat("###0.00", symbols);
        String valorFormatado = df.format(valorCusto);
        return Double.parseDouble(valorFormatado);
    }

    private static double gerarValorVenda(double valorCusto) {
        double valorVenda;
        do {
            valorVenda = 3.50 + (random.nextDouble() * (8.00 - 3.50));
            // Garantir que o valor de venda seja sempre maior que o de custo
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
            DecimalFormat df = new DecimalFormat("###0.00", symbols);
            String valorFormatado = df.format(valorVenda);
            valorVenda = Double.parseDouble(valorFormatado);
        } while (valorVenda <= valorCusto);  // Garante que o valor de venda seja maior que o de custo
        return valorVenda;
    }

    private static Random random1 = new Random();
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String gerarDataVencimento() {
        // Aumentando a probabilidade para datas acima de 30 dias (probabilidade de 70% para futuro distante)
        int tipoVencimento = random1.nextInt(10); // 0 a 9
        LocalDate hoje = LocalDate.now();
        LocalDate dataVencimento;

        if (tipoVencimento < 3) { // 30% chance de ser vencido ou perto de vencer
            if (tipoVencimento == 0) { // 10% chance de ser vencido (passado)
                dataVencimento = hoje.minusDays(random1.nextInt(365)); // Vencido até 1 ano atrás
            } else { // 20% chance de estar perto de vencer (próximos 30 dias)
                dataVencimento = hoje.plusDays(random1.nextInt(30) + 1); // Entre 1 e 30 dias à frente
            }
        } else { // 70% chance de ser um vencimento distante (mais de 30 dias)
            dataVencimento = hoje.plusDays(random1.nextInt(365) + 31); // Entre 31 dias e 1 ano à frente
        }

        // Formatar a data no formato "dd/MM/yyyy"
        return dataVencimento.format(formatter);
    }

    // Método para cadastrar um produto individualmente
    private static void cadastrarProduto(String[] produto) {
        String id = produto[0];
        String nomeDoProduto = produto[1];
        String responsavel = produto[2];
        String estoque = produto[3];
        String validade = produto[4];
        String valorDeCusto = produto[5];
        String valorDeVenda = produto[6];
        String lote = produto[7];

        // Verifica se o diretório do estoque existe (se não, cria)
        Diretorios dir = new Diretorios();
        File diretorio = new File(dir.getDirEstoque());
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        // Criar arquivo de produto
        try {
            File arquivo = new File(dir.getDirEstoque(), id + ".txt");

            BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true)); // 'true' para modo append
            writer.write("Nome do Produto: " + nomeDoProduto + "\n");
            writer.write("Responsável: " + responsavel + "\n");
            writer.write("Estoque: " + estoque + "\n");
            writer.write("Validade: " + validade + "\n");
            writer.write("Valor de Custo: " + valorDeCusto + "\n");
            writer.write("Valor de Venda: " + valorDeVenda + "\n");
            writer.write("Lote: " + lote + "\n");
            writer.write("Data e Hora de Cadastro: " + new java.util.Date().toString() + "\n");
            writer.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

package energy.bar.bancoDeDados;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Diretorios {

    private String dirFonteEnergyBar = "Arquivos de suporte/Fontes/DelaGothicOne-Regular.ttf";

    private String dirEstoque = "Dados/Estoque de produtos";
    private String dirLucroLiquido30h = "Dados/Lucros Liquidos/Lucro Liquido 30h";
    private String dirLucroLiquido7h = "Dados/Lucros Liquidos/Lucro Liquido 7h";
    private String dirLucroLiquidoDh = "Dados/Lucros Liquidos/Lucro Liquido Dh";

    private String dirHistoricoDeCompras = "Dados/Compras";

    public String getDirEstoque() {
        return dirEstoque;
    }

    public String getDirLucroLiquido30h() {
        return dirLucroLiquido30h;
    }

    public String getDirLucroLiquido7h() {
        return dirLucroLiquido7h;
    }

    public String getDirLucroLiquidoDh() {
        return dirLucroLiquidoDh;
    }

    public String getDirFonteEnergyBar() {
        return dirFonteEnergyBar;
    }

    public String getDirHistoricoDeCompras() {
        return dirHistoricoDeCompras;
    }

    // Método para verificar e criar diretórios
    public void verificarOuCriarDiretorios() {
        verificarOuCriarDiretorio(dirEstoque);
        verificarOuCriarDiretorio(dirLucroLiquido30h);
        verificarOuCriarDiretorio(dirLucroLiquido7h);
        verificarOuCriarDiretorio(dirLucroLiquidoDh);
        verificarOuCriarDiretorio(dirHistoricoDeCompras);
    }

    // Método auxiliar para verificar se o diretório existe e criar, se necessário
    private void verificarOuCriarDiretorio(String caminho) {
        File dir = new File(caminho);

        // Obtém a data e hora atuais no formato desejado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss - dd-MM-yyyy");
        String dataHoraAtual = LocalDateTime.now().format(formatter);

        if (dir.exists()) {
            System.out.println("[" + dataHoraAtual + "] - [Diretorios.java] - O diretorio '" + caminho + "' ja existe.");
        } else {
            if (dir.mkdirs()) {
                System.out.println("[" + dataHoraAtual + "] - [Diretorios.java] - O diretorio '" + caminho + "' foi criado com sucesso.");
            } else {
                System.out.println("[" + dataHoraAtual + "] - [Diretorios.java] - Falha ao criar o diretorio '" + caminho + "'.");
            }
        }
    }
}

import java.util.HashMap;

/**
 * Classe Ambiente - um ambiente em um jogo adventure.
 *
 * Esta classe é parte da aplicação "World of Zuul".
 * "World of Zuul" é um jogo de aventura muito simples, baseado em texto.
 *
 * Um "Ambiente" representa uma localização no cenário do jogo. Ele é conectado
 * aos
 * outros ambientes através de saídas. As saídas são nomeadas como norte, sul,
 * leste
 * e oeste. Para cada direção, o ambiente guarda uma referência para o ambiente
 * vizinho,
 * ou null se não há saída naquela direção.
 * 
 * @author Michael Kölling and David J. Barnes (traduzido e adaptado por Julio
 *         César Alves)
 */
public class Ambiente {
    // descrição do ambiente
    private String descricao;
    private HashMap<String, Ambiente> saidas;
    private HashMap<String, Ambiente> saidasBloqueadas;
    private Item item;
    private Item collectedItem;
    
    /**
     * Cria um ambiente com a "descricao" passada. Inicialmente, ele não tem saidas.
     * "descricao" eh algo como "uma cozinha" ou "um jardim aberto".
     * 
     * @param descricao A descrição do ambiente.
     */
    public Ambiente(String descricao) {
        this.descricao = descricao;
        saidas = new HashMap<String, Ambiente>();
        saidasBloqueadas = new HashMap<String, Ambiente>();
    }

    public Ambiente(String descricao, Item item) {
        this(descricao);
        this.item = item;
    }

    /**
     * Define as saídas do ambiente. Cada direção ou leva a um outro ambiente ou é
     * null
     * (indicando que não tem nenhuma saída para lá).
     * 
     * @param direcao  Direção da saída.
     * @param ambiente Para onde a saída leva.
     */
    public void ajustarSaidas(String direcao, Ambiente ambiente) {
        saidas.put(direcao, ambiente);
    }

    /**
     * Retornar a saída disponível
     * 
     * @param direcao Direção da saída.
     */

    public Ambiente getSaida(String direcao) {
        return saidas.get(direcao);
    }

    /** Retornar todas as saídas disponíveis */

    public String getSaidas() {
        String textoSaidas = "";
        for (String direcao : saidas.keySet()) {
            textoSaidas = textoSaidas + direcao + " ";
        }

        return textoSaidas;
    }

    /**Bloqueia saídas disponíveis */

    public void blockSaidas(String key, Ambiente ambiente) {
        saidas.get(key);
        saidasBloqueadas.put(key, ambiente);
    }

    public boolean getBlockSaidas(String saida){
        if(saidasBloqueadas.get(saida) != null) {
            return true;
        }

        return false;
    }

    /**
     * @return A descrição do ambiente.
     */
    public String getDescricao() {
        return descricao;
    }

    public String getDescricaoLonga() {
        String desc = "Você está no " + descricao + "\nVocê pode ir para as seguintes direções: " + getSaidas() + "\n";
        if (item != null) {
            desc += "Há um(a) " + item.getFullItemData();
        } else {
            desc += "Não há nenhum item para coletar aqui.";
        }

        return desc;
    }

    /**
     * @return O ambiente dada a direção.
     */
    public Ambiente getAmbiente(String direcao) {
        return saidas.get(direcao);
    }

    public Boolean hasItens() {
        if (item != null) {
            return true;
        } else {
            return false;
        }
    }

    public String getItemName() {
        if (hasItens()) {
            return item.getNome();
        } else {
            return null;
        }
    }

    public Item collectItem() {
        if (hasItens()) {
            collectedItem = new Item(item.getNome(), item.getDescricao(), item.getPeso());
            item.setItemAsCollected();
            System.out.println(
                    "Você coletou um item importante para montar o humanóide que irá retirar vocês a salvo da Universidade. \n Vá até a Torre de Astronomia para montar o robô.");
            return collectedItem;
        } else {
            return null;
        }
    }
}

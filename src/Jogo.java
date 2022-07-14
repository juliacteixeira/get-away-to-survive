import java.util.Scanner;

/**
 * Essa é a classe principal da aplicacao "Get away to Survive".
 * "Get away to Survive" é um jogo de aventura muito simples, baseado em texto.
 * 
 * Usuários podem caminhar em um cenário, recolher itens e encontrar a saída!
 * Ele realmente precisa ser estendido para fazer algo interessante!
 * 
 * Para jogar esse jogo, crie uma instancia dessa classe e chame o método
 * "jogar".
 * 
 * Essa classe principal cria e inicializa todas as outras: ela cria os
 * ambientes,
 * cria o analisador e começa o jogo. Ela também avalia e executa os comandos
 * que
 * o analisador retorna.
 * 
 * Baseado no código de:
 * 
 * @author Michael Kölling and David J. Barnes (traduzido e adaptado por Julio
 *         César Alves)
 */

public class Jogo {
    // analisador de comandos do jogo
    private Analisador analisador;
    // ambiente onde se encontra o jogador
    private Ambiente ambienteAtual;

    private Estudante estudante;
    private String nome;
    private static String ultmDirecao;

    Scanner inputName = new Scanner(System.in); // Create a Scanner object

    /**
     * Cria o jogo e incializa seu mapa interno.
     */
    public Jogo() {
        criarAmbientes();
        analisador = new Analisador();
        estudante = new Estudante(nome);
    }

    /**
     * Cria todos os ambientes e liga as saidas deles
     */
    private void criarAmbientes() {
        Ambiente biblioteca, nave_1, nave_2, cantina, departamento, torre_astronomia, praca_central;

        // cria os ambientes
        biblioteca = new Ambiente("no terceiro andar da biblioteca onde fica o laboratório de robótica aplicada.");
        nave_1 = new Ambiente("no pavilhão de aulas com entrada para a câmera que guarda partes do humanoide.",
                new Item("humanoide", "Parte superior do humanódie", 0));
        nave_2 = new Ambiente("no pavilhão de aulas com entrada para a câmera que guarda partes do humanoide.",
                new Item("humanoide", "Parte inferior do humanóide", 0));
        cantina = new Ambiente("na cantina da universidade");
        departamento = new Ambiente("no departamento de computacao");
        torre_astronomia = new Ambiente(
                "no local detentor de grande energia quantica capaz de juntar as partes de um humanoide");
        praca_central = new Ambiente("ponto central de locomoção");

        // inicializa as saidas dos ambientes
        biblioteca.ajustarSaidas("sul", cantina);
        biblioteca.ajustarSaidas("andar", praca_central);
        nave_1.ajustarSaidas("sul", cantina);
        nave_1.ajustarSaidas("andar", praca_central);
        cantina.ajustarSaidas("norte", nave_1);
        praca_central.ajustarSaidas("oeste", biblioteca);
        praca_central.ajustarSaidas("norte", nave_1);
        praca_central.ajustarSaidas("leste", nave_2);
        praca_central.ajustarSaidas("sul", cantina);
        nave_2.ajustarSaidas("oeste", biblioteca);
        nave_2.ajustarSaidas("andar", praca_central);
        nave_2.ajustarSaidas("nordeste", departamento);
        departamento.ajustarSaidas("leste", nave_2);
        departamento.ajustarSaidas("getOut", torre_astronomia);

        // bloqueia saidas
        biblioteca.blockSaidas("sul", cantina);
        nave_1.blockSaidas("sul", cantina);
        praca_central.blockSaidas("sul", cantina);
        ambienteAtual = biblioteca; // o jogo comeca em frente à biblioteca
        ultmDirecao = "oeste"; // mantem uma cópia da última localização
    }

    /**
     * Rotina principal do jogo. Fica em loop ate terminar o jogo.
     */
    public void jogar() {
        imprimirBoasVindas();

        // Entra no loop de comando principal. Aqui nós repetidamente lemos comandos e
        // os executamos até o jogo terminar.

        boolean terminado = false;
        while (!terminado) {
            Comando comando = analisador.pegarComando();
            terminado = processarComando(comando);
        }
        System.out.println("Obrigado por jogar. Até mais!");
    }

    /**
     * Imprime o mapa do jogo
     */
    private void imprimirMapa() {

        System.out.println("                        *********");
        System.out.println("                        **  x  **");
        System.out.println("                        *********");
        System.out.println("                    ******     ******");
        System.out.println("                    *   biblioteca  *");
        if (ultmDirecao.equals("oeste")) {
            System.out.println("                    *  vc esta aqui *");
        }
        System.out.println("                    *****************");
        System.out.println("                            |        ");
        System.out.println("                            |        ");
        System.out.println("                            |        ");
        System.out.println("                            |        ");
        switch (ultmDirecao) {
            case "andar":
                System.out.println("                    | vc esta aqui |");
                System.out.println("      | Cantina |-----Praça Central-----| Nave 1 |");
                break;
            case "sul":
                System.out.println("                            |        ");
                System.out.println("         | Cantina |-----Praça Central-----| Nave 1 |");
                System.out.println("         | vc esta aqui|");
                break;
            case "norte":
                System.out.println("                            |        ");
                System.out.println("         | Cantina |-----Praça Central-----| Nave 1 |");
                System.out.println("                                       | vc esta aqui |");
                break;
            default:
                System.out.println("                            |        ");
                System.out.println("         | Cantina |-----Praça Central-----| Nave 1 |");
                break;
        }
        System.out.println("                            |        ");
        System.out.println("                            |        ");
        System.out.println("                            |        ");
        System.out.println("                            |        ");
        System.out.println("                            |        ");
        System.out.println("                        | Nave 2 |");
        if (ultmDirecao.equals("leste")) {
            System.out.println("                 | vc esta aqui |");
        }

        System.out.println("                                   -------------------| Departamento |");
        if (ultmDirecao.equals("nordeste")) {
            System.out.println("                                                  | vc esta aqui |");
        }
        System.out.println("                                                              |       ");
        System.out.println("                                                              |        ");
        System.out.println("                                                              |        ");
        System.out.println("                                                              |        ");
        System.out.println("                                                              |        ");
        if (ultmDirecao.equals("getOut")) {
            System.out.println("                                     | vc chegou a torre de astronomia!|");
            System.out.println(
                    "                         | para montar o humanoide, digite o comando montar e em seguida o comando sair|");

        }
        System.out.println("                                                         *********");
        System.out.println("                                                         **  x  **");
        System.out.println("                                                         *********");
        System.out.println("                                                         *********");
        System.out.println("                                                         *********");
        System.out.println("                                                         *********");
        System.out.println("                                                             |        ");
        System.out.println("                                                             |        ");
        System.out.println("                                                        |  SAÍDA  |   ");

    }

    /**
     * Imprime a mensagem de abertura para o jogador.
     */
    private void imprimirBoasVindas() {
        System.out.println();
        System.out.println("Bem-vindo ao Get away to survive! \nQual o seu nome?");
        nome = inputName.nextLine();
        System.out.println("---------------------------------------------------------");
        System.out.println();
        System.out.println();
        System.out.println(nome + ", " +
                "estamos no ano de 2056 e o meio de transporte são os robôs humanóides de voô!");
        System.out.println(
                "Nas proximidades da Upper East Side, no monte Kilimanjaro existe um vulcão em atividade chamado de Krakatoa\nnos últimos dias o vulcão tem preocupado as autoridades expelindo uma fumaça negra e emitindo um som como se uma criatura estivesse acordando de um sono bem longo.");
        System.out.println(
                "A preocupação ficou mais séria quando nesta manhã as lavas finalmente começaram a escorrer, derretendo tudo o que tocava.");
        System.out.println(
                "Não restava dúvida de que a Universidade seria seriamente atingida e um grupo de alunos que estavam em um laboratório perderam o resgate e agora precisavam encontrar uma maneira de fugir de um desastre e preservar suas vidas.");
        System.out.println(
                "*******************************************************************************************************");
        System.out.println(
                "Distribuídos pelas naves de aula da universidade, existem partes de um humanoide de vôo que se montadas no alto da torre de astronomia, poderiam servir como uma maneira de fuga do jovem grupo de amigos.");
        System.out.println(
                "Sua missão é coletar as partes desse humanóide e levá-las até o topo da torre de astronomia.");
        System.out.println("Digite 'ajuda' se voce precisar de ajuda.");
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println();

        imprimirLocalizacaoAtual();
    }

    /**
     * Dado um comando, processa-o (ou seja, executa-o)
     * 
     * @param comando O Comando a ser processado.
     * @return true se o comando finaliza o jogo.
     */
    private boolean processarComando(Comando comando) {
        boolean querSair = false;

        if (comando.ehDesconhecido()) {
            System.out.println("Eu nao entendi o que voce disse...");
            return false;
        }

        String palavraDeComando = comando.getPalavraDeComando();
        if (palavraDeComando.equals("ajuda")) {
            imprimirAjuda();
        } else if (palavraDeComando.equals("ir")) {
            irParaAmbiente(comando);
        } else if (palavraDeComando.equals("sair")) {
            querSair = sair(comando);
        } else if (palavraDeComando.equals("observar")) {
            observar(comando);
        } else if (palavraDeComando.equals("pegar")) {
            collectItem(comando);
        } else if (palavraDeComando.equals("mostrar_mapa")) {
            imprimirMapa();
        }

        return querSair;
    }

    private void collectItem(Comando comando) {

        if (checkSegundaPalavras(comando)) {
            System.out.println("Informe qual item você gostaria de pegar?");
            return;
        }

        if (comando.getSegundaPalavra().equals(ambienteAtual.getItemName())) {
            estudante.setRobBall(ambienteAtual.collectItem());
        } else {
            System.out.println("Você digitou um nome incorreto para o item ou esse item não existe no mapa.");
        }
    }

    private void observar(Comando comando) {
        if (ambienteAtual.hasItens() || estudante.getAllRobBall().size() == 2) {
            int qtdItem = 0;
            for (Item item : estudante.getAllRobBall()) {
                System.out.println(item.getFullItemData());
                qtdItem++;
            }

            if (qtdItem > 0) {
                System.out.println("Sua RobBall tem os seguintes items: ");
            }

            if (estudante.getAllRobBall().size() <= 2) {
                System.out.println("Você precisa recolher as 2 partes do humanóide para conseguir montar o humanóide.");
                System.out.println("No momento você tem: " + qtdItem + " item(ns)");
            } else {
                System.out.println("Você já pode ir em direção a torre de astromia para montar o humanóide.");
            }
        } else {
            System.out.println("Sua RobBall não está completa. Volte e recolha todos itens no mapa.");
        }

        System.out.println("Você tem os seguintes comandos disponíveis: " + analisador.getComandos());
        System.out.println("Os caminhos disponíveis são: " + ambienteAtual.getSaidas());
    }

    /**
     * Exibe informações de ajuda.
     * Aqui nós imprimimos algo bobo e enigmático e a lista de palavras de comando
     */
    private void imprimirAjuda() {
        // if(ambienteAtual.d) fazer o metodo que pega o local atual
        System.out.println("Vocês estão " + ambienteAtual.getDescricao() + " que fica na parte " + ultmDirecao
                + " da universidade.");
        System.out.println("Dividem em dois grupos e cada grupo irá ao resgate de uma parte do humanóide.");
        System.out.println(
                "Alguns caminhos já estão obstruído pelas lavas, tenham cuidado e tentem outra saída disponível.");
        System.out.println();
        System.out.println("Suas palavras de comando são: " + analisador.getComandos());

        if (ultmDirecao.equals("norte")) {
            System.out.println("Aqui se encontra uma parte do: " + ambienteAtual.getItemName());
        }

        if (ultmDirecao.equals("leste")) {
            System.out.println("Aqui se encontra uma parte do: " + ambienteAtual.getItemName());
        }
    }

    /**
     * Tenta ir em uma direcao. Se existe uma saída para lá entra no novo ambiente,
     * caso contrário imprime mensagem de erro.
     */
    private void irParaAmbiente(Comando comando) {
        // se não há segunda palavra, não sabemos pra onde ir...
        if (checkSegundaPalavras(comando)) {
            System.out.println("Ir pra onde?");
            return;
        }

        String direcao = comando.getSegundaPalavra();

        // Tenta sair do ambiente atual
        if (ambienteAtual.getBlockSaidas(direcao)) {
            System.out.println("Esta saída está bloqueada! Tenta outra saída disponível!");
        } else {
            Ambiente proximoAmbiente = ambienteAtual.getSaida(direcao);
            if (proximoAmbiente == null) {
                System.out.println("Nao ha passagem!");
            } else {
                ambienteAtual = proximoAmbiente;
                ultmDirecao = direcao;

                imprimirLocalizacaoAtual();
            }
        }

    }

    private boolean checkSegundaPalavras(Comando comando) {
        return comando.temSegundaPalavra() ? false : true;
    }

    private void imprimirLocalizacaoAtual() {

        System.out.println("Você está " + ambienteAtual.getDescricao());
        System.out.println("Saídas: " + ambienteAtual.getSaidas());
        System.out.println("Comandos disponíveis: " + analisador.getComandos());

        System.out.println();
    }

    /**
     * "Sair" foi digitado. Verifica o resto do comando pra ver se nós queremos
     * realmente sair do jogo.
     * 
     * @return true, se este comando sai do jogo, false, caso contrário.
     */
    private boolean sair(Comando comando) {
        if (comando.temSegundaPalavra()) {
            System.out.println("Sair o que?");
            return false;
        } else {
            return true; // sinaliza que nós realmente queremos sair
        }
    }
}

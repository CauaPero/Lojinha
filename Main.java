import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    //variaveis declaradas !fora do main! como variáveis da classe
    static boolean baseInicializada = false;
    static int[] ids;
    static String[] nomes;
    static double[] precos;
    static int[] estoques;

    static int[] vendaIds = new int[1000];
    static int[] vendaQuantidades = new int[1000];
    static int vendaCount = 0;

    static int[] historicoIdsPedidos = new int[1000]; //declara um vetor, tem q definir um tamanho!!
    static double[] historicoValoresPedidos = new double[1000];
    static int[][] historicoItensVendidos = new int[1000][3];
    static int historicoItensCount = 0;
    static int pedidoAtualCount = 0;

    static Scanner entrada = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao; //cria variavel para usar no switch case
        do {

            System.out.println("\n=== Lojinha do Cauã ===");
            System.out.println("1. Inicializar base");
            System.out.println("2. Exibir catálogo");
            System.out.println("3. Adicionar item");
            System.out.println("4. Resumo da venda");
            System.out.println("5. Finalizar venda");
            System.out.println("6. Histórico de vendas");
            System.out.println("7. Buscar venda específica");
            System.out.println("8. Repor estoque (Admin)");
            System.out.println("9. Relatório de estoque baixo (Admin)");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = entrada.nextInt();

            switch (opcao) {
                case 1:
                    inicializarBase();
                    break;
                case 2:
                    exibirCatalogo();
                    break;
                case 3:
                    addItemVenda();
                    break;
                case 4:
                    resumoVendaAtual();
                    break;
                case 5:
                    finalizaVenda();
                    break;
                case 6:
                    verHistorico();
                    break;
                case 7:
                    buscarVendaEspecifica();
                    break;
                case 8:
                    reporEstoque();
                    break;
                case 9:
                    relatorioEstoqueBaixo();
                    break;
                case 0:
                    System.out.println("Fim!");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);

        entrada.close();
    }

    public static void inicializarBase() {
        ids = new int[]{101, 102, 103, 104, 105, 106};
        nomes = new String[]{"Mouse", "Teclado", "Headset", "Mouse Pad", "Monitor", "Chinelo"};
        precos = new double[]{150.0, 350.0, 420.5, 90.0, 1500.0, 31.5};
        estoques = new int[]{21, 12, 17, 42, 4, 0};

        baseInicializada = true; //obs: o programa só vai funcionar se baseInicializada = true, por isso esta aqui
        System.out.println("\nBase inicializada com sucesso!\nUse o programa à vontade!");
    }
    //métdo catálogo
    public static void exibirCatalogo() {
        if (!baseInicializada) {
            System.out.println("Erro: Execute primeiro a opção 1 - Inicializar base!");
            return;
        }

        System.out.println("\nCatálogo de Produtos");

        for (int i = 0; i < ids.length; i++) { //esse "for" percorre todos os produtos
            if (estoques[i] > 0) {
                System.out.printf("ID: %d | Nome: %s | Preço: R$ %.2f | Estoque: %d\n",
                        ids[i], nomes[i], precos[i], estoques[i]);
            }
        }
    }

    public static void addItemVenda() {
        if (!baseInicializada) {
            System.out.println("Erro: Execute primeiro a opção 1 - Inicializar base!");
            return;
        }

        System.out.print("Digite o ID do produto: ");
        int id = entrada.nextInt();

        //procura pelo ID na lista de produtos
        int i;
        for (i = 0; i < ids.length; i++) {
            if (ids[i] == id) {
                break;
            }
        }

        if (i == ids.length) {
            System.out.println("Produto não encontrado!");
            return;
        }

        System.out.print("Digite a quantidade: ");
        int qtd = entrada.nextInt();

        if (qtd <= 0) {
            System.out.println("Quantidade inválida!");
            return;
        }

        if (qtd > estoques[i]) {
            System.out.println("Estoque insuficiente! Disponível: " + estoques[i]);
            return;
        }

        //adiciona o item ao carrinho (venda atual)
        vendaIds[vendaCount] = ids[i];
        vendaQuantidades[vendaCount] = qtd;
        vendaCount++;

        System.out.printf("Produto %s adicionado à venda (Qtd: %d)\n", nomes[i], qtd);
    }

    //procura o índice de um produto pelo ID
    public static int procurarIndiceId(int id) {
        for (int i = 0; i < ids.length; i++) {
            if (ids[i] == id) return i;
        }
        return -1; //caso não ache o item, retorna o index como -1, pq ele não existe
    }

    //mostra um resumo da venda atual (carrinho)
    public static void resumoVendaAtual() {
        if (!baseInicializada) {
            System.out.println("Erro: Execute primeiro a opção 1 - Inicializar base!");
            return;
        }

        if (vendaCount == 0) {
            System.out.println("Nenhum item adicionado à venda ainda.");
            return;
        }

        System.out.println("=== Resumo da Venda Atual ===");
        double total = 0.0;

        for (int i = 0; i < vendaCount; i++) {
            int id = vendaIds[i];
            int qtd = vendaQuantidades[i];
            int idx = procurarIndiceId(id);

            if (idx >= 0) {
                double subtotal = precos[idx] * qtd;
                total += subtotal;
                System.out.printf("ID: %d | Produto: %s | Qtd: %d | Unit: R$ %.2f | Subtotal: R$ %.2f\n",
                        ids[idx], nomes[idx], qtd, precos[idx], subtotal);
            }
        }

        System.out.printf("TOTAL DA VENDA: R$ %.2f\n", total);
    }

    //finaliza a venda, gera nota e salva no histórico
    public static void finalizaVenda() {
        if (!baseInicializada) {
            System.out.println("Erro: Execute primeiro a opção 1 - Inicializar base!");
            return;
        }

        if (vendaCount == 0) {
            System.out.println("Adicione um produto para executar essa ação!");
            return;
        }

        pedidoAtualCount++; //add 1 e gera um novo ID de pedido
        int idPedido = pedidoAtualCount;
        double total = 0.0;

        //percorre todos os itens da venda atual
        for (int i = 0; i < vendaCount; i++) {
            int idProduto = vendaIds[i];
            int qtd = vendaQuantidades[i];
            int idx = procurarIndiceId(idProduto);
            double subtotal = precos[idx] * qtd;

            total += subtotal; //soma no total do pedido

            //salva itens no histórico
            historicoItensVendidos[historicoItensCount][0] = idPedido;
            historicoItensVendidos[historicoItensCount][1] = idProduto;
            historicoItensVendidos[historicoItensCount][2] = qtd;
            historicoItensCount++;

            estoques[idx] -= qtd; //tira os itens comprados do estoque
        }

        historicoIdsPedidos[pedidoAtualCount] = idPedido;
        historicoValoresPedidos[pedidoAtualCount] = total;

        imprimirNotaFiscal(idPedido); //notinha

        vendaCount = 0; //prepara próxima venda (limpa carrinho)
    }

    public static void imprimirNotaFiscal(int idPedido) {
        double total = historicoValoresPedidos[idPedido];

        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataEmissao = agora.format(formato);

        System.out.println("********************************************************************************************");
        System.out.println("*                                      MACKSHOP                                            *");
        System.out.println("*                                 CNPJ: 12.345.678/0001-99                                 *");
        System.out.println("********************************************************************************************");
        System.out.println("*                       NOTA FISCAL - VENDA AO CONSUMIDOR                                  *");
        System.out.println("* Pedido ID: " + idPedido);
        System.out.println("* Data de Emissão: " + dataEmissao);
        System.out.println("********************************************************************************************");
        System.out.println("* # | ID  | DESCRIÇÃO             | QTD | VL. UNIT.  | VL. TOTAL ");
        System.out.println("--------------------------------------------------------------------------------------------");

        int contador = 0;
        //percorre todos os itens vendidos e imprime apenas os do pedido atual
        for (int i = 0; i < historicoItensCount; i++) {
            if (historicoItensVendidos[i][0] == idPedido) {
                contador++;
                int idProduto = historicoItensVendidos[i][1];
                int qtd = historicoItensVendidos[i][2];
                int idx = procurarIndiceId(idProduto);
                String descricao = (idx >= 0) ? nomes[idx] : "Produto desconhecido";
                double vlUnit = (idx >= 0) ? precos[idx] : 0.0;
                double vlTotal = vlUnit * qtd;

                System.out.printf("* %2d | %3d | %-20s | %3d | R$ %7.2f | R$ %7.2f *\n",
                        contador, idProduto, descricao, qtd, vlUnit, vlTotal);
            }
        }

        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.printf("* SUBTOTAL DA VENDA | R$ %7.2f *\n", total);
        System.out.printf("* TOTAL DA VENDA    | R$ %7.2f *\n", total);
        System.out.println("*********************************************************************************************");
        System.out.println("* OBRIGADO PELA PREFERÊNCIA! VOLTE SEMPRE!                                                  *");
        System.out.println("*********************************************************************************************");
    }

    public static void verHistorico() {
        if (!baseInicializada) {
            System.out.println("Erro: Execute primeiro a opção 1 - Inicializar base!");
            return;
        }
        System.out.println("=== Histórico de Vendas ===");
        //percorre todos os pedidos já finalizados
        for (int i = 1; i <= pedidoAtualCount; i++) {
            System.out.printf("Pedido ID: %d - Total: R$ %.2f\n", historicoIdsPedidos[i], historicoValoresPedidos[i]);
        }
    }

    public static void buscarVendaEspecifica() {
        if (!baseInicializada) {
            System.out.println("Erro: Execute primeiro a opção 1 - Inicializar base!");
            return;
        }

        System.out.print("Digite o ID do pedido que deseja buscar: ");
        int idPedido = entrada.nextInt();

        //procura pedido no histórico
        boolean encontrado = false;
        for (int i = 1; i <= pedidoAtualCount; i++) {
            if (historicoIdsPedidos[i] == idPedido) {
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            imprimirNotaFiscal(idPedido);
        } else {
            System.out.println("Pedido não encontrado!");
        }
    }

    public static void reporEstoque() {
        if (!baseInicializada) {
            System.out.println("Erro: Execute primeiro a opção 1 - Inicializar base!");
            return;
        }

        System.out.print("Você é admin? (s/n): ");
        String resposta = entrada.next();
        boolean ehAdmin = resposta.equalsIgnoreCase("s");

        if (!ehAdmin) {
            System.out.println("Acesso negado! Somente administradores podem executar esta ação.");
            return;
        }
        else {
            System.out.println("Acesso concedido ao administrador!");
        }

        //digita o produto pra repor
        System.out.print("Digite o ID do produto a repor: ");
        int id = entrada.nextInt();

        int idx = procurarIndiceId(id);
        if (idx == -1) {
            System.out.println("Produto não encontrado!");
            return;
        }

        //digita a quantidade pra adicionar
        System.out.print("Digite a quantidade a adicionar: ");
        int qtd = entrada.nextInt();
        if (qtd <= 0) {
            System.out.println("Quantidade inválida!");
            return;
        }

        estoques[idx] += qtd; //atualiza o estoque
        System.out.printf("Estoque do produto %s atualizado. Novo estoque: %d\n", nomes[idx], estoques[idx]);
    }

    public static void relatorioEstoqueBaixo() {
        if (!baseInicializada) {
            System.out.println("Erro: Execute primeiro a opção 1 - Inicializar base!");
            return;
        }

        int limite = 10; //limite pré-definido
        System.out.println("=== Produtos com estoque baixo (menos de " + limite + ") ===");
        boolean algum = false;

        System.out.print("Você é admin? (s/n): ");
        String resposta = entrada.next();
        boolean ehAdmin = resposta.equalsIgnoreCase("s");

        if (!ehAdmin) {
            System.out.println("Acesso negado! Somente administradores podem executar esta ação.");
            return;
        }
        else {
            System.out.println("Acesso concedido ao administrador!");
        }

        //percorre todos os produtos e mostra apenas os com estoque < limite
        for (int i = 0; i < ids.length; i++) {
            if (estoques[i] < limite) {
                System.out.printf("ID: %d | Nome: %s | Estoque: %d\n", ids[i], nomes[i], estoques[i]);
                algum = true;
            }
        }

        if (!algum) {
            System.out.println("Nenhum produto com estoque baixo.");
        }
    }
}

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n===== MENU LOJA DE CONSERTO =====");
            System.out.println("1. Abrir nova Ordem de Serviço");
            System.out.println("2. Listar Ordens de Serviço");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine(); // consumir quebra de linha

            switch (opcao) {
                case 1:
                    abrirNovaOrdem(sc);
                    break;
                case 2:
                    OrdemServicoDAO.listOs();
                    break;
                case 3:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 3);

        sc.close();
    }

    private static void abrirNovaOrdem(Scanner sc) {
        try {
            // Dados da OS
            System.out.print("Digite o ID do Cliente: ");
            int idCliente = sc.nextInt();
            sc.nextLine();

            System.out.print("Digite o valor total estimado: ");
            double valorTotal = sc.nextDouble();
            sc.nextLine();

            System.out.print("Digite a previsão de entrega (AAAA-MM-DD): ");
            String prevEntrega = sc.nextLine();

            int idOs = OrdemServico.abrirOrdemServico(idCliente, valorTotal, prevEntrega);

            if (idOs  > 0) {
                // Agora inserir itens
                int opcaoItem;
                do {
                    System.out.print("Digite o ID do Produto/Peça: ");
                    int idProduto = sc.nextInt();

                    System.out.print("Digite a quantidade: ");
                    int qtde = sc.nextInt();
                    sc.nextLine();

                    ItemOrdemServico.inserirItemOs(idOs,idProduto,qtde);

                    System.out.print("Deseja adicionar mais peças? (1=Sim / 0=Não): ");
                    opcaoItem = sc.nextInt();
                    sc.nextLine();
                } while (opcaoItem == 1);

                System.out.println("Itens vinculados com sucesso!");
            } else {
                System.out.println("Erro ao criar ordem de serviço!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao abrir OS: " + e.getMessage());
        }
    }
}

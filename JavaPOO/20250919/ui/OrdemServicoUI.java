
import java.util.Scanner;



public class OrdemServicoUI {

    public static void abrirNovaOrdem(Scanner sc) {
        try {
            System.out.print("Digite o ID do Cliente: ");
            int idCliente = sc.nextInt();
            sc.nextLine();

            System.out.print("Digite o valor total estimado: ");
            double valorTotal = sc.nextDouble();
            sc.nextLine();

            System.out.print("Digite a previsão de entrega (AAAA-MM-DD): ");
            String prevEntrega = sc.nextLine();

            int idOs = OrdemServico.abrirOrdemServico(idCliente, valorTotal, prevEntrega);

            if (idOs > 0) {
                int opcaoItem;
                do {
                    System.out.print("Digite o ID do Produto/Peça: ");
                    int idProduto = sc.nextInt();

                    System.out.print("Digite a quantidade: ");
                    int qtde = sc.nextInt();
                    sc.nextLine();

                    ItemOrdemServico.inserirItemOs(idOs, idProduto, qtde);

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

    public static void consultarOSCliente(Scanner sc) {
        try {
            System.out.print("Informe o ID do cliente: ");
            int idCliente = sc.nextInt();
            sc.nextLine();

            OrdemServico.consultarOSCliente(idCliente);
        } catch (Exception e) {
            System.out.println("Erro ao consultar cliente: " + e.getMessage());
        }
    }
}

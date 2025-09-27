import java.util.Scanner;

import enums.CategoriaAparelho;
import enums.StatusAparelho;

/**
 * Classe principal da aplicação de gerenciamento de loja de conserto de celulares.
 * Controla o fluxo do menu e interações do usuário.
 */
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            // ===== MENU PRINCIPAL =====
            System.out.println("\n===== LOJA DE CONSERTO DE CELULARES =====");
            System.out.println("1. Abrir nova Ordem de Serviço");
            System.out.println("2. Listar Ordens de Serviço");
            System.out.println("3. Cadastrar Cliente");
            System.out.println("4. Consultar Cliente");
            System.out.println("5. Cadastrar Aparelho");
            System.out.println("6. Consultar Aparelhos");
            System.out.println("7. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = sc.nextInt();
            sc.nextLine(); // Consome a quebra de linha pendente

            switch (opcao) {
                case 1:
                    abrirNovaOrdem(sc);
                    break;
                case 2:
                    OrdemServicoDAO.listOs();
                    break;
                case 3:
                    cadastroCliente(sc);
                    break;
                case 4:
                    visualizarCliente(sc);
                    break;
                case 5:
                    cadastrarAparelho(sc);
                    break;
                case 6:
                    AparelhoDAO.listAparelhos();
                    break;
                case 7:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }

        } while (opcao != 7);

        sc.close();
    }

    /**
     * Abre uma nova Ordem de Serviço e permite associar peças.
     */
    private static void abrirNovaOrdem(Scanner sc) {
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
                // Adicionar itens à OS
                int opcaoItem;
                do {
                    System.out.print("Digite o ID do Produto/Peça: ");
                    int idProduto = sc.nextInt();

                    System.out.print("Digite a quantidade: ");
                    int qtde = sc.nextInt();
                    sc.nextLine(); // consome a quebra de linha

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

    /**
     * Cadastra um novo cliente no sistema.
     */
    private static void cadastroCliente(Scanner sc) {
        try {
            System.out.print("Digite o nome do cliente: ");
            String nome = sc.nextLine();

            System.out.print("Digite o telefone do cliente: ");
            String telefone = sc.nextLine();

            System.out.print("Digite o email do cliente: ");
            String email = sc.nextLine();

            int idCliente = Cliente.cadastrarCliente(nome, telefone, email);
            if (idCliente != -1) {
                System.out.println("Cliente cadastrado com sucesso! ID: " + idCliente);
            } else {
                System.out.println("Erro ao cadastrar cliente.");
            }
        } catch (Exception e) {
            System.out.println("Erro no cadastro: " + e.getMessage());
        }
    }

    /**
     * Exibe informações de um cliente específico.
     */
    private static void visualizarCliente(Scanner sc) {
        try {
            System.out.print("Informe o ID do cliente: ");
            int idCliente = sc.nextInt();
            sc.nextLine();

            Cliente.visualizarClienteEspecifico(idCliente);
        } catch (Exception e) {
            System.out.println("Erro ao consultar cliente: " + e.getMessage());
        }
    }

    /**
     * Cadastra um novo aparelho associado a um cliente, utilizando enums para categoria e status.
     */
    public static void cadastrarAparelho(Scanner sc) {
        try {
            System.out.print("Digite o ID do cliente: ");
            int idCliente = sc.nextInt();
            sc.nextLine(); // Consome a quebra de linha pendente

            System.out.print("Digite a marca do aparelho: ");
            String marca = sc.nextLine();

            System.out.print("Digite o modelo do aparelho: ");
            String modelo = sc.nextLine();

            // Escolha da categoria via enum
            System.out.println("Escolha a categoria do aparelho:");
            for (CategoriaAparelho c : CategoriaAparelho.values()) {
                System.out.println((c.ordinal() + 1) + " - " + c);
            }
            int categoriaChoice = Integer.parseInt(sc.nextLine());
            CategoriaAparelho categoria = CategoriaAparelho.values()[categoriaChoice - 1];

            System.out.print("Digite o IMEI do aparelho: ");
            String imei = sc.nextLine();

            StatusAparelho status = StatusAparelho.NOVO;

            // Chama o DAO para cadastrar o aparelho
            Aparelho.cadastrarAparelho(idCliente, marca, modelo, categoria.toString(), imei, status.toString());

            System.out.println("Aparelho cadastrado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao cadastrar aparelho: " + e.getMessage());
        }
    }

}

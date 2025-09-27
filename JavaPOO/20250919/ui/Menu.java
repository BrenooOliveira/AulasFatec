import java.util.Scanner;

public class Menu {

    private final Scanner sc;

    public Menu(Scanner sc) {
        this.sc = sc;
    }

    public void exibirMenu() {
        int opcao;

        do {
            System.out.println("\n===== LOJA DE CONSERTO DE CELULARES =====");
            System.out.println("1. Abrir nova Ordem de Serviço");
            System.out.println("2. Listar Ordens de Serviço");
            System.out.println("3. Cadastrar Cliente");
            System.out.println("4. Consultar Cliente");
            System.out.println("5. Consultar OSs do Cliente");
            System.out.println("6. Cadastrar Aparelho");
            System.out.println("7. Consultar Aparelhos");
            System.out.println("8. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = sc.nextInt();
            sc.nextLine();

            processarOpcao(opcao);

        } while (opcao != 8);
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> OrdemServicoUI.abrirNovaOrdem(sc);
            case 2 -> OrdemServicoDAO.listOs();
            case 3 -> ClienteUI.cadastroCliente(sc);
            case 4 -> ClienteUI.visualizarCliente(sc);
            case 5 -> OrdemServicoUI.consultarOSCliente(sc);
            case 6 -> AparelhoUI.cadastrarAparelho(sc);
            case 7 -> AparelhoDAO.listAparelhos();
            case 8 -> System.out.println("Saindo do sistema...");
            default -> System.out.println("Opção inválida! Tente novamente.");
        }
    }
}

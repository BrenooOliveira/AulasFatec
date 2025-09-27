import java.util.Scanner;

public class ClienteUI {

    public static void cadastroCliente(Scanner sc) {
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

    public static void visualizarCliente(Scanner sc) {
        try {
            System.out.print("Informe o ID do cliente: ");
            int idCliente = sc.nextInt();
            sc.nextLine();

            Cliente.visualizarClienteEspecifico(idCliente);
        } catch (Exception e) {
            System.out.println("Erro ao consultar cliente: " + e.getMessage());
        }
    }

}


import java.util.Scanner;
import enums.CategoriaAparelho;


public class AparelhoUI {

    public static void cadastrarAparelho(Scanner sc) {
        try {
            System.out.print("Digite o ID do cliente: ");
            int idCliente = sc.nextInt();
            sc.nextLine();

            System.out.print("Digite a marca do aparelho: ");
            String marca = sc.nextLine();

            System.out.print("Digite o modelo do aparelho: ");
            String modelo = sc.nextLine();

            System.out.println("Escolha a categoria do aparelho:");
            for (CategoriaAparelho c : CategoriaAparelho.values()) {
                System.out.println((c.ordinal() + 1) + " - " + c);
            }
            int categoriaChoice = Integer.parseInt(sc.nextLine());
            CategoriaAparelho categoria = CategoriaAparelho.values()[categoriaChoice - 1];

            System.out.print("Digite o IMEI do aparelho: ");
            String imei = sc.nextLine();

            Aparelho.cadastrarAparelho(idCliente, marca, modelo, categoria.toString(), imei);

            System.out.println("Aparelho cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar aparelho: " + e.getMessage());
        }
    }
}

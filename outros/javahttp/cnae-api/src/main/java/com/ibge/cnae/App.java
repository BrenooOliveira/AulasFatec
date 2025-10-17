package com.ibge.cnae;

import com.ibge.cnae.model.Cnae;
import com.ibge.cnae.service.CnaeService;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CnaeService service = new CnaeService();

        while (true) {
            System.out.println("\n=== MENU CNAE IBGE ===");
            System.out.println("1 - Listar todos");
            System.out.println("2 - Buscar por código");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            int op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1 -> {
                    List<Cnae> lista = service.listarTodos();
                    lista.stream().limit(10).forEach(System.out::println);
                }
                case 2 -> {
                    System.out.print("Digite o código CNAE: ");
                    String cod = sc.nextLine();
                    Cnae cnae = service.buscarPorId(cod);
                    if (cnae != null) System.out.println(cnae);
                    else System.out.println("CNAE não encontrado.");
                }
                case 0 -> {
                    System.out.println("Saindo...");
                    return;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }
}

package com.marido.aluguel;

import com.marido.aluguel.model.*;
import com.marido.aluguel.repository.ServicoRepository;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ServicoRepository repo = new ServicoRepository();

        int opcao;
        do {
            System.out.println("\n=== Marido de Aluguel ===");
            System.out.println("1 - Cadastrar Serviço");
            System.out.println("2 - Listar Serviços");
            System.out.println("0 - Sair");
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            switch(opcao) {
                case 1 -> {
                    System.out.println("Tipo (eletrica/hidraulica/alarme): ");
                    String tipo = scanner.nextLine();
                    System.out.println("Nome do prestador: ");
                    String nome = scanner.nextLine();
                    System.out.println("Telefone: ");
                    String telefone = scanner.nextLine();
                    System.out.println("Bairro: ");
                    String bairro = scanner.nextLine();

                    Servico servico = switch (tipo.toLowerCase()) {
                        case "eletrica" -> new Eletrica(nome, telefone, bairro);
                        case "hidraulica" -> new Hidraulica(nome, telefone, bairro);
                        case "alarme" -> new Alarme(nome, telefone, bairro);
                        default -> null;
                    };

                    if (servico != null) {
                        repo.addServico(servico);
                        System.out.println("Serviço cadastrado!");
                    } else {
                        System.out.println("Tipo inválido.");
                    }
                }
                case 2 -> {
                    System.out.println("\n--- Serviços Cadastrados ---");
                    repo.getServicos().forEach(System.out::println);
                }
            }
        } while(opcao != 0);
    }
}

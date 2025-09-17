package votacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


// Classe que representa um candidato

class Candidato {

    // Atributos privados para encapsulamento
    private String nome;
    private int votos;

    // Construtor da classe Candidato
    public Candidato(String nome) {
        this.nome = nome;
        this.votos = 0; // Inicialmente, o candidato não tem votos
    }

    // Método para adicionar um voto ao candidato
    public void adicionarVoto() {
        this.votos++;
    }


    // Métodos de acesso (getters)
    public String getNome() {
        return nome;
    }


    public int getVotos() {
        return votos;
    }
}

// Classe principal que gerencia a votação
public class UrnaEletronica {
    // Lista para armazenar os candidatos
    private List<Candidato> candidatos;
    private int votosEmBranco;
    private static final int TOTAL_VOTOS = 60; // Constante para o número total de votos

    // Construtor da UrnaEletronica
    public UrnaEletronica() {

        // Inicializa a lista de candidatos
        candidatos = new ArrayList<>();

        // Adiciona os 4 candidatos
        candidatos.add(new Candidato("Candidato 1"));
        candidatos.add(new Candidato("Candidato 2"));
        candidatos.add(new Candidato( "Candidato 3"));
        candidatos.add(new Candidato("Candidato 4"));

        this.votosEmBranco = 0; // Inicializa os votos em branco

    }
    // Método para simular o processo de votação
    public void simularVotacao() {

        Random random = new Random();
        System.out.println("Iniciando a simulação de votação...");
        System.out.println("Total de votos a serem simulados: " + TOTAL_VOTOS);

        for (int i = 0; i < TOTAL_VOTOS; i++) {
            // Gera um número aleatório de 0 a 4
            // 0 a 3 representam os candidatos (índices da lista)
            // 4 representa o voto em branco
            int votoAleatorio = random.nextInt(5);

            if (votoAleatorio < candidatos.size()) {
                // O voto é para um dos candidatos
                candidatos.get(votoAleatorio).adicionarVoto();

            } else {
            // O voto é em branco
            this.votosEmBranco++;
            }   

        }

        System.out.println("\nSimulação concluída!");

    }


    // Método para exibir os resultados da votação
    public void exibirResultados() {
        System.out.println("\n--- Resultados da Votação ---");

        // Itera sobre a lista de candidatos e imprime o resultado de cada um
        for (Candidato candidato : candidatos) {
            System.out.println(candidato.getNome() + ": " + candidato.getVotos() + " votos");
        }

        // Imprime o total de votos em branco
        System.out.println("Votos em branco: " + this.votosEmBranco + " votos");

        // Calcula e exibe o total de votos para verificação
        int totalVotosContados = 0;
        for (Candidato candidato : candidatos) {
            totalVotosContados += candidato.getVotos();
        }

        totalVotosContados += this.votosEmBranco;
        System.out.println("---------------------------");
        System.out.println("Total de votos contabilizados: " + totalVotosContados);
    }

    // Método principal (main) para executar o programa
    public static void main(String[] args) {
    // Cria uma nova instância da UrnaEletronica
    UrnaEletronica eleicao = new UrnaEletronica();

    // Simula os votos
    eleicao.simularVotacao();

    // Exibe o resultado final
    eleicao.exibirResultados();
    }

}
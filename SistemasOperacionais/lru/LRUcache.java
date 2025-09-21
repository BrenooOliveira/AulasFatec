package lru;

import java.util.*;

public class LRUcache{
    private int capacidade; //"capacidade" da cache
    private Map<Integer, String> cache; // chave, valor (dicionario em python) -> cache disponivel
    private LinkedList<Integer> ordem; // ordem do uso, equivalente ao deque

    // Constructor
    public LRUcache(int capacidade){
        this.capacidade = capacidade;
        this.cache = new HashMap<>();
        this.ordem = new LinkedList<>();
    }

    // Metodos
    // atual estado do cache
    private void mostrarCache(){
        List<String> estado = new ArrayList<>();
        for (int chave : ordem){
            estado.add(chave + " = " + cache.get(chave));
        }
        System.out.println("Estado atual: " + cache);
        System.out.println("---------------------------");
    }


    // acessar um valor em cache
    public String getValor(int chave){
        if (cache.containsKey(chave)){
            ordem.remove(chave); // remove a posicao atual
            ordem.addLast(chave); // insere na posicao mais recente

            System.out.println("Acessando a chave: " + chave + ": " + cache.get(chave));
            return cache.get(chave);
        } else{
            System.out.println(chave + "Chave não encontrada!");
            return null;
        }
    }

    public void putValor(int chave, String valor){
        if(cache.containsKey(chave)){
            cache.put(chave, valor); // atualiza o valor
            ordem.remove(chave); // remove a posicao atual
            ordem.addLast(chave); // insere na posicao mais recente
            System.out.println("Atualizando chave " + chave + " para " + valor);
        } else {
            if (cache.size() >= capacidade){ // se o tamanho do cache for maior que a capacidade
                int lru = ordem.removeFirst(); // remove o mais recente
                cache.remove(lru);
                System.out.println("Removendo " + lru + " (menos usado) ");
            }
            cache.put(chave, valor);
            ordem.addLast(chave); // add como mais recente
            System.out.println("Inserindo " + chave + ": " + valor);
        }
        mostrarCache();

    }
}

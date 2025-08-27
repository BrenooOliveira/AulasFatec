package lru;

public class Main {
    public static void main(String[] args) {
        LRUcache cache = new LRUcache(3);

        cache.putValor(1, "A");
        cache.putValor(2, "B");
        cache.putValor(3, "C");
        cache.getValor(1);
        cache.putValor(4, "D");
        cache.getValor(2);
        cache.putValor(5, "E");
    }
}
/*
 * Inserindo 1: A
Estado atual: {1=A}
---------------------------
Inserindo 2: B
Estado atual: {1=A, 2=B}
---------------------------
Inserindo 3: C
Estado atual: {1=A, 2=B, 3=C}
---------------------------
Acessando a chave: 1: A
Removendo 1 (menos usado) 
Inserindo 4: D
Estado atual: {2=B, 3=C, 4=D}
---------------------------
Acessando a chave: 2: B
Removendo 3 (menos usado) 
Inserindo 5: E
Estado atual: {2=B, 4=D, 5=E}
 * 
 * 
 */
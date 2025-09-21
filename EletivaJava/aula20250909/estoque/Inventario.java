package estoque;

import java.util.HashMap;
import java.util.Map;

public class Inventario {
    private Map<String,Produto> invent = new HashMap<>();
    private Map<String,Integer> logsVendas = new HashMap<>(); // hashmap que vai segurar em tempo de execucao quais os itens vendidos e a quantidade vendida.

    public Inventario(){
        // Adicionando os dados da tabela ao mapa (dados previamente definidos)
        invent.put("A", new Produto(12, 15.5, 6.2));
        invent.put("B", new Produto(12, 21.99, 12.22));
        invent.put("C", new Produto(14, 10.00, 9.52));
        invent.put("D", new Produto(82, 14.99, 14.69));

    }
    public Map<String,Produto> getInventario(){
        return invent;
    }
    public Produto getProductInfo(String product){
        return invent.get(product);
    }

    public void soldProductFromIndex(String product,int unitsSold){
        // pega o produto do invetario e entao realiza a "venda" com base na qtd vendida
        invent.get(product).soldProduct(unitsSold);

        int qtdeAnterior = logsVendas.getOrDefault(product, 0); // pega o ultimo valor associado a chave ou retorna 0 se não houver valor associado
        logsVendas.put(product, qtdeAnterior + unitsSold);
    }
    public void showProductInfo(String product){
        System.out.printf("============= INFORMAÇÕES SOBRE O PRODUTO %s =============\n",product);
        System.out.println("Quantidade:" + getProductInfo(product).getQtde());
        System.out.println("Quantidade de Alerta:" + getProductInfo(product).getQtdeAlerta());
        System.out.println("Valor Unitário:" + getProductInfo(product).getValorUnit());
        System.out.printf("=========================================================\n",product);
    }

    public Map<String,Integer> getLogVendas(){
        return logsVendas;
    }
    public void salesReport() {
        System.out.printf("============= RELATÓRIO DE VENDAS =============\n");
        double valorLiqVendasAcumulado = 0.0;

        for (Map.Entry<String, Integer> entry : logsVendas.entrySet()) {
            String k = entry.getKey();
            int unidadesVendidas = entry.getValue();

            double valorVendas = unidadesVendidas * invent.get(k).getValorUnit();
            double custoVendas = unidadesVendidas * invent.get(k).getCustoUnit();
            double valorLiqVendas = valorVendas - custoVendas;
            double custoRecomporEstoque = unidadesVendidas * invent.get(k).getCustoUnit();
            char reposicao = (invent.get(k).getQtde() < invent.get(k).getQtdeAlerta()) ? 'S' : 'N';

            System.out.printf("Produto: %s | Unidades Vendidas: %d | " +
                            "Valor em Vendas (bruto): R$%.2f | " +
                            "Custo para Recompor o Estoque: R$%.2f | " +
                            "Reposição? %c\n",
                            k, unidadesVendidas, valorVendas, custoRecomporEstoque, reposicao);

            valorLiqVendasAcumulado += valorLiqVendas;
        }

        System.out.printf("TOTAL LÍQUIDO DE VENDAS: R$%.2f\n", valorLiqVendasAcumulado);
    }
}

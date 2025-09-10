package estoque;


public class Main {
    public static void main(String[] args) {
        Inventario inventario = new Inventario();

        String product = "A"; // produto simulacao
        int unitsSold = 15; // simulacao de 15 vendas
        int qtdeAtual = inventario.getProductInfo(product).getQtde();
        int qtdeAlerta = inventario.getProductInfo(product).getQtdeAlerta();


        inventario.showProductInfo(product);
        
        /* ----------- VALIDAÇÕES ----------- */

        inventario.soldProductFromIndex(product, unitsSold);
        
        
        
        // Emitir aviso
        if (qtdeAtual <= qtdeAlerta) {
            System.out.println("⚠️  O Produto " + product + " entrou em alerta!");
            
        }
        // bloqueia a venda
        if()
    }
    


}

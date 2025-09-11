package estoque;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Inventario inventario = new Inventario();
        Random random = new Random();


        for (int i = 0; i < 10; i++) {
            // Sorteia produto entre A e D
            String[] productArsernal = {"A", "B", "C", "D"};
            String product = productArsernal[random.nextInt(productArsernal.length)];

            // Sorteia quantidade entre 1 e 15
            int unitsSold = random.nextInt(15) + 1;

            int qtdeAtual = inventario.getProductInfo(product).getQtde();
            int qtdeAlerta = inventario.getProductInfo(product).getQtdeAlerta();

            System.out.printf("➡️ Tentando vender %d unidades do produto %s (Estoque atual: %d)\n",
                    unitsSold, product, qtdeAtual);

            /* ----------- VALIDAÇÕES ----------- */
            if (qtdeAtual - unitsSold < 0) {
                System.out.println("❌  Venda do Produto " + product + " não autorizada. Estoque insuficiente.\n");
                continue; // passa para o proximo index do loop
            } else if (qtdeAtual <= qtdeAlerta) {
                System.out.println("⚠️  O Produto " + product + " entrou em alerta!");
            }

            inventario.soldProductFromIndex(product, unitsSold);
            System.out.println("✅ Venda realizada com sucesso!\n");
        } 
        inventario.salesReport();

    }
    


}
/* OUTPUT EXAMPLE
➡️ Tentando vender 9 unidades do produto B (Estoque atual: 12)
✅ Venda realizada com sucesso!

➡️ Tentando vender 4 unidades do produto A (Estoque atual: 12)
✅ Venda realizada com sucesso!

➡️ Tentando vender 9 unidades do produto B (Estoque atual: 3)
❌  Venda do Produto B não autorizada. Estoque insuficiente.
➡️ Tentando vender 1 unidades do produto D (Estoque atual: 82)
✅ Venda realizada com sucesso!

➡️ Tentando vender 9 unidades do produto C (Estoque atual: 14)
✅ Venda realizada com sucesso!

➡️ Tentando vender 8 unidades do produto D (Estoque atual: 81)
✅ Venda realizada com sucesso!

➡️ Tentando vender 2 unidades do produto B (Estoque atual: 3)
✅ Venda realizada com sucesso!

➡️ Tentando vender 10 unidades do produto D (Estoque atual: 73)
✅ Venda realizada com sucesso!

➡️ Tentando vender 9 unidades do produto D (Estoque atual: 63)
✅ Venda realizada com sucesso!

➡️ Tentando vender 9 unidades do produto B (Estoque atual: 1)
❌  Venda do Produto B não autorizada. Estoque insuficiente.
============= RELATÓRIO DE VENDAS =============
Produto: A | Unidades Vendidas: 4 | Valor em Vendas: R$62,00 | Reposição? N 
Produto: B | Unidades Vendidas: 11 | Valor em Vendas: R$241,89 | Reposição? S 
Produto: C | Unidades Vendidas: 9 | Valor em Vendas: R$90,00 | Reposição? N 
Produto: D | Unidades Vendidas: 28 | Valor em Vendas: R$419,72 | Reposição? N 
 * 
 * 
 */
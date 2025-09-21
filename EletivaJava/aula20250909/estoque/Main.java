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

package startingOnJava.ex2;

public class Churrasco {
    public int grCarnePessoa = 300; // quantas gramas cada pessoa consome
    public int mlCervejaPessoa = 1500; // quantos ml cada pessoa consome
    private int mlGarrafa = 600; // ml na garrafa

    void consumoTotal(int numConvidados){
        if (numConvidados <= 0){
            System.out.println("O número de convidados deve ser maior que 1.");
            return; 
        }

        int numGarrafas = (int) Math.ceil((numConvidados * (double) mlCervejaPessoa) / mlGarrafa); // quantas garrafas são necessárias.

        System.out.println("Para " + numConvidados + " convidados, você precisará de:");
        System.out.println(numConvidados*grCarnePessoa + " gramas de carne");
        System.out.println(numGarrafas + " garrafas de cerveja de " + mlGarrafa + " ml");
    }
}

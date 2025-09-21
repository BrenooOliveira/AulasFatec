public class Retangulo extends FormasGeometrica implements Forma2D {
    private double largura, altura;

    public Retangulo(double largura, double altura){
        super("Retangulo");
        this.largura = largura;
        this.altura = altura;
    }

    @Override
    public Double calcularArea() {
        return altura*largura;
    }

    @Override
    public Double calcularPerimetro() {
        return 2*(altura+largura);
    }

}

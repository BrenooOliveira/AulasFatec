public class Circulo extends FormasGeometrica implements Forma2D {
    private double raio;

    public Circulo(Double raio) {
        super("Circulo");
        this.raio = raio;
    }

    @Override
    public Double calcularArea(){
        return Math.PI * Math.pow(raio, 2);
    }   

    @Override
    public Double calcularPerimetro() {
        return 2* Math.PI * raio;
    }
}

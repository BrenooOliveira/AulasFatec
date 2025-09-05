public class Quadrado extends FormasGeometrica implements Forma2D {
    private double lado;
    
    public Quadrado(double lado) {
        super("Quadrado");
        this.lado = lado;
    }

    @Override
    public Double calcularArea() {
        return lado*lado;
    }

    @Override
    public Double calcularPerimetro() {
        return 2*(lado+lado);
    }

}

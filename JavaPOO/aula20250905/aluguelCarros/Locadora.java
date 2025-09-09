package aluguelCarros;

import java.util.Map;

public class Locadora {
    // java 9+ com map imutavel
    private Map<String, Double> modelosPrecos = Map.of(
        "Basico", 90.0,
        "Intermediario",110.0,
        "Luxo",200.0
    );

    public Map<String, Double> getModelosPrecos(){
        return modelosPrecos;

    }

    public Double calcularValorTotal(String modelo, int numDiarias){
        return modelosPrecos.get(modelo) * numDiarias;
    }
}

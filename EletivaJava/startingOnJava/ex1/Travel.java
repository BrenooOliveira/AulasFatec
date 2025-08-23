package startingOnJava.ex1;

public class Travel {
    public String name;
    public Double distance; // em km
    public Double avgConsumption; // consumo médio do veiculo
    public Double fuelPrice; // preço do combustível por litro

    public void showTravelInfo() {
        System.out.println("Nome do trajeto: " + name);
        System.out.println("Distância: " + distance + " km");
        System.out.println("Consumo médio do veículo: " + avgConsumption + " km/l");
        System.out.println("Preço do combustível por litro: R$ " + fuelPrice);
    }

    public Double aproxCost(){
        Double litersNeeded = distance / avgConsumption;
        Double cost = litersNeeded * fuelPrice;
        return cost;
    }
}

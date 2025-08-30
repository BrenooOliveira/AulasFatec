package aula20250829ex;
import java.awt.Toolkit;

public class Cachorro extends Animal {
    @Override
    public void fazerSom() {
        Toolkit.getDefaultToolkit().beep(); // Faz o beep do sistema
    }
}

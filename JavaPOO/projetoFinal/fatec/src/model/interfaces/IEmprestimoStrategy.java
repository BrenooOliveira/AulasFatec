// Essa interface permitirá que tanto Emprestimo quanto Devolucao implementem comportamentos diferentes para o mesmo tipo de operação.
public interface IEmprestimoStrategy {
    void executarEmprestimo(EmprestimoModel emprestimo);

}

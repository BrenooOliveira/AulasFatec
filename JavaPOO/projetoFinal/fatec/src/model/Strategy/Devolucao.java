public class Devolucao implements IEmprestimoStrategy {
    @Override
    public void executarEmprestimo(EmprestimoModel emprestimo) {
        // Implementação do método de devolução
        System.out.println("Registrando nova devolucao do livro ID " + emprestimo.getLivroId());

    }

}

public class EmprestimoContext{
    private IEmprestimoStrategy strategy;

    public EmprestimoContext(IEmprestimoStrategy strategy) {
        this.strategy = strategy;
    }

    public void setEmprestimoEstrategy() {
        strategy.emprestimo();
    }
}

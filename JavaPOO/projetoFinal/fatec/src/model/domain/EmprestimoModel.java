import java.sql.Date;

public class EmprestimoModel {
    // Atributos
    private Integer idEmprestimo;
    private Livro livro;
    private Cliente cliente;
    private enum statusEmprestimo {
        EMPRESTADO
        ,DEVOLVIDO
    }
    private Date dataEmprestimo;
    private Date dataDevolucao;

    // Construtor
    public EmprestimoModel(Integer idEmprestimo, Livro livro, Cliente cliente, String statusEmprestimo,Date dataEmprestimo, Date dataDevolucao) {
        this.idEmprestimo = idEmprestimo;
        this.livro = livro;
        this.cliente = cliente;
        this.statusEmprestimo = statusEmprestimo;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;

    }

    // Getters e Setters

    /** Retorna o ID do empréstimo */
    public Integer getIdEmprestimo() {
        return idEmprestimo;
    }

    /** Define o ID do empréstimo */
    public void setIdEmprestimo(Integer idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }

    /** Retorna o livro emprestado */
    public Livro getLivro() {
        return livro;
    }

    /** Define o livro emprestado */
    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    /** Retorna o nome do cliente que fez o empréstimo */
    public Cliente getCliente() {
        return cliente;
    }

    /** Define o nome do cliente que fez o empréstimo */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /** Retorna a data em que o empréstimo foi feito */
    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    /** Define a data do empréstimo */
    public void setDataEmprestimo(Date dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    /** Retorna a data prevista para devolução */
    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    /** Define a data prevista para devolução */
    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }
}

public class ItemOrdemServico {
    private int id_item;
    private int id_os;
    private int id_produto;
    private int qtde;

    public ItemOrdemServico(int id_os, int id_produto, int qtde) {
        this.id_os = id_os;
        this.id_produto = id_produto;
        this.qtde = qtde;
    }

    // insere um item em uma OS
    static void inserirItemOs(int id_os,int id_produto,int qtde){
        ItemOrdemServicoDAO.criarTabela();
        ItemOrdemServicoDAO.insertInto(id_os, id_produto, qtde);
    }

}

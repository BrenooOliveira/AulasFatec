package com.biblioteca.controller;

import com.biblioteca.facade.BibliotecaFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class HomeController {

    @FXML private TableView<Object[]> tabelaLivros;
    @FXML private TableColumn<Object[], Integer> colId;
    @FXML private TableColumn<Object[], String> colNome;
    @FXML private TableColumn<Object[], String> colData;
    @FXML private TableColumn<Object[], Integer> colQtd;
    @FXML private TableColumn<Object[], Void> colEditar;
    @FXML private TableColumn<Object[], Void> colExcluir;

    private final BibliotecaFacade facade = new BibliotecaFacade();

    @FXML
    public void initialize() {
        configurarTabela();
        carregarTabela();
    }

    private void configurarTabela() {
        colId.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty((Integer) c.getValue()[0]).asObject());
        colNome.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty((String) c.getValue()[1]));
        colData.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty((String) c.getValue()[2]));
        colQtd.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty((Integer) c.getValue()[3]).asObject());

        // botão editar
        colEditar.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Editar");

            {
                btn.setOnAction(e -> {
                    Object[] item = getTableView().getItems().get(getIndex());
                    onClickEditar((int) item[0], (String) item[1], (int) item[3]);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

        // botão excluir
        colExcluir.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Excluir");

            {
                btn.setOnAction(e -> {
                    Object[] item = getTableView().getItems().get(getIndex());
                    onClickDeletar((int) item[0], (String) item[1]);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

    public void carregarTabela() {
        ObservableList<Object[]> lista = FXCollections.observableArrayList(facade.listarEstoque());
        tabelaLivros.setItems(lista);
    }

    public void onClickAddLivro() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Adicionar Livro");
        dialog.setContentText("Nome do livro:");
        String nome = dialog.showAndWait().orElse(null);

        if (nome != null) {
            facade.adicionarLivro(nome, 1);
            carregarTabela();
        }
    }

    private void onClickEditar(int id, String nome, int qtd){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Editar Livro");

        TextField campoNome = new TextField(nome);
        TextField campoQtd = new TextField(String.valueOf(qtd));

        VBox v = new VBox(new Label("Nome:"), campoNome, new Label("Quantidade:"), campoQtd);
        v.setSpacing(10);

        dialog.getDialogPane().setContent(v);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(btn -> {
            if(btn == ButtonType.OK){
                facade.atualizarLivro(id, campoNome.getText(), Integer.parseInt(campoQtd.getText()));
                carregarTabela();
            }
        });
    }

    private void onClickDeletar(int id, String nome){
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setHeaderText("Excluir Livro");
        a.setContentText("Deseja excluir o livro " + nome + "?");

        if(a.showAndWait().get() == ButtonType.OK){
            facade.deletarLivro(id);
            carregarTabela();
        }
    }
}

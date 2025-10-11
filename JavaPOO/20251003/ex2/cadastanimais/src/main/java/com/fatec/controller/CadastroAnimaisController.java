package com.fatec.controller;

import com.fatec.model.*;
import com.fatec.model.DatabaseManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class CadastroAnimaisController {

    @FXML private TextField txtNome;
    @FXML private TextField txtIdade;
    @FXML private ComboBox<String> cmbEspecie;
    @FXML private ComboBox<String> cmbGenero;
    @FXML private TableView<Animal> animalTable;
    @FXML private TableColumn<Animal, String> colNome;
    @FXML private TableColumn<Animal, String> colEspecie;
    @FXML private TableColumn<Animal, Integer> colIdade;
    @FXML private TableColumn<Animal, String> colGenero;

    private ObservableList<Animal> animalList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // 1. Configurar ComboBoxes
        List<String> especies = Arrays.asList("Cachorro", "Gato", "Papagaio", "Hamster", "Peixe", "Cavalo");
        cmbEspecie.setItems(FXCollections.observableArrayList(especies));
        cmbGenero.setItems(FXCollections.observableArrayList("Macho", "Fêmea", "Não Informado"));

        // 2. Configurar Colunas da Tabela
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEspecie.setCellValueFactory(new PropertyValueFactory<>("especie"));
        colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));

        animalTable.setItems(animalList);

        // 3. Inicializar Banco de Dados e Carregar Dados
        DatabaseManager.createTable();
        loadAnimals();
    }

    private void loadAnimals() {
        List<Animal> animaisDB = DatabaseManager.getAllAnimals();
        animalList.setAll(animaisDB);
    }

    @FXML
    private void handleCadastrarAnimal() {
        try {
            String nome = txtNome.getText();
            String especie = cmbEspecie.getValue();
            int idade = Integer.parseInt(txtIdade.getText());
            String genero = cmbGenero.getValue();

            if (nome.isEmpty() || especie == null || genero == null) {
                showAlert("Erro de Cadastro", "Todos os campos devem ser preenchidos.", Alert.AlertType.ERROR);
                return;
            }

            Animal novoAnimal = createAnimalInstance(nome, especie, idade, genero);

            // Salva no banco de dados
            DatabaseManager.saveAnimal(novoAnimal);

            // Atualiza a lista da interface (Model -> View)
            loadAnimals();

            // Limpa os campos
            txtNome.clear();
            txtIdade.clear();
            cmbEspecie.getSelectionModel().clearSelection();
            cmbGenero.getSelectionModel().clearSelection();

            showAlert("Sucesso", "Animal cadastrado com sucesso!", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            showAlert("Erro de Entrada", "A idade deve ser um número inteiro válido.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Erro", "Ocorreu um erro ao cadastrar o animal: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Método auxiliar que demonstra o Polimorfismo na criação de objetos
    private Animal createAnimalInstance(String nome, String especie, int idade, String genero) {
        return switch (especie) {
            case "Cachorro" -> new Cachorro(nome, idade, genero);
            case "Gato" -> new Gato(nome, idade, genero);
            case "Papagaio" -> new Papagaio(nome, idade, genero);
            case "Hamster" -> new Hamster(nome, idade, genero);
            case "Peixe" -> new Peixe(nome, idade, genero);
            case "Cavalo" -> new Cavalo(nome, idade, genero);
            default -> throw new IllegalArgumentException("Espécie desconhecida.");
        };
    }

    @FXML
    private void handleVerSons() {
        Animal selectedAnimal = animalTable.getSelectionModel().getSelectedItem();

        if (selectedAnimal != null) {
            // Polimorfismo em Ação:
            // O Java chama o método som() da classe concreta (Cachorro, Gato, etc.),
            // mesmo que a variável seja do tipo Animal.
            String somFeito = selectedAnimal.som();
            showAlert("Som do Animal",
                      selectedAnimal.getNome() + " (" + selectedAnimal.getEspecie() + ") faz: " + somFeito,
                      Alert.AlertType.INFORMATION);
        } else {
            showAlert("Aviso", "Selecione um animal na lista para ouvir o som.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void handleEncerrar() {
        // Fecha a janela principal
        Stage stage = (Stage) animalTable.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

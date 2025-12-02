package org.projeto_poo2_estoque.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.projeto_poo2_estoque.dao.ProdutoDAO;
import org.projeto_poo2_estoque.model.Produto;

import java.io.IOException;

public class MainController {
    @FXML private TextField txtID;
    @FXML private TextField txtNome;
    @FXML private TextField txtQuantidade;
    @FXML private TextField txtPreco;
    @FXML private TableView<Produto> tableView;
    @FXML private TableColumn<Produto, Integer> colId;
    @FXML private TableColumn<Produto, String> colNome;
    @FXML private TableColumn<Produto, Integer> colQtd;
    @FXML private TableColumn<Produto, Double> colPreco;

    private ProdutoDAO dao = new ProdutoDAO();
    private ObservableList<Produto> listaProdutos = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        atualizarTabela();
        if (tableView != null) {
            tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    preencherCampos(newSelection);
                }
            });
        }
    }

    @FXML
    protected void onAdicionarClick() {
        try {
            String nome = txtNome.getText();
            int qtd = Integer.parseInt(txtQuantidade.getText());
            double preco = Double.parseDouble(txtPreco.getText().replace(",", "."));
            int novoId = dao.carregarProximoId();
            Produto p = new Produto(novoId, nome, qtd, preco);
            dao.salvarProduto(p);

            limparCampos();
            atualizarTabela();
        } catch (Exception e) {
            mostrarErro("Erro ao salvar: " + e.getMessage());
        }
    }

    @FXML
    protected void onAtualizarClick() {
        try {
            int id = Integer.parseInt(txtID.getText());
            String nome = txtNome.getText();
            int qtd = Integer.parseInt(txtQuantidade.getText());
            double preco = Double.parseDouble(txtPreco.getText().replace(",", "."));
            Produto p = new Produto(id, nome, qtd, preco);
            dao.atualizarProduto(p);

            limparCampos();
            atualizarTabela();
        } catch (Exception e) {
            mostrarErro("Erro ao atualizar: " + e.getMessage());
        }
    }

    @FXML
    protected void onExcluirClick() {
        try {
            if (!txtID.getText().isEmpty()) {
                int id = Integer.parseInt(txtID.getText());
                dao.excluirProduto(id);

                limparCampos();
                atualizarTabela();
            }
        } catch (Exception e) {
            mostrarErro("Erro ao excluir: " + e.getMessage());
        }
    }

    private void atualizarTabela() {
        try {
            listaProdutos.clear();
            listaProdutos.addAll(dao.listarProdutos());
            if (tableView != null) {
                tableView.setItems(listaProdutos);
                if (tableView.getColumns().size() >= 4) {
                    colId.setCellValueFactory(new PropertyValueFactory<>("id"));
                    colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
                    colQtd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
                    colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
                }
            }
        } catch (IOException e) {
            mostrarErro("Erro ao ler arquivo: " + e.getMessage());
        }
    }

    private void limparCampos() {
        txtID.clear();
        txtNome.clear();
        txtQuantidade.clear();
        txtPreco.clear();
    }

    private void preencherCampos(Produto p) {
        txtID.setText(String.valueOf(p.getId()));
        txtNome.setText(p.getNome());
        txtQuantidade.setText(String.valueOf(p.getQuantidade()));
        txtPreco.setText(String.valueOf(p.getPreco()));
    }

    private void mostrarErro(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.show();
    }
}
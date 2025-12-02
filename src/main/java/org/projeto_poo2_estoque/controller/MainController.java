package org.projeto_poo2_estoque.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MainController {
    @FXML
    public TextField txtID;
    @FXML
    public TextField txtNome;
    @FXML
    public TextField txtQuantidade;
    @FXML
    public TextField txtPreco;

    @FXML
    protected void onAdicionarClick() {
    System.out.println(txtID.getText());
    System.out.println(txtNome.getText());
    System.out.println(txtQuantidade.getText());
    System.out.println(txtPreco.getText());
    }

    @FXML
    protected void onAtualizarClick() {
        System.out.println(txtID.getText());
        System.out.println(txtNome.getText());
        System.out.println(txtQuantidade.getText());
        System.out.println(txtPreco.getText());
    }

    @FXML
    protected void onExcluirClick() {
        System.out.println(txtID.getText());
        System.out.println(txtNome.getText());
        System.out.println(txtQuantidade.getText());
        System.out.println(txtPreco.getText());
    }
}
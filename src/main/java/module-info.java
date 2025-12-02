module org.projeto_poo2_estoque {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires jakarta.persistence;


    opens org.projeto_poo2_estoque to javafx.fxml;
    exports org.projeto_poo2_estoque;
    exports org.projeto_poo2_estoque.controller;
    opens org.projeto_poo2_estoque.controller to javafx.fxml;
}
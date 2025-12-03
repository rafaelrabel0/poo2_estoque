module org.projeto_poo2_estoque {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // Necessário para JDBC
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming; // Necessário para o Hibernate
    requires org.postgresql.jdbc; // Driver do Postgres

    // Abre o pacote de modelos para o Hibernate conseguir ler/gravar
    opens org.projeto_poo2_estoque.model to org.hibernate.orm.core, javafx.base;

    opens org.projeto_poo2_estoque to javafx.fxml;
    exports org.projeto_poo2_estoque;
    exports org.projeto_poo2_estoque.controller;
    opens org.projeto_poo2_estoque.controller to javafx.fxml;
}
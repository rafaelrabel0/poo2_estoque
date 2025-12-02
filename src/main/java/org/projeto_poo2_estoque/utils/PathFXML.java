package org.projeto_poo2_estoque.utils;

import java.nio.file.Paths;

public class PathFXML {
    public static String pathBase(){
        return Paths.get("src/main/java/org/projeto_poo2_estoque/view").toAbsolutePath().toString();
    }
}

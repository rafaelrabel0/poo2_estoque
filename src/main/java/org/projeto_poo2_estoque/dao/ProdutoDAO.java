package org.projeto_poo2_estoque.dao;

import org.projeto_poo2_estoque.model.Produto;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProdutoDAO {
    private static final String ARQUIVO_CSV = "produtos";

    public ProdutoDAO() {
        try {
            if (!Files.exists(Paths.get(ARQUIVO_CSV))) {
                Files.createFile(Paths.get(ARQUIVO_CSV));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatarProdutoParaCSV(Produto p) {
        return String.format(Locale.US, "%d,%s,%.2f,%d",
                p.getId(), p.getNome(), p.getPreco(), p.getQuantidade());
    }

    public void salvarProduto(Produto produto) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(ARQUIVO_CSV), StandardOpenOption.APPEND)) {
            writer.write(formatarProdutoParaCSV(produto));
            writer.newLine();
        }
    }

    public List<Produto> listarProdutos() throws IOException {
        List<Produto> produtos = new ArrayList<>();
        if (!Files.exists(Paths.get(ARQUIVO_CSV))) return produtos;

        List<String> linhas = Files.readAllLines(Paths.get(ARQUIVO_CSV));

        for (String linha : linhas) {
            if (linha.trim().isEmpty()) continue;
            String[] partes = linha.split(",");
            if (partes.length == 4) {
                try {
                    int id = Integer.parseInt(partes[0]);
                    String nome = partes[1];
                    double preco = Double.parseDouble(partes[2].replace(",", "."));
                    int quantidade = Integer.parseInt(partes[3]);
                    produtos.add(new Produto(id, nome, quantidade, preco));
                } catch (NumberFormatException e) {
                    System.err.println("Erro ao ler linha: " + linha);
                }
            }
        }
        return produtos;
    }

    public void atualizarProduto(Produto produtoEditado) throws IOException {
        List<Produto> produtos = listarProdutos();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(ARQUIVO_CSV), StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Produto p : produtos) {
                Produto paraSalvar = (p.getId() == produtoEditado.getId()) ? produtoEditado : p;
                writer.write(formatarProdutoParaCSV(paraSalvar));
                writer.newLine();
            }
        }
    }

    public void excluirProduto(int id) throws IOException {
        List<Produto> produtos = listarProdutos();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(ARQUIVO_CSV), StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Produto p : produtos) {
                if (p.getId() != id) {
                    writer.write(formatarProdutoParaCSV(p));
                    writer.newLine();
                }
            }
        }
    }

    public int carregarProximoId() throws IOException {
        List<Produto> produtos = listarProdutos();
        int maxId = 0;
        for (Produto p : produtos) {
            if (p.getId() > maxId) {
                maxId = p.getId();
            }
        }
        return maxId + 1;
    }
}
package org.projeto_poo2_estoque.dao;

import org.projeto_poo2_estoque.model.Produto;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    private static final String ARQUIVO_CSV = "produtos.csv";

    public ProdutoDAO() {
        // Cria o arquivo se não existir
        try {
            if (!Files.exists(Paths.get(ARQUIVO_CSV))) {
                Files.createFile(Paths.get(ARQUIVO_CSV));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void salvarProduto(Produto produto) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(ARQUIVO_CSV), StandardOpenOption.APPEND)) {
            String linha = String.format("%d,%s,%.2f,%d",
                    produto.getId(),
                    produto.getNome(),
                    produto.getPreco(),
                    produto.getQuantidade());
            writer.write(linha);
            writer.newLine();
        }
    }

    public List<Produto> listarProdutos() throws IOException {
        List<Produto> produtos = new ArrayList<>();
        List<String> linhas = Files.readAllLines(Paths.get(ARQUIVO_CSV));

        for (String linha : linhas) {
            if (linha.trim().isEmpty()) continue;
            String[] partes = linha.split(",");
            if (partes.length == 4) {
                int id = Integer.parseInt(partes[0]);
                String nome = partes[1];
                double preco = Double.parseDouble(partes[2].replace(",", ".")); // Garante leitura correta do double
                int quantidade = Integer.parseInt(partes[3]);
                produtos.add(new Produto(id, nome, (int) preco, quantidade));
            }
        }
        return produtos;
    }

    public void atualizarProduto(Produto produtoEditado) throws IOException {
        List<Produto> produtos = listarProdutos();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(ARQUIVO_CSV), StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Produto p : produtos) {
                if (p.getId() == produtoEditado.getId()) {
                    salvarProduto(produtoEditado); // Salva o novo
                } else {
                    salvarProduto(p); // Salva o antigo
                }
            }
        }
    }

    public void excluirProduto(int id) throws IOException {
        List<Produto> produtos = listarProdutos();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(ARQUIVO_CSV), StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Produto p : produtos) {
                if (p.getId() != id) {
                    salvarProduto(p);
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
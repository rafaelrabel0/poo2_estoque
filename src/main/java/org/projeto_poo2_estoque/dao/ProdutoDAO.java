package org.projeto_poo2_estoque.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.projeto_poo2_estoque.model.Produto;

import java.util.List;

public class ProdutoDAO {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("un-jpa");

    public void salvarProduto(Produto produto) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            if (produto.getId() == 0) {
                em.persist(produto);
            } else {
                em.merge(produto);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<Produto> listarProdutos() {
        EntityManager em = emf.createEntityManager();
        List<Produto> lista = null;
        try {
            lista = em.createQuery("FROM Produto", Produto.class).getResultList();
        } finally {
            em.close();
        }
        return lista;
    }

    public void atualizarProduto(Produto produto) {
        salvarProduto(produto);
    }

    public void excluirProduto(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Produto p = em.find(Produto.class, id);
            if (p != null) {
                em.remove(p);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public int carregarProximoId() {
        return 0;
    }
}
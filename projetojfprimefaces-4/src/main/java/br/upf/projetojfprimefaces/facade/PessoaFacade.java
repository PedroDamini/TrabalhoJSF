package br.upf.projetojfprimefaces.facade;

import br.upf.projetojfprimefaces.entity.PessoaEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

//respondem apenas a uma chamada e logo depois podem ser 
@Stateless //utilizados para outras chamadas de qualquer cliente.            
public class PessoaFacade extends AbstractFacade<PessoaEntity> {

    /**
     * Definindo a unidade de persistencia
     */
    @PersistenceContext(unitName = "ProjetojfprimefacesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Construtor que passa para superclasse a instância de PessoaEntity
     */
    public PessoaFacade() {
        super(PessoaEntity.class);
    }

    private List<PessoaEntity> entityList;

    public List<PessoaEntity> buscarTodos() {
        entityList = new ArrayList<>();
        try {
            //utilizando JPQL para construir a query 
            Query query = getEntityManager().createQuery("SELECT p FROM PessoaEntity p order by p.nome");
            //verifica se existe algum resultado para não gerar excessão
            if (!query.getResultList().isEmpty()) {
                entityList = (List<PessoaEntity>) query.getResultList();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return entityList;
    }

    public PessoaEntity buscarUser(String user) {
        PessoaEntity entityList = null; // Inicializa como null
        try {
            // Utilizando JPQL para construir a query 
            Query query = getEntityManager().createQuery("SELECT p FROM PessoaEntity p WHERE p.email = :email");
            query.setParameter("email", user); // Define o parâmetro da consulta

            // Executa a consulta e atribui o resultado a entityList
            entityList = (PessoaEntity) query.getSingleResult();
        } catch (NoResultException e) {
            // Trata caso não haja resultado
            System.out.println("Nenhum usuário encontrado com o email fornecido.");
        } catch (Exception e) {
            // Trata outras exceções de forma genérica
            System.out.println("Erro ao buscar usuário: " + e.getMessage());
            e.printStackTrace(); // Imprime o stack trace para depuração
        }
        return entityList;
    }
}

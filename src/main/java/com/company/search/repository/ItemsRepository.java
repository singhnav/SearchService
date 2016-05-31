package com.company.search.repository;

import com.company.search.database.Item;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
public class ItemsRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Item item) {
        em.persist(item);
    }

    public List<Item> getAll() {
        return em.createQuery("SELECT s FROM Item s", Item.class).getResultList();
    }

    public List<Item> findByName(String name) {
        return em.createQuery("SELECT s FROM Item s WHERE name LIKE :name", Item.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();

    }

    public void deleteTable() {
        em.createQuery("DELETE FROM Item").executeUpdate();
    }
}

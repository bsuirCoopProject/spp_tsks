package dao.implementations;

import dao.GenericDao;
import dbException.DbException;
import entities.Category;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CategoryDao implements GenericDao<Category, Integer> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Category create(Category category) throws DbException {
        try {
            entityManager.persist(category);
            return category;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DbException("Exception while persisting new category");
        }
    }

    @Override
    public Category update(Category category) throws DbException {
        try {
            return entityManager.merge(category);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DbException("Exception while updating category with id = " + category.getId());
        }
    }

    @Override
    public void delete(Category category) throws DbException {
        try {
            entityManager.remove(category);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DbException("Exception while deleting category with id = " + category.getId());
        }
    }

    @Override
    public List<Category> getAll() throws DbException {
        try {
            return entityManager.createQuery("from Category c").getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DbException("Exception while getting list of all categories");
        }
    }

    @Override
    public Category getById(Integer id) throws DbException {
        try {
            return entityManager.find(Category.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DbException("Exception while getting category with id = " + id);
        }
    }
}

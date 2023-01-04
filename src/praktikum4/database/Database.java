package database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import menue.Action;
import model.Cast;

public class Database implements AutoCloseable {

    EntityManagerFactory emf;
    EntityManager em;

    public Database() {
        emf = Persistence.createEntityManagerFactory("DatenbankJPA");
    }

    public void add(EmptyModel model) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(model);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    public void update(EmptyModel model) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge(model);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    public void delete(Model model) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            model = em.find(model.getClass(), model.getId());

            em.remove(model);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    public void delete(Cast cast) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            cast = em.find(cast.getClass(), cast.getId());

            em.remove(cast);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    public void execute(Action action) {
        try {
            em = emf.createEntityManager();
            action.execute();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public void close() throws Exception {
        em.close();
        emf.close();
    }

    public EntityManager getEM() {
        return em;
    }

    public Model findByID(Model model) {

        try {
            em = emf.createEntityManager();
            model = em.find(model.getClass(), model.getId());
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }

        return model;
    }

    public CastModel findByID(CastModel cast) {

        try {
            em = emf.createEntityManager();
            cast = em.createNamedQuery("Cast.findById", CastModel.class).setParameter("pid", cast.getId().getPid())
                    .setParameter("mid", cast.getId().getMid()).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }

        return cast;
    }

}

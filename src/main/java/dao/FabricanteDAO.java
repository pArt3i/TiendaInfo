package dao;

import entity.Fabricante;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class FabricanteDAO {

    public Fabricante buscarPorId(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Fabricante.class, id);
        }
    }

    public Fabricante buscarPorNombre(String nombre) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Fabricante f WHERE f.nombre = :nombre",Fabricante.class).setParameter("nombre", nombre).uniqueResult();
        }
    }


    public List<Fabricante> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Fabricante> q = session.createQuery("FROM Fabricante", Fabricante.class);
            return q.getResultList();
        }
    }

    public void guardarFabricante(Fabricante fabricante) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(fabricante);
            tx.commit();
        }catch (Exception ex){
            if (tx!=null) tx.rollback();
        }
    }

    public void actualizarFabricante(Fabricante fabricante) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(fabricante);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
        }
    }

    public void borrarFabricante(Fabricante fabricante) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.remove(session.merge(fabricante));
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
        }
    }
}

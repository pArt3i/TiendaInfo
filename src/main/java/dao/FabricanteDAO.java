package dao;

import entity.Fabricante;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
                            "FROM Fabricante f WHERE LOWER(f.nombre) = LOWER(:nombre)", Fabricante.class)
                    .setParameter("nombre", nombre)
                    .uniqueResult();
        }
    }
    public Fabricante buscarPorNombreProducto(String nombreProducto) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT p.fabricante FROM Producto p WHERE LOWER(p.nombre) = LOWER(:prod)";
            return session.createQuery(hql, Fabricante.class).setParameter("prod", nombreProducto).uniqueResult();
        }
    }

    public List<Fabricante> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Fabricante", Fabricante.class).getResultList();
        }
    }

    public void guardarFabricante(Fabricante fabricante) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(fabricante);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
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
package dao;

import entity.Fabricante;
import entity.Producto;
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

    public Fabricante buscarPorNombreProducto(String nombreProducto) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String sql = "SELECT p.fabricante FROM Producto p WHERE p.nombre = :nombreProd";
            return session.createQuery(sql, Fabricante.class).setParameter("nombreProd", nombreProducto).uniqueResult();
        }
    }
    public List<Producto> obtenerProductosDeFabricante(String nombreFabricante) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT p FROM Producto p WHERE p.fabricante.nombre = :nombreFab";
            return session.createQuery(hql, Producto.class)
                    .setParameter("nombreFab", nombreFabricante)
                    .getResultList();
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

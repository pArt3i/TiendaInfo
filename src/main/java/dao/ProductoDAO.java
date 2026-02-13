package dao;

import entity.Fabricante;
import entity.Producto;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;
import java.util.List;

public class ProductoDAO {

    public Producto buscarPorIdProducto(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Producto.class, id);
        }
    }

    public List<Producto> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Producto", Producto.class).getResultList();
        }
    }

    // Nueva funcionalidad para listar productos de un fabricante concreto
    public List<Producto> buscarPorFabricante(String nombreFabricante) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Producto p WHERE LOWER(p.fabricante.nombre) = LOWER(:nom)", Producto.class).setParameter("nom", nombreFabricante).getResultList();
        }
    }

    public void guardarProductoJuntoFabricante(Producto nuevoProducto, String nombreFabricante) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Fabricante fabricante = session.createQuery("FROM Fabricante f WHERE LOWER(f.nombre) = LOWER(:nomb)", Fabricante.class).setParameter("nomb", nombreFabricante).uniqueResult();
            if (fabricante == null) {
                fabricante = new Fabricante(nombreFabricante);
                session.persist(fabricante);
            }
            nuevoProducto.setFabricante(fabricante);
            session.persist(nuevoProducto);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
        }
    }

    public void actualizarPrecio(int id, double nuevoPrecio) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Producto p = session.get(Producto.class, id);
            if (p != null) {
                p.setPrecio(nuevoPrecio);
                session.merge(p);
            }
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
        }
    }

    public void borrarProducto(Producto producto) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.remove(session.merge(producto));
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
        }
    }
}
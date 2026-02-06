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
//    public List<Producto> leerProductos(String nombreFabricante){
//        .getResultList();
//    }
    public void guardarProductoJuntoFabricante(Producto nuevoProducto, String nombreFabricante){
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String consulta = "Select * from Fabricante f where LOWER(f.nombre) = LOWER(:nomb)";
            List<Fabricante> fabricantes = session.createQuery(consulta, Fabricante.class).setParameter("nomb", nombreFabricante).getResultList();
            Fabricante fabricante;
            if(!fabricantes.isEmpty()){
                fabricante = fabricantes.get(0);
            }else {
                fabricante = new Fabricante(nombreFabricante);
                session.persist(fabricante);
            }
            nuevoProducto.setFabricante(fabricante);
            session.persist(nuevoProducto);
            tx.commit();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    public void actualizarProdcuto(Producto producto) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(producto);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
        }
    }

    public void actualizar(int id, double precio) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Producto producto = session.find(Producto.class, id);
            producto.setPrecio(precio);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
        }
    }

    public void guardarProducto(Producto producto) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(producto);
            tx.commit();
        }catch (Exception ex){
            if (tx!=null) tx.rollback();
        }
    }

    public void actualizarProducto(Producto producto) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(producto);
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

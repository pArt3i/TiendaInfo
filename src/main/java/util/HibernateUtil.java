package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.logging.Level; // Importación necesaria
import java.util.logging.Logger; // Importación necesaria

public class HibernateUtil {
    static {
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
    }

    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
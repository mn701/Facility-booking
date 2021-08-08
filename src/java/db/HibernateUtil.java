package db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Session;

public class HibernateUtil
{
    private static SessionFactory sessionFactory;
    public static void createSessionFactory()
    {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }
    public static Session getSession()
    {
        return sessionFactory.openSession();
    }
}

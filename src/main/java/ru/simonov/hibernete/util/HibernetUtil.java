package ru.simonov.hibernete.util;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernetUtil {
    public static SessionFactory bildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure();
        return configuration.buildSessionFactory();
    }
}

package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static SessionFactory sessionFactory;
    private static final String URL = "jdbc:mysql://localhost:3306/testdb?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static  Connection connection;
    static {
        Configuration configuration = new Configuration();
        Properties properties = new Properties();
        properties.put(Environment.DRIVER,"com.mysql.cj.jdbc.Driver");
        properties.put(Environment.URL, URL);
        properties.put(Environment.USER, USERNAME);
        properties.put(Environment.PASS,PASSWORD);
        //properties.put(Environment.SHOW_SQL, true);
        properties.put(Environment.DIALECT,"org.hibernate.dialect.MySQL8Dialect");
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        properties.put(Environment.FORMAT_SQL,true);
        properties.put(Environment.DEFAULT_SCHEMA,"testdb");

        configuration.setProperties(properties);
        configuration.addAnnotatedClass(User.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(properties).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);

    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;

    }


    public static void jdbcConnect(){
        try {
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if (connection==null){
            jdbcConnect();
        }
        return connection;

    }
// реализуйте настройку соеденения с БД
}

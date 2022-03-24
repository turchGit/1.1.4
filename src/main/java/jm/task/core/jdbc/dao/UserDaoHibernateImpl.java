package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        sessionFactory = Util.getSessionFactory();
    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS User (" +
                    "id INT NOT NULL AUTO_INCREMENT," +
                    " name VARCHAR(20)," +
                    " lastName VARCHAR(20)," +
                    " age INT," +
                    "PRIMARY KEY (id))";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
            System.out.println("Таблица создана");
        } catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS User";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
            System.out.println("Таблица удалена");
        } catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.save(new User(name,lastName,age));
            transaction.commit();
            System.out.println(String.format("Пользователь с именем %s добавлен в базу данных", name));
        } catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
            System.out.println("asfaf");
        }

    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            User user = session.get(User.class,id);
            session.delete(user);

            transaction.commit();
        } catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }


    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List <User> result = new ArrayList<>();
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            String hql = "FROM User";
            result = session.createQuery(hql,User.class).list();
            transaction.commit();
        }catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;

        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            String hql = "delete FROM User";
            Query query = session.createQuery(hql,User.class);
            query.executeUpdate();
            transaction.commit();
        }catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            System.out.println("Таблицы не существует");
        }
    }
}

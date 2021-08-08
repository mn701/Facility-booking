package db;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.User;
import util.SHA256Encoder;

public class UserHelper {

    public User login(String userId, String u_passwd) {
        Session session = HibernateUtil.getSession();
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            Query query = session.createQuery("from User u where u.id=:userId and u.passwd=:passwd");
            query.setString("userId", userId);
            query.setString("passwd", getEncodedPw(u_passwd));
            tx.commit();
            return (User) query.uniqueResult();
        } catch (ObjectNotFoundException ex) {
            return null;
        }
    }
    
    // Encode password
    private String getEncodedPw(String pw) {
        SHA256Encoder encoder = new SHA256Encoder();
        return encoder.encodePassword(pw);
    }
    
    // create new user
    public void addUser(User user){
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(user);
            System.out.println("User Record With Id: " + user.getId()); 
            transaction.commit();
        }
        catch (RuntimeException e) {
            if(transaction != null) transaction.rollback();
                throw e;
        }finally {
            if(session != null) {
//                session.close();
            }
        }
    }
    
    // list all users
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        Session session = HibernateUtil.getSession();
        List<User> userList = new ArrayList<>();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            userList = session.createCriteria(User.class).list();
            transaction.rollback();
        }catch (Exception e) {
            if(transaction != null) transaction.rollback();
            System.out.print("Error while fetching"+e);
        } finally {
            if(session != null) {
//                    session.close();
            }
        }
        return userList;
    }
	
    public User findUserById(String find_u_id) {
        Session session = HibernateUtil.getSession();
        User u = null;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            u = (User) session.load(User.class, find_u_id);
            transaction.rollback();
        } catch (ObjectNotFoundException ex) {
            if(transaction != null) transaction.rollback();
        }
//        session.close();
        return u;
    }
    
    // update existing user
    public void updateUser(String uid, String newPasswd, String newEmail, String newGroupId){
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            User user = (User) session.get(User.class, uid);
            user.setPasswd(newPasswd);
            user.setEmail(newEmail);
            user.setGroupid(newGroupId);
           
            session.update(user);
            transaction.commit();
        }
        catch (RuntimeException e) {
            if(transaction != null) transaction.rollback();
            throw e;
        }finally {
            if(session != null) {
//                session.close();
            }
        }
    }
    
    // delete existing user
    public void deleteUser(String uid){
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            User user = findUserById(uid);
            session.delete(user);
            transaction.commit();
        }
        catch (RuntimeException e) {
            if(transaction != null) transaction.rollback();
            throw e;
        }finally {
            if(session != null) {
//                session.close();
            }
        }
    }
}

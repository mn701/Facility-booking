package db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.*;

public class FacilityHelper {

    public void addFacility(Facility facility){
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(facility);
            transaction.commit();
        }
        catch (RuntimeException e) {
            if(transaction != null) transaction.rollback();
                throw e;
        }finally {
            if(session != null) {
                session.close();
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<Facility> getAllFacilities() {
        Session session = HibernateUtil.getSession();
        List<Facility> facilityList = new ArrayList<>();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            facilityList = session.createCriteria(Facility.class).list();
            transaction.rollback();
        }catch (Exception e) {
            if(transaction != null) transaction.rollback();
            System.out.print("Error while fetching"+e);
        } finally {
            if(session != null) {
                session.close();
            }
        }
        return facilityList;
    }
    
    public Facility findFacilityById(Long find_f_id) {
        Session session = HibernateUtil.getSession();
        Facility facility = null;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            facility= (Facility) session.load(Facility.class, find_f_id);
            transaction.rollback();
        } catch (ObjectNotFoundException ex) {
            if(transaction != null) transaction.rollback();
        }
        //close ->error session closed!
        // not close -> cannot delete facility
//        session.close();
        return facility;
    }
    
    public void updateFacility(Long fid, String newName, Integer newCapacity, Ftype newType, String newDescription, String newPicstr) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Facility facility = (Facility) session.get(Facility.class, fid);
            facility.setName(newName);
			facility.setCapacity(newCapacity);
			facility.setType(newType);
			facility.setDescription(newDescription);
			facility.setPicstr(newPicstr);

            session.update(facility);
            transaction.commit();
        }
        catch (RuntimeException e) {
            if(transaction != null) transaction.rollback();
            throw e;
        }finally {
            if(session != null) {
                session.close();
            }
        }
    }
    
    public void deleteFacility(Long fid){
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        try {
            // error nested transaction
            transaction = session.beginTransaction();
            Facility facility = (Facility) session.get(Facility.class, fid);
            session.delete(facility);
            transaction.commit();
        }
        catch (RuntimeException e) {
            if(transaction != null) transaction.rollback();
            throw e;
        }finally {
            if(session != null) {
                session.close();
            }
        }
    }
    
    // from FacilityManager
    public  List<Facility> getFromDb(int availItem, Ftype typeItem, Date qDate, Date qStart, Date qEnd){
        Session session = HibernateUtil.getSession();
        List<Facility> facilList = null;
        Query q = null;
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
                if(typeItem==Ftype.NONE ){
                    if(availItem==1){
                        q = session.createQuery("from Facility f ORDER BY f.name");
//                        
                    }else{
                        q = session.createQuery("from Facility f where f.id not in(select b.facility.id from Booking b where b.evdate =:qDate and b.startTime <:qEnd and b.endTime>:qStart and b.active = True) ORDER BY f.name");
                        q.setParameter("qDate", qDate);
                        q.setParameter("qEnd", qEnd);
                        q.setParameter("qStart", qStart);
                    }
                }else{
                    if(availItem==1){
                        q = session.createQuery("from Facility f where f.type=:valueOfType ORDER BY f.name");
                    }else{
                        q = session.createQuery("from Facility f where f.type=:valueOfType and f.id not in(select b.facility.id from Booking b where b.evdate =:qDate and b.startTime <:qEnd and b.endTime>:qStart and b.active = True) ORDER BY f.name");
                        q.setParameter("qDate", qDate);
                        q.setParameter("qEnd", qEnd);
                        q.setParameter("qStart", qStart);
                    }
                    q.setParameter("valueOfType", typeItem.name());
                }
                facilList = (List<Facility>) q.list();
                tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return facilList;
    }
}

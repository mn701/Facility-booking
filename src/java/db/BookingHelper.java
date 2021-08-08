package db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import oracle.jrockit.jfr.parser.ParseException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.*;


public class BookingHelper {
    // add new booking
    public void addBooking(Booking booking){
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(booking);
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
    
    // list all bookings
    @SuppressWarnings("unchecked")
    public List<Booking> getAllBookings() {
        Session session = HibernateUtil.getSession();
        List<Booking> bookingList = new ArrayList<>();
        try {
            session.beginTransaction();
            bookingList = session.createCriteria(Booking.class).list();
        }catch (Exception e) {
            System.out.print("Error while fetching"+e);
        } finally {
            if(session != null) {
                    session.close();
            }
        }
        return bookingList;
    }

    public Booking findBookingById(Long find_b_id) {
        Session session = HibernateUtil.getSession();
        Booking b = null;
        try {
            session.beginTransaction();
            b = (Booking) session.load(Booking.class, find_b_id);
        } catch (ObjectNotFoundException ex) {
        }
//        session.close();
        return b;
    }
    
    // update existing booking
    public void updateBooking(Long bid, User newUser, Facility newFacility, Date newEvdate, Date newStartTime, Date newEndTime, Date newBookedDate, String newRemarks, String newEquipment, boolean newActive){
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Booking booking = (Booking) session.get(Booking.class, bid);
            booking.setUser(newUser);
            booking.setFacility(newFacility);
            booking.setEvdate(newEvdate);
            booking.setStartTime(newStartTime);
            booking.setEndTime(newEndTime);
            booking.setBookedDate(newBookedDate);
            booking.setRemarks(newRemarks);
            booking.setEquipment(newEquipment);
            booking.setActive(newActive);
            
            session.update(booking);
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
    
    // delete existing booking
    public void deleteBooking(Long bid){
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Booking booking = findBookingById(bid);
            session.delete(booking);
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
    
//from BookingManager
    public  List<Booking> getHistory(String id){
        Session session = HibernateUtil.getSession();
        List<Booking> bookingList = null;
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            Query query = session.createQuery("from Booking b where b.user.id=:userId and b.evdate <= CURRENT_DATE ORDER BY b.evdate DESC");
            query.setParameter("userId", id);
            bookingList = (List<Booking>) query.list();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookingList;
    }
    
//  
    public  List<Booking> getUserCurBookings(String uid){
        Session session = HibernateUtil.getSession();
        List<Booking> bookingList = null;
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            Query query = session.createQuery("from Booking b where b.user.id=:userId and b.evdate > CURRENT_DATE ORDER BY b.evdate");
            query.setParameter("userId", uid);
            bookingList = (List<Booking>) query.list();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookingList;
    }
    
//    @NamedQuery(name=Booking.DuplicateCheck, query="select c "),
    public List<Booking> checkDuplicates(long fid, Date qDate, Date from, Date to){
        Session session = HibernateUtil.getSession();
        List<Booking> bookingList = null;
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            Query query = session.createQuery("from Booking b where b.facility.id=:fclId and b.evdate =:qDate and b.startTime <:qEnd and b.endTime>:qStart and b.active = True");
            query.setParameter("fclId", fid);
            query.setParameter("qDate", qDate);
            query.setParameter("qEnd", to);
            query.setParameter("qStart", from);
            bookingList = (List<Booking>) query.list();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookingList;
    }
    
    //query="select c "),
    public List<Booking> getSameDayBookings(long fid, Date qDate){
        Session session = HibernateUtil.getSession();
        List<Booking> bookingList = null;
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            Query query = session.createQuery("from Booking b where b.facility.id=:fclId and b.evdate =:qDate ORDER BY b.startTime");
            query.setParameter("fclId", fid);
            query.setParameter("qDate", qDate);
            bookingList = (List<Booking>) query.list();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookingList;
    }
    
    
    //For admin report - facility usage - total time
    public List<Object[]> getFacilityReport(Date qDateFrom, Date qDateTo) throws ParseException{
        Session session = HibernateUtil.getSession();
        List<Object[]> result  = null;
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            Query q = session.createSQLQuery("select b.facility_id, f.name, cast(sec_to_time(SUM(time_to_sec(timediff(b.endTime, b.startTime)))) as CHAR) from booking b, facility f "
                  + "where b.facility_id = f.id and b.active = True and b.evdate >= ? and b.evdate <= ?"
                  + " group by b.facility_id " 
                  + "order by b.facility_id");
            /*
                Query q = em.createNativeQuery("select b.facility_id, cast(TIME(SUM(timediff(b.endTime, b.startTime))) as CHAR) from ip1_booking b "
                  //  + "where b.evdate >= ? and b.evdate <= ? "
                    + "GROUP BY b.facility_id "
                    + "order by b.facility_id");
            */
            q.setParameter(0, qDateFrom);
            q.setParameter(1, qDateTo);
            result = q.list();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
            
    //For admin report - facility usage by user - total time
    public List<Object[]> getUserReport(Date qDateFrom, Date qDateTo) throws ParseException{
        Session session = HibernateUtil.getSession();
        List<Object[]> result  = null;
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            Query q = session.createSQLQuery("select b.user_id, cast(sec_to_time(SUM(time_to_sec(timediff(b.endTime, b.startTime)))) as CHAR) from booking b "
                  + "where b.active = True and b.evdate >= ? and b.evdate <= ?"
                  + " group by b.user_id " 
                  + "order by b.user_id"); 
            q.setParameter(0, qDateFrom);
            q.setParameter(1, qDateTo);
            result = q.list();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    //For admin report - facility usage for each facility id
    public List<Booking> getFacilityReportByFid(Long fid, Date qDateFrom, Date qDateTo) throws ParseException{
        Session session = HibernateUtil.getSession();
        List<Booking> bookingList = null;
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            Query query = session.createQuery("from Booking b where b.facility.id=:fclId and b.evdate >= :qDateFrom and b.evdate <= :qDateTo ORDER BY b.evdate, b.user.id");
            query.setParameter("fclId", fid);
            query.setParameter("qDateFrom", qDateFrom);
            query.setParameter("qDateTo", qDateTo);
            bookingList = (List<Booking>) query.list();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookingList;
    }
        
    //For admin report - facility usage for each user
    public List<Booking> getFacilityReportByUserId(String uid, Date qDateFrom, Date qDateTo) throws ParseException{
        Session session = HibernateUtil.getSession();
        List<Booking> bookingList = null;
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            Query query = session.createQuery("from Booking b where b.user.id=:usrId and b.evdate >= :qDateFrom and b.evdate <= :qDateTo ORDER BY b.evdate, b.facility.id");
            query.setParameter("usrId", uid);
            query.setParameter("qDateFrom", qDateFrom);
            query.setParameter("qDateTo", qDateTo);
            bookingList = (List<Booking>) query.list();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookingList;
    }

}

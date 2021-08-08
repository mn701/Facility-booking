package action;

import static action.AvailabilityCheckAction.OPERATING_HOURS_FROM;
import static action.AvailabilityCheckAction.OPERATING_HOURS_TO;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import db.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import pojo.*;

public class BookingAction extends ActionSupport implements SessionAware{
    private Map<String, Object> session;
    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
    public Map<String, Object> getSession() {
        return session;
    }
    
    //For DB
    private BookingHelper bookingHelper = new BookingHelper();
    private UserHelper userHelper = new UserHelper();
    private FacilityHelper facilityHelper = new FacilityHelper();

    //For booking
    private User b_user;
    private Facility b_facility;
    private Date b_evdate;
    private Date b_startTime;
    private Date b_endTime;
    private Date b_bookedDate;
    private String b_remarks;
    private String b_equipment;
    private Boolean b_active;
    
    private String b_startTimeStr;
    private String b_endTimeStr;
    
    private User bookinguser;
    private Long bookingid;
    private String bookingFailMessage = "";

    public String beforeBooking() throws ParseException
    {
        if(session != null && session.get("curUser") != null){
            bookinguser = (User)session.get("curUser");
        }else{
            // Logged-in user and booking user should be different, guest user should not access history of other guests
            bookinguser = (User) userHelper.findUserById("guest");
        }
        
        return SUCCESS;
    }

    public String createBooking() throws ParseException {
        if(session != null && session.get("curUser") != null){
            b_user = (User)session.get("curUser");
        }else{
            // Logged-in user and booking user should be different, guest user should not access history of other guests
            b_user = (User) userHelper.findUserById("guest");
        }

        b_bookedDate = new Date();
        b_startTime = getTimeFromStr(b_startTimeStr);
        b_endTime = getTimeFromStr(b_endTimeStr);

        if(isAvailable()){
            try{
               Booking booking = new Booking(getB_user(), getB_facility(),getB_evdate(), getB_startTime(), getB_endTime(),getB_bookedDate(), getB_remarks(), getB_equipment(), true);
               bookingHelper.addBooking(booking);
               System.out.println("adding booking " +booking.getId());
            }catch(Exception e){
            }
            clear();
            return SUCCESS;
        }else{
            clear();
            bookingFailMessage = "Facility Not available. Try another day/time/facility";
            return ERROR;
        }
    }
    
     public String cancelBooking() {
        Booking booking = bookingHelper.findBookingById(bookingid);

        b_user = booking.getUser();
        b_facility = booking.getFacility();
        b_evdate = booking.getEvdate();
        b_startTime = booking.getStartTime();
        b_endTime = booking.getEndTime();
        b_bookedDate = booking.getBookedDate();
        b_equipment = booking.getEquipment();
        b_remarks = booking.getRemarks();
        
        try {
            bookingHelper.updateBooking(bookingid, b_user, b_facility, b_evdate, b_startTime, b_endTime, b_bookedDate, b_remarks, b_equipment, false);
            clear();
        } catch (Exception e) {
        }
        

        return SUCCESS;
    }
     
    public void clear() {
        b_facility = null;
        b_evdate = b_startTime = b_endTime = b_bookedDate = null;
        b_equipment = b_remarks  = null;
        b_active = false;
    }
    
    public Date getTimeFromStr(String tstr){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date tm = null;
        try {
            if(tstr != null){
                tm = formatter.parse(tstr);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return tm;
    
    }
        
    public boolean isAvailable() throws ParseException {
        boolean res = true;
        if(b_evdate != null){
            if(checkDateInPast()){
                
                res = false;
            }
            if(checkUnreservableDay()){
                
                res = false;
            }
            if(b_startTime != null && b_endTime != null){
                if(checkStartAfterEnd()){
                    
                    res = false;
                }
                if(checkUnreservableHour()){
                    
                    res = false;
                }
                if(checkDuplicates()){
                    
                    res = false;
                }
                if(checkDateIsToday()){
                    if(checkTimeInPast()){
                        
                        res = false;
                    }
                }
            }
        }  
        return res;
    }

     //Check if there are overlapped bookings
    public boolean checkDuplicates() {
        List<Booking> dupbookings = new ArrayList<>();
        try {
            dupbookings = bookingHelper.checkDuplicates(b_facility.getId(), b_evdate, b_startTime, b_endTime);
        } catch (Exception e) {
            //facesMessage("No other bookings found for the day and time");
        }
        return dupbookings.size() > 0;
    }
    
    public boolean checkUnreservableHour() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return (b_startTime.getTime() < format.parse(OPERATING_HOURS_FROM).getTime()) ||
                (b_endTime.getTime() > format.parse(OPERATING_HOURS_TO).getTime());
    }
    
    public boolean checkUnreservableDay() throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("E");
        if(b_evdate != null){
            return (fmt.format(b_evdate).equals("Sat")) ||(fmt.format(b_evdate).equals("Sun")) ;
        }
        return false;
    }
    
    public boolean checkDateInPast() throws ParseException {
        // using LocalDate 
        LocalDate ldtoday = LocalDate.now();
        LocalDate ldev = b_evdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return (ldev.isBefore(ldtoday));
    }
    
    public boolean checkTimeInPast() throws ParseException {
        // using  LocalTime only for today
        LocalTime ltnow = LocalTime.now();
        if(b_startTime != null && checkDateIsToday()){
            LocalTime ltstart = b_startTime.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
            return ltstart.isBefore(ltnow);
        }else{
            return false;
        }
    }
    
    public boolean checkDateIsToday() throws ParseException {
        LocalDate ltoday = LocalDate.now();
        LocalDate levday = b_evdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return (levday.equals(ltoday));
    }
    
    public boolean checkStartAfterEnd() throws ParseException {
        if(b_startTime != null && b_endTime != null){
            return (b_startTime.after(b_endTime));
        }else{
            return false;
        }
    }
    
    public User getB_user() {
        return b_user;
    }

    public void setB_user(User b_user) {
        this.b_user = b_user;
    }

    public Facility getB_facility() {
        return b_facility;
    }

    public void setB_facility(Facility b_facility) {
        this.b_facility = b_facility;
    }

    public Date getB_evdate() {
        return b_evdate;
    }

    public void setB_evdate(Date b_evdate) {
        this.b_evdate = b_evdate;
    }

    public Date getB_startTime() {
        return b_startTime;
    }

    public void setB_startTime(Date b_startTime) {
        this.b_startTime = b_startTime;
    }

    public Date getB_endTime() {
        return b_endTime;
    }

    public void setB_endTime(Date b_endTime) {
        this.b_endTime = b_endTime;
    }

    public Date getB_bookedDate() {
        return b_bookedDate;
    }

    public void setB_bookedDate(Date b_bookedDate) {
        this.b_bookedDate = b_bookedDate;
    }

    public String getB_remarks() {
        return b_remarks;
    }

    public void setB_remarks(String b_remarks) {
        this.b_remarks = b_remarks;
    }

    public String getB_equipment() {
        return b_equipment;
    }

    public void setB_equipment(String b_equipment) {
        this.b_equipment = b_equipment;
    }

    public Boolean getB_active() {
        return b_active;
    }

    public void setB_active(Boolean b_active) {
        this.b_active = b_active;
    }

    public String getB_startTimeStr() {
        return b_startTimeStr;
    }

    public void setB_startTimeStr(String b_startTimeStr) {
        this.b_startTimeStr = b_startTimeStr;
    }

    public String getB_endTimeStr() {
        return b_endTimeStr;
    }

    public void setB_endTimeStr(String b_endTimeStr) {
        this.b_endTimeStr = b_endTimeStr;
    }

    public String getBookingFailMessage() {
        return bookingFailMessage;
    }

    public void setBookingFailMessage(String bookingFailMessage) {
        this.bookingFailMessage = bookingFailMessage;
    }

   

}

package action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import db.*;
import java.text.DateFormat;
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

// code looks awkward because copy&pasted from Bb
public class AvailabilityCheckAction extends ActionSupport implements SessionAware{
    static final String OPERATING_HOURS_FROM = "08:00";
    static final String OPERATING_HOURS_TO = "18:00";
    
    // for storing session attributes
    private Map<String, Object> session;
    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
    public Map<String, Object> getSession() {
        return session;
    }
    
    //For availability check
    private Facility sel;
    private Date selDate;
    private Date selSTime; 
    private Date selETime;
    private String selSTimeStr;
    private String selETimeStr; 
    private String messages = "";
    private List<Booking> sameDayBookingsList;
    private List<Slot> slot;
    boolean res;
    private String picstr;
    
    //for DB operations
    private BookingHelper bookingHelper = new BookingHelper();
    private UserHelper userHelper = new UserHelper();
    private FacilityHelper facilityHelper = new FacilityHelper();

    @Override
    public String execute() throws ParseException
    {
        if(session != null && session.get("selectedFacility") != null){
            sel = (Facility)session.get("selectedFacility");
        }
        
        // converting from string to time(HH:mm) as textfield cannot handle time
        if(selSTimeStr != null){
            selSTime = getTimeFromStr(selSTimeStr);      
        }
        if(selETimeStr != null){
                selETime = getTimeFromStr(selETimeStr);
            }
       
        picstr = sel.getPicstr();
        
        //used in booking form
        session.put("bookingdate", selDate);
        session.put("bookingstimestr", getSelSTimeStr());
        session.put("bookingetimestr", getSelETimeStr());
        
        sameDayBookingsList = bookingHelper.getSameDayBookings(sel.getId(), selDate);
        slot = getSlots();

        res = true;
        if(selDate != null){
            if(checkDateInPast()){
                messages += ("Date must be in the future!\n");
                res = false;
            }
            if(checkUnreservableDay()){
                messages += ("Room is not available for booking on weekends!\n");
                res = false;
            }
            if(selSTime != null && selETime != null){
                if(checkStartAfterEnd()){
                    messages +=("Start time must be before end time!\n");
                    res = false;
                }
                if(checkUnreservableHour()){
                    messages +=("Room is available from " + OPERATING_HOURS_FROM + " to " + OPERATING_HOURS_TO + " only!\n");
                    res = false;
                }
                if(checkDuplicates()){
                    messages +=("The room is occupied. Try another date/time!\n");
                    res = false;
                }
                if(checkDateIsToday()){
                    if(checkTimeInPast()){
                        messages +=("Start Time must be in the future!\n");
                        res = false;
                    }
                }
            }
        }  
        if(res){
            messages = "Facility is available for booking";
            return SUCCESS;
        }else{
            return ERROR;
        }
        
    }

    //Check if there are overlapped bookings
    public boolean checkDuplicates() {
        List<Booking> dupbookings = new ArrayList<>();
        try {
            dupbookings = bookingHelper.checkDuplicates(sel.getId(), selDate, selSTime, selETime);
        } catch (Exception e) {
            //facesMessage("No other bookings found for the day and time");
        }
        return dupbookings.size() > 0;
    }
    
    public boolean checkUnreservableHour() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return (selSTime.getTime() < format.parse(OPERATING_HOURS_FROM).getTime()) ||
                (selETime.getTime() > format.parse(OPERATING_HOURS_TO).getTime());
    }
    
    public boolean checkUnreservableDay() throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("E");
        if(selDate != null){
            return (fmt.format(selDate).equals("Sat")) ||(fmt.format(selDate).equals("Sun")) ;
        }
        return false;
    }
    
    public boolean checkDateInPast() throws ParseException {
        // using LocalDate 
        LocalDate ldtoday = LocalDate.now();
        LocalDate ldev = selDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return (ldev.isBefore(ldtoday));
    }
    
    public boolean checkTimeInPast() throws ParseException {
        // using  LocalTime only for today
        LocalTime ltnow = LocalTime.now();
        if(selSTime != null && checkDateIsToday()){
            LocalTime ltstart = selSTime.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
            return ltstart.isBefore(ltnow);
        }else{
            return false;
        }
    }
    
    public boolean checkDateIsToday() throws ParseException {
        LocalDate ltoday = LocalDate.now();
        LocalDate levday = selDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return (levday.equals(ltoday));
    }
    
    public boolean checkStartAfterEnd() throws ParseException {
        if(selSTime != null && selETime != null){
            return (selSTime.after(selETime));
        }else{
            return false;
        }
    }
    
    //for content-2 bottom availability for each time slot - display purpose only
    public List<Slot> getSlots() throws ParseException{
        List<Booking> bookings;
        List<Slot> slot = new ArrayList<>();
        DateFormat dateTimeFormat = new SimpleDateFormat("HH:mm");
        Date dt = new Date();
        
        try {
            dt = dateTimeFormat.parse(OPERATING_HOURS_FROM);
        } catch (ParseException ex) {
        }
        
        LocalTime ltnow = LocalTime.now();
        
        for(int i = 0;i<11;i++){
            Date nt = new Date(dt.getTime() + (1000 * 60 * 60) * i);
            Date nnt = new Date(nt.getTime() + (1000 * 60 * 60));
            bookings = bookingHelper.checkDuplicates(sel.getId(), selDate, nt, nnt);
            LocalTime ltnt = nt.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
            
            if(checkUnreservableDay()){
                slot.add(new Slot(nt,"ooh"));
            }else if(bookings.size() > 0){
                slot.add(new Slot(nt,"occupied"));
            }else if(checkDateInPast()){
                slot.add(new Slot(nt,"past"));
            }else if(checkDateIsToday() && ltnt.isBefore(ltnow) ){
                slot.add(new Slot(nt,"past"));
            }else{
                slot.add(new Slot(nt,"available"));
            }
        }
        return slot;
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
    
    public Facility getSel() {
        return sel;
    }

    public void setSel(Facility sel) {
        this.sel = sel;
    }

    public Date getSelDate() {
        return selDate;
    }

    public void setSelDate(Date selDate) {
        this.selDate = selDate;
    }

    public Date getSelSTime() {
        return selSTime;
    }

    public void setSelSTime(Date selSTime) {
        this.selSTime = selSTime;
    }

    public Date getSelETime() {
        return selETime;
    }

    public void setSelETime(Date selETime) {
        this.selETime = selETime;
    }

    public String getSelSTimeStr() {
        return selSTimeStr;
    }

    public void setSelSTimeStr(String selSTimeStr) {
        this.selSTimeStr = selSTimeStr;
    }

    public String getSelETimeStr() {
        return selETimeStr;
    }

    public void setSelETimeStr(String selETimeStr) {
        this.selETimeStr = selETimeStr;
    }

    public List<Booking> getSameDayBookingsList() {
        return sameDayBookingsList;
    }

    public void setSameDayBookingsList(List<Booking> sameDayBookingsList) {
        this.sameDayBookingsList = sameDayBookingsList;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public List<Slot> getSlot() {
        return slot;
    }

    public void setSlot(List<Slot> slot) {
        this.slot = slot;
    }

    public boolean isRes() {
        return res;
    }

    public void setRes(boolean res) {
        this.res = res;
    }

    public String getPicstr() {
        return picstr;
    }

    public void setPicstr(String picstr) {
        this.picstr = picstr;
    }



}

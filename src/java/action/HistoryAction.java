package action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import db.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import pojo.*;


public class HistoryAction extends ActionSupport implements SessionAware{
    private List<Booking> userHistList;
    private List<Booking> userCurList;
    protected User curUser;
    
    //for DB operations
    private BookingHelper bookingHelper = new BookingHelper();
    private UserHelper userHelper = new UserHelper();
    private FacilityHelper facilityHelper = new FacilityHelper();
    
    private Map<String, Object> httpSession;
    @Override
    public void setSession(Map<String, Object> session) {
        this.httpSession = session;
    }
    public Map<String, Object> getSession() {
        return httpSession;
    }

    @Override
    public String execute() throws ParseException{
        Map<String, Object> session = getSession();
        if (session != null) {
            curUser = (User)session.get("curUser");
        }
        if(curUser != null){
            try {
                userHistList = bookingHelper.getHistory(getCurUser().getId());
                userCurList = bookingHelper.getUserCurBookings(getCurUser().getId());
            } catch(Exception e){
            }
        }
        return SUCCESS;
   
    }
    

 // Booking History
    public List<Booking> getHistory() {
        List<Booking> history = new ArrayList<>();
        try {
            history = bookingHelper.getHistory(getCurUser().getId());
        } catch (Exception e) {
        }
        return history;
    }
    
    //User's current(future) bookings
    public List<Booking> getCurrentBookings() {
        List<Booking> curbookings = new ArrayList<>();
        try {
            curbookings = bookingHelper.getUserCurBookings(getCurUser().getId());
        } catch (Exception e) {
           
        }
        return curbookings;
    }

    public List<Booking> getUserHistList() {
        return userHistList;
    }

    public void setUserHistList(List<Booking> userHistList) {
        this.userHistList = userHistList;
    }

    public List<Booking> getUserCurList() {
        return userCurList;
    }

    public void setUserCurList(List<Booking> userCurList) {
        this.userCurList = userCurList;
    }

    public User getCurUser() {
        return curUser;
    }

    public void setCurUser(User curUser) {
        this.curUser = curUser;
    }

    

}

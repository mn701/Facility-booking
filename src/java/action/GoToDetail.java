package action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import db.*;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import pojo.*;


public class GoToDetail extends ActionSupport implements SessionAware{
    private Map<String, Object> session;
    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
    public Map<String, Object> getSession() {
        return session;
    }
    
    private Long facilityId;
    //For availability check
    private Facility sel;
    private String picstr;
    private Date selDate;

    private List<Booking> sameDayBookingsList;
    
    
    //for DB operations
    private BookingHelper bookingHelper = new BookingHelper();
    private UserHelper userHelper = new UserHelper();
    private FacilityHelper facilityHelper = new FacilityHelper();

    @Override
    public String execute() throws ParseException
    {
        if(session != null && session.get("bookingdate") != null){
            selDate = (Date)session.get("bookingdate");
        }else{
            selDate = new Date(System.currentTimeMillis() + 24 * 3600 * 1000);
        }
        if(facilityId != null){
            sel = facilityHelper.findFacilityById(facilityId);
            session.put("selectedFacility", sel);
            sameDayBookingsList = bookingHelper.getSameDayBookings(facilityId, selDate);
            picstr = sel.getPicstr();
        }
   
        return SUCCESS;
    }

    public Long getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Long facilityId) {
        this.facilityId = facilityId;
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

    public List<Booking> getSameDayBookingsList() {
        return sameDayBookingsList;
    }

    public void setSameDayBookingsList(List<Booking> sameDayBookingsList) {
        this.sameDayBookingsList = sameDayBookingsList;
    }

    public String getPicstr() {
        return picstr;
    }

    public void setPicstr(String picstr) {
        this.picstr = picstr;
    }


}

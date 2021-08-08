package action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import db.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import pojo.*;


public class DisplayAction2 extends ActionSupport implements SessionAware{
    private Map<String, Object> session;
    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
    public Map<String, Object> getSession() {
        return session;
    }

    //variables for sorting
    private Map<String, Ftype> typeItems;
    private Map<String, Integer> availItems;
    private Ftype typeItem = Ftype.NONE;
    private Integer availItem = 1;
    
    private String faciTypeStr;
    private String availItemStr;
    
    //For booking
    private Facility sel;
    private Date selDate;
    private Date selSTime; 
    private Date selETime;
    
    private String selSTimeStr;
    private String selETimeStr;
    
    private User curUser;
    private List<Facility> facilities;
    
    //for DB operations
    private BookingHelper bookingHelper = new BookingHelper();
    private UserHelper userHelper = new UserHelper();
    private FacilityHelper facilityHelper = new FacilityHelper();
    

    @Override
    public String execute() throws ParseException
    {
        setTypeItem(getFtypeFromStr(getFaciTypeStr()));
        
        if(null == getAvailItemStr() || getAvailItemStr().equals("Show All")){
            setAvailItem(1);
        }else {
            setAvailItem(2);
        }
        
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        try {
            if(selSTimeStr != null){
                selSTime = formatter.parse(selSTimeStr);
            }
            if(selETimeStr != null){
                selETime = formatter.parse(selETimeStr);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        session.put("bookingdate", selDate);
        session.put("bookingstimestr", getSelSTimeStr());
        session.put("bookingetimestr", getSelETimeStr());
        
        try {
            facilities = facilityHelper.getFromDb(availItem, typeItem, selDate, selSTime, selETime);
        } catch(Exception e)
        {
        }
        return SUCCESS;
    }
    
     //converting string to Ftype(enum)
    public Ftype getFtypeFromStr(String ftstr){
        if(null == ftstr){
            return Ftype.NONE;
        }else switch (ftstr) {
            case "Meeting Rooms":
                return Ftype.MEETING;
            case "Conference Rooms":
                return Ftype.CONFERENCE;
            case "Discussion Rooms":
                return Ftype.DISCUSSION;
            default:
                return Ftype.NONE;
        }
    }

    public Map<String, Ftype> getTypeItems() {
        return typeItems;
    }

    public void setTypeItems(Map<String, Ftype> typeItems) {
        this.typeItems = typeItems;
    }

    public Map<String, Integer> getAvailItems() {
        return availItems;
    }

    public void setAvailItems(Map<String, Integer> availItems) {
        this.availItems = availItems;
    }

    public Ftype getTypeItem() {
        return typeItem;
    }

    public void setTypeItem(Ftype typeItem) {
        this.typeItem = typeItem;
    }

    public Integer getAvailItem() {
        return availItem;
    }

    public void setAvailItem(Integer availItem) {
        this.availItem = availItem;
    }

    public String getFaciTypeStr() {
        return faciTypeStr;
    }

    public void setFaciTypeStr(String faciTypeStr) {
        this.faciTypeStr = faciTypeStr;
    }

    public String getAvailItemStr() {
        return availItemStr;
    }

    public void setAvailItemStr(String availItemStr) {
        this.availItemStr = availItemStr;
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

    public User getCurUser() {
        return curUser;
    }

    public void setCurUser(User curUser) {
        this.curUser = curUser;
    }

    public List<Facility> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<Facility> facilities) {
        this.facilities = facilities;
    }


    

    

}

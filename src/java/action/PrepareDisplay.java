package action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import db.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import pojo.*;


public class PrepareDisplay extends ActionSupport
{
    //variables for sorting
    protected Map<String, Ftype> typeItems;
    protected Map<String, Integer> availItems;
    protected Ftype typeItem = Ftype.NONE;
    protected Integer availItem = 1;
    
    private List<Facility> facilities;
    private User curUser;

    //for DB operations
    private BookingHelper bookingHelper = new BookingHelper();
    private UserHelper userHelper = new UserHelper();
    private FacilityHelper facilityHelper = new FacilityHelper();
    
    public PrepareDisplay(){
        typeItems = new LinkedHashMap<>();
        typeItems.put("All", Ftype.NONE);
        typeItems.put("Meeting Rooms", Ftype.MEETING);
        typeItems.put("Conference Rooms", Ftype.CONFERENCE);
        typeItems.put("Discussion Rooms", Ftype.DISCUSSION);
        
        availItems = new LinkedHashMap<>();
        availItems.put("Show All", 1);
        availItems.put("Show Only Available", 2);
    }

    @Override
    public String execute() {
        try {
            facilities = facilityHelper.getFromDb(1, Ftype.NONE, null, null, null);
        } catch(Exception e){
        }
        return SUCCESS;
    }
    public String display() {
        return NONE;
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

    public List<Facility> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<Facility> facilities) {
        this.facilities = facilities;
    }

    public User getCurUser() {
        return curUser;
    }

    public void setCurUser(User curUser) {
        this.curUser = curUser;
    }

    

}

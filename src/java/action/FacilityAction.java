package action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import db.*;
import java.text.ParseException;
import java.util.List;
import pojo.*;


public class FacilityAction extends ActionSupport
{
    //for DB operations
    private FacilityHelper facilityHelper = new FacilityHelper();
    private List<Facility> facilList;
    private Long f_id;
    private String f_name;
    private Integer f_capacity;
    private Ftype f_type;
    private String f_description;
    private String f_picstr;
    
    private Long facilityid;
    private Facility facilityToUpdate;
    
    // using string because radio cannot handle enum type
    private String f_typestr;
    private String ftypestr_before;

    // list all facilities
    public String list() {
        try {
            facilList = facilityHelper.getAllFacilities();
        } catch(Exception e){
        }
        return SUCCESS;
    }
    
    // create new facility
     public String createFacility() throws ParseException
    {
        setF_type(getFtypeFromStr(getF_typestr()));
        try {
            Facility f = new Facility(f_name, f_capacity, f_type, f_description, "resources/images/L01.jpg", null);
            facilityHelper.addFacility(f);
            facilList = facilityHelper.getAllFacilities();
            clear();
        } catch (Exception e) {
        }
        return SUCCESS;
    }
    
     // delete  facility
     public String deleteFacility() throws ParseException
    {
        try {
            System.out.println("deleting"+facilityid);
            facilityHelper.deleteFacility(facilityid);
            facilList = facilityHelper.getAllFacilities();
        } catch (Exception e) {
        }
        return SUCCESS;
    }
    
    // before go to update page
     public String prepareUpdateFacility() throws ParseException
    {
        try {
            facilityToUpdate = facilityHelper.findFacilityById(facilityid);
            //passing ftype string before update
            ftypestr_before = facilityToUpdate.getType().toString();
        } catch (Exception e) {
        }
        return SUCCESS;
    }
    
    // update existing facility
     public String updateFacility() throws ParseException
    {
        if(null != getF_typestr()){
            setF_type(getFtypeFromStr(getF_typestr()));
        }
        
        try {
            facilityHelper.updateFacility(f_id, f_name, f_capacity, f_type, f_description, f_picstr);
            facilList = facilityHelper.getAllFacilities();
            clear();
        } catch (Exception e) {
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
    

    public void clear() {
        f_name =  f_description = null;
        f_capacity = null;
        f_type = Ftype.NONE;
    }

    public List<Facility> getFacilList() {
        return facilList;
    }

    public void setFacilList(List<Facility> facilList) {
        this.facilList = facilList;
    }

    public Long getF_id() {
        return f_id;
    }

    public void setF_id(Long f_id) {
        this.f_id = f_id;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public Integer getF_capacity() {
        return f_capacity;
    }

    public void setF_capacity(Integer f_capacity) {
        this.f_capacity = f_capacity;
    }

    public Ftype getF_type() {
        return f_type;
    }

    public void setF_type(Ftype f_type) {
        this.f_type = f_type;
    }

    public String getF_description() {
        return f_description;
    }

    public void setF_description(String f_description) {
        this.f_description = f_description;
    }

    public String getF_picstr() {
        return f_picstr;
    }

    public void setF_picstr(String f_picstr) {
        this.f_picstr = f_picstr;
    }

    public String getF_typestr() {
        return f_typestr;
    }

    public void setF_typestr(String f_typestr) {
        this.f_typestr = f_typestr;
    }

    public Long getFacilityid() {
        return facilityid;
    }

    public void setFacilityid(Long facilityid) {
        this.facilityid = facilityid;
    }

    public Facility getFacilityToUpdate() {
        return facilityToUpdate;
    }

    public void setFacilityToUpdate(Facility facilityToUpdate) {
        this.facilityToUpdate = facilityToUpdate;
    }

    public String getFtypestr_before() {
        return ftypestr_before;
    }

    public void setFtypestr_before(String ftypestr_before) {
        this.ftypestr_before = ftypestr_before;
    }
    
}

package action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import db.*;
import java.util.Date;
import java.util.List;
import pojo.*;


public class ReportAction extends ActionSupport{
    //For DB
    private BookingHelper bookingHelper = new BookingHelper();
    private FacilityHelper facilityHelper = new FacilityHelper();
    private Date reportFrom; 
    private Date reportTo;
    private List<Object[]> rptFacilTotal;
    private List<Object[]> rptUserTotal;
    private Long rptFid;
    private String rptUid;
    private String rptFName;
    private List<Booking> rptFacilIndv;
    private List<Booking> rptUserIndv;
    

    public String reportTotal() {
        try {
            rptFacilTotal = bookingHelper.getFacilityReport(reportFrom, reportTo);
            rptUserTotal = bookingHelper.getUserReport(reportFrom, reportTo);
        } catch(Exception e){
        }
        return SUCCESS;
    }

    public String reportFacilityEach() {
        try {
            rptFacilIndv  = bookingHelper.getFacilityReportByFid(rptFid, reportFrom, reportTo);
            rptFName = facilityHelper.findFacilityById(rptFid).getName();
        } catch(Exception e){
        }
        return SUCCESS;
    }
    
     public String reportUserEach() {
        try {
            rptUserIndv = bookingHelper.getFacilityReportByUserId(rptUid, reportFrom, reportTo);
        } catch(Exception e){
        }
        return SUCCESS;
    }

    public Date getReportFrom() {
        return reportFrom;
    }

    public void setReportFrom(Date reportFrom) {
        this.reportFrom = reportFrom;
    }

    public Date getReportTo() {
        return reportTo;
    }

    public void setReportTo(Date reportTo) {
        this.reportTo = reportTo;
    }

    public List<Object[]> getRptFacilTotal() {
        return rptFacilTotal;
    }

    public void setRptFacilTotal(List<Object[]> rptFacilTotal) {
        this.rptFacilTotal = rptFacilTotal;
    }

    public List<Object[]> getRptUserTotal() {
        return rptUserTotal;
    }

    public void setRptUserTotal(List<Object[]> rptUserTotal) {
        this.rptUserTotal = rptUserTotal;
    }

    public Long getRptFid() {
        return rptFid;
    }

    public void setRptFid(Long rptFid) {
        this.rptFid = rptFid;
    }

    public String getRptUid() {
        return rptUid;
    }

    public void setRptUid(String rptUid) {
        this.rptUid = rptUid;
    }

    public String getRptFName() {
        return rptFName;
    }

    public void setRptFName(String rptFName) {
        this.rptFName = rptFName;
    }

    public List<Booking> getRptFacilIndv() {
        return rptFacilIndv;
    }

    public void setRptFacilIndv(List<Booking> rptFacilIndv) {
        this.rptFacilIndv = rptFacilIndv;
    }

    public List<Booking> getRptUserIndv() {
        return rptUserIndv;
    }

    public void setRptUserIndv(List<Booking> rptUserIndv) {
        this.rptUserIndv = rptUserIndv;
    }
     
    

   
    
}

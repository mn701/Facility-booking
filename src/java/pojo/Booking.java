package pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQueries ({
    @NamedQuery(name=Booking.BookingHistory, query="select c from Booking c where c.user.id=:userId and c.evdate <= CURRENT_DATE ORDER BY c.evdate DESC"),
    @NamedQuery(name=Booking.UserCurBookings, query="select c from Booking c where c.user.id=:userId and c.evdate > CURRENT_DATE ORDER BY c.evdate"),
    @NamedQuery(name=Booking.DuplicateCheck, query="select c from Booking c where c.facility.id=:fclId and c.evdate =:qDate and c.startTime <:qEnd and c.endTime>:qStart and c.active = True"),
    @NamedQuery(name=Booking.SameDayBookings, query="select c from Booking c where c.facility.id=:fclId and c.evdate =:qDate ORDER BY c.startTime"),
    @NamedQuery(name=Booking.ReportFacility, query="select c from Booking c where c.facility.id=:fclId and c.evdate >= :qDateFrom and c.evdate <= :qDateTo ORDER BY c.evdate,c.user.id"),
    @NamedQuery(name=Booking.ReportUser, query="select c from Booking c where c.user.id=:usrId and c.evdate >= :qDateFrom and c.evdate <= :qDateTo ORDER BY c.evdate, c.facility.id")
})

@Entity
@Table(name="BOOKING")
public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String BookingHistory = "BookingHistory";
    public static final String UserCurBookings = "UserCurBookings";
    public static final String DuplicateCheck = "DuplicateCheck";
    public static final String SameDayBookings = "SameDayBookings";
    public static final String ReportFacility = "ReportFacility";
    public static final String ReportUser = "ReportUser";

    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private User user;
    private Facility facility;
    @Temporal(TemporalType.DATE)
    private Date evdate;
    @Temporal(TemporalType.TIME)
    private Date startTime;
    @Temporal(TemporalType.TIME)
    private Date endTime;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date bookedDate;
    private String remarks;
    private String equipment;
    private boolean active;
    
    public String getStyleClass(){
        if(active){
            return "active";
        }
        if(!active){
            return "canceled";
        }else{
            return null;
        }
    }

    //Followning code is auto-generated

    public Booking() {
    }

    public Booking(User user, Facility facility, Date evdate, Date startTime, Date endTime, Date bookedDate, String remarks, String equipment, boolean active) {
        this.user = user;
        this.facility = facility;
        this.evdate = evdate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bookedDate = bookedDate;
        this.remarks = remarks;
        this.equipment = equipment;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public Date getEvdate() {
        return evdate;
    }

    public void setEvdate(Date evdate) {
        this.evdate = evdate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getBookedDate() {
        return bookedDate;
    }

    public void setBookedDate(Date bookedDate) {
        this.bookedDate = bookedDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.user);
        hash = 53 * hash + Objects.hashCode(this.facility);
        hash = 53 * hash + Objects.hashCode(this.evdate);
        hash = 53 * hash + Objects.hashCode(this.startTime);
        hash = 53 * hash + Objects.hashCode(this.endTime);
        hash = 53 * hash + Objects.hashCode(this.bookedDate);
        hash = 53 * hash + Objects.hashCode(this.remarks);
        hash = 53 * hash + Objects.hashCode(this.equipment);
        hash = 53 * hash + (this.active ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Booking other = (Booking) obj;
        if (this.active != other.active) {
            return false;
        }
        if (!Objects.equals(this.remarks, other.remarks)) {
            return false;
        }
        if (!Objects.equals(this.equipment, other.equipment)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.facility, other.facility)) {
            return false;
        }
        if (!Objects.equals(this.evdate, other.evdate)) {
            return false;
        }
        if (!Objects.equals(this.startTime, other.startTime)) {
            return false;
        }
        if (!Objects.equals(this.endTime, other.endTime)) {
            return false;
        }
        if (!Objects.equals(this.bookedDate, other.bookedDate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Booking{" + "id=" + id + ", user=" + user + ", facility=" + facility + ", evdate=" + evdate + ", startTime=" + startTime + ", endTime=" + endTime + ", bookedDate=" + bookedDate + ", remarks=" + remarks + ", equipment=" + equipment + ", active=" + active + '}';
    }
    
    

}



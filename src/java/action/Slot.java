package action;

import java.util.Date;
/*content-2 table
*  display purpose only
*/
public class Slot {
    private Date stime;	
    private String avail;

    public Slot() {
    }

    public Slot(Date stime, String avail) {
        this.stime = stime;
        this.avail = avail;
    }
    
    public Date getStime() {
        return stime;
    }

    public void setStime(Date stime) {
        this.stime = stime;
    }

    public String getAvail() {
        return avail;
    }

    public void setAvail(String avail) {
        this.avail = avail;
    }
}

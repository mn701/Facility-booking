package action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import db.*;
import java.text.ParseException;
import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import pojo.*;


public class UserGroupCheck extends ActionSupport implements SessionAware{
    private UserHelper userHelper = new UserHelper();
    private User curUser;
    private String userid;
    private String passwd;
    
    private Map<String, Object> session;

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
    public Map<String, Object> getSession() {
        return session;
    }


    public String execute() throws ParseException{
        if (session != null && session.get("curUser")!= null) {
            curUser = (User)session.get("curUser");
        }
        if(curUser!=null && curUser.getGroupid().equals("admin")){
                return SUCCESS;
        }else {
            return ERROR;
        }
    }

    public User getCurUser() {
        return curUser;
    }

    public void setCurUser(User curUser) {
        this.curUser = curUser;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

  
}

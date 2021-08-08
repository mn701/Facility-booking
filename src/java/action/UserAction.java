package action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import db.UserHelper;
import java.text.ParseException;
import java.util.List;
import pojo.*;
import util.SHA256Encoder;

public class UserAction extends ActionSupport{
    //for DB operations
    private UserHelper userHelper = new UserHelper();
    
    private List<User> userList;
    private String u_id = "";
    private String u_passwd= ""; 
    private String u_email= "";
    private String u_group= "";

    public String createUser() throws ParseException
    {
        User newUser = new User(u_id, getEncodedPw(u_passwd), u_email, u_group);
        try {
            userHelper.addUser(newUser);
            userList = userHelper.getAllUsers();
            clear();
        } catch (Exception e) {
        }
        return SUCCESS;
    }
    
    // Encode password
    private String getEncodedPw(String pw) {
        SHA256Encoder encoder = new SHA256Encoder();
        return encoder.encodePassword(pw);
    }
    
    public String list() {
        try {
            userList = userHelper.getAllUsers();
        } catch(Exception e){
        }
        return SUCCESS;
    }
    public void clear() {
        u_id = u_passwd = u_email = u_group = null;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getU_passwd() {
        return u_passwd;
    }

    public void setU_passwd(String u_passwd) {
        this.u_passwd = u_passwd;
    }

    public String getU_email() {
        return u_email;
    }

    public void setU_email(String u_email) {
        this.u_email = u_email;
    }

    public String getU_group() {
        return u_group;
    }

    public void setU_group(String u_group) {
        this.u_group = u_group;
    }
}

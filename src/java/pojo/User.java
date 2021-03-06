package pojo;
// Generated Apr 9, 2018 7:23:08 PM by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User generated by hbm2java
 */
@Entity
@Table(name="user"
    ,catalog="ci6225"
)
public class User  implements java.io.Serializable {


     private String id;
     private String passwd;
     private String email;
     private String groupid;

    public User() {
    }

	
    public User(String id) {
        this.id = id;
    }

    public User(String id, String passwd, String email, String groupid) {
        this.id = id;
        this.passwd = passwd;
        this.email = email;
        this.groupid = groupid;
    }
   
    
   
    @Id 
    @Column(name="ID", unique=true, nullable=false)
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    
    @Column(name="EMAIL")
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    
    @Column(name="PASSWD")
    public String getPasswd() {
        return this.passwd;
    }
    
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    
    @Column(name="GROUPID")
    public String getGroupid() {
        return this.groupid;
    }
    
    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }




}



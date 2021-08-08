package pojo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries ({
    @NamedQuery(name=Facility.Q_ALL, query="select c from Facility c ORDER BY c.name"),
    @NamedQuery(name=Facility.Q_TYPE, query="select c from Facility c where c.type=:valueOfType ORDER BY c.name"),
    @NamedQuery(name=Facility.Q_ALL_AVAILABLE, query="select c from Facility c where c not in(select b.facility from Booking b where b.evdate =:qDate and b.startTime <:qEnd and b.endTime>:qStart and b.active = True) ORDER BY c.name"),
    @NamedQuery(name=Facility.Q_TYPE_AVAILABLE, query="select c from Facility c where c.type=:valueOfType and c not in(select b.facility from Booking b where b.evdate =:qDate and b.startTime <:qEnd and b.endTime>:qStart and b.active = True) ORDER BY c.name")
})

@Entity
@Table(name="FACILITY")
public class Facility implements Serializable {
    public static final String Q_TYPE = "Q_TYPE";
    public static final String Q_ALL = "Q_ALL";
    public static final String Q_TYPE_AVAILABLE = "Q_TYPE_AVAILABLE";
    public static final String Q_ALL_AVAILABLE = "Q_ALL_AVAILABLE";

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Integer capacity;
    @Enumerated(EnumType.STRING)
    private Ftype type;
    private String description;
    private String picstr;
    @Basic(fetch = FetchType.LAZY)
    private byte[] picture;
    
    //Followning code is auto-generated

    public Facility() {
    }

    public Facility(String name, Integer capacity, Ftype type, String description, String picstr, byte[] picture) {
        this.name = name;
        this.capacity = capacity;
        this.type = type;
        this.description = description;
        this.picstr = picstr;
        this.picture = picture;
    }

    public Facility(String name, Integer capacity, Ftype type, String description) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.type = type;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Ftype getType() {
        return type;
    }

    public void setType(Ftype type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicstr() {
        return picstr;
    }

    public void setPicstr(String picstr) {
        this.picstr = picstr;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
        hash = 41 * hash + Objects.hashCode(this.name);
        hash = 41 * hash + Objects.hashCode(this.capacity);
        hash = 41 * hash + Objects.hashCode(this.type);
        hash = 41 * hash + Objects.hashCode(this.description);
        hash = 41 * hash + Objects.hashCode(this.picstr);
        hash = 41 * hash + Arrays.hashCode(this.picture);
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
        final Facility other = (Facility) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.picstr, other.picstr)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.capacity, other.capacity)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        if (!Arrays.equals(this.picture, other.picture)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Facility{" + "id=" + id + ", name=" + name + ", capacity=" + capacity + ", type=" + type + ", description=" + description + ", picstr=" + picstr + ", picture=" + picture + '}';
    }
    
    

    
}



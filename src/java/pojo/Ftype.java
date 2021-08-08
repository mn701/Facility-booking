package pojo;
/**
 *
 * Facility types
 */
public enum Ftype {
    NONE("All"), 
    MEETING("Meeting Rooms"), 
    CONFERENCE("Conference Rooms"), 
    DISCUSSION("Discussion Rooms");

    private String name;
 
    public String getName() {
        return name;
    }
   
    private Ftype (String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return name;
    }
 

    public static Ftype getEnum(String str) {
        Ftype[] enumArray = Ftype.values();

        for(Ftype enumStr : enumArray) {
            // 引数とenum型の文字列部分を比較
            if (str.equals(enumStr.name.toString())){
                return enumStr;
            }
        }
        return null;
    }
}


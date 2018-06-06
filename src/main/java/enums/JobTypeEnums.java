package enums;

public enum JobTypeEnums {

    CITY(2), //地级
    COUNTY(3),//县级
    TOWN(4),//乡级
    VILLAGE(5)//村级
    ;

    public final int code;

    JobTypeEnums(int code) {
        this.code = code;
    }

}

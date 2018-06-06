package enums;

public enum JobStateEnums {

    NOT_PARSING(false),
    PARSING(true);

    public final boolean value;

    JobStateEnums(boolean value) {
        this.value = value;
    }

}
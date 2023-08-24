package ${pkg};

/**
* ${description}
* @author ${author}
*/
public enum ${name} {

    INSTANCE_1("1"),
    INSTANCE_2("2");

    private final String code;

    ${name}(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static ${name} getInstance(String code) {
        for (${name} value : ${name}.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
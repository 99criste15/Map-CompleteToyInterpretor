package Domain.Values;

import Domain.Types.StringType;
import Domain.Types.Type;

public class StringValue implements Value {

    private String text;
    @Override
    public Type getType() {
        return new StringType();
    }

    public String getVal() {
        return text;
    }

    public StringValue(String text) {
        this.text = text;
    }

    public String toString() {
        return text;
    }

    public static StringValue asString(Value v) {
        return (StringValue) v;
    }



}

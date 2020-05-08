package Domain.Types;

import Domain.Values.StringValue;
import Domain.Values.Value;

public class StringType implements Type{
    @Override
    public Value defaultValue() {
        return new StringValue("");
    }

    public boolean equals(Object another) {
        return another instanceof StringType;

    }

    public String toString() {
        return "String";
    }


    public static void checkString (Type v ){
        if (!v.equals(new StringType())) {
            throw new RuntimeException();
        }
    }

}

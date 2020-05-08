package Domain.Types;

import Domain.Values.BoolValue;
import Domain.Values.Value;

public class BoolType implements Type {
    public boolean equals(Object another) {
        return another instanceof BoolType;
    }

    public String toString() {
        return "boolean";
    }

    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }

    public static void checkBool(Type v ){
        if (!v.equals(new BoolType())) {
            throw new RuntimeException();
        }
    }
}

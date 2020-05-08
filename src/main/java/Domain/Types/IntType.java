package Domain.Types;

import Domain.Values.IntValue;
import Domain.Values.Value;

public class IntType implements Type {
    public boolean equals(Object another) {
        return another instanceof IntType;

    }


    public static void checkInt(Type v){
        if (!v.equals(new IntType())) {
            throw new RuntimeException();
        }
    }

    public String toString() {
        return "int";
    }

    @Override
    public Value defaultValue() {
        return new IntValue(0);
    }
}
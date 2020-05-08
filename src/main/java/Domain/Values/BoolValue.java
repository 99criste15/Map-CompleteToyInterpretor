package Domain.Values;


import Domain.Types.BoolType;
import Domain.Types.Type;

public class BoolValue implements Value {
    private boolean val;

    public BoolValue(boolean v) {
        val = v;
    }

    public boolean getVal() {
        return val;
    }

    public String toString() {
        return val + "";
    }

    public Type getType() {
        return new BoolType();
    }

    public static BoolValue asBool(Value v) {

        return (BoolValue) v;
    }

}

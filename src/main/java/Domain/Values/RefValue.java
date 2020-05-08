package Domain.Values;

import Domain.Types.*;

public class RefValue implements Value {
    private final int address;
    private final Type locationType;


    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddr() {
        return address;
    }

    public Type getType() {
        return new RefType(locationType);
    }

      public Type getInner() {
        return locationType;
    }

    public static RefValue asRef(Value v) {
        return (RefValue) v;
    }

    public static boolean isRefValue(Value v) {
        return (v.getType() instanceof RefType);
    }

    @Override
    public String toString() {
        return "( " + address + ", " + locationType + ")";
    }

    public RefValue setAddress(int address) {
        return new RefValue(address, this.locationType);
    }



}

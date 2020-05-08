package Domain.Types;

import Domain.Values.RefValue;
import Domain.Values.Value;

public class RefType implements Type {
    private Type inner;

    public RefType(Type inner) {
        this.inner = inner;
    }

    public Type getInner() {
        return inner;
    }

    public boolean equals(Object another) {
        if (another instanceof RefType) {
            RefType refType = (RefType) another;
            return inner.equals(refType.getInner());
        } else
            return false;
    }

    private static boolean sameType(Object another){
        return another instanceof RefType;
    }

    public String toString() {
        return "Ref(" + inner.toString() + ")";
    }

    @Override
    public Value defaultValue() {
        return new RefValue(0, inner);
    }


    public static void checkRef(Type v){
        if (!sameType(v)) {
            throw new RuntimeException();
        }

    }
}

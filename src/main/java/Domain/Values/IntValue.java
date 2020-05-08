package Domain.Values;
import Domain.Types.IntType;
import Domain.Types.Type;

public class IntValue implements Value{
    private int val;
    public IntValue(int v){val=v;}
    public int getVal() {return val;}
    public String toString() {
        return val+ "";
    }
    public Type getType() { return new IntType();}

     public static IntValue asInt(Value v) {
        return (IntValue) v;
    }


}

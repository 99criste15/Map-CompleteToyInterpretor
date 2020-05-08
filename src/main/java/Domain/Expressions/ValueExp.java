package Domain.Expressions;

import Domain.ProgramState.MyIDictionary;
import Domain.ProgramState.MyIHeap;
import Domain.Types.Type;
import Domain.Values.Value;

import java.util.concurrent.locks.ReadWriteLock;


public class ValueExp implements Exp {
    private final Value e;

    public ValueExp(Value e) {
        this.e = e;
    }

    @Override
    public String toString() {
        return e.toString();
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer,Value> hp) throws Exception {
        return e;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws Exception {
        return e.getType();
    }
}

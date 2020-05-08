package Domain.Expressions;

import Domain.ProgramState.MyIDictionary;
import Domain.ProgramState.MyIHeap;
import Domain.Types.Type;
import Domain.Values.Value;

import java.util.concurrent.locks.ReadWriteLock;

public class VarExp implements Exp {
    private final String id;

    public VarExp(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer,Value> hp)throws Exception {
        return tbl.lookup(id);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws Exception {
        return typeEnv.lookup(id);
    }
}

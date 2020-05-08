package Domain.Expressions;

import Domain.ProgramState.MyIDictionary;
import Domain.ProgramState.MyIHeap;
import Domain.Types.RefType;
import Domain.Types.Type;
import Domain.Values.RefValue;
import Domain.Values.Value;

import java.io.Serializable;
import java.util.concurrent.locks.ReadWriteLock;

public class rH implements Exp {
    private final Exp exp;
    public rH(Exp exp) {

        this.exp = exp;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer,Value> hp) throws Exception {
        Value value = exp.eval(tbl,hp);
        RefValue refValue = RefValue.asRef(value);

        Value v = hp.lookup(refValue.getAddr());

        return v;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws Exception {
        Type t = exp.typecheck(typeEnv);
        RefType.checkRef(t);
        RefType rt = (RefType)t;
        return rt.getInner();
    }

    @Override
    public String toString() {
        return "rH(" + exp.toString() + " )";
    }
}


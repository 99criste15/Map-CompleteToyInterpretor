package Domain.Expressions;

import Domain.ProgramState.MyIDictionary;
import Domain.ProgramState.MyIHeap;
import Domain.Types.Type;
import Domain.Values.Value;

import java.io.Serializable;
import java.util.concurrent.locks.ReadWriteLock;

public interface Exp {
    Value eval(MyIDictionary<String,Value> tbl, MyIHeap<Integer,Value> hp) throws Exception;
    Type typecheck(MyIDictionary<String,Type> typeEnv) throws Exception;

}

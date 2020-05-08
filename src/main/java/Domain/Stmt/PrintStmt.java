package Domain.Stmt;

import Domain.Expressions.Exp;
import Domain.ProgramState.MyIDictionary;
import Domain.ProgramState.MyIList;
import Domain.ProgramState.PrgState;
import Domain.Types.Type;
import Domain.Values.Value;

import java.util.concurrent.locks.ReadWriteLock;

public class PrintStmt implements IStmt {
    private final Exp exp;

    public PrintStmt(Exp exp) {
        this.exp = exp;
    }

    public String toString() {
        return "print(" + exp.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        MyIList<Value> list = state.getList();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        Value val;
        synchronized (state.getList()) {
            synchronized (state.getHeap()) {
                val = exp.eval(symTbl, state.getHeap());
            }
            list.addBack(val);
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws Exception {
        exp.typecheck(typeEnv);
        return typeEnv;
    }
}

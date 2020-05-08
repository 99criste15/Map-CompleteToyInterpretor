package Domain.Stmt;

import Domain.Expressions.Exp;
import Domain.ProgramState.MyIDictionary;
import Domain.ProgramState.MyIStack;
import Domain.ProgramState.PrgState;
import Domain.Types.Type;
import Domain.Values.BoolValue;
import Domain.Values.Value;

import java.util.concurrent.locks.ReadWriteLock;

public class IfStmt implements IStmt {
    private final Exp exp;
    private final IStmt thenS;
    private final IStmt elseS;

    public IfStmt(Exp e, IStmt t, IStmt el) {
        exp = e;
        thenS = t;
        elseS = el;
    }

    public String toString() {
        return "IF(" + exp.toString() + ") THEN(" + thenS.toString()
                + ")ELSE(" + elseS.toString() + ")";
    }

    public PrgState execute(PrgState state) throws Exception {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        boolean b1;
        synchronized (state.getHeap()) {
            b1 = BoolValue.asBool(exp.eval(symTbl, state.getHeap())).getVal();
        }if (b1) stk.push(thenS);
        else stk.push(elseS);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws Exception {
        thenS.typecheck(typeEnv);
        elseS.typecheck(typeEnv);
        return typeEnv;
    }

}

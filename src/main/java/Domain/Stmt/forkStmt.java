package Domain.Stmt;
import Domain.ProgramState.*;
import Domain.Types.Type;
import Domain.Values.Value;

import java.util.concurrent.locks.ReadWriteLock;

public class forkStmt implements IStmt {
    private final IStmt stmt;

    public forkStmt(IStmt stmt) {
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) {
        MyIStack<IStmt> newStack = new MyStack();
        MyIDictionary<String, Value> newDict = new MyDictionary(state.getSymTable().getContent());
        return new PrgState(newStack, newDict, state.getHeap(), state.getList(), state.getFileTable(), stmt);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws Exception {
        stmt.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "fork(" + stmt.toString() + ");";
    }
}

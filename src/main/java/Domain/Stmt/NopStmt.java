package Domain.Stmt;

import Domain.ProgramState.MyIDictionary;
import Domain.ProgramState.PrgState;
import Domain.Types.Type;
import Exception.MyException;

import java.util.concurrent.locks.ReadWriteLock;

public class NopStmt implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws Exception {
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws Exception {
        return typeEnv;
    }
}

package Domain.Stmt;

import Domain.ProgramState.MyIDictionary;
import Domain.ProgramState.PrgState;
import Domain.Types.Type;

import java.util.concurrent.locks.ReadWriteLock;


public interface IStmt {

    PrgState execute(PrgState state) throws Exception;
    MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws Exception;


}

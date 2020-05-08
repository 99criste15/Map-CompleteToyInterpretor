package Domain.Stmt;

import Domain.ProgramState.MyIDictionary;
import Domain.ProgramState.MyIStack;
import Domain.ProgramState.PrgState;
import Domain.Types.Type;


public class CompStmt implements IStmt {
    private final IStmt first;
    private final IStmt snd;

    public CompStmt(IStmt first, IStmt snd) {
        this.first = first;
        this.snd = snd;
    }

    public String toString() {
        return "( " + first.toString() + " ; " + snd.toString() + " ) ";
    }

    public PrgState execute(PrgState state) {
        MyIStack<IStmt> stk = state.getStk();
        stk.push(snd);
        stk.push(first);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws Exception {
        return snd.typecheck(first.typecheck(typeEnv));
    }
}
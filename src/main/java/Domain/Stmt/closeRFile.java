package Domain.Stmt;

import Domain.Expressions.Exp;
import Domain.ProgramState.MyIDictionary;
import Domain.ProgramState.PrgState;
import Domain.Types.StringType;
import Domain.Types.Type;


public class closeRFile implements IStmt {
    private final Exp exp;

    public closeRFile(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        String fileName;
        synchronized (state.getHeap()){
        fileName = state.getFileName(exp);}
        synchronized (state.getFileTable()){
        state.getFileTable().close(fileName);}
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws Exception {
        Type type = exp.typecheck(typeEnv);
        if(!type.equals(new StringType()))throw new Exception("Open argument is not of type String");
        return typeEnv;
    }

    public String toString() {
        return "close (" + exp.toString() +")";
    }
}

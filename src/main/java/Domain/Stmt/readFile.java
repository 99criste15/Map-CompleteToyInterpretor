package Domain.Stmt;

import Domain.Expressions.Exp;
import Domain.ProgramState.MyIDictionary;
import Domain.ProgramState.PrgState;
import Domain.Types.IntType;
import Domain.Types.StringType;
import Domain.Types.Type;
import Domain.Values.IntValue;
import Domain.Values.Value;

import java.util.concurrent.locks.ReadWriteLock;


public class readFile implements IStmt {
    private final Exp exp;
    private final String varName;

    public readFile(Exp exp, String varName) {
        this.exp = exp;
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        String fileName;
        int x;
        synchronized (state.getHeap()){ fileName = state.getFileName(exp);}
        synchronized (state.getFileTable()) {
            x = state.getFileTable().read(fileName);
        }
        System.out.println(x);
        if (x!=0) {
            state.setSymTable(state.getSymTable().update(varName, new IntValue(x)));
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws Exception {
        Type type = exp.typecheck(typeEnv);
        if(!type.equals(new StringType()))throw new Exception("Open cannot have a non StringType argument\n");
        if(!typeEnv.lookup(varName).equals(new IntType())) throw new Exception("Read Statement cannot read into a variable that is not of type int\n");
        return typeEnv;
    }

    public String toString() {
        return "read (" + exp.toString() + ", "+ varName +")";
    }
}

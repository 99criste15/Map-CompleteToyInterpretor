package Domain.Stmt;

import Domain.Expressions.Exp;
import Domain.ProgramState.MyIDictionary;
import Domain.ProgramState.PrgState;
import Domain.Types.Type;
import Domain.Values.Value;

import java.util.concurrent.locks.ReadWriteLock;

public class AssignStmt implements IStmt{
    private final String id;
    private final Exp exp;

    public AssignStmt(String id1, Exp exp2){
        id = id1;
        exp = exp2;
    }

    public String toString(){ return id + "="+ exp.toString();}
    public PrgState execute(PrgState state) throws Exception{
        MyIDictionary<String,Value> symTbl= state.getSymTable();
        Value val;
        synchronized (state.getHeap()){
            val = exp.eval(symTbl,state.getHeap());}
        if (symTbl.isDefined(id)){
            Type typId= (symTbl.getValue(id)).getType();
            if (val.getType(). equals(typId))state.setSymTable(symTbl.update(id, val));
            else throw new Exception("declared type of variable "+id+" and type of the assigned expression do not match");
        }else throw new Exception("the used variable " +id + " was not declared before");
        return null;
        }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws Exception {
        Type type1 = exp.typecheck(typeEnv);
        Type type2 = typeEnv.lookup(id);
        if (!type1.equals(type2))throw new Exception("Arguments are of diffrent type\n");
        return  typeEnv;
    }
}

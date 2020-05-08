package Domain.Stmt;

import Domain.Expressions.Exp;
import Domain.ProgramState.MyIDictionary;
import Domain.ProgramState.MyIStack;
import Domain.ProgramState.PrgState;
import Domain.Types.BoolType;
import Domain.Types.Type;
import Domain.Values.BoolValue;
import Domain.Values.Value;


public class WhileStmt implements IStmt {
    private final Exp exp;
    private final IStmt stmt;

    public WhileStmt(Exp exp, IStmt stmt) {
        this.exp = exp;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        MyIDictionary<String, Value> dict = state.getSymTable();
        MyIStack<IStmt> stck = state.getStk();
        Value val;
        synchronized (state.getHeap()){val = exp.eval(dict,state.getHeap());}
        BoolValue boolValue = BoolValue.asBool(val);
        boolean primitiveVal = boolValue.getVal();
        if(primitiveVal){
            stck.push(new WhileStmt(exp,stmt));
            stck.push(stmt);
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws Exception {
        Type type = exp.typecheck(typeEnv);
        BoolType.checkBool(type);
        stmt.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "while( "+exp.toString() +" ) { "+ stmt.toString() + " }";
    }
}

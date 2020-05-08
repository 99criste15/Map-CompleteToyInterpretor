package Domain.Stmt;

import Domain.Expressions.Exp;
import Domain.ProgramState.MyIDictionary;
import Domain.ProgramState.MyIHeap;
import Domain.ProgramState.PrgState;
import Domain.Types.RefType;
import Domain.Types.Type;
import Domain.Values.RefValue;
import Domain.Values.Value;


public class wH implements IStmt {
    private final String varName;
    private final Exp exp;

    public wH(String varName, Exp exp) {
        this.varName = varName;//variable name
        this.exp = exp;         //expression to be assigned
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        MyIDictionary<String, Value> dict = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        synchronized (state.getHeap()) {
            Value val = dict.lookup(varName);
            RefValue rf = RefValue.asRef(val);
            Value heap_value = heap.lookup(rf.getAddr());
            Value exp_val = exp.eval(dict, heap);
            if (exp_val.getType().equals(heap_value.getType())) {
                heap.update(rf.getAddr(), exp_val);
            }
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws Exception {
        Type type1 = typeEnv.lookup(varName);
        RefType.checkRef(type1);
        RefType refType = (RefType) type1;
        Type type2 = exp.typecheck(typeEnv);
        if(!type2.equals(refType.getInner()))throw new Exception("Incompatible type for heap writing argument\n");

        return typeEnv;
    }

    @Override
    public String toString() {
        return "wH(" + varName + ", " + exp.toString() + " )";
    }
}

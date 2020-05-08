package Domain.Stmt;

import Domain.Expressions.Exp;
import Domain.ProgramState.*;
import Domain.Types.RefType;
import Domain.Types.Type;
import Domain.Values.RefValue;
import Domain.Values.Value;

public class aH implements IStmt{
    private final String varName;
    private final Exp exp;

    public aH(String varName, Exp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        MyIDictionary<String, Value> dict = state.getSymTable();
        synchronized (state.getHeap()) {
            MyIHeap<Integer, Value> heap = state.getHeap();
            Value var_value = dict.lookup(varName);
            RefValue rf = RefValue.asRef(var_value);
            Value exp_value = exp.eval(dict, heap);
            //deepcopy pe exp_eval;
            int x = Heap.IncAndGet();
            heap.update(x, exp_value);
            RefValue nRefValue = rf.setAddress(x);
            state.setSymTable(dict.update(varName, nRefValue));
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws Exception {

        Type type1 = typeEnv.lookup(varName);
        RefType.checkRef(type1);
        RefType refType = (RefType) type1;
        Type type2 = exp.typecheck(typeEnv);
        if(!type2.equals(refType.getInner()))throw new Exception("Incompatible type for heap assignment argument\n");

        return typeEnv;
    }

    @Override
    public String toString() {

        return "aH(" + varName + ", " + exp.toString() + " )";

    }
}

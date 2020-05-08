package Domain.Expressions;

import Domain.ProgramState.MyIDictionary;
import Domain.ProgramState.MyIHeap;
import Domain.Types.BoolType;
import Domain.Types.Type;
import Domain.Values.BoolValue;
import Domain.Values.Value;


public class LogicExp implements Exp{

    public enum Operator {
        OR("or"),
        AND("and");

        private final String label;

        Operator(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    private final Exp e1;
    private final Exp e2;
    private final Operator op;

    public LogicExp(Exp e1, Exp e2, Operator op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public String toString() {
        return e1.toString()+" " +op.toString()+" "+e2.toString();
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer,Value> hp) throws Exception {
        Value v1,v2;
        v1= e1.eval(tbl,hp);
        v2 = e2.eval(tbl,hp);
        BoolValue i1 = BoolValue.asBool(v1);
        BoolValue i2 = BoolValue.asBool(v2);
        if (op==Operator.AND) return new BoolValue( i1.getVal() && i2.getVal());
        return new  BoolValue(i1.getVal() || i2.getVal());

    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws Exception {
        Type type1 = e1.typecheck(typeEnv);
        Type type2 = e2.typecheck(typeEnv);
        BoolType.checkBool(type1);
        BoolType.checkBool(type2);
        return type1;
    }
}

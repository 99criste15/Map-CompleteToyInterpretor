package Domain.Expressions;
import Domain.ProgramState.MyIDictionary;
import Domain.ProgramState.MyIHeap;
import Domain.Types.IntType;
import Domain.Types.Type;
import Domain.Values.BoolValue;
import Domain.Values.IntValue;
import Domain.Values.Value;
import java.io.Serializable;
import java.util.concurrent.locks.ReadWriteLock;

import static Domain.Values.IntValue.asInt;

public class ArithExp implements Exp {
    public enum Operator {
        PLUS("+"),
        MINUS("-"),
        MULTIPLICATION("*"),
        DIVISION("/");

        private final String label;

        Operator(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    ;

    private final Exp e1;
    private final Exp e2;
    private final Operator op; //1-plus, 2-minus, 3-star, 4-divide

    public ArithExp(Exp e1, Operator op, Exp e2) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public String toString() {
        return e1 + " " + op + " " + e2;
    }


    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer,Value> hp) throws Exception {
        int left, right;
        left = asInt(e1.eval(tbl, hp)).getVal();
        right = asInt(e2.eval(tbl, hp)).getVal();
        switch (op) {
            case PLUS:
                return new IntValue(left + right);
            case MINUS:
                return new IntValue(left - right);
            case MULTIPLICATION:
                return new IntValue(left * right);
            case DIVISION:
                if (right == 0) throw new Exception("division by zero");
                else return new IntValue(left / right);

        }
        return new IntValue(0);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws Exception {
        Type type1 = e1.typecheck(typeEnv);
        Type type2 = e2.typecheck(typeEnv);
        IntType.checkInt(type1);
        IntType.checkInt(type2);
        return type1;
    }
}


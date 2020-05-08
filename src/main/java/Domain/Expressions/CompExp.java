package Domain.Expressions;

import Domain.ProgramState.MyIDictionary;
import Domain.ProgramState.MyIHeap;
import Domain.Types.BoolType;
import Domain.Types.IntType;
import Domain.Types.Type;
import Domain.Values.BoolValue;
import Domain.Values.IntValue;
import Domain.Values.Value;

import java.util.concurrent.locks.ReadWriteLock;

import static Domain.Values.IntValue.asInt;

public class CompExp implements Exp {
    public enum Operator {
        GREATER(">"),
        LESS("<"),
        LESS_EQUAL("<="),
        GREATER_EQUAL(">="),
        EQUAL("==");

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

    public CompExp(Exp e1, Operator op, Exp e2) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public String toString() {
        return e1 + " " + op + " " + e2;
    }


    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> hp) throws Exception {
        int left, right;
        left = asInt(e1.eval(tbl, hp)).getVal();
        right = asInt(e2.eval(tbl, hp)).getVal();
        switch (op) {

            case GREATER:
                if (left > right) return new BoolValue(true);
                else return new BoolValue(false);
            case GREATER_EQUAL:
                if (left >= right) return new BoolValue(true);
                else return new BoolValue(false);
            case LESS:
                if (left < right) return new BoolValue(true);
                else return new BoolValue(false);
            case LESS_EQUAL:
                if (left <= right) return new BoolValue(true);
                else return new BoolValue(false);
            case EQUAL:
                if (left == right) return new BoolValue(true);
                else return new BoolValue(false);

        }
        return new IntValue(0);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws Exception {
        Type type1 = e1.typecheck(typeEnv);
        Type type2 = e2.typecheck(typeEnv);
        IntType.checkInt(type1);
        IntType.checkInt(type2);
        return new BoolType();
    }

}


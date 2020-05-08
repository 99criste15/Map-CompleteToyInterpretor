package Domain.Stmt;

import Domain.Types.*;
import Exception.MyException;
import Domain.ProgramState.MyIDictionary;
import Domain.ProgramState.PrgState;
import Domain.Values.Value;

import java.util.concurrent.locks.ReadWriteLock;

public class VarDeclStmt implements IStmt {
    private final String name;
    private final Value defaultValue;
    private final Type typ;

    public VarDeclStmt(String name, Type typ) {
        this.name = name;
        this.defaultValue = typ.defaultValue();
        this.typ = typ;
    }

    @Override
    public String toString() {
        return typ.toString() + " " + name;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        if (symTbl.isDefined(name))
            throw new MyException("the variable " + name + " was declared before");
        state.setSymTable(symTbl.update(name, defaultValue));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws Exception {
        return typeEnv.update(name, typ);
    }
}

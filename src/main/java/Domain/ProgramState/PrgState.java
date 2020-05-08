package Domain.ProgramState;

import Domain.Expressions.Exp;
import Domain.Values.StringValue;
import Exception.MyException;
import Domain.Stmt.IStmt;
import Domain.Values.Value;

import java.io.*;
import java.sql.CallableStatement;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;


public class PrgState {

    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, Value> symTable;
    private MyIHeap<Integer, Value> heap;
    private MyITable<String, BufferedReader> fileTable;
    private MyIList<Value> out;
    private IStmt originalProgram;
    private int prgStateId;
    private static AtomicInteger id=new AtomicInteger(0);

    public int getPrgStateId() {
        return prgStateId;
    }

    @Override
    public String toString() {
        return  "The program state id is : "+ prgStateId + "\n"+
                "The stack is: \n " + exeStack.toString() + "\n" +
                "The dictionary is :\n" + symTable.toString() + "\n" +
                "The heap is :\n" + heap.toString() + "\n" +
                "The output is :\n" + out.toString() + "\n" +
                "The fileTable is :\n" + fileTable.toString() + "\n"
                + "______________________________________________________________________\n";
    }

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIHeap<Integer, Value> h,
                    MyIList<Value> ot, MyITable<String, BufferedReader> fileT, IStmt prg) {
        exeStack = stk;
        symTable = symtbl;
        prgStateId=id.incrementAndGet();;
        heap = h;
        out = ot;
        fileTable = fileT;
        originalProgram = prg;//recreate the entire original prg
        stk.push(prg);
    }

    public MyIStack<IStmt> getStk() {
        return exeStack;

    }

    public MyIDictionary<String, Value> getSymTable() {

        return symTable;
    }

    public MyIHeap<Integer, Value> getHeap() {

        return heap;
    }

    public MyIList<Value> getList() {

        return out;
    }

    public MyITable<String, BufferedReader> getFileTable() {

        return fileTable;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public boolean isNotCompleted(){
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws Exception {
        if(exeStack.isEmpty()) throw new MyException("prgstate stack is empty");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    public String getFileName(Exp e) throws Exception {
        Value v = e.eval(symTable, heap);
        return StringValue.asString(v).getVal();
    }

    public void setSymTable(MyIDictionary<String, Value> symTable) {
        this.symTable = symTable;
    }


}

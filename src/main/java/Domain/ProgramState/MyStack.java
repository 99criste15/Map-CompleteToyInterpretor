package Domain.ProgramState;

import Domain.Stmt.IStmt;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MyStack implements MyIStack<IStmt>{
    private Stack<IStmt> stack;

    public MyStack() {
        stack = new Stack<IStmt>();
    }

    public IStmt pop(){ return stack.pop();}
    public void push(IStmt v){ stack.push(v);}

    @Override
    public boolean isEmpty() {
        return stack.empty();
    }

    @Override
    public List<String> getToString() {
        List<String> result =new ArrayList<>();
        for (IStmt value: stack
        ){result.add(value.toString());}
        return result;
    }

    public String toString(){
        StringBuilder result = new StringBuilder();
        for (IStmt value: stack
             ) { result.insert(0, value.toString() + "\n"); }
        return result.toString();
    }
}


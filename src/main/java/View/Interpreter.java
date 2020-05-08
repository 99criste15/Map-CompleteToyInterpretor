package View;
import Controller.Ctrl;
import Domain.Expressions.*;
import Domain.ProgramState.*;
import Domain.Stmt.*;
import Domain.Types.*;
import Domain.Values.BoolValue;
import Domain.Values.IntValue;
import Domain.Values.StringValue;
import Repository.StateList;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.Callable;

import static Domain.Expressions.ArithExp.Operator.*;
import static Domain.Expressions.CompExp.Operator.*;

public class Interpreter {
    private static Callable[] programs = {
            Interpreter::program1,
            Interpreter::program2,
            Interpreter::program3,
            Interpreter::program4,
            Interpreter::program5,
            Interpreter::program6,
            Interpreter::program7,
            Interpreter::program8,
            Interpreter::program9,
            Interpreter::program10
    };

    private static IStmt program1() {
        Stack<IStmt> constructionStack = new Stack<>();
        constructionStack.push(new VarDeclStmt("v", new IntType()));
        constructionStack.push(new AssignStmt("v", new ValueExp(new IntValue(2))));
        return constructCompound(constructionStack);

    }

    private static IStmt program2() {
        Stack<IStmt> constructionStack = new Stack<>();
        Exp artmExp3 = new ArithExp(new VarExp("a"), PLUS, new ValueExp(new IntValue(1)));
        Exp artmExp2 = new ArithExp(new ValueExp(new IntValue(3)), MULTIPLICATION, new ValueExp(new IntValue(5)));
        Exp artmExp1 = new ArithExp(new ValueExp(new IntValue(2)), PLUS, artmExp2);
        constructionStack.push(new VarDeclStmt("a", new IntType()));
        constructionStack.push(new VarDeclStmt("b", new IntType()));
        constructionStack.push(new AssignStmt("a", artmExp1));
        constructionStack.push(new AssignStmt("b", artmExp3));
        constructionStack.push(new PrintStmt(new VarExp("b")));
        return constructCompound(constructionStack);

    }

    private static IStmt program3() {
        Stack<IStmt> constructionStack = new Stack<>();

        IStmt orElse = new AssignStmt("v", new ValueExp(new IntValue(3)));
        IStmt then = new AssignStmt("v", new ValueExp(new IntValue(2)));
        IStmt ifStm = new IfStmt(new VarExp("a"), then, orElse);
        constructionStack.push(new VarDeclStmt("a", new BoolType()));
        constructionStack.push(new VarDeclStmt("v", new IntType()));
        constructionStack.push(new AssignStmt("a", new ValueExp(new BoolValue(true))));
        constructionStack.push(ifStm);
        constructionStack.push(new PrintStmt(new VarExp("v")));
        return constructCompound(constructionStack);

    }

    private static IStmt program4() {
        Stack<IStmt> constructionStack = new Stack<>();
        constructionStack.push(new VarDeclStmt("varf", new StringType()));
        constructionStack.push(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))));
        constructionStack.push(new openRFile(new VarExp("varf")));
        constructionStack.push(new VarDeclStmt("varc", new IntType()));
        constructionStack.push(new readFile(new VarExp("varf"), "varc"));
        constructionStack.push(new PrintStmt(new VarExp("varc")));
        constructionStack.push(new readFile(new VarExp("varf"), "varc"));
        constructionStack.push(new PrintStmt(new VarExp("varc")));
        constructionStack.push(new closeRFile(new VarExp("varf")));
        return constructCompound(constructionStack);

    }


    private static IStmt program5() {
        Stack<IStmt> constructionStack = new Stack<>();
        constructionStack.push(new VarDeclStmt("v", new RefType(new IntType())));
        constructionStack.push(new aH("v", new ValueExp(new IntValue(20))));
        constructionStack.push(new VarDeclStmt("a", new RefType(new RefType(new IntType()))));
        constructionStack.push(new aH("a", new VarExp("v")));
        constructionStack.push(new PrintStmt(new VarExp("v")));
        constructionStack.push(new PrintStmt(new VarExp("a")));
        return constructCompound(constructionStack);
    }
   // Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)


    private static IStmt program6() {
        Stack<IStmt> constructionStack = new Stack<>();
        constructionStack.push(new VarDeclStmt("v", new RefType(new IntType())));
        constructionStack.push(new aH("v", new ValueExp(new IntValue(20))));
        constructionStack.push(new VarDeclStmt("a", new RefType(new RefType(new IntType()))));
        constructionStack.push(new aH("a", new VarExp("v")));
        constructionStack.push(new PrintStmt(new rH(new VarExp("v"))));
        constructionStack.push(new PrintStmt(new ArithExp(new rH(new rH(new VarExp("a"))),PLUS,new ValueExp(new IntValue(5)))));
        return constructCompound(constructionStack);
    }
//: Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)


    private static IStmt program7() {
        Stack<IStmt> constructionStack = new Stack<>();
        constructionStack.push(new VarDeclStmt("v", new RefType(new IntType())));
        constructionStack.push(new aH("v", new ValueExp(new IntValue(20))));
        constructionStack.push(new PrintStmt(new rH(new VarExp("v"))));
        constructionStack.push(new wH("v", new ValueExp(new IntValue(30))));
        constructionStack.push(new PrintStmt(new ArithExp(new rH(new VarExp("v")),PLUS,new ValueExp(new IntValue(5)))));
        return constructCompound(constructionStack);
    }
//Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);


    private static IStmt program8() {
        Stack<IStmt> constructionStack = new Stack<>();
        constructionStack.push(new VarDeclStmt("v", new RefType(new IntType())));
        constructionStack.push(new aH("v", new ValueExp(new IntValue(20))));
        constructionStack.push(new VarDeclStmt("a", new RefType(new RefType(new IntType()))));
        constructionStack.push(new aH("a", new VarExp("v")));
        constructionStack.push(new aH("v", new ValueExp(new IntValue(30))));
        constructionStack.push(new PrintStmt(new rH(new rH(new VarExp("a")))));
        return constructCompound(constructionStack);
    }
//Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))

    private static IStmt program9() {
        Stack<IStmt> constructionStack = new Stack<>();
        constructionStack.push(new VarDeclStmt("v", new IntType()));
        constructionStack.push(new AssignStmt("v", new ValueExp(new IntValue(4))));
        IStmt compound = new CompStmt(new AssignStmt("v", new ArithExp(new VarExp("v"),MINUS,new ValueExp(new IntValue(1)))),new PrintStmt(new VarExp("v")));
        constructionStack.push(new WhileStmt(new CompExp(new VarExp("v"),GREATER,new ValueExp(new IntValue(0))),compound));
        constructionStack.push(new PrintStmt(new VarExp("v")));
        return constructCompound(constructionStack);
    }
    //int v; v=4; (while (v>0) print(v);v=v-1);print(v)
    private static IStmt program10() {
        Stack<IStmt> constructionStack = new Stack<>();
        Stack<IStmt> forkStack = new Stack<>();
        constructionStack.push(new VarDeclStmt("v", new IntType()));
        constructionStack.push(new VarDeclStmt("a", new RefType(new IntType())));
        constructionStack.push(new AssignStmt("v", new ValueExp(new IntValue(10))));
        constructionStack.push(new aH("a", new ValueExp(new IntValue(22))));
        forkStack.push(new wH("a", new ValueExp(new IntValue(30))));
        forkStack.push(new AssignStmt("v", new ValueExp(new IntValue(32))));
        forkStack.push(new PrintStmt(new VarExp("v")));
        forkStack.push(new PrintStmt(new rH(new VarExp("a"))));
        constructionStack.push(new forkStmt(constructCompound(forkStack)));
        constructionStack.push(new PrintStmt(new VarExp("v")));
        constructionStack.push(new PrintStmt(new rH(new VarExp("a"))));
        return constructCompound(constructionStack);
    }
/*
    int v; Ref int a; v=10;new(a,22);
    fork(wH(a,30);v=32;print(v);print(rH(a)));
    print(v);print(rH(a))
    At the end:
    Id=1
    SymTable_1={v->10,a->(1,int)}
    Id=10
    SymTable_10={v->32,a->(1,int)}
    Heap={1->30}
    Out={10,30,32,30}
*/


    public static void create(ArrayList<Ctrl> controllers) throws Exception {

        int prgNO = 10;
        for(int i=1;i<=prgNO;i++){
            controllers.add(new Ctrl(new StateList(program(i),"log"+i+".txt")));
        }
    }

    private static IStmt constructCompound(Stack<IStmt> stkk) {
        IStmt compound = null;
        while (!stkk.empty()) {
            compound = stkk.pop();
            if (!stkk.empty()) {
                IStmt stm2 = stkk.pop();
                IStmt tempCompound = new CompStmt(stm2, compound);
                stkk.push(tempCompound);
            }
        }
        return compound;
    }

    private static PrgState program(int c) throws Exception {
        MyIDictionary<String, Type> typeEnv = new TypeEnv();
        IStmt ex1 = (IStmt) programs[c-1].call();
        System.out.println(c+"");
        System.out.println(ex1.typecheck(typeEnv).toString());
        return new PrgState( new MyStack(), new MyDictionary(), new Heap(), new MyList(), new FileTable(), ex1);
    }

    public static List<String> getPrograms() throws Exception {
        List<String> myList = new ArrayList<>();
        for (Callable x:programs
             ) {
            myList.add(x.call().toString());
        }
        return myList;
    }

}
//vavr


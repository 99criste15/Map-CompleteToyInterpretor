package Repository;

import Domain.ProgramState.MyIHeap;
import Domain.ProgramState.PrgState;
import Domain.Stmt.IStmt;
import Exception.MyException;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public interface RepoInterface {
    void addPrg(PrgState x) throws MyException;
    void logPrgStateExec(PrgState x) throws Exception;
    ArrayList<PrgState> getList();
    void setList(ArrayList<PrgState> newList);
    List<String> getIndexes();
    List<String> fileTableToString() throws Exception;
    List<Pair<String,String>> symToString(int x) throws Exception;
    List<String> stackToString(int x) throws Exception;
    List<String> outToString() throws Exception;
    List<Pair<String,String>> heapToString() throws Exception;


}

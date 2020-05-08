package Repository;

import Domain.ProgramState.PrgState;
import javafx.util.Pair;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class StateList implements RepoInterface {
    private ArrayList<PrgState> list;
    private String logfile;

    public StateList(PrgState state, String log) {
        list = new ArrayList<>();
        addPrg(state);
        this.logfile = log;
    }


    @Override
    public void addPrg(PrgState x) {
        list.add(x);
    }

    public String getLogfile() {
        return logfile;
    }

    @Override
    public ArrayList<PrgState> getList() {
        return list;
    }

    @Override
    public void setList(ArrayList<PrgState> newList) {
        list = newList;
    }

    @Override
    public List<String> getIndexes() {
        ArrayList<String> result = new ArrayList<>();
        for (PrgState x:list
             ) {
            result.add(x.getPrgStateId()+"");
        }
        return result;
    }

    @Override
    public List<String> fileTableToString() throws Exception {
        notEmpty();
        return list.get(0).getFileTable().getToString();
    }

    @Override
    public List<Pair<String, String>> symToString(int x) throws Exception {
        hasXthElem(x);
        return list.get(x).getSymTable().getToString();
    }


    private void notEmpty() throws Exception {
        if (list.isEmpty())throw new Exception("The program states list is empty\n");;
    }


    private void hasXthElem(int x) throws Exception {
        if (list.size()<=x)throw new Exception("The program states list doesn't have the Program state with the id "+x+ "\n");;
    }

    @Override
    public List<String> stackToString(int x) throws Exception {
        hasXthElem(x);
        return list.get(x).getStk().getToString();
    }

    @Override
    public List<String> outToString() throws Exception {
        notEmpty();
        return list.get(0).getList().getToString();
    }

    @Override
    public List<Pair<String, String>> heapToString() throws Exception {
        notEmpty();
        return list.get(0).getHeap().getToString();
    }

    @Override
    public void logPrgStateExec(PrgState prgState) throws Exception {

        BufferedWriter writer = new BufferedWriter(new FileWriter(logfile, true));
        String result = prgState.toString();
        writer.write(result);
        writer.close();
    }
}
//haxl
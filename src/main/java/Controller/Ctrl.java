package Controller;

import Domain.ProgramState.*;
import Domain.Stmt.IStmt;
import Domain.Values.Value;
import Repository.RepoInterface;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class Ctrl {
    private RepoInterface repo;
    private ExecutorService executor= Executors.newFixedThreadPool(2);
    private IStmt program;
    private MyIHeap<Integer,Value> heap;

    public IStmt getProgram() {
        return program;
    }

    public MyIHeap<Integer, Value> getHeap() {
        return heap;
    }



    public List<String> fileTableToString() throws Exception {
        return repo.fileTableToString();
    }

    public List<Pair<String, String>> heapToString() throws Exception {
        return repo.heapToString();
    }

    public List<Pair<String, String>> symToString(int x) throws Exception {
        return repo.symToString(x);
    }

    public List<String> stackToString(int x) throws Exception {
        return repo.stackToString(x);
    }
    public List<String> outToString() throws Exception {
        return repo.outToString();
    }


    public Ctrl(RepoInterface repo) {
        this.repo = repo;
        program = repo.getList().get(0).getOriginalProgram();
        heap = repo.getList().get(0).getHeap();
    }

    public static ArrayList<PrgState> removeCompletedPrg(ArrayList<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toCollection(ArrayList::new));

    }

    public void oneStepForAllPrg(ArrayList<PrgState> prgList) throws Exception {

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) p::oneStep)
                .collect(Collectors.toList());

        ArrayList<PrgState> newPrgList = executor.invokeAll(callList).stream().map(future -> {

            try {
                return future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } catch (Exception e) {
                System.err.println(e.toString());
            }
            return null;
        }).filter(Objects::nonNull)
                .collect(Collectors.toCollection(ArrayList::new));
        //add the new created threads to the list of existing threads
        prgList.addAll(newPrgList);
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        repo.setList(prgList);
    }

    public RepoInterface getRepo(){
        return repo;
    }
}

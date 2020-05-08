package Domain.ProgramState;

import Domain.Values.Value;

import java.util.ArrayList;
import java.util.HashMap;
import Exception.MyException;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Heap implements  MyIHeap<Integer, Value>{

    private Map<Integer, Value> dictionary;
    private static AtomicInteger freePos = new AtomicInteger(0);
    public Heap() {
        dictionary = new HashMap<>();
    }

    @Override
    public synchronized Value getValue(Integer symbol) {
        return dictionary.get(symbol);
    }
    @Override
    public synchronized void update(Integer id, Value val) {
        dictionary.put(id,val);
    }


    @Override
    public synchronized boolean isDefined(Integer id) {
        return dictionary.containsKey(id);
    }


    @Override
    public synchronized Value lookup(Integer id) throws Exception {
        if (isDefined(id))return getValue(id);
        throw new MyException("Variable "+id.toString()+ " is not defined.\n");
    }

    @Override
    public synchronized String toString() {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<Integer, Value> entry : dictionary.entrySet()) {
            result.append(entry.getKey().toString()).append(" ---> ").append(entry.getValue().toString()).append("\n");
        }
        return result.toString();
    }

    @Override
    public synchronized Map<Integer,Value> getContent() {
        return dictionary;
    }

    @Override
    public synchronized void setContent(Map<Integer, Value> x) {

        dictionary = x;
    }

    public static int IncAndGet(){
        return freePos.incrementAndGet();
    }


    @Override
    public synchronized List<Pair<String,String>> getToString() {
        List<Pair<String,String>> list1= new ArrayList<>();
        for (Map.Entry k:dictionary.entrySet()
        ) {
            list1.add(new Pair<>(k.getKey().toString(), k.getValue().toString()));
        }
        return list1;
    }

}

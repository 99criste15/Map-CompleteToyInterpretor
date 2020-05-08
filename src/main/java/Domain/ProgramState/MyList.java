package Domain.ProgramState;
import Domain.Values.Value;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MyList implements MyIList<Value> {
    private LinkedList<Value> outList;
    public MyList() {
        this.outList = new LinkedList<>();
    }

    @Override
    public synchronized void addBack(Value obj)
    {
        outList.addLast(obj);
    }

    @Override
    public synchronized List<String> getToString() {
        List<String> result =new ArrayList<>();
        for (Value value: outList
        ){result.add(value.toString());}
        return result;
    }

    @Override
    public synchronized String toString() {
        StringBuilder result = new StringBuilder();
        for (Value t : outList) {
            result.append(t.toString()).append("\n");
        }
        return result.toString();
    }
}

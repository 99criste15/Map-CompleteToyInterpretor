package Domain.ProgramState;

import Domain.Values.Value;
import Exception.MyException;
import io.vavr.Tuple2;
import io.vavr.collection.LinkedHashMap;
import io.vavr.collection.Map;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class MyDictionary implements MyIDictionary<String, Value>{
    private final Map<String, Value> dictionary;

    public MyDictionary(Map<String, Value> dict) {
        dictionary = dict;
    }

    public MyDictionary() {
        dictionary = LinkedHashMap.empty();
    }

    @Override
    public Value getValue(String symbol) {
        return dictionary.get(symbol).get();
    }

    @Override
    public MyDictionary update(String id, Value val) {


        return new MyDictionary(dictionary.put(id, val));

    }

    @Override
    public MyIDictionary<String, Value> delete(String id) {

        return new MyDictionary(dictionary.remove(id));

    }

    @Override
    public boolean isDefined(String id) {
        return dictionary.containsKey(id);
    }


    @Override
    public Value lookup(String id) throws Exception {
        if (isDefined(id)) return getValue(id);
        throw new MyException("Variable " + id + " is not defined.\n");
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Tuple2 entry : dictionary) {
            result.append(entry._1).append(" ---> ").append(entry._2.toString()).append("\n");
        }
        return result.toString();
    }

    @Override
    public Map<String, Value> getContent() {
        return dictionary;
    }

    @Override
    public List<Pair<String,String>> getToString() {
        List<Pair<String,String>> list1= new ArrayList<>();

        for (Tuple2 k:dictionary
             ) {
            list1.add(new Pair<String, String>(k._1.toString(),k._2.toString()));
        }
        return list1;
    }

}

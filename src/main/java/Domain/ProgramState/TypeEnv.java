package Domain.ProgramState;

import java.util.List;

import Domain.Types.Type;
import Exception.MyException;
import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import javafx.util.Pair;

public class TypeEnv implements MyIDictionary<String, Type>{
    private final Map<String, Type> dictionary;

    public TypeEnv() {
        dictionary = HashMap.empty();
    }

    public TypeEnv(Map<String,Type> map){

        dictionary = map;
    }

    @Override
    public Type getValue(String symbol) {
        return dictionary.get(symbol).get();
    }

    @Override
    public MyIDictionary<String, Type> update(String id, Type val) {

        return new TypeEnv(dictionary.put(id, val));

    }

    @Override
    public MyIDictionary<String, Type> delete(String id) {
        return new TypeEnv(dictionary.remove(id));
    }

    @Override
    public boolean isDefined(String id) {
        return dictionary.containsKey(id);
    }


    @Override
    public Type lookup(String id) throws Exception {
        if (isDefined(id)) return getValue(id);
        throw new MyException("Variable " + id + " is not defined.\n");
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Tuple2<String, Type> entry : dictionary) {
            result.append(entry._1).append(" ---> ").append(entry._2.toString()).append("\n");
        }
        return result.toString();
    }

    @Override
    public Map<String, Type> getContent() {
        return dictionary;
    }

    @Override
    public List<Pair<String,String>> getToString() {
        return null;
    }

}

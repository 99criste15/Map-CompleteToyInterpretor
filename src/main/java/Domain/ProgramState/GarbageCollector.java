package Domain.ProgramState;

import Domain.Values.RefValue;
import Domain.Values.Value;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GarbageCollector {

    static public Collection<Value> toDict(ArrayList<PrgState> list) {
        Collection<Value> symTableValues = new LinkedList<>();
        list.forEach(e -> e.getSymTable().getContent().forEach((k, v) -> symTableValues.add(v)));
        return symTableValues;
    }

    static public Map<Integer, Value> safeGarbageCollector(List<Integer> tablesAddr, Map<Integer, Value>
            heap) {
        return heap.entrySet().stream()
                .filter(e -> tablesAddr.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    static public List<Integer> getAddrFromTables(Collection<Value> symTableValues, Collection<Value> heapValues) {
        return Stream.concat(symTableValues.stream(), heapValues.stream())
                .filter(RefValue::isRefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.getAddr();
                })
                .collect(Collectors.toList());
    }

}

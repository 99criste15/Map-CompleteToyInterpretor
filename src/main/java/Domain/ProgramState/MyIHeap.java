package Domain.ProgramState;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public interface MyIHeap<T,V> {
    V getValue(T key);
    void  update(T id, V val);

    boolean isDefined(T id);
    V lookup(T id) throws Exception;
    Map<T,V> getContent();
    void setContent(Map<T, V> x);

    List<Pair<String,String>> getToString();
}

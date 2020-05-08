package Domain.ProgramState;
import io.vavr.collection.Map;
import javafx.util.Pair;

import java.util.List;

public interface MyIDictionary<T,V>  {
    V getValue(T key);
    MyIDictionary<T,V>  update(T id, V val);
    MyIDictionary<T,V>  delete(T id);

    boolean isDefined(T id);
    V lookup(T id) throws Exception;
    Map<T,V> getContent();
    List<Pair<String,String>> getToString();



}

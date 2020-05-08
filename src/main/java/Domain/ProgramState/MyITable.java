package Domain.ProgramState;

import java.util.List;
import java.util.Map;

public interface MyITable<T,V> {

    V getValue(T key);
    void update(T id, V val);
    boolean isDefined(T id);
    V lookup(T id) throws Exception;
    void close(T key) throws  Exception;
    void open(T key) throws  Exception;
    int read(T key) throws  Exception;
    Map<T,V> getContent();
    List<String> getToString();

}

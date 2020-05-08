package Domain.ProgramState;

import java.util.List;

public interface MyIList<T> {
    void addBack(T obj);
    List<String> getToString();
}

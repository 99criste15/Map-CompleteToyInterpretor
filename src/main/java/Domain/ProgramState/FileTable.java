package Domain.ProgramState;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import Domain.Values.Value;
import Exception.MyException;

public class FileTable implements MyITable<String, BufferedReader> {
    private Map<String, BufferedReader> dictionary;

    public FileTable() {
        dictionary = new HashMap<>();

    }

    @Override
    public synchronized BufferedReader getValue(String symbol) {


        return dictionary.get(symbol);
    }

    @Override
    public synchronized void update(String id, BufferedReader val) {

        dictionary.put(id, val);
    }

    @Override
    public synchronized boolean isDefined(String id) {

        return dictionary.containsKey(id);
    }


    @Override
    public synchronized List<String> getToString() {
        return new ArrayList<>(dictionary.keySet());
    }


    @Override
    public synchronized BufferedReader lookup(String id) throws Exception {
        if (isDefined(id)) {
            BufferedReader bo = getValue(id);
            if (bo == null)
                throw new MyException("there is no file associated to this string id\n");
            return bo;
        }
        throw new MyException("File " + id + " is not defined.\n");
    }

    @Override
    public synchronized void close(String fileName) throws Exception {

        BufferedReader br = lookup(fileName);
        br.close();
        dictionary.remove(fileName);
    }

    @Override
    public synchronized void open(String fileName) throws FileNotFoundException {

        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        update(fileName, reader);
    }

    @Override
    public synchronized int read(String key) throws Exception {

        BufferedReader reader = getValue(key);
        String line = reader.readLine();
        if (line == null || line.equals("")) return 0;
        else return Integer.parseInt(line);
    }

    @Override
    public synchronized Map<String, BufferedReader> getContent() {
        return dictionary;
    }


    @Override
    public synchronized String toString() {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, BufferedReader> entry : dictionary.entrySet()) {
            result.append(entry.getKey()).append("\n");
        }
        return result.toString();
    }




}


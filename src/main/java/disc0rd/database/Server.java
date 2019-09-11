package disc0rd.database;

import java.util.ArrayList;
import java.util.HashMap;

public class Server {

    private int _id;
    private ArrayList<Sub> _subs;
    private HashMap<String, Boolean> _settings;

    public Server(int id) {
        this(id, new HashMap<String, Boolean>());
    }

    public Server(int id, HashMap<String, Boolean> settings) {
        _id = id;
        _subs = new ArrayList<Sub>();
        _settings = settings;
    }

    public int getId() {
        return _id;
    }

    public ArrayList<Sub> getSubs() {
        return _subs;
    }

    public Server addSub(Sub sub) {
        if (!_subs.contains(sub)) {
            _subs.add(sub);
        }
        return this;
    }

    public Server removeSubs(Sub sub) {
        if(_subs.contains(sub)) {
            _subs.remove(sub);
        }
        return this;
    }

    public HashMap<String, Boolean> getSettings() {
        return _settings;
    }
}

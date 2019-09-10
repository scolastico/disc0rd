package disc0rd.database;

import java.util.ArrayList;

public class Server {

    private int _id;
    private ArrayList<Sub> _subs;

    public Server(int id) {
        _id = id;
        _subs = new ArrayList<Sub>();
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

}

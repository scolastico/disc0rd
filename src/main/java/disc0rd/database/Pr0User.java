package disc0rd.database;

import java.util.ArrayList;

public class Pr0User {

    private String _username;
    private ArrayList<Sub> _subs;

    public Pr0User(String username) {
        _username = username;
        _subs = new ArrayList<Sub>();
    }

    public String getUsername() {
        return _username;
    }

    public ArrayList<Sub> getSubs() {
        return _subs;
    }

    public Pr0User addSub(Sub sub) {
        if (!_subs.contains(sub)) {
            _subs.add(sub);
        }
        return this;
    }

    public Pr0User removeSubs(Sub sub) {
        if(_subs.contains(sub)) {
            _subs.remove(sub);
        }
        return this;
    }

}

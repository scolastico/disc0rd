package disc0rd.database;

public class Sub {

    private Server _server;
    private Pr0User _pr0User;
    private int _channelId;

    public Sub(Server server, Pr0User pr0User, int channelId) {
        _server = server;
        _pr0User = pr0User;
        _channelId = channelId;
    }

    public Server getServer() {
        return _server;
    }

    public Pr0User getPr0User() {
        return _pr0User;
    }

    public int getChannelId() {
        return _channelId;
    }

}

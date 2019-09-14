package disc0rd.database;

public class Module_DB_Settings {

    private static Module_DB_Settings _instance = null;

    private Module_DB_Settings() {

        try {
            BaseConnector.getInstance().getConnection().createStatement().executeQuery(
                        "CREATE TABLE IF NOT EXISTS ` PREFIX settings` (\n" +
                            "\t`id` int NOT NULL auto_increment,\n" +
                            "\t`serverId` int NOT NULL,\n" +
                            "\t`allowAdmin` bit(1) NOT NULL DEFAULT `0`,\n" +
                            "\tPRIMARY KEY( `id`)\n" +
                            ");"
            );
        } catch (Exception e) {
            BaseConnector.HandleSqlError(e);
        }

    }

    public static Module_DB_Settings getInstance() {
        if(_instance == null) {
            _instance = new Module_DB_Settings();
        }
        return _instance;
    }

}

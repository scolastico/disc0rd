package disc0rd;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class VersionController {

    private static String version = "";
    private static String commit = "";
    private static String time = "";

    public static String getTime() {
        if (time == "") {
            try {
                String versionInformation = new VersionController().versionInformation();
                JSONObject object = new JSONObject(versionInformation);
                time = object.getString("git.commit.time");
            } catch (Exception e) {
                return "error";
            }
        }
        return time;
    }

    public static String getVersion() {
        if (version == "") {
            try {
                String versionInformation = new VersionController().versionInformation();
                JSONObject object = new JSONObject(versionInformation);
                version = object.getString("git.build.version");
            } catch (Exception e) {
                return "error";
            }
        }
        return version;
    }

    public static String getCommit() {
        if (commit == "") {
            try {
                String versionInformation = new VersionController().versionInformation();
                JSONObject object = new JSONObject(versionInformation);
                commit = object.getString("git.commit.id.abbrev");
            } catch (Exception e) {
                return "error";
            }
        }
        return commit;
    }

    public static String getVersionsCode() {
        return "$v=" + getVersion() + "$c=" + getCommit();
    }

    public String versionInformation() {
        return readGitProperties();
    }

    private String readGitProperties() {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("git.properties");
        try {
            return readFromInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[ERROR] This build is corrupt!");
            System.exit(1);
            return "Version information could not be retrieved";
        }
    }

    private String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = br.readLine()) != null) {
            resultStringBuilder.append(line).append("\n");
        }
        return resultStringBuilder.toString();
    }

}



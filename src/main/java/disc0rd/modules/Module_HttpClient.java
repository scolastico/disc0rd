package disc0rd.modules;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Module_HttpClient {

    public String makeRequest(String path) throws IOException {
        URL url = new URL(path);
        URLConnection uc = url.openConnection();
        uc.setRequestProperty("X-Requested-With", "Curl");
        InputStreamReader inputStreamReader = new InputStreamReader(uc.getInputStream());
        return IOUtils.toString(inputStreamReader);
    }

}

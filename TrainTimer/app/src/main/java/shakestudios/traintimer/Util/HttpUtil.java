package shakestudios.traintimer.Util;

/**
 * Created by abbm on 5/16/2016.
 */

import android.os.StrictMode;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpUtil {

    public String  getclient() throws Exception {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet("http://localhost:8080/pixo-web/metroTimings/getTimings");
        HttpResponse response = client.execute(get);
        String teest =response.getEntity().toString();
        StatusLine entity = response.getStatusLine();
        int instream = entity.getStatusCode();
        if (instream < 1000) {
            return "from if";



        }
        return "fuck";
    }

    private static String read(InputStream instream) {
        StringBuilder sb = null;
        try {
            sb = new StringBuilder();
            BufferedReader r = new BufferedReader(new InputStreamReader(
                    instream));
            for (String line = r.readLine(); line != null; line = r.readLine()) {
                sb.append(line);
            }

            instream.close();

        } catch (IOException e) {
        }
        return sb.toString();

    }
  }



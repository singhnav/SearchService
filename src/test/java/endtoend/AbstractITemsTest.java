package endtoend;

import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import server.SearchServer;

import java.io.IOException;

public abstract class AbstractITemsTest {
    CloseableHttpClient client = HttpClients.createDefault();
    SearchServer server = new SearchServer();

    @Before
    public void startServer() throws Exception {
        server.start();
    }

    public SearchResponse hit(String uri) throws IOException {
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = client.execute(httpGet);
        StatusLine statusLine = response.getStatusLine();
        String content = EntityUtils.toString(response.getEntity());
        response.close();
        return new SearchResponse(statusLine.getStatusCode(), content);
    }

    @After
    public void stopServer() throws Exception {
        server.stop();
    }

    public static class SearchResponse {
        public SearchResponse(int statusCode, String content) {
            this.statusCode = statusCode;
            this.content = content;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public String getContent() {
            return content;
        }

        int statusCode;
        String content;
    }

}
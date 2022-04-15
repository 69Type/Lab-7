import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;

public class MyRequest {
    static String path = "/";
    final static int timeout = 2000;
    final static int readTimeout = 2000;

    public static String httpsRequest(String url) throws IOException {
        final URL _url = new URL(null, url);
        System.out.println(_url.getProtocol());
        final HttpsURLConnection connection = (HttpsURLConnection) _url.openConnection();
        connection.setRequestProperty("Accept-Charset", "UTF-8");
        connection.setConnectTimeout(timeout);
        connection.setReadTimeout(readTimeout);
        connection.setRequestMethod("GET");

        try (final InputStream inputStream = connection.getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null){
                response.append(line);
            }
            return response.toString();
        }
    }


    public static String httpRequest(String host, int port) throws IOException {

        try {
            URL url = new URL(host);
            host = url.getHost();
            path = url.getPath();
        } catch (MalformedURLException e){
            System.out.println();
        }

        Socket socket = new Socket(host, port);
        socket.setSoTimeout(timeout);

        OutputStream outStream = socket.getOutputStream();
        InputStream inStream = socket.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));

        PrintWriter writer = new PrintWriter(new BufferedOutputStream(outStream));

        writer.printf("GET %s HTTP/1.1\r\nHost: %s\r\nConnection: close\r\n\n", path, host);

        // System.out.printf("GET %s HTTP/1.1\r\nHost: %s\r\nConnection: close\r\n\n", path, host);

        writer.flush();

        StringBuilder text = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null){
            text.append(line);
        }

        socket.close();

        return text.toString();
    }

}
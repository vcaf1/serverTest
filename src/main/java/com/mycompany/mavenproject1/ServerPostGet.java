/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Victoria
 */
public class ServerPostGet {
    


    private static HttpURLConnection con;

    public static void main(String[] args) {
        try {

            // Bind to port 8080
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(9123), 0);

            // Adding '/test' context
            httpServer.createContext("/test", new ServerPostGet.TestHandler());
            httpServer.createContext("/info", new ServerPostGet.InfoHandler());

            // Start the server
            httpServer.start();

        } catch (IOException ex) {
            Logger.getLogger(ServerTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    static class InfoHandler implements HttpHandler {

        public void handle(HttpExchange t) throws IOException {
            String response = "Use /get to download a PDFFFF";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    // Handler for '/test' context
    static class TestHandler implements HttpHandler {

        public void handle(HttpExchange he) throws IOException {
            System.out.println("Serving the request");
            byte[] data;

            // Serve for POST requests only
            if (he.getRequestMethod().equalsIgnoreCase("POST")) {

                try {

                    // REQUEST Headers
                    Headers requestHeaders = he.getRequestHeaders();
                    Set<Map.Entry<String, List<String>>> entries = requestHeaders.entrySet();

                    int contentLength = Integer.parseInt(requestHeaders.getFirst("Content-length"));

                    // REQUEST Body
                    InputStream is = he.getRequestBody();

                    System.out.println(is);

                    data = new byte[contentLength];
                    int length = is.read(data);

                    // RESPONSE Headers
                    Headers responseHeaders = he.getResponseHeaders();

                    // Send RESPONSE Headers
                    he.sendResponseHeaders(HttpURLConnection.HTTP_OK, contentLength);

                    // RESPONSE Body
                    OutputStream os = he.getResponseBody();

                    os.write(data);
                    System.out.println(data);
                    //throw new IllegalAccessException("demo");

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                    String url = "https://web.postman.co/build/workspace/My-Workspace~e5828086-c33d-4b46-826d-f34f3d991609/request/13439854-4c2b9c67-9fbf-4e0a-886f-3c2fa4e228bb";
                    URL myurl = new URL(url);
                    con = (HttpURLConnection) myurl.openConnection();

                    StringBuilder content;
                    con.setRequestMethod("GET");

                    try (BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()))) {

                        String line;
                        content = new StringBuilder();

                        while ((line = in.readLine()) != null) {

                            content.append(line);
                            content.append(System.lineSeparator());
                        }
                    }

                    System.out.println(content.toString());

                    con.disconnect();
                    he.close();
                }

            } else if (he.getRequestMethod().equalsIgnoreCase("Get")) {
                try {
                    String response = "Use /get to download a PDF";
                    he.sendResponseHeaders(200, response.length());
                    OutputStream os = he.getResponseBody();
                    os.write(response.getBytes());
                    Headers requestHeaders = he.getRequestHeaders();
                    Set<Map.Entry<String, List<String>>> entries = requestHeaders.entrySet();

                    int contentLength = Integer.parseInt(requestHeaders.getFirst("Content-length"));
                    data = new byte[contentLength];
                    System.out.println(data);
                    os.close();

                } catch (Exception e) {
                    System.out.println("caught in main.");
                }

            }

        }

    }

}
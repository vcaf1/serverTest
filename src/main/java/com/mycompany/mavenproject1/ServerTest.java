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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Victoria
 */
public class ServerTest {

    private static HttpURLConnection con;

    public static void main(String[] args) {
        try {

            // Bind to port 8080
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(9876), 0);

            // Adding '/test' context
            httpServer.createContext("/test", new ServerTest.TestHandler());
            httpServer.createContext("/info", new ServerTest.InfoHandler());

            // Start the server
            httpServer.start();

        } catch (IOException ex) {
            Logger.getLogger(ServerTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    static class InfoHandler implements HttpHandler {

        public void handle(HttpExchange t) throws IOException {
            String response = "Use /get to download a PDF";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    // Handler for '/test' context
    static class TestHandler implements HttpHandler {

        String id;
        Modules mod = new Modules();
        ArrayList<LuchtModule> lijst = new ArrayList<>();

        public void handle(HttpExchange he) throws IOException {
            System.out.println("Serving the request");
            byte[] data;

            // Serve for POST requests only
            if (he.getRequestMethod().equalsIgnoreCase("POST")) {
                System.out.println("Serving the request");

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
                String dataJsonformaat = new String(data);
                JSONArray json = new JSONArray();
                JSONParser parser = new JSONParser();
                try {
                    json = (JSONArray) parser.parse(dataJsonformaat);
                } catch (ParseException pe) {
                    System.out.println(pe.getMessage());
                }
                for (Object o : json) {
                    JSONObject jsonobject = (JSONObject) o;
                    LuchtModule module = new LuchtModule();
                    try {
                        id = (String) jsonobject.get("vs");

                        module.setId(id);
                        lijst.add(module);

                        mod.voegtoe(module);

                    } catch (Exception e) {
                        System.out.println(e.getMessage());

                    }
                }
                String moduleId = lijst.get(1).id;
                System.out.println("Original String: " + moduleId);
                try {

                    try {

                        StringBuilder output = new StringBuilder("");
                        for (int i = 0; i < moduleId.length(); i += 2) {
                            String str = moduleId.substring(i, i + 2);
                            output.append((char) Integer.parseInt(str, 16));
                        }
                    System.out.println("Ascii String: " + output.toString());
                                    os.write(output.toString().getBytes());

                    } catch (Exception e) {

                    }

                    //String asciiEquivalent = hexToASCII(demoString);
                    //ASCII value obtained from Hex value
                    //System.out.println("Ascii String: " + asciiEquivalent);
                    //String hexEquivalent = asciiToHex(asciiEquivalent);
                    //Hex value of original String
                    //System.out.println("Hex String: " + hexEquivalent);
                } catch (Exception e) {
                    System.out.print("The hex");
                }


                System.out.println("Data: " + data);
                System.out.println("String : " + new String(data));
                he.close();
                //throw new IllegalAccessException("demo");

            } else {
                System.out.print(he);
            }
        }

        public String asciiToHex(String asciiValue) {
            char[] chars = asciiValue.toCharArray();
            StringBuffer hex = new StringBuffer();
            for (int i = 0; i < chars.length; i++) {
                hex.append(Integer.toHexString((int) chars[i]));
            }
            return hex.toString();
        }

        private String hexToASCII(String hexValue) {
            StringBuilder output = new StringBuilder("");
            String string = new String();
            for (int i = 0; i < hexValue.length(); i += 2) {
                String str = hexValue.substring(i, i + 2);
                output.append((char) Integer.parseInt(str, 16));
            }
            return output.toString();
        }

    }

}

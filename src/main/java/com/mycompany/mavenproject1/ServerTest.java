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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Victoria
 */
public class ServerTest {
    
    public ServerTest(){
        try{
            Connection con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();
            ResultSet result= stat.executeQuery("select * from luchtmoduledatas");
            while(result.next()){
                LuchtModule lchtmod = new LuchtModule();
                lchtmod.setValueTem(result.getInt("LuchtTemperatuur"));
                lchtmod.setValueHum(result.getInt("LuchtHumidity"));
                System.out.println(lchtmod.getValueHum());
            }
        }catch(SQLException e){
            
        }
    }

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
                        String Payload = (String) jsonobject.get("vs");
                        String HumidityHex = Payload.substring(2, 4);
                        String TemperatuurHex = Payload.substring(6);
                        Integer HumidityDec = Integer.parseInt(HumidityHex, 16);
                        Integer TemperatuurDec = Integer.parseInt(TemperatuurHex, 16);

                        module.setValueHum(HumidityDec);
                        module.setValueTem(TemperatuurDec);
                        lijst.add(module);


                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        System.out.println("Couldn't find the vs atribute");

                    }
                }
                int moduleHumidity = lijst.get(0).valueHum;
                int moduleTemperatuur = lijst.get(0).valueTem;
                
                System.out.println("Humidity: " + moduleHumidity);
                System.out.println("Temperatuur: " + moduleTemperatuur);

                System.out.println("Json formaat String : " + new String(data));
                he.close();
                //throw new IllegalAccessException("demo");

            } else {
                System.out.print(he);
            }
        }

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Victoria
 */
public class Jsonobject {

    public Jsonobject() {
    }

    public JSONObject fillkunstwerkgegevens() {
        JSONObject jo = new JSONObject();
        jo.put("name", "jon doe");
        jo.put("age", "22");
        jo.put("city", "chicago");
        return jo;
    }
}

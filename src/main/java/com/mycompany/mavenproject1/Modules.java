/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import java.util.ArrayList;

/**
 *
 * @author Victoria
 */
public class Modules {

    private final ArrayList<LuchtModule> lijst;

    public Modules() {
        lijst = new ArrayList<>();
    }
    /**
    * Met deze methode voeg je de id van alle kunstwerken van de klass allkunst.
    * Je gebruik het bij de JSON_CSV klasse wanneer de kunstwerken worden toegevoegd aan de arraylist van deze klasse.
    */
    public void voegtoe(LuchtModule k) {
        getLijst().add(k);
    }
    public ArrayList<LuchtModule> getLijst() {
        return lijst;
    }
}

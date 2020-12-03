/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

/**
 *
 * @author Victoria
 */
public class LuchtModule {

    int id, valueTem,valueHum;

    public LuchtModule() {

    }

    public LuchtModule(int valueTem, int valueHum) {
        this.valueTem = valueTem;
        this.valueHum = valueHum;
    }

    public LuchtModule(int id, int valueTem, int valueHum) {
        this.id = id;
        this.valueTem = valueTem;
        this.valueHum = valueHum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValueTem() {
        return valueTem;
    }

    public void setValueTem(int valueTem) {
        this.valueTem = valueTem;
    }

    public int getValueHum() {
        return valueHum;
    }

    public void setValueHum(int valueHum) {
        this.valueHum = valueHum;
    }


}

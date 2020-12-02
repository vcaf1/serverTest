/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Victoria
 */
public class Moduleoverzicht {
    
    public Moduleoverzicht(){
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
    
}

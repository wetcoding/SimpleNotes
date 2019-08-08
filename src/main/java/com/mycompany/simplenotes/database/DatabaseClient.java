/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.simplenotes.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Mashakaev
 */
public class DatabaseClient {
    
    static Connection connection = null;
    
    private static void connect(){
       try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/notes_database"+
                "?verifyServerCertificate=false"+
                "&useSSL=false"+
                "&allowPublicKeyRetrieval=true"+
                "&requireSSL=false"+
                "&useLegacyDatetimeCode=false"+
                "&amp"+
                "&serverTimezone=UTC";
            connection = DriverManager.getConnection(url, "root", "root");           
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static ArrayList<Note> getAllNotes(){
        
        
        
        ArrayList<Note> result=new ArrayList<Note>();
        
       
        try{
            connect();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Notes");
            while (rs.next()) {
                result.add(new Note(rs.getString("title"),rs.getString("note")));
            }

            rs.close();
            statement.close();
            connection.close();

        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        
        return result;
    }
}

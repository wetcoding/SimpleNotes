/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.simplenotes.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Mashakaev
 */
public class DatabaseClient {
    
    private static Connection connection = null;
    private static boolean isConnected=false;
    
    public static boolean connect(){
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
            isConnected=true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            isConnected=false;
        }
       
       return isConnected;
    }
    
    public static ArrayList<Note> getNotes(String key){
              
        ArrayList<Note> result=new ArrayList<>();
        
        if(!isConnected)
            return result;
       
        try{            
            String query="SELECT * FROM notes WHERE title LIKE ? OR note LIKE ?;";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1, "%"+key+"%");
            preparedStatement.setString(2, "%"+key+"%");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                result.add(new Note(rs.getInt("id"),rs.getString("title"),rs.getString("note")));
            }

            isConnected=false;
            rs.close();
            preparedStatement.close();
            connection.close();

        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        
        return result;
    }
    
    public static boolean addNote(String title,String note){
        try{
            String query = "INSERT INTO notes (title, note) VALUES (?,?);";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2,note);
            preparedStatement.executeUpdate();
            return true;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public static boolean deleteNote(int id){
        try{
            String query = "DELETE FROM notes WHERE id=?;";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}

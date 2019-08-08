/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.simplenotes.database;

import java.util.ArrayList;

/**
 *
 * @author Mashakaev
 */
public class DatabaseClient {
    
    
    public static ArrayList<Note> getAllNotes(int count){
        ArrayList<Note> result=new ArrayList<Note>();
        
        for(int i=0;i<count;i++){
            result.add(new Note("Заголовок"+String.valueOf(i),"Текст для  заголовка"+String.valueOf(i)));
        }
        
        return result;
    }
}

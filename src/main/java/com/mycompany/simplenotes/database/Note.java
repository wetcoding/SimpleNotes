/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.simplenotes.database;

/**
 *
 * @author Mashakaev
 */
public class Note {
    String title;
    String text;
    
    public Note (String title,String text){
        this.title=title;
        this.text=text;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
    
    
}

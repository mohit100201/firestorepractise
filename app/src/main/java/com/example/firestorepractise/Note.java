package com.example.firestorepractise;

import com.google.firebase.firestore.Exclude;

public class Note {
    private String Title;
    private String Description;

    private String documentID;

    public Note() {
    }

    public Note(String title, String description) {
        this.Title = title;
       this.Description = description;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Exclude
    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }
}

package com.example.firestorepractise;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.ktx.Firebase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText title,description;
    Button saveBtn,loadBtn,updateBtn,deleteNote,deleteDescription,AddBtn;
    TextView text_view_data;

    FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference notebookRef=db.collection("notebook");
    private DocumentReference note=notebookRef.document("My first note");



    ListenerRegistration noteListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title=findViewById(R.id.edit_text_title);
        description=findViewById(R.id.edit_text_description);
//        saveBtn=findViewById(R.id.saveBtn);
        loadBtn=findViewById(R.id.loadBtn);
        text_view_data=findViewById(R.id.text_view_data);
//        updateBtn=findViewById(R.id.updateBtn);
//        deleteDescription=findViewById(R.id.deleteDescriptionBtn);
//        deleteNote=findViewById(R.id.DeleteNoteBtn);
        AddBtn=findViewById(R.id.AddBtn);



        AddBtn.setOnClickListener(v -> {
            String Title=title.getText().toString();
            String Description=description.getText().toString();
            Note note=new Note(Title,Description);

            notebookRef.add(note);


        });


        loadBtn.setOnClickListener(v -> {

            notebookRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    String data="";
                    for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                        Note note=documentSnapshot.toObject(Note.class);
                        String id=note.getDocumentID();
                        String title=note.getTitle();
                        String description= note.getDescription();
                        note.setDocumentID(documentSnapshot.getId());
                        data+="Title: "+title+"\n"+"Description: "+description+"\nID: "+id+"\n\n";

                    }

                    text_view_data.setText(data);


                }
            });



        });





    }


    @Override
    protected void onStart() {
        super.onStart();
        notebookRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    return;
                }

                String data="";
                for(QueryDocumentSnapshot documentSnapshot:value){
                    Note note=documentSnapshot.toObject(Note.class);
                    note.setDocumentID(documentSnapshot.getId());

                    String title=note.getTitle();
                    String description= note.getDescription();
                    String id=note.getDocumentID();
                    data+="Title: "+title+"\n"+"Description: "+description+"\nID: "+id+"\n\n";

                }

                text_view_data.setText(data);





            }
        });
    }
}
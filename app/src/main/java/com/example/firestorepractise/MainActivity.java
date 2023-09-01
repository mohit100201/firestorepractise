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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.ktx.Firebase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText title,description;
    Button saveBtn,loadBtn,updateBtn,deleteNote,deleteDescription;
    TextView text_view_data;

    FirebaseFirestore db=FirebaseFirestore.getInstance();


    ListenerRegistration noteListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title=findViewById(R.id.edit_text_title);
        description=findViewById(R.id.edit_text_description);
        saveBtn=findViewById(R.id.saveBtn);
        loadBtn=findViewById(R.id.loadBtn);
        text_view_data=findViewById(R.id.text_view_data);
        updateBtn=findViewById(R.id.updateBtn);
        deleteDescription=findViewById(R.id.deleteDescriptionBtn);
        deleteNote=findViewById(R.id.DeleteNoteBtn);

        loadBtn.setOnClickListener(v -> {
            FirebaseFirestore.getInstance().collection("notebook").document("My first note").get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()){
//                        String title=documentSnapshot.getString("Title");
//                        String description=documentSnapshot.getString("Description");
//
//                        // another way
////                        Map<String ,Object>note=documentSnapshot.getData();

                        Note note=documentSnapshot.toObject(Note.class);
                        String title=note.getTitle();
                        String description=note.getDescription();


                        text_view_data.setText("Title : "+title+"\n"+"Description : "+description);



                    }
                    else{
                        Toast.makeText(MainActivity.this, "Document does not exist!", Toast.LENGTH_SHORT).show();
                    }

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG,e.toString());

                }
            });



        });


        saveBtn.setOnClickListener(v -> {
            String Title=title.getText().toString();
            String Description=description.getText().toString();

//            Map<String,Object> note=new HashMap<>();
//            note.put("Title",Title);
//            note.put("Description",Description);
            Note note=new Note(Title,Description);

            db.collection("notebook").document("My first note").set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(MainActivity.this, "note saved", Toast.LENGTH_SHORT).show();


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                   Log.d(TAG,e.toString());

                }
            });







        });

        updateBtn.setOnClickListener(v -> {
            String Des=description.getText().toString();
            Map<String ,Object>note=new HashMap<>();
            note.put("description",Des);
//            // only this then overwrite whole document and make title null
//            FirebaseFirestore.getInstance().collection("notebook").document("My first note").set(note);
            // only overwrite the Description not title if not present then make one
//            FirebaseFirestore.getInstance().collection("notebook").document("My first note").set(note, SetOptions.merge());

            // this will also update descrption keeping title same but not add data field if not present

            FirebaseFirestore.getInstance().collection("notebook").document("My first note").update(note);




        });


        deleteDescription.setOnClickListener(v -> {
            Map<String,Object>note=new HashMap<>();
            note.put("description", FieldValue.delete());
            FirebaseFirestore.getInstance().collection("notebook").document("My first note").update(note);




        });

        deleteNote.setOnClickListener(v -> {

            FirebaseFirestore.getInstance().collection("notebook").document("My first note").delete();


        });









    }

    @Override
    protected void onStart() {
        super.onStart();
      FirebaseFirestore.getInstance().collection("notebook").document("My first note").addSnapshotListener(this,new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG,error.toString());
                    return;
                }

                if(value.exists()){
//                    String title=value.getString("Title");
//                    String description=value.getString("Description");
//
//
//                    text_view_data.setText("Title : "+title+"\n"+"Description : "+description);


                    Note note=value.toObject(Note.class);
                    String title=note.getTitle();
                    String description=note.getDescription();


                    text_view_data.setText("Title : "+title+"\n"+"Description : "+description);



                }
                else{
                    text_view_data.setText("");
                }


            }
        });

    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        noteListener.remove();
//    }
}
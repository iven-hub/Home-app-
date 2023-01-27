package com.example.finalproject;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

public class Activity_note extends AppCompatActivity {
    Button Addnote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        Addnote = findViewById(R.id.addnewnotebtn);

        Addnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_note.this, AddNoteActivity.class));
            }
        });
        //iniciando realmdb
        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();
        //acedendo ao atributos para guardar na base de dados
        RealmResults<Note> notesList = realm.where(Note.class).findAll().sort("createdTime", Sort.DESCENDING);

        //apanhar o recycleview do activity xlm
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //apanhar as informacoes do note
        MyNoteAdapter mynoteAdapter = new MyNoteAdapter(getApplicationContext(),notesList);
        recyclerView.setAdapter(mynoteAdapter);
        //alteracao na base de dados guardando dados
        notesList.addChangeListener(new RealmChangeListener<RealmResults<Note>>() {
            @Override
            public void onChange(RealmResults<Note> notes) {
                mynoteAdapter.notifyDataSetChanged();
            }
        });

    }
}
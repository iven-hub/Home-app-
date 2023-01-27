package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import io.realm.Realm;

public class AddNoteActivity extends AppCompatActivity {

    EditText titleInput,descriptionInput;
    MaterialButton saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        EditText titleInput = findViewById(R.id.titleinput);
        EditText descriptionInput = findViewById(R.id.descriptioninput);
        MaterialButton saveBtn = findViewById(R.id.savebtn);


        Realm.init(getApplicationContext());//iniciar a base de dados realm
        Realm realm = Realm.getDefaultInstance();//Construtor estático do Realm que retorna a instância do Realm

        saveBtn.setOnClickListener(new View.OnClickListener() {//botao save
            @Override
            public void onClick(View v) {
                //apanhar textos dos campos e o data
                String title = titleInput.getText().toString();
                String description = descriptionInput.getText().toString();
                long createdTime = System.currentTimeMillis();


                    realm.beginTransaction();//As transações são usadas para criar, atualizar e excluir objetos atomicamente dentro de uma DB realm.
                    Note note = realm.createObject(Note.class);
                    note.setTitle(title);
                    note.setDescription(description);
                    note.setCreatedTime(createdTime);
                    //Todas as alterações desde Realm.beginTransaction() são mantidas no disco e o Realm volta a ser somente leitura.
                    // Um evento é enviado para notificar todas as outras instâncias do Realm sobre a ocorrência de uma alteração.
                    realm.commitTransaction();
                    Toast.makeText(getApplicationContext(), "Note saved", Toast.LENGTH_SHORT).show();
                    finish();


            }
        });


    }
}



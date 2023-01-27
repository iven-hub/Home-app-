package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button Weather, note, calculator,notifybtn, quizbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Weather=findViewById(R.id.weather_button);
        note = findViewById(R.id.note_button);
        calculator = findViewById(R.id.calculator_button);
        notifybtn = findViewById(R.id.notify_button);
        quizbtn = findViewById(R.id.quiz_button);

        //notificacao versoes superiores
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){ //sefor vai criar um nova canal senao nao cria apenas para android d versao alta
            NotificationChannel channel = new NotificationChannel("Notification", "Notification", NotificationManager.IMPORTANCE_DEFAULT);//importancia da notificacao
            NotificationManager manager = getSystemService(NotificationManager.class);//gerente de servicos do sistema
            manager.createNotificationChannel(channel);//esse manager criara um canal de notifcacao
        }

        Weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WeatherActivity.class));
                Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_SHORT).show();

            }
        });

        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Activity_note.class));
                Toast.makeText(getApplicationContext(), "Notes Room", Toast.LENGTH_SHORT).show();
            }
        });

        calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CalculatorActivity.class));
                Toast.makeText(getApplicationContext(), "Calculator", Toast.LENGTH_SHORT).show();
            }
        });

        notifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //passar um construtor passa utilizar o notification depois o contexto e o chanel id com ajuda desse contrutor vamos criar nossa notificacao
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "Notification");
                builder.setContentTitle("Final Android Project");
                builder.setContentText("welcome to your home application");
                builder.setSmallIcon(R.drawable.ic_baseline_house_24);
                builder.setAutoCancel(true);//para quando deslizarmos e limpamos a aplicação
                //notificando o user com a ajuda do id
                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
                managerCompat.notify(1, builder.build());

            }
        });

        quizbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, QuizActivity.class));
                Toast.makeText(getApplicationContext(), "Start your quiz 🤔", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //menu
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.LogOut_conta:
                Intent Intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(Intent);
                return true;
            case R.id.definicoes:
                showSettings();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
    //aceder as definicoes do telemovel
    private void showSettings() {
        Intent settingsIntent = new Intent(Settings.ACTION_SETTINGS);
        if(settingsIntent.resolveActivity(getPackageManager())!= null){
            startActivity(settingsIntent);
        }else{
            Log.d("ImplicitIntents","Can't handle this!");
        }
    }


}
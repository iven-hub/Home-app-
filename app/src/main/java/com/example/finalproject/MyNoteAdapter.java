package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;

import io.realm.Realm;
import io.realm.RealmResults;
//extender recycle adpater defenir um classe interna para no adapter do nosso recycle e mas antes temos de implementar alguns metodos
public class MyNoteAdapter extends RecyclerView.Adapter<MyNoteAdapter.MyViewHolder> {

    Context context;
    RealmResults<Note> notesList;


    public MyNoteAdapter(Context context, RealmResults<Note> notesList) {//apanhar os valores das nossas variaveis
        this.context = context;//inflar layout
        this.notesList = notesList;
    }

    @NonNull
    @Override
    //vamos inflar nosso layout e dar aparencia a cada um das nossas coluna
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false));
    }


    @Override

    public void onBindViewHolder(@NonNull MyNoteAdapter.MyViewHolder holder, int position) {
        //ter acesso aos dados
        Note note = notesList.get(position);
        holder.titleOutput.setText(note.getTitle());
        holder.descriptionOutput.setText(note.getDescription());


        String formatedTime = DateFormat.getDateTimeInstance().format(note.getCreatedTime());
        holder.timeOutput.setText(formatedTime);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {//long click delete
            @Override
            public boolean onLongClick(View v) {

                PopupMenu menu = new PopupMenu(context,v);
                menu.getMenu().add("DELETE");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("DELETE")){
                            //delete the note
                            Realm realm = Realm.getDefaultInstance();//atualizar db
                            realm.beginTransaction();
                            note.deleteFromRealm();
                            realm.commitTransaction();
                            Toast.makeText(context,"Note deleted",Toast.LENGTH_SHORT).show();
                        }

                        return true;
                    }
                });
                menu.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }
    //esqueleto no nosso recycle
    public class MyViewHolder extends RecyclerView.ViewHolder{
        //vai apanhar as views do nosso recycle-view layout tipo um one create metodo

        TextView titleOutput;
        TextView descriptionOutput;
        TextView timeOutput;
      //  LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleOutput = itemView.findViewById(R.id.titleoutput);
            descriptionOutput = itemView.findViewById(R.id.descriptionoutput);
            timeOutput = itemView.findViewById(R.id.timeoutput);
           // mainLayout = itemView.findViewById(R.id.mainLayout);

        }
    }
}

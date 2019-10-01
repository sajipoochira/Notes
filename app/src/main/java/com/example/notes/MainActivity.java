package com.example.notes;

import androidx.annotation.NonNull;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;


public class MainActivity extends AppCompatActivity {
    static ArrayList<String> notes;
    static ArrayAdapter arrayAdapter;
    ListView List_View;
    int itemId;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater Menuinflate = getMenuInflater();
        Menuinflate.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.addnote)
        {
            Intent intent = new Intent(getApplicationContext(),CreateNoteActivity.class);
             startActivity(intent);

        }

        super.onOptionsItemSelected(item);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List_View = findViewById(R.id.List_View);
        notes = new ArrayList<>();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes", null);
        if (set == null) {
            notes.add("FirstNote");
        }else{

            notes = new ArrayList<>(set);
        }
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,notes);

        List_View.setAdapter(arrayAdapter);

        List_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), CreateNoteActivity.class);

                intent.putExtra("notes_id", position);
                startActivity(intent);
            }
        });

List_View.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            itemId = position;
        new AlertDialog.Builder(MainActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Delete !")
                .setMessage("Are you sure ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notes.remove(itemId);
                        arrayAdapter.notifyDataSetChanged();
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);

                        HashSet<String> set = new HashSet<>(MainActivity.notes);

                        sharedPreferences.edit().putStringSet("notes",set).apply();

                    }
                })
                .setNegativeButton("No", null)
                .show();

        return true;
    }

});


    }
}

package com.wes.project_1918083_tokopakaian;

import static com.wes.project_1918083_tokopakaian.DBmain.TABLENAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.wes.project_1918083_tokopakaian.databinding.ActivityMain4Binding;
import java.util.ArrayList;
public class MainActivity4 extends AppCompatActivity {
    DBmain dBmain;
    SQLiteDatabase sqLiteDatabase;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    private ActivityMain4Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain4Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        findId();
        dBmain = new DBmain(this);
        displayData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        binding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity4.this, AddContact.class);
                startActivity(a);
            }
        });
        dl = (DrawerLayout)findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this,dl,R.string.Open,R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(abdt);
        abdt.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nav_view = (NavigationView)findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_pp){
                    Intent a = new Intent(MainActivity4.this, MainActivity.class);
                    startActivity(a);
                }else if (id == R.id.nav_pw){
                    Intent a = new Intent(MainActivity4.this, MainActivity2.class);
                    startActivity(a);
                }else if (id == R.id.nav_by){
                    Intent a = new Intent(MainActivity4.this, MainActivity3.class);
                    startActivity(a);
                }else if (id == R.id.nav_cy){
                    Intent a = new Intent(MainActivity4.this, MainActivity4.class);
                    startActivity(a);
                }

                return true;
            }
        });
    }
    private void displayData() {
        sqLiteDatabase = dBmain.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+TABLENAME,null);
        ArrayList<Model> models = new ArrayList<>();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            byte[]avatar = cursor.getBlob(3);
            String nomer = cursor.getString(2);
            models.add(new Model(id,avatar,name,nomer));
        }
        cursor.close();
        myAdapter = new MyAdapter(this, R.layout.singledata,models,sqLiteDatabase);
        recyclerView.setAdapter(myAdapter);
    }
    private void findId() {
        recyclerView = findViewById(R.id.rv);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

}
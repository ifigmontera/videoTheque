package com.example.ifigm.mavideotheque.view.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.example.ifigm.mavideotheque.R;

import com.example.ifigm.mavideotheque.control.FilmBDD;

import com.example.ifigm.mavideotheque.view.Popup.PopupAdd;
import com.example.ifigm.mavideotheque.view.Popup.PopupRemove;
import com.example.ifigm.mavideotheque.view.Fragment.SectionPageAdapter;





public class MainActivity extends AppCompatActivity {


    private Context context;
    private FilmBDD filmBDD;
    private TabLayout tab;
    private ViewPager viewPager;
    private FloatingActionButton add;
    private FloatingActionButton min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =   (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        context = MainActivity.this;

        // Gestion des fragment
        tab = (TabLayout) findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.pager);
        add = (FloatingActionButton) findViewById(R.id.add);
        min = (FloatingActionButton) findViewById(R.id.min);
        viewPager.setAdapter(new SectionPageAdapter(getSupportFragmentManager()));
        tab.setupWithViewPager(viewPager);
        filmBDD = new FilmBDD(this);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupAdd popAdd = new PopupAdd(MainActivity.this, filmBDD);
                popAdd.show();
                Snackbar.make(v,"Film ajouté, veuillez mettre à jour",Snackbar.LENGTH_INDEFINITE).setAction("MAJ", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);

                    }
                }).show();
            }
        });

        min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupRemove popRemove = new PopupRemove(context, filmBDD);
                popRemove.show();
            }
        });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                finish();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_help:
                 intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }




}

package com.example.ifigm.mavideotheque.view.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import com.example.ifigm.mavideotheque.R;
import com.example.ifigm.mavideotheque.control.FilmAdapter;
import com.example.ifigm.mavideotheque.control.FilmBDD;
import com.example.ifigm.mavideotheque.model.Film;
import com.example.ifigm.mavideotheque.view.Popup.PopupAdd;
import com.example.ifigm.mavideotheque.view.Popup.PopupRemove;
import com.example.ifigm.mavideotheque.view.Fragment.SectionPageAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private Context context;
    private FilmBDD filmBDD;
    private TabLayout tab;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;

        // Gestion des fragment
        tab = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.pager);

        viewPager.setAdapter(new SectionPageAdapter(getSupportFragmentManager()));
        tab.setupWithViewPager(viewPager);
        filmBDD = new FilmBDD(this);




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
                PopupAdd popAdd = new PopupAdd(context, filmBDD);
                popAdd.show();
                finish();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_remove:
                PopupRemove popRemove = new PopupRemove(context, filmBDD);
                popRemove.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }




}

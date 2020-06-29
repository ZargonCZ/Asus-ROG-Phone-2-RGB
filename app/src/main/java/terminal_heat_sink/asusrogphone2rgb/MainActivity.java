package terminal_heat_sink.asusrogphone2rgb;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;


import terminal_heat_sink.asusrogphone2rgb.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private boolean fab_on = false;
    private String fab_on_shared_preference_key = "terminal_heat_sink.asusrogphone2rgb.fab_on";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        final Context context = getApplicationContext();

        SharedPreferences prefs = context.getSharedPreferences(
                "terminal_heat_sink.asusrogphone2rgb", Context.MODE_PRIVATE);

        fab_on = prefs.getBoolean(fab_on_shared_preference_key,false);
        if(!fab_on){
            if(savedInstanceState != null){
                fab_on = savedInstanceState.getBoolean("fab_on");
            }
        }


        if(fab_on){
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorON) ));
        }else{
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorBG) ));
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
                if(fab_on){
                    fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorBG) ));
                    SystemWriter.turn_on(false,getApplicationContext());
                    fab_on = false;
                }else{
                    fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorON) ));
                    SystemWriter.turn_on(true,getApplicationContext());
                    fab_on = true;
                }
                SharedPreferences prefs = context.getSharedPreferences(
                        "terminal_heat_sink.asusrogphone2rgb", Context.MODE_PRIVATE);
                prefs.edit().putBoolean(fab_on_shared_preference_key, fab_on).apply();

            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("fab_on", fab_on);
    }
}
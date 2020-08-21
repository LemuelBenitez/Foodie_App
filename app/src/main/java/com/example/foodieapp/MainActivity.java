package com.example.foodieapp;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
     private RecyclerView recyclerView;
     private ArrayList<MealItem> mealData;
     private MealAdapter mealAdapter;

     private int gridColumnCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        /*Receiver receiver = new Receiver(mealData);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Receiver.I_AM_HOME);
        registerReceiver(receiver, intentFilter);*/

        gridColumnCount = getResources().getInteger(R.integer.grid_column_count);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, gridColumnCount));
        mealData = new ArrayList<>();
        mealAdapter = new MealAdapter(this, mealData);
        recyclerView.setAdapter(mealAdapter);

        //loadMealData();
        loadMealData2();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, AddItemActivity.class),itemCode );
            }

        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mealAdapter = new MealAdapter(this, mealData);
        recyclerView.setAdapter(mealAdapter);

        recyclerView.setLayoutManager(new GridLayoutManager(this, gridColumnCount));

        Receiver receiver = new Receiver(mealData);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Receiver.I_AM_HOME);
        registerReceiver(receiver, intentFilter);
    }

    public void loadMealData2() {
        TypedArray meal_image = getResources().obtainTypedArray(R.array.meal_image);
        String[] meal_title = getResources().getStringArray(R.array.meal_title);
        String[] meal_info = getResources().getStringArray(R.array.meal_description);
        String[] meal_calories = getResources().getStringArray(R.array.meal_calories);
        String[] meal_ingredients = getResources().getStringArray(R.array.meal_ingredients);
        String[] meal_link = getResources().getStringArray(R.array.meal_links);

        for (int i = 0; i < meal_title.length; i++) {
            MealItem meal = new MealItem(meal_image.getResourceId(i, 0), meal_title[i], meal_info[i]);
            meal.setCalories(meal_calories[i])
                    .setIngredients(meal_ingredients[i])
                    .setLinkToRecipe(meal_link[i]);

            mealData.add(meal);
        }

        mealAdapter.notifyDataSetChanged();

        meal_image.recycle();
    }
    private void loadMealData() {
        mealData.clear();

        String[] mealTitles = getResources().getStringArray(R.array.meal_title);
        String[] mealDescriptions = getResources().getStringArray(R.array.meal_description);
        TypedArray mealImages = getResources().obtainTypedArray(R.array.meal_image);

        for( int i = 0; i < mealTitles.length; i++){
            mealData.add( new MealItem(mealImages.getResourceId(i,0),mealTitles[i],mealDescriptions[i]));
        }
        mealAdapter.notifyDataSetChanged();
        mealImages.recycle();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

     int addItem = 101;
    int itemCode= 0;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == itemCode) {
            if (resultCode == addItem) {

                MealItem meal = new MealItem(data.getByteArrayExtra("image"), data.getStringExtra("title"), data.getStringExtra("description"));
                meal.setCalories(data.getStringExtra("calories"))
                        .setIngredients(data.getStringExtra("ingredients"))
                        .setLinkToRecipe(data.getStringExtra("link"));

                mealData.add(meal);

                mealAdapter.notifyDataSetChanged();
            }
        }
    }
}
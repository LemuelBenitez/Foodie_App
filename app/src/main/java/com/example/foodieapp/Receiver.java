package com.example.foodieapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class Receiver extends BroadcastReceiver {

    private Context context;
    protected static final String I_AM_HOME = "com.example.I_AM_HOME";

    private ArrayList<MealItem> mealData;


    public Receiver(ArrayList<MealItem> mealData) {
        this.mealData = mealData;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("MyReceiver","Broadcast Received");
        this.context = context;
        String action = intent.getAction();

        int position = intent.getExtras().getInt("mealPosition");

        MealItem meal = mealData.get(position);
        Intent intent1 = new Intent(context, MealItemActivity.class);

        Toast.makeText(context, "Happy cooking "+meal.getTitle(), Toast.LENGTH_LONG).show();

        intent1.putExtra("title", meal.getTitle())
                .putExtra("description", meal.getDescription())
                .putExtra("calories", meal.getCalories())
                .putExtra("ingredients", meal.getIngredients())
                .putExtra("link", meal.getLinkToRecipe());

        if (meal.getByteArray() != null) {
            intent1.putExtra("image", meal.getByteArray());
        } else {
            intent1.putExtra("image", meal.getImageResource());
        }

        context.startActivity(intent1);

    }
}

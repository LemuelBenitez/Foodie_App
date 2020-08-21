package com.example.foodieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MealItemActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView titleView, descriptionView, caloriesView, ingredientsView;
    private String mealLink, mealTitle, mealDescription, mealCalories, mealIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_item);
        try {
            Bundle extras = getIntent().getExtras();
            mealTitle = extras.get("title").toString();
            mealDescription = extras.get("description").toString();
            mealCalories = extras.get("calories").toString();
            mealIngredients = extras.get("ingredients").toString();
            mealLink = extras.get("link").toString();

            imageView = (ImageView) findViewById(R.id.meal_image);
            titleView = (TextView) findViewById(R.id.meal_title);
            descriptionView = (TextView) findViewById(R.id.meal_description);
            caloriesView = (TextView) findViewById(R.id.meal_calories);
            ingredientsView = (TextView) findViewById(R.id.meal_ingredients);

            if (extras.get("image").getClass() == Integer.class) {
                Glide.with(this).load(extras.getInt("image")).into(imageView);
            } else {
                byte[] image = extras.getByteArray("image");
                imageView.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
            }
            titleView.setText(mealTitle);
            descriptionView.setText(mealDescription);
            caloriesView.setText(getResources().getString(R.string.caloriesPlaceHolder) + " " + mealCalories);
            ingredientsView.setText(mealIngredients);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public void onButtonClick(View view) { //Click Button to goto link of recipie
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mealLink));
        startActivity(intent);
    }


}
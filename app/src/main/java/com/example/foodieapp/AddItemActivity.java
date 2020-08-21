package com.example.foodieapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class AddItemActivity extends AppCompatActivity {

    private ImageView image;
    private EditText title, description, calories, ingredients, link;
    private Button addButton;
    private Bitmap placeHolderImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        image = (ImageView) findViewById(R.id.image_view);
        title = (EditText) findViewById(R.id.title);
        description = (EditText) findViewById(R.id.description);
        calories = (EditText) findViewById(R.id.calories);
        ingredients = (EditText) findViewById(R.id.ingredients);
        link = (EditText) findViewById(R.id.link);
    }

    public void onButtonClick(View view) {
        if (fieldsNotEmpty(title, description, calories, ingredients, link) && image != null) {

            Intent intent = new Intent();

            placeHolderImage = ((BitmapDrawable) image.getDrawable()).getBitmap();

            byte[] byteArray = getBytesFromBitmap(placeHolderImage, 100);

            intent.putExtra("image", byteArray)
                    .putExtra("title", title.getText().toString())
                    .putExtra("description", description.getText().toString())
                    .putExtra("calories", calories.getText().toString())
                    .putExtra("ingredients", ingredients.getText().toString())
                    .putExtra("link", link.getText().toString());

            setResult(101, intent);
            finish();
            return;
        }

        Snackbar.make(view, this.getResources().getString(R.string.something), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void setImage(View view) {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 0);
    }

    private boolean fieldsNotEmpty(EditText ... fields) {
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getText().toString().isEmpty())
                return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            Uri targetUri = data.getData();

            try {
                placeHolderImage = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                image.setImageBitmap(placeHolderImage);
            } catch (FileNotFoundException e) {
                Log.d("ERROR", e.getMessage().toUpperCase());
            }
        }
    }

    private byte[] getBytesFromBitmap(Bitmap image, int quality) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        image.compress(Bitmap.CompressFormat.JPEG, quality, stream);

        return stream.toByteArray();
    }
    private Button yuGiOh;

}
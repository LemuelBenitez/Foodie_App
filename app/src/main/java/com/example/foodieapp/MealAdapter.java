package com.example.foodieapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MealItem> mealData;
    private int item = -1;

    public MealAdapter(Context context, ArrayList<MealItem> mealItemArrayList){
        this.context = context;
        mealData = mealItemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     MealItem currentMeal = mealData.get(position);
     holder.bindItem(currentMeal);
    }

    @Override
    public int getItemCount() {

        return mealData.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener{
        private TextView textTitle, textInfo;
        private ImageView mealView;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            textTitle = itemView.findViewById(R.id.meal_title);
            textInfo = itemView.findViewById(R.id.meal_description);
            mealView = itemView.findViewById(R.id.meal_image);

            itemView.setOnCreateContextMenuListener((View.OnCreateContextMenuListener) this);
            itemView.setOnClickListener((View.OnClickListener) this);
        }
        public void bindItem(MealItem currentMeal){
            textTitle.setText(currentMeal.getTitle());
            textInfo.setText(currentMeal.getDescription());
            //Glide.with(context).load(currentMeal.getImageResource()).into(mealView);
            if (currentMeal.getByteArray() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(currentMeal.getByteArray(), 0, currentMeal.getByteArray().length);
                mealView.setImageBitmap(bitmap);
            } else {
                Glide.with(context).load(currentMeal.getImageResource()).into(mealView);
            }

            textTitle.setText(currentMeal.getTitle());
            textInfo.setText(currentMeal.getDescription());
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            item = getLayoutPosition();
            MenuItem itemDelete = contextMenu.add(Menu.NONE,1,2,"Delete");
            MenuItem itemEdit = contextMenu.add(Menu.NONE,2,2,"Edit");

            itemDelete.setOnMenuItemClickListener(menuItemClicked);
            itemEdit.setOnMenuItemClickListener(menuItemClicked);
        }

        MenuItem.OnMenuItemClickListener menuItemClicked = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case 1:
                        confirmDeletion(itemView);
                        break;
                    case 2:
                        editMealItemContent(itemView);
                    default:
                        break;
                }
                return false;
            }

        };

        public void confirmDeletion(final View mealView) {
            final MealItem meal = mealData.get(item);
            Context context = mealView.getContext();
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
            dialogBuilder.setMessage(context.getString(R.string.dialog_warning) + " " + meal.getTitle() + "?");

            dialogBuilder.setPositiveButton(context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mealData.remove(item);
                    notifyItemRemoved(item);
                }
            });

            dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            dialogBuilder.create().show();
        }

        private int selected_edit_type = 0;

        public void editMealItemContent(final View mealView) {
            final MealItem meal = mealData.get(item);
            final Context context = mealView.getContext();
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);

            LayoutInflater layoutInflater = LayoutInflater.from(context);
            final View dialogView = layoutInflater.inflate(R.layout.custom_dialog_edit_item, null);
            dialogBuilder.setView(dialogView);
            final Spinner spinner = (Spinner) dialogView.findViewById(R.id.spinner);
            final EditText editText = (EditText) dialogView.findViewById(R.id.edit_type);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.edit_options,
                    android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            final String[] edit_type = context.getResources().getStringArray(R.array.edit_options);
            editText.setHint(context.getString(R.string.change) + " " + edit_type[0]);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    editText.setHint(context.getString(R.string.change) + " " + edit_type[position]);
                    selected_edit_type = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            dialogBuilder.setTitle(context.getResources().getString(R.string.dialog_title));

            dialogBuilder.setPositiveButton(context.getResources().getString(R.string.done), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String text = editText.getText().toString();

                    if (text.length() > 0) {
                        switch (selected_edit_type) {
                            case 1:
                                meal.setDescription(text);
                                break;
                            case 2:
                                meal.setCalories(text);
                                break;
                            case 3:
                                meal.setIngredients(text);
                                break;
                            case 4:
                                meal.setLinkToRecipe(text);
                                break;
                            default:
                                meal.setTitle(text);
                                break;
                        }

                        mealData.set(item, meal);
                        notifyDataSetChanged();
                    }
                }
            });

            dialogBuilder.setNegativeButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            dialogBuilder.create().show();
        }

        @Override
        public void onClick(View view) {
            MealItem meal = mealData.get(getLayoutPosition());
            Intent intent = new Intent(context, MealItemActivity.class);

            intent.putExtra("title", meal.getTitle())
                    .putExtra("description", meal.getDescription())
                    .putExtra("calories", meal.getCalories())
                    .putExtra("ingredients", meal.getIngredients())
                    .putExtra("link", meal.getLinkToRecipe());

            if (meal.getByteArray() != null) {
                intent.putExtra("image", meal.getByteArray());
            } else {
                intent.putExtra("image", meal.getImageResource());
            }
            context.startActivity(intent);
        }
    }
}

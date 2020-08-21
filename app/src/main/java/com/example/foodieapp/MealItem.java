package com.example.foodieapp;

public class MealItem {


    private int imageResource;
    private String title, description, ingredients,calories, linkToOnlineRecipe;
    private byte[] byteArray;

    public MealItem(byte[] imageId, String title, String description) {
        this.byteArray= imageId;
        this.title = title;
        this.description = description;
    }
    public MealItem(int imageId, String title, String description) {
        this.imageResource = imageId;
        this.title = title;
        this.description = description;
    }


    public String getCalories(){return calories ;}

    public int getImageResource() {
        return imageResource;
    }

    public byte[] getByteArray(){return byteArray;}

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }


    public String getLinkToRecipe() {
        return linkToOnlineRecipe;
    }

    public String getIngredients() {
        return ingredients;
    }

    public MealItem setCalories(String calories){
        this.calories = calories;
        return this;
    }
    public MealItem setImage(int imageID) {
        this.imageResource = imageID;
        return this;
    }
    public MealItem setImage(byte[] imageView){
        this.byteArray  = imageView;
        return this;
    }
    public MealItem setTitle(String title) {
        this.title = title;
        return this;
    }

    public MealItem setDescription(String description) {
        this.description = description;
        return this;
    }

    public MealItem setLinkToRecipe(String link) {
        this.linkToOnlineRecipe = link;
        return this;
    }

    public MealItem setIngredients(String ingredients) {
        this.ingredients = ingredients;
        return this;
    }
}

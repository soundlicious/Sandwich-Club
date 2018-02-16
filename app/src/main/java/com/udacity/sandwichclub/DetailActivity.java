package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView descriptionTv;
    private TextView alsoKnowTv;
    private TextView originTv;
    private TextView ingredientsTv;
    private TextView originTitle;
    private TextView alsoKnowTitle;
    private TextView descriptionTitle;
    private TextView ingredientsTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        descriptionTv = findViewById(R.id.description_tv);
        alsoKnowTv = findViewById(R.id.also_known_tv);
        originTv = findViewById(R.id.origin_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        descriptionTitle = findViewById(R.id.textView);
        originTitle = findViewById(R.id.textView2);
        alsoKnowTitle = findViewById(R.id.textView3);
        ingredientsTitle = findViewById(R.id.textView4);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        //Need to use this since we're using the AppCompatActivity, Allow Up Navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        descriptionTv.setText(sandwich.getDescription());
        List<String> alsoKnowList = sandwich.getAlsoKnownAs();
        List<String> ingredientList = sandwich.getIngredients();
        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        String description = sandwich.getDescription();

        setList(alsoKnowTitle, alsoKnowTv, alsoKnowList);
        setList(ingredientsTitle, ingredientsTv, ingredientList);
        setText(originTitle, originTv, placeOfOrigin);
        setText(descriptionTitle, descriptionTv, description);
    }

    /**
     * Set the content of the TextView if the text is not null or not empty, otherwise, hide the Title and the TextView
     *
     * @param title    Title View
     * @param textView Text View
     * @param text     String to add in TexView if textview not null
     */
    private void setText(TextView title, TextView textView, String text) {
        if (text != null && !text.isEmpty()) {
            textView.setText(text);
        } else {
            setViewAsGone(title, textView);
        }
    }

    /**
     * Set the content of the TextView if the list is not null or not empty, otherwise, hide the Title and the TextView
     *
     * @param title    Title View
     * @param textView Text View
     * @param list     List to add in TexView if textview not null
     */
    private void setList(TextView title, TextView textView, List<String> list) {
        if (list != null && !list.isEmpty()) {
            textView.setText(list.toString().replace("[", "").replace("]", "")); // delete [ and ] from string
        } else {
            setViewAsGone(title, textView);
        }
    }

    /**
     * Hide Views
     *
     * @param title Title View
     * @param text Text View
     */
    public void setViewAsGone(View title, View text) {
        title.setVisibility(View.GONE);
        text.setVisibility(View.GONE);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}

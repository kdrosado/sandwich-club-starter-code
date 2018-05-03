package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    // declare all textviews
    TextView mAlsoKnownAs;
    TextView mPlaceOfOrigin;
    TextView mDescription;
    TextView mIngredients;
    TextView mPlaceOfOriginLabel;
    TextView mAlsoKnownAsLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        // get sandwich details
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    // displays sandwich details
    private void populateUI(Sandwich sandwich) {

        // get aka and loop thru array
        mAlsoKnownAsLabel = (TextView) findViewById(R.id.also_known_as_label_tv);
        mAlsoKnownAs = (TextView) findViewById(R.id.also_known_tv);
        if (sandwich.getAlsoKnownAs() != null && !sandwich.getAlsoKnownAs().isEmpty()) {
            for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {
                mAlsoKnownAs.append(sandwich.getAlsoKnownAs().get(i) + "\n");
            }
        } else {
            mAlsoKnownAs.setVisibility(View.GONE);
            mAlsoKnownAsLabel.setVisibility(View.GONE);
        }

        // get origin
        mPlaceOfOriginLabel = (TextView) findViewById(R.id.origin_label_tv);
        mPlaceOfOrigin = (TextView) findViewById(R.id.origin_tv);
        if(!TextUtils.isEmpty(sandwich.getPlaceOfOrigin())){
            mPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin());
        }else {
            mPlaceOfOrigin.setVisibility(View.GONE);
            mPlaceOfOriginLabel.setVisibility(View.GONE);
        }

        // get description
        mDescription = (TextView) findViewById(R.id.description_tv);
        mDescription.setText(sandwich.getDescription());

        // get ingredients and loop thru array
        mIngredients = (TextView) findViewById(R.id.ingredients_tv);
        for (int i = 0; i < sandwich.getIngredients().size(); i++) {
            mIngredients.append(sandwich.getIngredients().get(i) + "\n");
        }
    }
}

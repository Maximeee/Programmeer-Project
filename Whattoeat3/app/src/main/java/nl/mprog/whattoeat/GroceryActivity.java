package nl.mprog.whattoeat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GroceryActivity extends AppCompatActivity {

    Button yesbutton;
    Button nobutton;
    Button favorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);

        yesbutton = (Button) findViewById(R.id.yesbutton);
        nobutton = (Button) findViewById(R.id.nobutton);
        favorites = (Button) findViewById(R.id.favorites);

        yesbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroceryActivity.this, recipefinderActivity.class);
                startActivity(intent);
            }
        });

        nobutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroceryActivity.this, recipeProductActivity.class);
                startActivity(intent);
            }
        });

        favorites.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroceryActivity.this, favoriteActivity.class);
                startActivity(intent);
            }
        });
    }
}


package nl.mprog.whattoeat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GroceryActivity extends AppCompatActivity {

    Button yesbutton = (Button) findViewById(R.id.yesbutton);
    Button nobutton = (Button) findViewById(R.id.nobutton);
    Button favorites = (Button) findViewById(R.id.favorites);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);

//        Button yesbutton = (Button) findViewById(R.id.yesbutton);
//        Button nobutton = (Button) findViewById(R.id.nobutton);
//        Button favorites = (Button) findViewById(R.id.favorites);
    }

    void buttonClick(View v) {
        switch (v.getId()) {
            case R.id.yesbutton: {
                Intent intent = new Intent(this, recipeProductActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.nobutton: {
                Intent intent = new Intent(this, recipefinderActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.favorites: {
                Intent intent = new Intent(this, favoriteActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}


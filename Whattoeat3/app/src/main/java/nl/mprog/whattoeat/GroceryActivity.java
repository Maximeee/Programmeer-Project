package nl.mprog.whattoeat;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.auth.api.signin.internal.SignInHubActivity;
import com.google.firebase.auth.FirebaseAuth;

public class GroceryActivity extends AppCompatActivity {

    ImageButton yesbutton;
    ImageButton nobutton;
    ImageButton favorites;
    Button Signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);

        yesbutton = (ImageButton) findViewById(R.id.yesbutton);
        nobutton = (ImageButton) findViewById(R.id.nobutton);
        favorites = (ImageButton) findViewById(R.id.favorites);
        Signout = (Button) findViewById(R.id.SignOut);

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

        Signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("signout");
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(GroceryActivity.this, EmailPasswordActivity.class);
                startActivity(intent);
            }

        });
    }
}


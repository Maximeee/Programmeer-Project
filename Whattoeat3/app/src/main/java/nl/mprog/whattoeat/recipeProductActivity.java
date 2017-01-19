package nl.mprog.whattoeat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class recipeProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_product);

        final EditText editText = (EditText) findViewById(R.id.products);
        ImageButton search = (ImageButton) findViewById(R.id.Search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String products = editText.getText().toString();
                System.out.println(products);

                Intent intent = new Intent(recipeProductActivity.this, RecipeActivity.class);
                intent.putExtra("products", products);
                startActivity(intent);

            }
        });

    }
}

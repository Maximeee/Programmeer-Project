package nl.mprog.whattoeat;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {

    String products;
    String id;

    private List<String> recipeArray = new ArrayList<String>();
    private ListView recipes;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            products = bundle.getString("products");
            System.out.println(products);
        }

        recipes = (ListView) findViewById(R.id.recipes);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, recipeArray);
        recipes.setAdapter(arrayAdapter);

        try {
            new FoodAPI().execute("/recipes/findByIngredients?number=100&ingredients=" + URLEncoder.encode(products, "UTF-8"));
            System.out.println("jeeeej");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        recipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                System.out.println(recipes.getItemAtPosition(position));
                System.out.println(id);

                Intent intent = new Intent(RecipeActivity.this, detailActivity.class);
                intent.putExtra("detailID", id);
                startActivity(intent);
            }
        });

    }

    void reloadList(JSONArray array) {
        recipeArray.clear();

        try {
            if (array != null) {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject result = (JSONObject) array.get(i);
                    recipeArray.add(result.getString("title"));
                    id = result.getString("id");
                    arrayAdapter.notifyDataSetChanged();
                }
            } else {
                recipeArray.add("No results");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // https://developer.android.com/reference/android/os/AsyncTask.html
    public class FoodAPI extends AsyncTask<String, String, String> {

        protected String doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {

                URL url = new URL("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com" + params[0]);
                connection = (HttpURLConnection) url.openConnection();

                connection.setRequestProperty("X-Mashape-Key", "bVHlZI0lTgmshWbLKrIwVoLEWzOep1BH5BDjsn7Ku4YFjGoQZN");
                connection.setRequestProperty("Accept", "application/json");

                try {
                    connection.setRequestMethod("GET");
                } catch (ProtocolException e) {
                    e.printStackTrace();
                }

                connection.connect();

                InputStream stream = null;
                try {
                    stream = connection.getInputStream();
                } catch (IOException ioe) {
                    if (connection instanceof HttpURLConnection) {
                        int statusCode = connection.getResponseCode();
                        if (statusCode != 200) {
                            stream = connection.getErrorStream();
                        }
                    }
                }

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                }


                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
                System.out.println("MalFunc");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("IO");
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            JSONArray object = null;
            try {
                object = new JSONArray(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            System.out.println(object);
            RecipeActivity.this.reloadList(object);
        }
    }

}

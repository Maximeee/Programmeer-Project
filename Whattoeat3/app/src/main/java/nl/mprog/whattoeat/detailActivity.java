package nl.mprog.whattoeat;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class detailActivity extends AppCompatActivity {

    String detailID;
    TextView Title;
    TextView Instructions;
    ListView Ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailID = bundle.getString("detailID");
            System.out.println(bundle.getString("detailID"));
        }

        System.out.println(detailID + "detailact");
        new FoodAPI().execute("/recipes/" + detailID + "/analyzedInstructions");


//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");

        Title = (TextView) findViewById(R.id.vTitle);
        Instructions = (TextView) findViewById(R.id.vInstructions);
        Instructions.setMovementMethod(new ScrollingMovementMethod());
        Ingredients = (ListView) findViewById(R.id.vIngredients);
    }

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

            JSONArray array;
            try {
                array = new JSONArray(result);
                JSONObject object = (JSONObject) array.get(0);
                JSONArray instr = object.getJSONArray("steps");

                System.out.println(instr.length());
                int i;
                StringBuilder stringBuilder = new StringBuilder();

                for(i = 0; i < instr.length(); i++) {
                    JSONObject jeej = (JSONObject) instr.get(i);
                    stringBuilder.append("Step" + jeej.getString("number") + ": " + jeej.getString("step"));
                }

                String instructions = stringBuilder.toString();
                Instructions.setText(instructions);

                System.out.println(object.getJSONArray("steps"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}


package nl.mprog.whattoeat;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class favoriteActivity extends AppCompatActivity {

    private ListView favorites;
    private List<String> recipeArray = new ArrayList<>();

    private ArrayAdapter arrayAdapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        favorites = (ListView) findViewById(R.id.favorites);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, (ArrayList) recipeArray);
        favorites.setAdapter(arrayAdapter);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        myRef = database.getReference("Users").child(user.getUid());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recipeArray.clear();
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    String value = (String) child.getValue();
                    System.out.println(value);
                    new FoodAPI().execute("/recipes/" + value + "/summary");

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Failed to read value." + databaseError.toException());
            }
        });

        favorites.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {



                return true;
            }
        });

    }

    void reloadList(JSONObject object) {

        try {
            //JSONArray results = object.getJSONArray("results");
            if (object != null) {
                String result = object.getString("title");
                recipeArray.add(result);
                arrayAdapter.notifyDataSetChanged();

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

            JSONObject object = null;
            try {
                object = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            System.out.println(object);
            favoriteActivity.this.reloadList(object);
        }
    }
}

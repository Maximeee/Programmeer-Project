package nl.mprog.whattoeat;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import java.util.Timer;
import java.util.TimerTask;

public class recipefinderActivity extends AppCompatActivity {

    private List<String> recipeArray = new ArrayList<String>();

    private ListView Recipelist;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipefinder);

        Recipelist = (ListView) findViewById(R.id.recipelist);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, recipeArray);
        Recipelist.setAdapter(arrayAdapter);

        Recipelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                System.out.println(Recipelist.getItemAtPosition(position));
                String s = (String) Recipelist.getItemAtPosition(position);

                Intent intent = new Intent(recipefinderActivity.this, detailActivity.class);
                intent.putExtra("detailID", s);
                startActivity(intent);
            }
        });



        EditText recipeFinder = (EditText) findViewById(R.id.recipeSearchBox);
        recipeFinder.addTextChangedListener(new TextWatcher() {

            private Timer timer = new Timer();
            private final long DELAY = 500; // milliseconds

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                try {
                                    // NOTE: UNSAFE, DEBUG
                                    new FoodAPI().execute("/recipes/autocomplete?number=100&query=" + URLEncoder.encode(editable.toString(), "UTF-8"));
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        DELAY
                );


            }
        });

    }

    void reloadList(JSONArray array) {
        recipeArray.clear();

        try {
            //JSONArray results = object.getJSONArray("results");
            if (array != null) {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject result = (JSONObject) array.get(i);
                    recipeArray.add(result.getString("title"));
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
            recipefinderActivity.this.reloadList(object);
        }
    }
}

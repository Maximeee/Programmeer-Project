package nl.mprog.whattoeat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;


public class recipefinderActivity extends AppCompatActivity {

    private String[] recpearray = {"recept 1", "recept 2", "recept 3", "recept 4"};
    private ListView Recipelist;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipefinder);

        Recipelist = (ListView) findViewById(R.id.recipelist);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, recpearray);
        Recipelist.setAdapter(arrayAdapter);

    }
}

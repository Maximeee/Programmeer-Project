package nl.mprog.whattoeat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    }

    //TODO: API implementeren hierzo,


}

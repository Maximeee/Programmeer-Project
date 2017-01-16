package nl.mprog.whattoeat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class detailActivity extends AppCompatActivity {

    String detailID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailID = bundle.getString("detailID");
            System.out.println(bundle.getString("detailID"));
        }

//        Firebase
    }
}

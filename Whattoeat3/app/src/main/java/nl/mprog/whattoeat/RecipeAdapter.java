package nl.mprog.whattoeat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by maximeweekhout on 19-01-17.
 */

public class RecipeAdapter extends ArrayAdapter<ArrayList> {

    private ArrayList recipeList;

    public RecipeAdapter(Context context, int resource, ArrayList object) {
        super(context, resource);
        this.recipeList = object;
    }

    @Override
    public int getCount() {
        return recipeList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        System.out.println(recipeList.get(position));


        View rowView = inflater.inflate(R.layout.single_row, parent, false);
        TextView title = (TextView) rowView.findViewById(R.id.titleView);

        JSONObject object = (JSONObject) recipeList.get(position);
        try {
            title.setText(object.getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


//
//        TextView tv_outagnumber = (TextView) rowView.findViewById(R.id.tv_outagnumber);
//        TextView tv_impact = (TextView) rowView.findViewById(R.id.tv_impact);
//        TextView tv_status = (TextView) rowView.findViewById(R.id.tv_status);
//        TextView tv_timestamp = (TextView) rowView.findViewById(R.id.tv_timestamp);
//
//        tv_outagnumber.setText(outagesObject.outagnumber);
//        tv_impact.setText(outagesObject.impact);
//        tv_status.setText(outagesObject.status);
//        tv_timestamp.setText(outagesObject.timestamp);

        return rowView;
    }
}


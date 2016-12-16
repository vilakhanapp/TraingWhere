package ltc.vilakhan.traingwhere;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Boualy on 12/16/2016.
 */

public class MyAdapter extends BaseAdapter{

    private Context context;
    private String[] nameStrings, latStrings, lngStrings, iconStrings;

    public MyAdapter(Context context, String[] nameStrings, String[] latStrings, String[] lngStrings, String[] iconStrings) {
        this.context = context;
        this.nameStrings = nameStrings;
        this.latStrings = latStrings;
        this.lngStrings = lngStrings;
        this.iconStrings = iconStrings;
    }

    @Override
    public int getCount() {
        return nameStrings.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.my_listview, parent, false);

        TextView nameTextView = (TextView) view.findViewById(R.id.textView7);
        TextView latTextView = (TextView) view.findViewById(R.id.textView8);
        TextView lngTextView = (TextView) view.findViewById(R.id.textView9);
        ImageView iconImageView = (ImageView) view.findViewById(R.id.imageView4);

        nameTextView.setText(nameStrings[position]);
        latTextView.setText("Lat = " + String.format("%.3f", Double.parseDouble(latStrings[position])));
        lngTextView.setText("Lng = " + String.format("%.3f", Double.parseDouble(lngStrings[position])));

        Picasso.with(context).load(iconStrings[position]).into(iconImageView);


        return view;
    }
}// Main class

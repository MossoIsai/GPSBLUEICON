package com.blueicon.gpsblueicon.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.blueicon.gpsblueicon.R;
import com.blueicon.gpsblueicon.activities.models.response.Negocio;

import java.util.List;

/**
 * Created by developer on 26/07/17.
 */

public class AdapterNegocio extends ArrayAdapter<Negocio> {
    private List<Negocio> negociosList;


    public AdapterNegocio(Context context, List<Negocio> negociosList) {
        super(context, 0, negociosList);
        this.negociosList =  negociosList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater)
                getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View listViewInflate = convertView;

        if (convertView == null) {
            listViewInflate = inflater.inflate(R.layout.item_layout, viewGroup, false);
        }
        TextView textView = (TextView) listViewInflate.findViewById(R.id.nombreNegocio);
        ImageView imageView = (ImageView) listViewInflate.findViewById(R.id.flecha);

        final Negocio negocio = getItem(position);
        textView.setText(negocio.getNombre());
        imageView.setImageResource(R.drawable.flecha);

        return listViewInflate;
    }

}

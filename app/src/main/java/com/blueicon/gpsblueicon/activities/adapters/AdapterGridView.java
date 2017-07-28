package com.blueicon.gpsblueicon.activities.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.blueicon.gpsblueicon.R;
import com.blueicon.gpsblueicon.activities.models.response.Horario;

import java.util.List;

/**
 * Created by developer on 25/07/17.
 */

public class AdapterGridView extends ArrayAdapter<Horario> {

    private List<Horario> horarios;


    public AdapterGridView( Context context, List<Horario> horarios) {
        super(context,0, horarios);
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater)
                getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Horario horario = getItem(position);

        View view = convertView;
        if(convertView == null){
            view = inflater.inflate(R.layout.item_horario,viewGroup,false);
        }
        TextView texhorario = (TextView) view.findViewById(R.id.textDiaSemana);
        texhorario.setText(horario.getTextDia());

        return view;
    }
}

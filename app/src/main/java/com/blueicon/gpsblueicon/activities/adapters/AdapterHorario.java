package com.blueicon.gpsblueicon.activities.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.blueicon.gpsblueicon.R;
import com.blueicon.gpsblueicon.activities.models.response.Horario;
import com.blueicon.gpsblueicon.activities.models.response.HorarioItem;
import com.blueicon.gpsblueicon.activities.models.response.ObjectListaHorario;
import com.blueicon.gpsblueicon.activities.models.response.Semana;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by developer on 26/07/17.
 */

public class AdapterHorario  extends RecyclerView.Adapter<AdapterHorario.ViewHolder>{

    private final List<ObjectListaHorario> horarioItems;
    private final OnItem listener;
    private AdapterGridViewMarcado adapterGridViewMarcado;

    public AdapterHorario(List<ObjectListaHorario> horarioItems,OnItem listener){
        this.horarioItems = horarioItems;
        this.listener = listener;

    }
    public interface  OnItem{
        void  onItemClick(ObjectListaHorario objectListaHorario);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_horario,null);
        ViewHolder viewHolder = new ViewHolder(vista);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ObjectListaHorario objectListaHorario = horarioItems.get(position);

        Context contextGridView = holder.gridSemana.getContext();
        holder.horaApertura.setText(objectListaHorario.getHoraApertura());
        holder.horacerrado.setText(objectListaHorario.getHoraCerrado());
        adapterGridViewMarcado =  new AdapterGridViewMarcado(contextGridView,objectListaHorario.getDiasMarcados());
        System.out.println("dias marcados : "+objectListaHorario.getDiasMarcados());
        holder.gridSemana.setAdapter(adapterGridViewMarcado);
        holder.bind(objectListaHorario,listener);
    }

    @Override
    public int getItemCount() {
        return horarioItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.gridSemana) GridView gridSemana;
        @BindView(R.id.horaApertura) TextView horaApertura;
        @BindView(R.id.horaCerrado) TextView horacerrado;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        public void bind(final ObjectListaHorario  objectListaHorario,final OnItem listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(objectListaHorario);

                }
            });
        }
    }


}

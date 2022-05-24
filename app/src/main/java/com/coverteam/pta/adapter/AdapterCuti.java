package com.coverteam.pta.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.coverteam.pta.R;
import com.coverteam.pta.data.models.DocumentCuti;
import com.coverteam.pta.model.DataCuti;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class AdapterCuti extends ArrayAdapter<DocumentCuti> {
    private Activity context;
    private List<DocumentCuti> dataCutiList;

    public AdapterCuti(Activity context, List<DocumentCuti> dataCutiList){
        super(context, R.layout.desain_list_cuti, dataCutiList);
        this.context = context;
        this.dataCutiList = dataCutiList;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder") View listViewItem = inflater.inflate(R.layout.desain_list_cuti,null,true);
        TextView textNama = listViewItem.findViewById(R.id.namacuti);
        TextView textTglMulai = listViewItem.findViewById(R.id.tglcuti);

        DocumentCuti dataCutilist = dataCutiList.get(position);
        textNama.setText(dataCutilist.getNamaPengaju() + '-' + dataCutilist.getNipPengaju());

        // format date
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());

        textTglMulai.setText(dateFormatter.format(dataCutilist.getListTgl().get(0)) + " - " + dateFormatter.format(dataCutilist.getListTgl().get(dataCutilist.getListTgl().size() - 1)) );
        return listViewItem;
    }
}


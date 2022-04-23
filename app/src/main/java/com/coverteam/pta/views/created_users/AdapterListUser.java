package com.coverteam.pta.views.created_users;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.coverteam.pta.R;
import com.coverteam.pta.data.models.Users;

import java.util.ArrayList;

public class AdapterListUser extends RecyclerView.Adapter<AdapterListUser.ViewHolder> {
    private Context context;
    private ArrayList<Users> listUsers = new ArrayList<>();

    //interfaces callback
    private  IUserListOnTap iUserListOnTap;

    //constructor
    public AdapterListUser(Context context,ArrayList<Users> listUser,   IUserListOnTap callback){
        this.context = context;
        this.listUsers = listUser;
        this.iUserListOnTap = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.desain_list_users,parent,false);
        return new ViewHolder(view,iUserListOnTap);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListUser.ViewHolder holder, int position) {
        //ambil user berdasarkan urutan
        Users user = listUsers.get(position);
        // set tampilan
        holder.v_name.setText(user.getNama());
        holder.v_jabatan.setText(user.getJabatan());

    }

    @Override
    public int getItemCount() {
        return listUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView v_name;
        TextView v_jabatan;
        CardView cardView;
        public ViewHolder(View view, IUserListOnTap iUserListOnTap) {
            super(view);
            this.cardView = view.findViewById(R.id.card_view);
            this.v_name = view.findViewById(R.id.tx_name);
            this.v_jabatan = view.findViewById(R.id.tx_jabatan);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iUserListOnTap.onButtonOrderClick(getAdapterPosition());
                }
            });

        }
    }
}

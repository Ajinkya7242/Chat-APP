package com.example.makechat;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewHolder> {
    MainActivity mainActivity;
    ArrayList<Users> usersArrayList;

    public  UserAdapter(){

    }
    public UserAdapter(MainActivity mainActivity, ArrayList<Users> usersArrayList) {
        this.mainActivity=mainActivity;
        this.usersArrayList=usersArrayList;
    }

    @NonNull
    @Override
    public UserAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mainActivity).inflate(R.layout.user_item,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.viewHolder holder, int position) {
        Users users=usersArrayList.get(position);
        holder.username.setText(users.userName);
        holder.userstatus.setText(users.status);
        Picasso.get().load(users.profilePic).into(holder.userImg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mainActivity,chatWin.class);
                intent.putExtra("name",users.getUserName());
                intent.putExtra("reciverImg",users.getProfilePic());
                intent.putExtra("uid",users.getUserId());
                mainActivity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView userImg;
        TextView username,userstatus;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            userImg=itemView.findViewById(R.id.imaProfile);
            username=itemView.findViewById(R.id.name);
            userstatus=itemView.findViewById(R.id.txtstatus);
        }
    }
}

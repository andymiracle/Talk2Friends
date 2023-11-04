package com.example.talk2friends;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {
    private ArrayList<User> userslist;
    private Context context;
    //private OnClickListener onClickListener;

    public recyclerAdapter(ArrayList<User> userslist, Context context) {
        this.userslist = userslist;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTxt;
        private Button view_bt;
        public MyViewHolder(final View view) {
            super(view);
            nameTxt = view.findViewById(R.id.name);
            view_bt = view.findViewById(R.id.details);
        }
    }

    @NonNull
    @Override
    public recyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.MyViewHolder holder, int position) {
        String name = userslist.get(position).getUsername();
        String real_name = userslist.get(position).getDisplayName();
        holder.nameTxt.setText(real_name + " (" + name + ")");

        holder.view_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //System.out.println("Button is clicked ouch! " + name);

                Intent intent = new Intent(context, FriendsProfileActivity.class);

                intent.putExtra("username", name);

                /*
                if (context == null) {
                    System.out.println("What happened?");
                } else {
                    System.out.println(context);
                }
                 */

                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return userslist.size();
    }

    /*
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(int position, User user);
    }
     */
}

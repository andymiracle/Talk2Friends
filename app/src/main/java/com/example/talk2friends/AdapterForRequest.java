package com.example.talk2friends;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterForRequest extends RecyclerView.Adapter<AdapterForRequest.MyViewHolder> {
    private ArrayList<String> request;
    private Context context;
    private String username;
    private HashMap<String, String> userToDisplay;

    public AdapterForRequest(HashMap<String, String> userToDisplay, ArrayList<String> request, Context context, String username) {
        this.request = request;
        this.context = context;
        this.username = username;
        this.userToDisplay = userToDisplay;
    }

    @NonNull
    @Override
    public AdapterForRequest.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // This is where you inflate the layout (Giving a look to our rows)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items2, parent, false);
        return new AdapterForRequest.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForRequest.MyViewHolder holder, int position) {
        // assigning values to the views we created in the recycler view row layout file
        // based on the position of the recycler view
        String name = request.get(position);

        holder.nameTxt.setText(userToDisplay.get(name) + " (" + name + ")");

        holder.accept_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //System.out.println("Accept me!");
                FriendsActivity.friendUser(name);
                FriendsActivity.friendCurrentUser(name);
                Toast toast = Toast.makeText(context, "Accepted", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }


        });

        holder.reject_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(username);

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User u = snapshot.getValue(User.class);
                        ArrayList<String> incoming = u.getIncomingRequests();

                        int index = 0;
                        for (int i = 0; i < incoming.size(); ++i) {
                            if (incoming.get(i).equals(name)) {
                                //System.out.println("You sure you want to delete " + name + " from " + username + "'s friend request collection?");
                                index = i;
                            }
                        }

                        incoming.remove(index);

                        /*
                        for (int i = 0; i < incoming.size(); ++i) {
                            System.out.println("You have " + incoming.get(i));
                        }
                         */

                        u.setIncomingRequests(incoming);
                        DatabaseUtil.saveUser(u);
                        Toast toast = Toast.makeText(context, "Rejected", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                //System.out.println("You are gonna reject me?");
            }
        });
    }

    @Override
    public int getItemCount() {
        // the recycler view just wants to know the number of items you want displayed
        return request.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // grabbing the views from our recycler view row layout file
        // kinda like in the onCreate method

        private TextView nameTxt;
        private Button accept_bt;
        private Button reject_bt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.name);
            accept_bt = itemView.findViewById(R.id.accept);
            reject_bt = itemView.findViewById(R.id.reject);
        }
    }
}
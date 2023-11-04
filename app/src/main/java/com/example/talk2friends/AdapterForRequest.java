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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdapterForRequest extends RecyclerView.Adapter<AdapterForRequest.MyViewHolder> {
    private ArrayList<String> request;
    private String username;

    public AdapterForRequest(ArrayList<String> request, String username) {
        this.request = request;
        this.username = username;
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
        holder.nameTxt.setText(name);

        holder.accept_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Accept me!");
                friendUser(name);
                friendCurrentUser(name);
            }

            public void friendUser(String friend) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(Singleton.getInstance().getUsername());
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User u = snapshot.getValue(User.class); //current user
                        ArrayList<String> friends = u.getFriends(); //get current user's friends
                        if (friends == null) {
                            System.out.println("Congrats for having a new friend! " + u.getUsername());
                            friends = new ArrayList<>();
                        }

                        Boolean isNotFriend = true;
                        for (int i = 0; i < friends.size(); i++) {
                            if (friends.get(i).equals(friend)) {
                                isNotFriend = false;
                            }
                        }
                        if (isNotFriend) {
                            System.out.println(friend + " is gonna be " + u.getUsername() + "'s new friend Ooh hoh");
                            friends.add(friend); //add friend in current user's friend list
                        }

                        ArrayList<String> incomingRequests = u.getIncomingRequests(); //get current user's incoming request

                        if (incomingRequests == null) {
                            incomingRequests = new ArrayList<>();
                        }

                        for (int i = 0; i < incomingRequests.size(); i++) {
                            System.out.println("I'm comparing " + friend + " with " + incomingRequests.get(i));
                            if (incomingRequests.get(i).equals(friend)) {
                                //System.out.println("Remove it plz");
                                incomingRequests.remove(i);
                                break;
                            }
                        }

                        u.setFriends(friends);
                        u.setIncomingRequests(incomingRequests);
                        DatabaseUtil.saveUser(u);
                        for (int i = 0; i < u.getFriends().size(); ++i) {
                            System.out.println("You have " + u.getFriends().get(i) + " as friend!");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println(error.getMessage());
                    }
                });
            }

            public void friendCurrentUser(String username) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(username);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User u = snapshot.getValue(User.class);
                        ArrayList<String> friends = u.getFriends();
                        if (friends == null) {
                            friends = new ArrayList<>();
                            System.out.println("Congrats for having new friend! " + username);
                        }

                        Boolean isNotFriend = true;
                        for (int i = 0; i < friends.size(); i++) {
                            if (friends.get(i).equals(Singleton.getInstance().getUsername())) {
                                isNotFriend = false;
                            }
                        }
                        if (isNotFriend) {
                            friends.add(Singleton.getInstance().getUsername());
                            System.out.println(Singleton.getInstance().getUsername() + " is gonna be new friend Ooh hoh");
                        }

                        ArrayList<String> incomingRequests = u.getIncomingRequests();
                        if (incomingRequests == null) {
                            incomingRequests = new ArrayList<>();
                        }
                        for (int i = 0; i < incomingRequests.size(); i++) {
                            if (incomingRequests.get(i).equals(Singleton.getInstance().getUsername())) {
                                incomingRequests.remove(i);
                                break;
                            }
                        }

                        u.setFriends(friends);
                        u.setIncomingRequests(incomingRequests);
                        DatabaseUtil.saveUser(u);
                        for (int i = 0; i < u.getFriends().size(); ++i) {
                            System.out.println(username + " has " + u.getFriends().get(i) + " as friend!");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println(error.getMessage());
                    }
                });

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
                                System.out.println("You sure you want to delete " + name + " from " + username + "'s friend request collection?");
                                index = i;
                            }
                        }

                        incoming.remove(index);

                        for (int i = 0; i < incoming.size(); ++i) {
                            System.out.println("You have " + incoming.get(i));
                        }

                        u.setIncomingRequests(incoming);
                        DatabaseUtil.saveUser(u);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                System.out.println("You are gonna reject me?");
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
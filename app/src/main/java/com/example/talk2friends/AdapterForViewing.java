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

public class AdapterForViewing extends RecyclerView.Adapter<AdapterForViewing.MyViewHolder> {
    private ArrayList<String> friendslist;
    private Context context;
    private String current_user;
    private HashMap<String, String> userToDisplay;

    public AdapterForViewing(HashMap<String, String> userToDisplay, ArrayList<String> friendslist, Context context, String current_user) {
        this.userToDisplay = userToDisplay;
        this.friendslist = friendslist;
        this.context = context;
        this.current_user = current_user;
    }

    @NonNull
    @Override
    public AdapterForViewing.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items3, parent, false);
        return new AdapterForViewing.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForViewing.MyViewHolder holder, int position) {
        String name = friendslist.get(position);
        String displayName = userToDisplay.get(name);

        holder.nameTxt.setText(displayName + " (" + name + ")");

        holder.details_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //System.out.println("I want to look at " + name + "'s detail");

                Intent intent = new Intent(context, FriendsDetailActivity.class);
                intent.putExtra("username", name);
                context.startActivity(intent);
            }
        });

        holder.remove_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //System.out.println("I want to remove " + name + " from the friend list");

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User currentUser = null;
                        User targetUser = null;
                        ArrayList<String> currentFriends = new ArrayList<>();
                        ArrayList<String> targetFriends = new ArrayList<>();

                        for (DataSnapshot snap : snapshot.getChildren()) {
                            User u = snap.getValue(User.class);
                            if (u.getUsername().equals(name)) {
                                targetUser = u;
                            }
                            else if (u.getUsername().equals(Singleton.getInstance().getUsername())) {
                                currentUser = u;
                            }
                        }

                        // Should never be reached
                        if (targetUser == null || currentUser == null) {
                            return;
                        }

                        if (currentUser.getFriends() != null) {
                            currentFriends = currentUser.getFriends();
                        }
                        if (targetUser.getFriends() != null) {
                            targetFriends = targetUser.getFriends();
                        }

                        for (int i = 0; i < currentFriends.size(); i++) {
                            if (currentFriends.get(i).equals(name)) {
                                currentFriends.remove(i);
                                break;
                            }
                        }

                        for (int i = 0; i < targetFriends.size(); i++) {
                            if (targetFriends.get(i).equals(Singleton.getInstance().getUsername())) {
                                targetFriends.remove(i);
                                break;
                            }
                        }

                        currentUser.setFriends(currentFriends);
                        targetUser.setFriends(targetFriends);

                        DatabaseUtil.saveUser(currentUser);
                        DatabaseUtil.saveUser(targetUser);

                        Toast toast = Toast.makeText(context, "Removed from the friends list", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return friendslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTxt;
        private Button details_bt;
        private Button remove_bt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.name);
            details_bt = itemView.findViewById(R.id.details);
            remove_bt = itemView.findViewById(R.id.remove);
        }
    }
}
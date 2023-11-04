package com.example.talk2friends;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Comparator;
import java.util.Collections;

public class JoinMeetingActivity extends AppCompatActivity {

    ArrayList<Meeting> displayedMeetings = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_meeting);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("meetings");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Meeting> meetingList = new ArrayList<>();

                for (DataSnapshot snap : snapshot.getChildren()) {
                    Meeting m = snap.getValue(Meeting.class);
                    meetingList.add(m);
                }



                Collections.sort(meetingList, new MeetingComparator());
                // meetingList now contains all meetings sorted from first occurring to last occurring.


                for (int i = 0; i < meetingList.size(); i++) {
                    if(i==15)
                    {
                        break;
                    }

                    displayedMeetings.add(meetingList.get(i));
                }

                System.out.println("DisplayedMeeting size is " + displayedMeetings.size());
                for (int i = 1; i <= displayedMeetings.size(); i++) {
                    // https://stackoverflow.com/questions/4730100/android-and-getting-a-view-with-id-cast-as-a-string
                    int meeting_text = getResources().getIdentifier("meeting_id" + String.valueOf(i),  "id", getPackageName());
                    int host_text = getResources().getIdentifier("host"+ String.valueOf(i),  "id", getPackageName());
                    int details_text = getResources().getIdentifier("meeting_info" + String.valueOf(i),  "id", getPackageName());

                    //System.out.println(R.id.meeting_id1);
                    //System.out.println(meeting_text);

                    TextView meetingIDView = (TextView)findViewById(meeting_text);
                    meetingIDView.setText(displayedMeetings.get(i - 1).getName());

                    TextView hostView = (TextView)findViewById(host_text);
                    hostView.setText(displayedMeetings.get(i - 1).getCreator());

                    Button detailsView = (Button)findViewById(details_text);
                    detailsView.setVisibility(View.VISIBLE);
                    detailsView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int ID = view.getId();
                            // Get the name of the view we just clicked
                            String viewName = getResources().getResourceEntryName(ID);
                            int underscoreIndex = viewName.indexOf('_');
                            underscoreIndex += 1 + 4;

                            // Grab the index of the button we pressed meeting_info1->1
                            viewName = viewName.substring(underscoreIndex, viewName.length());
                            int index = Integer.parseInt(viewName);

                            System.out.println(viewName);

                            Intent intent = new Intent(JoinMeetingActivity.this, MeetingInfoActivity.class);
                            intent.putExtra("ID", displayedMeetings.get(index-1).getMeetingID());

                            startActivity(intent);
                        }
                    }); // see meeting info
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getMessage());
            }


        });

        /*
        // display only up to 15 meetings
        // displayedMeetings.size()
        for (int i = 1; i <= displayedMeetings.size(); i++) {
            // https://stackoverflow.com/questions/4730100/android-and-getting-a-view-with-id-cast-as-a-string
            int meeting_text = getResources().getIdentifier("meeting_id" + String.valueOf(i),  "id", getPackageName());
            int host_text = getResources().getIdentifier("host"+ String.valueOf(i),  "id", getPackageName());
            int details_text = getResources().getIdentifier("meeting_info" + String.valueOf(i),  "id", getPackageName());

            //System.out.println(R.id.meeting_id1);
            //System.out.println(meeting_text);

            TextView meetingIDView = (TextView)findViewById(meeting_text);
            meetingIDView.setText(displayedMeetings.get(i - 1).getMeetingID());

            TextView hostView = (TextView)findViewById(host_text);
            hostView.setText(displayedMeetings.get(i - 1).getCreator());

            Button detailsView = (Button)findViewById(details_text);
            detailsView.setVisibility(View.VISIBLE);
            detailsView.setOnClickListener(this::toMeetingInfo); // see meeting info
        }
        */
    }

    /*
    public void toMeetingInfo(View view) {
        Intent intent = new Intent(this, MeetingInfoActivity.class);
        startActivity(intent);
    }
    */

    public static void deleteExpiredMeetings() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("meetings");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Meeting> meetingList = new ArrayList<>();

                for (DataSnapshot snap : snapshot.getChildren()) {
                    Meeting m = snap.getValue(Meeting.class);
                    meetingList.add(m);
                }
                Date current = new Date();
                Date yesterdayDate = new Date(current.getTime() - 24 * 60 * 60 * 1000);

                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                dateFormat.setLenient(false);
                Date date = null;

                for (int i = 0; i < meetingList.size(); i++) {
                    try {
                        date = dateFormat.parse(meetingList.get(i).getTime());

                        if (date.before(yesterdayDate)) {
                            DatabaseReference remove = FirebaseDatabase.getInstance().getReference("meetings").child(meetingList.get(i).getMeetingID());
                            remove.removeValue();
                        }
                    } catch (ParseException e) {
                        System.out.println("Error parsing date");
                    }
                }







            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getMessage());
            }


        });
    }
}

class MeetingComparator implements Comparator<Meeting> {

    // override the compare() method
    public int compare(Meeting m1, Meeting m2)
    {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        dateFormat.setLenient(false);
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = dateFormat.parse(m1.getTime());
            date2 = dateFormat.parse(m2.getTime());
        } catch (ParseException e) {
            System.out.println("Error parsing date");
        }

        return date1.compareTo(date2);
    }
}


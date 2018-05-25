package com.example.ali.smartcity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ali.smartcity.data.Groupe;
import com.example.ali.smartcity.data.GroupeAdapter;
import com.example.ali.smartcity.data.InfoUser;
import com.example.ali.smartcity.data.Message;
import com.example.ali.smartcity.data.Posts;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatRoom extends AppCompatActivity {

    LinearLayout layout;
    RelativeLayout layout_2;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    Toolbar toolbar;
    List<Message> messages;
    Groupe groupe;
    String groupeId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            firebaseUser = firebaseAuth.getCurrentUser();
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference().child("social");
        }

        Intent intent = getIntent();
        final String groupeName = intent.getStringExtra("groupe");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                DataSnapshot dataSnapshotPosts = null;
                for (DataSnapshot child: children) {
                    Groupe grp = child.getValue(Groupe.class);
                    if(grp.getNom().equals(groupeName)){
                        //groupe = grp;
                        groupeId = child.getKey().toString();
                    }
                }

                databaseReference.child(groupeId+"/posts").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                        messages = new ArrayList<Message>();
                        for (DataSnapshot child: children) {
                            Message msg = child.getValue(Message.class);
                            if(msg.sender.equals(firebaseUser.getEmail())){
                                addMessageBox("You:-\n" + msg.getText(), 2);
                            }else {
                                addMessageBox(msg.getSender() + ":-\n" + msg.getText(), 1);
                            }
                            messages.add(msg);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) { }
                });

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        LinearLayout chatroom = (LinearLayout) findViewById(R.id.chatroom_activity);
        toolbar = chatroom.findViewById(R.id.toolbar);
        toolbar.setTitle(groupeName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layout = (LinearLayout) findViewById(R.id.layout1);
        layout_2 = (RelativeLayout)findViewById(R.id.layout2);
        sendButton = (ImageView)findViewById(R.id.sendButton);
        messageArea = (EditText)findViewById(R.id.messageArea);
        scrollView = (ScrollView)findViewById(R.id.scrollView);

        messageArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "dldld", Toast.LENGTH_LONG).show();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!messageArea.getText().toString().matches(""))
                    databaseReference.child(groupeId).child("posts").push()
                      .setValue(new Message(firebaseUser.getEmail().toString(), messageArea.getText().toString()));
            }
        });

    }

    public void addMessageBox(String message, int type){
        TextView textView = new TextView(getBaseContext());
        textView.setText(message);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if(type == 1) {
            lp2.gravity = Gravity.LEFT;
            textView.setBackgroundResource(R.drawable.bb2);
        }
        else{
            lp2.gravity = Gravity.RIGHT;
            textView.setBackgroundResource(R.drawable.bb1);
        }
        textView.setLayoutParams(lp2);
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

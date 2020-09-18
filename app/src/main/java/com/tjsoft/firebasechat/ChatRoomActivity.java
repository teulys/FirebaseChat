package com.tjsoft.firebasechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tjsoft.firebasechat.adapters.ChatAdapter;
import com.tjsoft.firebasechat.model.entities.Message;
import com.tjsoft.firebasechat.model.entities.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ChatRoomActivity extends AppCompatActivity {

    private ListView lvCharRoom;
    private EditText etChat;
    private ImageView btSend;
    private FirebaseDatabase database;
    private User myUser;
    private User chatUser;
    private List<Message> messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        lvCharRoom = findViewById(R.id.lvCharRoom);
        etChat = findViewById(R.id.etChat);
        etChat.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    addMassage();
                    return true;
                }
                return false;
            }
        });
        //btSend = findViewById(R.id.btSend);

        myUser = (User) getIntent().getSerializableExtra("myUser");
        chatUser = (User) getIntent().getSerializableExtra("chatUser");

        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("chatRoom");

        myRef.child(myUser.getUid()).child(chatUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messages.clear();

                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Message message = data.getValue(Message.class);
                    messages.add(message);
                }

                ChatAdapter chatAdapter = new ChatAdapter(messages, myUser.getPhotoUrl(), chatUser.getPhotoUrl());

                lvCharRoom.setAdapter(chatAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("ChatRoom", "Failed to read value.", error.toException());
            }
        });


//        btSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (view.getId() == R.id.btSend && etChat.getText().toString().trim() != ""){
//                    Long tsLong = System.currentTimeMillis()/1000;
//                    String ts = tsLong.toString();
//                    String text = etChat.getText().toString().trim();
//
//                    Message mensajeEmisor = new Message(text,ts,true);
//                    Message mensajeReseptor = new Message(text,ts,false);
//
//                    //Emisor
//                    DatabaseReference myRef = database.getReference("chatRoom").child(myUser.getUid()).child(chatUser.getUid());
//                    String key = myRef.push().getKey();
//                    myRef.child(key).setValue(mensajeEmisor);
//
//                    //Receptor
//                    myRef = database.getReference("chatRoom").child(chatUser.getUid()).child(myUser.getUid());
//                    key = myRef.push().getKey();
//                    myRef.child(key).setValue(mensajeReseptor);
//                }
//            }
//        });

    }

    public void onClick(View view) {
        if (view.getId() == R.id.btSend && !etChat.getText().toString().isEmpty()){
            addMassage();
        }
    }

    private void addMassage(){
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        String text = etChat.getText().toString().trim();

        Message mensajeEmisor = new Message(text,ts,true);
        Message mensajeReseptor = new Message(text,ts,false);

        //Emisor
        DatabaseReference myRef = database.getReference("chatRoom").child(myUser.getUid()).child(chatUser.getUid());
        String key = myRef.push().getKey();
        myRef.child(key).setValue(mensajeEmisor);

        //Receptor
        myRef = database.getReference("chatRoom").child(chatUser.getUid()).child(myUser.getUid());
        key = myRef.push().getKey();
        myRef.child(key).setValue(mensajeReseptor);

        etChat.setText("");
    }

}

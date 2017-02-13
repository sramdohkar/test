package com.example.ss899.homework09;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ItemsAdapter extends ArrayAdapter<Message>{
    List<Message> mData;
    Context mContext;
    int mResource;
    int messageIndex;
    ArrayList<String> messageKey;
    String name;
    ItemsInterface activity;

    public ItemsAdapter(Context context, int resource, List<Message> objects) {
        super(context, resource, objects);
        this.mContext=context;
        this.mData=objects;
        this.mResource=resource;
        this.activity=(ItemsInterface) context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       // ViewHolder holder;
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource,parent,false);
                    }
        convertView.findViewById(R.id.holder).setBackgroundColor(Color.WHITE);
        final Firebase ref = new Firebase("https://blinding-inferno-3349.firebaseio.com/");
        Firebase.setAndroidContext(mContext);
        final Message msg = mData.get(position);
        final TextView nameTextView = (TextView) convertView.findViewById(R.id.textViewName);
        messageKey=new ArrayList<>();
        Firebase newref = new Firebase("https://blinding-inferno-3349.firebaseio.com/users/");
        newref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    User firebaseUser = postSnapshot.getValue(User.class);
                    if (firebaseUser.getEmail().equals(msg.getSender())) {
                        nameTextView.setText(firebaseUser.getFullName());
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

//        nameTextView.setText(ViewMessagesFragment.current.getFullName());
        TextView msgTextView = (TextView) convertView.findViewById(R.id.textViewMessage);
        msgTextView.setText(msg.getMessage());
        TextView dateTextView = (TextView) convertView.findViewById(R.id.textViewDate);
        dateTextView.setText(msg.getTimeStamp());
        ImageView deleteImageView = (ImageView) convertView.findViewById(R.id.imageViewDelete);
        deleteImageView.setVisibility(View.INVISIBLE);
//        String userFirstName = user;
        if(msg.getSender().equals(ViewMessagesFragment.current.getEmail())){
        convertView.findViewById(R.id.holder).setBackgroundColor(Color.DKGRAY);
            deleteImageView.setVisibility(View.VISIBLE);
            deleteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Firebase postref=ref.child("Messages");
                    postref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                                Message message = postSnapshot.getValue(Message.class);

                                if(message.getTimeStamp().equals(msg.getTimeStamp())) {
//                                    messageKey.add(postSnapshot.getKey());
                                    Firebase ref = new Firebase("https://blinding-inferno-3349.firebaseio.com/Messages");
                                    Firebase convRef = ref.child(postSnapshot.getKey());
                                    Map<String, Object> details = new HashMap<String, Object>();
                                    details.put("deletedBy",ViewMessagesFragment.current.getEmail());
                                    details.put("message", message.getMessage());
                                    details.put("message_read", "true");
                                    details.put("receiver", message.getReceiver());
                                    details.put("sender", message.getSender());
                                    details.put("timeStamp", message.getTimeStamp());
                                    convRef.updateChildren(details);
                                    ViewMessagesFragment.adapter.notifyDataSetChanged();
                                    activity.displaylatestmessages(ViewMessagesFragment.receiver,ViewMessagesFragment.current);
                                }
                            }
                            System.out.println(snapshot.getValue());
                        }
                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            System.out.println("The read failed: " + firebaseError.getMessage());
                        }
                    });

                }
            });

        }else{
            deleteImageView.setVisibility(View.GONE);
        }


        return convertView;
    }

    public interface ItemsInterface{
        void displaylatestmessages(User user,User currentuser);
    }

}

package com.example.ss899.homework09;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConversationsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ConversationsFragment extends Fragment {
    ListView lv;
    ArrayList<User> userList;
    ArrayList<User> nonArchiveUserList;
    User currentUser;
    String email;
    Firebase firebase;
    static ConversationsFragment INSTANCE;
    String picture;
    ArrayList<String> newSenders;
    ArrayList<String> nonArchiveMessages;
    private OnFragmentInteractionListener mListener;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    ArrayList<Conversation> conversationList;
    int conversationIndex;
    ArrayList<String> conversationKey;
    ArrayList<String> messageKey;
    ArrayList<Message> messageList;

    public ConversationsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        DrawerLayout drawerLayout= (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
//        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity().getApplicationContext());
        final View dialogEditText = layoutInflater.inflate(R.layout.prompts, null);
        final Integer[] myParams = {0};
        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Archive or Delete");
        builder.setView(dialogEditText);
        Button archive = (Button) dialogEditText.findViewById(R.id.btnArchive);
        Button delete = (Button) dialogEditText.findViewById(R.id.btnDelete);


        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        INSTANCE=this;
        Firebase.setAndroidContext(getActivity());
        firebase = new Firebase("https://blinding-inferno-3349.firebaseio.com/");
        Firebase ref = new Firebase("https://blinding-inferno-3349.firebaseio.com/users/");
        email = (String) firebase.getAuth().getProviderData().get("email");
        Log.d("demo", email);
        userList = new ArrayList<>();
        nonArchiveUserList = new ArrayList<>();
        newSenders = new ArrayList<>();
        nonArchiveMessages = new ArrayList<>();
        conversationList = new ArrayList<>();
        conversationKey = new ArrayList<>();
        messageKey = new ArrayList<>();
        messageList = new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    User firebaseUser = postSnapshot.getValue(User.class);
                    if (!firebaseUser.getEmail().toString().equals(email)) {
                        userList.add(firebaseUser);
                    }
                    else
                    {
                        currentUser = new User();
                        currentUser.fullName = firebaseUser.getFullName();
                        currentUser.picture = firebaseUser.getpicture();
                        currentUser.email = firebaseUser.getEmail();
                        currentUser.phoneNumber = firebaseUser.getPhoneNumber();
                        currentUser.password = firebaseUser.getpassword();
                    }
                }
try {
    ImageView profilePic = (ImageView) getActivity().findViewById(R.id.imageView);

    TextView textView = (TextView) getActivity().findViewById(R.id.textView);
    textView.setText(currentUser.getFullName());
    textView.setTextColor(Color.WHITE);
    byte[] decodedString = Base64.decode(currentUser.picture, Base64.DEFAULT); //Base64.decode(user.getPhoto(), Base64.DEFAULT);
    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    profilePic.setImageBitmap(decodedByte);
}catch (Exception e){
    e.printStackTrace();
}


                Firebase refM = new Firebase("https://blinding-inferno-3349.firebaseio.com/Messages/");
                refM.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            Message messages = postSnapshot.getValue(Message.class);
                            if (messages.getReceiver().equals(currentUser.getEmail()) && messages.getMessage_read().equals("false")) {
                                newSenders.add(messages.getSender());
                                messageList.add(messages);
                                messageKey.add(postSnapshot.getKey());
                            }
                        }

                        for (int i = 0; i < userList.size(); i++) {
                            if (newSenders.contains(userList.get(i).getEmail())) {
                                userList.set(i, userList.get(i)).setStatus("false");
                            } else {
                                userList.set(i, userList.get(i)).setStatus("true");
                            }
                        }


                        //Conversations
                        Firebase refC = new Firebase("https://blinding-inferno-3349.firebaseio.com/Conversations/");
                        refC.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                int j = 0;
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    Conversation conversation = postSnapshot.getValue(Conversation.class);

                                    for (int i = 0; i < userList.size(); i++) {
                                        if (!conversation.getDeletedBy().equals(currentUser.getEmail())
                                                && ((userList.get(i).getEmail().equals(conversation.getParticipant1()) && conversation.getParticipant2().equals(currentUser.getEmail()) && conversation.archived_by_participant2.equals("false"))
                                                || (userList.get(i).getEmail().equals(conversation.getParticipant2()) && conversation.getParticipant1().equals(currentUser.getEmail()) && conversation.archived_by_participant1.equals("false")))) {

                                            conversationList.add(conversation);
                                            conversationKey.add(postSnapshot.getKey());
                                            nonArchiveUserList.add(userList.get(i));
                                            if(userList.get(i).getStatus().equals("true"))
                                                nonArchiveUserList.set(j, nonArchiveUserList.get(j)).setStatus("true");
                                            else
                                                nonArchiveUserList.set(j, nonArchiveUserList.get(j)).setStatus("false");
                                            j++;
                                        }
                                    }
                                }

                                try {
                                    lv = (ListView) getActivity().findViewById(R.id.listViewconversations);
                                    ConversationAdapter adapter = new ConversationAdapter(getActivity(), R.layout.row_item, nonArchiveUserList);
                                    lv.setAdapter(adapter);
                                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            for(int i = 0; i < messageList.size(); i++)
                                            {
                                                if(messageList.get(i).getMessage_read().equals("false")
                                                        && messageList.get(i).getSender().equals(nonArchiveUserList.get(position).getEmail())
                                                        && messageList.get(i).getReceiver().equals(currentUser.getEmail()))
                                                {
                                                    Firebase ref = new Firebase("https://blinding-inferno-3349.firebaseio.com/Messages");
                                                    Firebase convRef = ref.child(messageKey.get(i));
                                                    Map<String, Object> details = new HashMap<String, Object>();
                                                    details.put("deletedBy", messageList.get(i).getDeletedBy());
                                                    details.put("message", messageList.get(i).getMessage());
                                                    details.put("message_read", "true");
                                                    details.put("receiver", messageList.get(i).getReceiver());
                                                    details.put("sender", messageList.get(i).getSender());
                                                    details.put("timeStamp", messageList.get(i).getTimeStamp());
                                                    convRef.updateChildren(details);

                                                }
                                            }
                                            mListener.moveToViewMessageFrag(nonArchiveUserList.get(position), currentUser);
                                        }
                                    });

                                    lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                        @Override
                                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                                            conversationIndex = position;
                                            dialog = builder.create();
                                            dialog.show();
                                            return false;
                                        }
                                    });
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        System.out.println("The read failed: " + firebaseError.getMessage());
                    }
                });
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        archive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase ref = new Firebase("https://blinding-inferno-3349.firebaseio.com/Conversations");
                Firebase convRef = ref.child(conversationKey.get(conversationIndex));
                if(conversationList.get(conversationIndex).getParticipant1().equals(currentUser.getEmail()))
                {
                    Map<String, Object> details = new HashMap<String, Object>();
                    details.put("archived_by_participant1", "true");
                    details.put("archived_by_participant2", conversationList.get(conversationIndex).getArchived_by_participant2());
                    details.put("deletedBy", conversationList.get(conversationIndex).getDeletedBy());
                    details.put("participant1", conversationList.get(conversationIndex).getParticipant1());
                    details.put("participant2", conversationList.get(conversationIndex).getParticipant2());
                    convRef.updateChildren(details);
                }
                else
                {
                    Map<String, Object> details = new HashMap<String, Object>();
                    details.put("archived_by_participant1", conversationList.get(conversationIndex).getArchived_by_participant1());
                    details.put("archived_by_participant2", "true");
                    details.put("deletedBy", conversationList.get(conversationIndex).getDeletedBy());
                    details.put("participant1", conversationList.get(conversationIndex).getParticipant1());
                    details.put("participant2", conversationList.get(conversationIndex).getParticipant2());
                    convRef.updateChildren(details);
                }
                dialog.dismiss();
                Fragment fragment = new ConversationsFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Firebase ref = new Firebase("https://blinding-inferno-3349.firebaseio.com/Conversations");
                Firebase convRef = ref.child(conversationKey.get(conversationIndex));
                String deleteLink = "https://blinding-inferno-3349.firebaseio.com/Conversations/" + conversationKey.get(conversationIndex);
                if(conversationList.get(conversationIndex).getDeletedBy().equals("none"))
                {
                    Map<String, Object> details = new HashMap<String, Object>();
                    details.put("archived_by_participant1", conversationList.get(conversationIndex).getArchived_by_participant1());
                    details.put("archived_by_participant2", conversationList.get(conversationIndex).getArchived_by_participant2());
                    details.put("deletedBy", currentUser.getEmail());
                    details.put("participant1", conversationList.get(conversationIndex).getParticipant1());
                    details.put("participant2", conversationList.get(conversationIndex).getParticipant2());
                    convRef.updateChildren(details);
                }
                else
                {
                    Firebase deleteRef = new Firebase(deleteLink);
                    deleteRef.removeValue();
                }
                dialog.dismiss();
                Fragment fragment = new ConversationsFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_conversations, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener=(OnFragmentInteractionListener)activity;
    }
    /*    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

        void moveToViewMessageFrag(User user, User currentUser);
    }
    public static ConversationsFragment getActivityInstance()
    {
        return INSTANCE;
    }

    public String getData()
    {
        return this.picture;
    }
}

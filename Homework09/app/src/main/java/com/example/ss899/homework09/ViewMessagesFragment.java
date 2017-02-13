package com.example.ss899.homework09;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ViewMessagesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    ArrayList<Message> messagesList;
    ListView lv;
    Firebase ref;
    Button bsend;
    EditText editTextmsg;
    static User receiver;
    static User current;
    static ItemsAdapter adapter;
    private OnFragmentInteractionListener mListener;
    boolean isEmpty;


    public ViewMessagesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Firebase.setAndroidContext(getActivity());
        ref = new Firebase("https://blinding-inferno-3349.firebaseio.com/Messages/");
        displayMessage();
        Firebase newref = new Firebase("https://blinding-inferno-3349.firebaseio.com/users/");
        newref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    User firebaseUser = postSnapshot.getValue(User.class);
                    if (firebaseUser.getEmail().equals(receiver.getEmail())) {
//                        nameTextView.setText(firebaseUser.getFullName());
                        try {
                            ((MainActivity) getActivity()).getSupportActionBar().setTitle(firebaseUser.getFullName());
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        bsend = (Button) getActivity().findViewById(R.id.buttonsend);
        editTextmsg = (EditText) getActivity().findViewById(R.id.editText);
        bsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editTextmsg.getText().toString();
//                Timestamp date = new Timestamp(System.currentTimeMillis());
                if(message.equals("")){
                    Toast.makeText(getActivity().getApplicationContext(), "Input field cannot be blank", Toast.LENGTH_LONG).show();
                }else {
                    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                    String timeStamp = df.format(Calendar.getInstance().getTime());
                    String msg_read = "false";
                    Firebase convref = new Firebase("https://blinding-inferno-3349.firebaseio.com/Conversations/");
                    Firebase newPostRef = ref.push();


                    Message msg = new Message(timeStamp, "none", msg_read, message, receiver.getEmail(),current.getEmail());
                    if(isEmpty==true) {
                        Firebase newconvref=convref.push();
                        Conversation conversation = new Conversation( "false", "false", "none", current.getEmail(), receiver.getEmail());
                        newconvref.setValue(conversation);
                        isEmpty=false;
                    }
                    newPostRef.setValue(msg);
                    editTextmsg.setText("");

//                adapter.setNotifyOnChange(true);
                    mListener.displayUpdatedContent(receiver, current);
//                    displayMessage();
                }

            }
        });
    }


    public void displayMessage() {
        final Firebase newref = new Firebase("https://blinding-inferno-3349.firebaseio.com/Messages");

        newref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messagesList = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Message msg = postSnapshot.getValue(Message.class);

                    String emailkey = (String) newref.getAuth().getProviderData().get("email");

                   if(!(msg.deletedBy.equals(emailkey))) {
                       if ((msg.getReceiver().equals(receiver.getEmail()) && msg.getSender().equals(current.getEmail()))
                               || (msg.getReceiver().equals(current.getEmail()) && (msg.getSender().equals(receiver.getEmail())))) {
//                        if(msg.getDeletedBy().equals("none") ||msg.getDeletedBy().equals(receiver.getEmail())){
                           messagesList.add(msg);
                       }
                   }
//                    }

                }
                if(messagesList.size()==0){
                    isEmpty=true;
                }
                System.out.println(dataSnapshot.getValue());
                ListView listView = (ListView) getActivity().findViewById(R.id.listViewDisplayMessages);
                adapter = new ItemsAdapter(getActivity(), R.layout.row_item_layout, messagesList);
                listView.setAdapter(adapter);
                adapter.setNotifyOnChange(true);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return inflater.inflate(R.layout.fragment_view_messages, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }



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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name

        void displayUpdatedContent(User user, User currentuser);
    }

    public void getContactDetails(User currentuser, User user){
        receiver=user;
        current=currentuser;
    }
}


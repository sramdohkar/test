package com.example.ss899.homework09;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContactsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContactsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactsFragment extends Fragment {
    ListView lv;
    ArrayList<User> userList;
    User currentUser;
    String email;
    Firebase firebase;
    static ContactsFragment INSTANCE;
    String picture;
    ArrayList<String> newSenders;
    ContactsAdapter adapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ContactsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactsFragment newInstance(String param1, String param2) {
        ContactsFragment fragment = new ContactsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts, container, false);
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

        mListener = (OnFragmentInteractionListener) activity;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        INSTANCE=this;
        Firebase.setAndroidContext(getActivity());
        firebase = new Firebase("https://blinding-inferno-3349.firebaseio.com/users/");
        Firebase ref = new Firebase("https://blinding-inferno-3349.firebaseio.com/users/");
        email = (String) firebase.getAuth().getProviderData().get("email");
        Log.d("demo", email);
        userList = new ArrayList<>();
        newSenders = new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    User firebaseUser = postSnapshot.getValue(User.class);
                    if (!firebaseUser.getEmail().toString().equals(email)) {
                        userList.add(firebaseUser);
                    } else {
                        currentUser = new User();
                        currentUser.fullName = firebaseUser.getFullName();
                        currentUser.picture = firebaseUser.getpicture();
                        currentUser.email = firebaseUser.getEmail();
                        currentUser.phoneNumber = firebaseUser.getPhoneNumber();
                        currentUser.password = firebaseUser.getpassword();
                    }
                }

                Firebase refM = new Firebase("https://blinding-inferno-3349.firebaseio.com/Messages/");
                refM.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            Message messages = postSnapshot.getValue(Message.class);
                            if (messages.getReceiver().equals(currentUser.getEmail()) && messages.getMessage_read().equals("false")) {
                                newSenders.add(messages.getSender());
                            }
                        }

                        for (int i = 0; i < userList.size(); i++) {
                            if (newSenders.contains(userList.get(i).getEmail())) {
                                userList.set(i, userList.get(i)).setStatus("false");
                            } else {
                                userList.set(i, userList.get(i)).setStatus("true");
                            }
                        }


                     try{
                         lv = (ListView) getActivity().findViewById(R.id.listViewcontacts);
                         adapter = new ContactsAdapter(getActivity(), R.layout.row_item, userList);
                        lv.setAdapter(adapter);
                         lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                             @Override
                             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                 Log.d("dmeo", mListener + "");
                                 mListener.sendUserDetails(currentUser, userList.get(position));
                             }
                         });

                         lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                             @Override
                             public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                                 mListener.sendContactDetails(userList.get(position));
                                 return false;
                             }
                         });
                     }catch(Exception e){
                         e.printStackTrace();
                     }

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
        void sendUserDetails(User currentUser, User user);
        void sendContactDetails(User user);
    }
}

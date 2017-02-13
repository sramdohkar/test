package com.example.ss899.homework09;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class LoginFragment extends Fragment {
    EditText editTextemail,editTextpassword;
    Firebase firebase;
    Button blogin,bsignup;
    Firebase.AuthResultHandler authResultHandler;
    String email=null;
//    Intent intent;
    String password=null;
    OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        DrawerLayout drawerLayout= (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed() {
//        if (mListener != null) {
//            mListener.onFragmentInteraction();
//        }
//    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(false);

        Firebase.setAndroidContext(getActivity());
        firebase=new Firebase("https://blinding-inferno-3349.firebaseio.com/");
        editTextemail= (EditText) getActivity().findViewById(R.id.editText);
        editTextpassword= (EditText) getActivity().findViewById(R.id.editText2);
        blogin= (Button) getActivity().findViewById(R.id.buttonlogin);
        bsignup= (Button) getActivity().findViewById(R.id.buttonsignup);
        blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("demo", "Iam here");
                email=editTextemail.getText().toString();
                password=editTextpassword.getText().toString();
                Log.d("email",email);
//                intent=new Intent(LoginAcivity.this,ExpensesActivity.class);
//                intent.putExtra("email",email);
                firebase.authWithPassword(email, password, authResultHandler);

            }
        });
        authResultHandler=new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                Log.d("demo", authData.getUid() + "");
                mListener.moveToConversation();

            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                switch(firebaseError.getCode()){
                    case FirebaseError.EMAIL_TAKEN:
                        Toast.makeText(getActivity(), "Email is already present.", Toast.LENGTH_SHORT).show();
                        break;
                    case FirebaseError.INVALID_EMAIL:
                        Toast.makeText(getActivity(),"Check your email",Toast.LENGTH_SHORT).show();
                        break;
                    case FirebaseError.INVALID_PASSWORD:
                        Toast.makeText(getActivity(),"Invalid Password.",Toast.LENGTH_SHORT).show();
                        break;
                    case FirebaseError.USER_DOES_NOT_EXIST:
                        Toast.makeText(getActivity(),"User does not exist",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }

            }
        };

        bsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.moveToSignUp();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setHasOptionsMenu(false);
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

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
        void moveToConversation();
        void moveToSignUp();
    }
}

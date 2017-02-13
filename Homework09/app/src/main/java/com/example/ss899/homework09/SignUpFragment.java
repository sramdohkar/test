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
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignUpFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class SignUpFragment extends Fragment {
    EditText etSignUpUserName,etSignUpEmail,etSignUpPassword,etConfirmPassword,etPhoneNum;
    Button btnSignUp,btnCancel;
    Firebase firebase;
    Firebase.AuthResultHandler authResultHandler;
    String uname,email,password,cpassword, phoneNum;
    OnFragmentInteractionListener mListener;
    MainActivity activity;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Firebase.setAndroidContext(getActivity());
        firebase=new Firebase("https://blinding-inferno-3349.firebaseio.com/");
        etSignUpUserName= (EditText) getActivity().findViewById(R.id.userName);
        etSignUpEmail= (EditText) getActivity().findViewById(R.id.email);
        etSignUpPassword= (EditText) getActivity().findViewById(R.id.password);
        etConfirmPassword= (EditText) getActivity().findViewById(R.id.confirmPass);
        etPhoneNum = (EditText) getActivity().findViewById(R.id.phoneNumber);
        btnSignUp= (Button) getActivity().findViewById(R.id.button2);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uname=etSignUpUserName.getText().toString();
                email=etSignUpEmail.getText().toString();
                password=etSignUpPassword.getText().toString();
                cpassword=etConfirmPassword.getText().toString();
                phoneNum=etPhoneNum.getText().toString();
                if(uname.trim().equals("") || email.trim().equals("") || password.trim().equals("") || cpassword.trim().equals(""))
                {
                    Toast.makeText(getActivity(), "All fields are mandatory", Toast.LENGTH_SHORT).show();
                }
                else if(!password.equals(cpassword)){
                    Toast.makeText(getActivity(),"Passwords do not match",Toast.LENGTH_SHORT).show();
                }else{
//                    firebase.authWithPassword(email, password, authResultHandler);
                    firebase.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                        @Override
                        public void onSuccess(Map<String, Object> result) {
                            System.out.println("Successfully created user account with uid: " + result.get("uid"));
                            Map<String, String> map = new HashMap<String, String>();
                            User user=new User();
                            user.setEmail(email);
                            user.setpassword(password);
                            user.setPhoneNumber(phoneNum);
                            user.setFullName(uname);
                            String picture = "/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBwgHBgkIBwgKCgkLDRYPDQwMDRsUFRAWIB0iIiAdHx8kKDQsJCYxJx8fLT0tMTU3Ojo6Iys/RD84QzQ5OjcBCgoKDQwNGg8PGjclHyU3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3N//AABEIAIYAhgMBIgACEQEDEQH/xAAbAAEAAwEBAQEAAAAAAAAAAAAAAQUGBAIDB//EAC8QAAEDAgQFAwMEAwAAAAAAAAABAgMEEQUhMnEVNFOBoTFBYRITUSJygpEzUrH/xAAYAQADAQEAAAAAAAAAAAAAAAAAAQIDBP/EAB0RAQEAAwEBAAMAAAAAAAAAAAABAhExAxMSQVH/2gAMAwEAAhEDEQA/AP22WRkTFfItmp6qc/EqTq+CMW5GTsZ0vHHZWtHxKk6vgcSpOr4M4CvnC20fEqTq+BxKk6vgzgD5wbaPiVJ1fA4lSdXwZw+kMMkzrRMVy/Afhie1/wASpOr4HEqTq+CviweZyXkc1u2Z90weJqKrpXr2RCdYjbp4lSdXwOJUnV8Gdfa+XpfIgr5wttHxKk6vgcSpOr4M4A+cG2j4lSdXwExGlVcpPBnD0zUm4fOQ9tYi3S4IZpTYGRuTFeRk7GdQ0WK8jJ2M6hr58TUgA0IPUbHSvRjEVXKeC4wSBUa6fLP9KCyuoI9UGGIifXUsu5FyS5ZMY1iWYxGp+ESx6JMLbVhCpdCQIKTEMOVl5YG/pVbq33QrDWqhRYvSthkbKyyI9c0/CmmOX6TYrwAakEs1puQSzWm4rwRq2aU2AZpTYHOtyYryMnYzqGixXkZOxnUNfPiakAGhINBgvJfzUz5oMF5L+akenDjvABioAAAKrHv8UX7lLUqse/wxfuHj0qpSSEJOhISzWm5BLNabivBGrZpTYBmlNgc63JivIydjOmixXkZOxnUNfPiakAGhBo8NgdT0qMeqKqqq5fJm/Q1FG9X00bnLdVbmZ+hx9wAZKAAACtxtt6RF/DiyOLFkvQvv8f8AR49Ks6SAdCQlmtNyCWa03FeCNWzSmwDNKbA51uTFeRk7GdNFivIydjOmvnxNSADQkF/g8/3ab6XZOYtuxQnZhM7YapEeuT0snwTnNwRogQhJgsAAAKrGalqQ/YRbvVUv8FqZivk+5VyOt72/orCbpVzkgG6QlmtNyCWa03FeCNWzSmwDNKbA51uTFeRk7GdNFivIydjOoa+fE1IANCB6LcEewBp6GVZ6SORfVUz39zoPhQx/bo4mWtZqXPuc16sAABx4pO6Ckc5l0c6yJ8GdX1ve+fuaLE4Fmo5ET1b+pOxnTXzTQAGhBLNabkEs1puK8EatmlNgGaU2BzrcmK8jJ2M6aLFuRk7GdNfPiakBGuctmtVy/CFhS4VLLZ0q/bavt7l2yFpXnbh1E+aZj5GKkSLfP3LWDDqeBUVrLu/LszrM7n/D0ISAZqAAAQvoZ7EqN0EqvY28blvl7GiPKtullRFT5HLorGTBfVOFQy3dHeN3x6FTUUVRBdXs+pv+zczaZSlpzks1puefa56ZrbuO8Jq2aU2AZpTYHOtz4hE6WlcxiXctrIcEGDuVbzSWT/Vvr/ZcgctgfKCnigb9MTEb8/k+ls7kgQAAAAAAAAAAAACLEgA46jDqefNW/S5fduRXyYTLG5Ficj0RUyXJS8A/ypaQ3JqAkCMAAAAAAAAAAAAAAAAAAAAAAAAAAB//2Q==";

                            Firebase newUser = firebase.child("users").child(result.get("uid").toString());
                            newUser.child("email").setValue(user.getEmail());
                            newUser.child("fullName").setValue(user.getFullName());
                            newUser.child("password").setValue(user.getpassword());
                            newUser.child("phoneNumber").setValue(user.getPhoneNumber());
                            newUser.child("picture").setValue(picture);
                            Toast.makeText(getActivity(), "User is added", Toast.LENGTH_SHORT).show();
                            mListener.returnToLogin();

                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {
                            switch(firebaseError.getCode()){
                                case FirebaseError.EMAIL_TAKEN:
                                    Toast.makeText(getActivity(),"Email is already present.",Toast.LENGTH_SHORT).show();
                                    break;
                                case FirebaseError.INVALID_EMAIL:
                                    Toast.makeText(getActivity(),"Check your email",Toast.LENGTH_SHORT).show();
                                    break;
                                case FirebaseError.INVALID_PASSWORD:
                                    Toast.makeText(getActivity(),"Invalid Password.",Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    break;
                            }
                        }
                    });

                }

            }
        });
        btnCancel= (Button) getActivity().findViewById(R.id.buttonCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.returnToLogin();
            }
        });


    }

    @Override
    @SuppressWarnings("deprecation")
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (OnFragmentInteractionListener) activity;
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
        void returnToLogin();
    }
}

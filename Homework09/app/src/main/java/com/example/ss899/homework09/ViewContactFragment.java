package com.example.ss899.homework09;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewContactFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ViewContactFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    User contact;
    public ViewContactFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        try{
            ((MainActivity)getActivity()).getSupportActionBar().setTitle("View Contact");
        }catch (Exception e){
            e.printStackTrace();
        }
        //send image from messages activity without using intent...you can find similar code in edit profile activity.
//        String testImage = "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";
        String testImage = contact.getpicture();
        TextView flName = (TextView) getActivity().findViewById(R.id.textView);
        TextView username = (TextView) getActivity().findViewById(R.id.textView5);
        TextView phoneNum = (TextView) getActivity().findViewById(R.id.textView6);
        TextView emailID = (TextView) getActivity().findViewById(R.id.textView7);
        ImageView profilePic = (ImageView) getActivity().findViewById(R.id.imageView);

        username.setText(contact.getFullName());
        phoneNum.setText(contact.getPhoneNumber());
        emailID.setText(contact.getEmail());
        flName.setText(contact.getFullName());
        byte[] decodedString = Base64.decode(testImage, Base64.DEFAULT); //Base64.decode(user.getPhoto(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        profilePic.setImageBitmap(decodedByte);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_contact, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void getUserDetails(User user){
        contact = user;
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

    }
}

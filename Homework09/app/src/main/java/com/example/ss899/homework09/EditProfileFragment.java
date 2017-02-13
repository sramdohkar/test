package com.example.ss899.homework09;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class EditProfileFragment extends Fragment {
    String email;
    ImageView imgPhoto;
    String photo;
    Firebase firebase;
    static User currentUser;
    static EditProfileFragment INSTANCE;
    //    String uid = "d3d7e595-8293-4682-911a-71dc2f942c02"; //firebase.getAuth().getUid();
    private static int RESULT_LOAD_IMAGE = 1;
    private OnFragmentInteractionListener mListener;

    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    public void onButtonPressed(Uri uri) {

    }
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mListener = (OnFragmentInteractionListener) activity;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final TextView txtFullName = (TextView) getActivity().findViewById(R.id.txtFullName);
        imgPhoto = (ImageView) getActivity().findViewById(R.id.imgPhoto);
        final EditText edtFullName = (EditText) getActivity().findViewById(R.id.edtFullName);
        final TextView edtEmail = (TextView) getActivity().findViewById(R.id.edtEmail);
        final EditText edtPhone = (EditText) getActivity().findViewById(R.id.edtPhone);
        final TextView edtPass = (TextView) getActivity().findViewById(R.id.edtPass);
        Button btnUpdate = (Button) getActivity().findViewById(R.id.btnUpdate);
        Button btnCancel = (Button) getActivity().findViewById(R.id.btnCancel);
        try{
            ((MainActivity)getActivity()).getSupportActionBar().setTitle("Edit Profile");
        }catch (Exception e){
            e.printStackTrace();
        }
        INSTANCE=this;
        Firebase.setAndroidContext(getActivity());
        firebase = new Firebase("https://blinding-inferno-3349.firebaseio.com/users/");
        Firebase ref = new Firebase("https://blinding-inferno-3349.firebaseio.com/users/");
        email = (String) firebase.getAuth().getProviderData().get("email");
        Log.d("demo", email);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    User firebaseUser = postSnapshot.getValue(User.class);
                    if (!firebaseUser.getEmail().toString().equals(email)) {
                        Log.d("demo", "testMessage");
                    } else {
                        Log.d("demo", "testMessage1");
                        currentUser = new User();
                        currentUser.fullName = firebaseUser.getFullName();
                        currentUser.picture = firebaseUser.getpicture();
                        currentUser.email = firebaseUser.getEmail();
                        currentUser.phoneNumber = firebaseUser.getPhoneNumber();
                        currentUser.password = firebaseUser.getpassword();
                        Log.d("demoo", currentUser + "");


                        String defaultImage = currentUser.getpicture();

                        String fullName = currentUser.getFullName();

                        //        if (ConversationsFragment.getActivityInstance().getData() == null
                        //                || ConversationsFragment.getActivityInstance().getData().equals(null) ||
                        //                ConversationsFragment.getActivityInstance().getData().equals("")) {
                        //            photo = defaultImage;
                        //        } else {
                        //            photo = ConversationsFragment.getActivityInstance().getData();
                        //        }

                        email = currentUser.getEmail();
                        final String phone = currentUser.getPhoneNumber();
                        String pass = currentUser.getpassword();
                        photo = defaultImage;

                        txtFullName.setText(fullName);
                        byte[] decodedString = Base64.decode(defaultImage, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        imgPhoto.setImageBitmap(decodedByte);
                        edtFullName.setText(fullName);
                        edtEmail.setText(email);
                        edtPhone.setText(phone);
                        edtPass.setText(pass);

                    }
                }
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtFullName.getText().toString().trim().equals("") || edtEmail.getText().toString().trim().equals("") ||
                        edtPhone.getText().toString().trim().equals("") || edtPass.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    String uid = firebase.getAuth().getUid();
                    Firebase ref = new Firebase("https://blinding-inferno-3349.firebaseio.com/users/");

                    Firebase userRef = ref.child(uid);
                    Map<String, Object> details = new HashMap<String, Object>();
                    details.put("email", edtEmail.getText().toString().trim());
                    details.put("fullName", edtFullName.getText().toString().trim());
                    details.put("password", edtPass.getText().toString().trim());
                    details.put("phoneNumber", edtPhone.getText().toString().trim());
                    details.put("picture", photo);
                    userRef.updateChildren(details);
                    Toast.makeText(getActivity(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
//                    getActivity().finish();

                    Fragment fragment = new ConversationsFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new ConversationsFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
//                getActivity().finish();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);

            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
//            imgPhoto.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            cursor.close();

            Bitmap bitmap= BitmapFactory.decodeFile(picturePath);
            ByteArrayOutputStream stream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
            final byte[] image=stream.toByteArray();
            final String img_str = Base64.encodeToString(image, 0);
            photo = img_str;
            byte[] decodedString = Base64.decode(img_str, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imgPhoto.setImageBitmap(decodedByte);
        } else {
            Toast.makeText(getActivity(), "Try Again!!", Toast.LENGTH_SHORT)
                    .show();
        }

    }


  /*  @Override
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
    }
}


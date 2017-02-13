package com.example.ss899.homework09;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by shash on 26/04/2016.
 */
public class ContactsAdapter extends ArrayAdapter<User> {

    List<User> mData;
    Context mContext;
    int mResource;

    public ContactsAdapter(Context context, int resource, List<User> objects) {
        super(context, resource, objects);

        this.mContext = context;
        this.mData = objects;
        this.mResource = resource;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);
        }

        String testImage = "iVBORw0KGgoAAAANSUhEUgAAAJYAAACWCAYAAAA8AXHiAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAZdEVYdFNvZnR3YXJlAEFkb2JlIEltYWdlUmVhZHlxyWU8AAAFcklEQVR4Xu3bMW4bSRSEYZ9JjvcOm+4hNtIFNlboeA+gbI8gwLEDRTqAUgW6greDBh6In+SQ1d2socrAF6jedI/1VLYEA/72/6/vERNgGKHCMEKFYYQKwwgVhhEqDCNUGEaoMIxQYRihwjBChWGECsMIFYYRKgwjVBhGqDCMUGEYocIwQoVhhArDCBWGESoMI1QYRqgwjFBhGKHCMEKFYYQKwwgVhhEqDCNUGEaoMIxQYRihwjBChWGECsMIFYZ2Pj8/f8XnL9qNKQzt0JK/ItqNKQzt0JK/ItqNKQzt0JI/Pj5+Pj4+/knP71n7nNrnRp8zPW8KQzu05ObeynWqVA2dMYWhnbrcw8XfS7nOlaqhc6YwtFOXS1+AvZfrWKmen5//rh/TWVMY2jlc7j2V61Sp2rxmh2eNYWiHlnsP5TpXqqbm9aw5DO0cW+6ey7WlVE2d1dwchnZOLXeP5dpaqqbOD2fGMLRzbrl7KtclpWrqMzQ3haGdLcvdQ7kuLVVTn6O5KQztbF2uc7muKVVTn6W5KQztXLJcx3I9PDz8cU2pmvo8zU1haOfS5TqVq5Xq/f39v/p7abaUqqlnaG4KQzvXLNehXGqpmnqO5qYwtHPtcm9ZrhGlaupZmpvC0I6y3FuUa1Spmnqe5qYwtKMud2W5RpaqqXfQ3BSGdkYsd0W5jpXq5eXlH3p+i3oPzU1haGfUcmeW61ipXl9ff9DzW9W7aG4KQzsjlzujXLNK1dT7aG4KQzujlzuyXDNL1dQ7aW4KQzszljuiXLNL1dR7aW4KQzuzlquUa0Wpmno3zU1haGfmcq8p16pSNfV+mpvC0M7s5V5SrpWlauo7aG4KQzsrlrulXKtL1dT30NwUhnZWLfdUuW5Rqqa+i+amMLSzcrnHyvX29vZvzZrZpWrq+2huCkM7q5dL5Tq0olRNfSfNTWFo5xbLPVWuVaVq6ntpbgpDO7daLn37a1rp6PkZ6ntpbgpDO7dYbvtbqb636j/Q07nR6ntpbgpDO6uXe6pU3apy1XfS3BSGdlYul0rV/pnh6enpr8OfuVaUq76P5qYwtLNqucdK1f4Nq83pB/rZ5arvorkpDO2sWO65UnWry1XfQ3NTGNqZvdytpepWlqu+g+amMLQzc7mXlqpbVa56P81NYWhn1nKvLVW3olz1bpqbwtDOjOWqpepml6veS3NTGNoZvdxRpepmlqveSXNTGNoZudzRpepmlaveR3NTGNoZtdz2H0frXc2IUnUzylXvorkpDO2MWG77L+71nmZkqbrR5ar30NwUhnbU5a4qVTeyXPUOmpvC0I6y3NWl6kaVq56nuSkM7Vy73FuVqhtRrnqW5qYwtHPNcm9dqk4tVz1Hc1MY2rl0uVSq9sVcXapOKVc9Q3NTGNq5ZLnHSnXJt58Zri1XfZ7mpjC0s3W5rqXqrilXfZbmpjC0s2W57qXqLi1XfY7mpjC0c265eylVd0m56jOHM2MY2jm13L2VqttarjqvuTkM7Rxb7l5L1W0pV53Vs+YwtEPL3XupunPlqvnhWWMY2jlc7r2UqjtVrprRWVMY2qnLvbdSdcfKVT+mc6YwtFOXe+xPNp3bGypXRWdMYWiHltzcU6m6U+Wi501haIeW/BXRbkxhaIeW/BXRbkxhaIeW/BXRbkxhGKHCMEKFYYQKwwgVhhEqDCNUGEaoMIxQYRihwjBChWGECsMIFYYRKgwjVBhGqDCMUGEYocIwQoVhhArDCBWGESoMI1QYRqgwjFBhGKHCMEKFYYQKwwgVhhEqDCNUGEaoMIxQYRihwjBChWGECsMIFYYRKgwjVBhGqDCMUGEYocIwQoVhhArDCBWGESoMI1QYRgi+ff8Nm8F1qnaW1J0AAAAASUVORK5CYII=";
        final User user = mData.get(position);

        TextView txtName = (TextView) convertView.findViewById(R.id.txtName);
        ImageView imgPhoto = (ImageView) convertView.findViewById(R.id.imageViewThumb);
        //ImageView imgStatus = (ImageView) convertView.findViewById(R.id.imageStatus);
        ImageView imgCall = (ImageView) convertView.findViewById(R.id.imageCall);
//        String testImage=user.getpicture();
        if (user.getpicture() == null || !user.getpicture().equals(null) || !user.getpicture().equals("")) {
            byte[] decodedString = Base64.decode(user.getpicture(), Base64.DEFAULT); //Base64.decode(testImage, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imgPhoto.setImageBitmap(decodedByte);
        } else {
            byte[] decodedString = Base64.decode(user.getpicture(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imgPhoto.setImageBitmap(decodedByte);
        }
        txtName.setText(user.getFullName());
//        if (user.getStatus().equals("false")) {
//            imgStatus.setImageResource(R.drawable.red_bubble_clipart_1);
//        }
        imgCall.setImageResource(R.drawable.phone_icon_hi);

//        if ( Build.VERSION.SDK_INT >= 23 &&
//                ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
//
//        }

        imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + user.getPhoneNumber()));
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }
}
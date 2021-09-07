package com.student.studentinformation;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.util.List;

public class firebase_adapter extends ArrayAdapter<dbref> {

    private Activity context;
    private List<dbref> databaseView;


    public firebase_adapter(Activity context, List<dbref> databaseView) {
        super(context, R.layout.firebase_data_show_layout, databaseView);
        this.context = context;
        this.databaseView = databaseView;
    }

    @NonNull
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {

        LayoutInflater layoutInflater=context.getLayoutInflater();
        View vieww=layoutInflater.inflate(R.layout.firebase_data_show_layout,null,false);

        dbref firebasess=databaseView.get(position);

        ImageView img=vieww.findViewById(R.id.profilepicture);
        TextView t1= vieww.findViewById(R.id.Uname);
        TextView t2= vieww.findViewById(R.id.Ufaculty);
        TextView t3= vieww.findViewById(R.id.Udept);
        TextView t4= vieww.findViewById(R.id.Useassion);
 /*       TextView t5= vieww.findViewById(R.id.Uaddress);*/

        if(!firebasess.getImgurl().isEmpty()){
            Picasso.get().load(firebasess.getImgurl())

                    .placeholder(R.drawable.preloader)
                    .into(img);
        }


        t1.setText(firebasess.getName());
        t2.setText("Faculty of "+firebasess.getFaculty());
        t3.setText("Department of "+firebasess.getDepartment());
        t4.setText("Batch "+firebasess.getSession());
      /*  t5.setText(firebasess.getPresent());*/
        /*t5.setText(firebasess.getMobile());
        t6.setText(firebasess.getId());*/

        return vieww;
    }


}

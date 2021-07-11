package com.example.medapp.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medapp.EditReminder;
import com.example.medapp.LoginActivity;
import com.example.medapp.MainActivity;
import com.example.medapp.R;

public class MyListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] mainTitle;
    private final String[] button1Title;
    private final String[] button2Title;
    private final Integer[] imgId;

    public MyListAdapter(Activity context, String[] mainTitle, String[] button1Title,String[] button2Title, Integer[] imgId) {
        super(context, R.layout.my_layout, mainTitle);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.mainTitle =mainTitle;
        this.button1Title=button1Title;
        this.button2Title=button2Title;
        this.imgId =imgId;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.my_layout, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.iconImage);
        TextView button1Text = (TextView) rowView.findViewById(R.id.textview1Display);
        TextView button2Text = (TextView) rowView.findViewById(R.id.textview2Display);
        ImageView imageView1 = rowView.findViewById(R.id.imageView10);

        titleText.setText(mainTitle[position]);
        imageView.setImageResource(imgId[position]);
        button1Text.setText(button1Title[position]);
        button2Text.setText(button2Title[position]);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Icon clicked", Toast.LENGTH_SHORT).show();
            }
        });

        button1Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        button2Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        return rowView;
    };
}

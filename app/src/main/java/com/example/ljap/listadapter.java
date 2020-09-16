package com.example.ljap;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ljap.liste;


import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class listadapter extends ArrayAdapter<liste> {




    int mbcolor;






    public listadapter(Activity context, ArrayList<liste> numar, int bcolor) {

        super(context, 0, numar);
        mbcolor = bcolor;   //placing this below super is crucial for some reason
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        //if the view is not null we can just udate the values, but if it is , we need to create anew
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        final liste listitem = (liste) getItem(position);// had to add data type

        TextView engl = (TextView) listItemView.findViewById(R.id.text2);
        engl.setText(listitem.getMeng());

        TextView japa = (TextView) listItemView.findViewById(R.id.text1);
        japa.setText(listitem.getMjap());

        ImageView img = (ImageView) listItemView.findViewById(R.id.img);

        if (listitem.hasimg()) {

            img.setImageResource(listitem.getMimgres());
            img.setVisibility(View.VISIBLE); // check if this line is necessary
        } else {
            img.setVisibility(View.GONE);
        }

        LinearLayout textt = listItemView.findViewById(R.id.text);

        int color = ContextCompat.getColor(getContext(), mbcolor);
        textt.setBackgroundColor(color);



        return listItemView;
    }
}

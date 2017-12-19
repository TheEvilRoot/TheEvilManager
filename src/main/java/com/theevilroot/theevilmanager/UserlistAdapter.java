package com.theevilroot.theevilmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.theevilroot.theevilmanager.utils.User;

import java.util.List;

/**
 * Created by theevilroot on 14/12/2017.
 */

public class UserlistAdapter extends ArrayAdapter<User>{
    public UserlistAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.userlist_entry, parent, false);
        TextView name = rowView.findViewById(R.id.UL_name);
        TextView classif = rowView.findViewById(R.id.UL_classif);
        ImageView pic = rowView.findViewById(R.id.UL_pic);
        User cur = getItem(position);
        name.setText(cur.surname + ", " + cur.name);
        classif.setText(cur.classification);
        pic.setImageDrawable(getContext().getDrawable(cur.classification_pic));
        return rowView;

    }
}

package com.theevilroot.theevilmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.theevilroot.theevilmanager.utils.DownloadImageTask;
import com.theevilroot.theevilmanager.utils.User;
import com.theevilroot.theevilmanager.utils.Utilities;

import java.util.Arrays;

public class ProfileActivity extends AppCompatActivity {

    public ImageView user_picture;
    public TextView user_name, user_class, user_alises;

    public User user;

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.profile_activity);
        Intent intent = getIntent();
        user = new User(intent.getIntExtra("uid",0),
                intent.getStringExtra("name"),
                intent.getStringExtra("surname"),
                intent.getStringExtra("classification"),
                intent.getStringExtra("aliases"),
                intent.getStringExtra("picture"));
        initElements();
        initEvents();
        setTitle("User #" + user.UID);
        if(Utilities.isValidURL(user.picture))
            new DownloadImageTask(user_picture).execute(user.picture);
        else
            user_picture.setImageDrawable(getDrawable(R.drawable.unknown));
        user_name.setText(user.surname + ", " + user.name);
        user_class.setText(user.classification);
        user_alises.setText(Arrays.toString(user.aliases.split(";")));
    }

    public void initElements() {
        user_picture = (ImageView) findViewById(R.id.user_pic);
        user_name = (TextView) findViewById(R.id.user_name);
        user_class = (TextView) findViewById(R.id.user_class);
        user_alises = (TextView) findViewById(R.id.user_aliases);
    }

    public void initEvents() {

    }
}

package com.theevilroot.theevilmanager;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.theevilroot.theevilmanager.utils.User;
import com.theevilroot.theevilmanager.utils.Utilities;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddProfileActivity extends AppCompatActivity{

    public EditText name_field, surname_field, class_field, picture_field, aliases_field;
    public Button submit_button;
    public TextView output_field;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_profile_activity);
        initElements();
        initEvents();
    }

    public void initElements() {
        name_field = (EditText) findViewById(R.id.name_field);
        surname_field = (EditText) findViewById(R.id.surname_field);
        class_field = (EditText) findViewById(R.id.class_field);
        picture_field = (EditText) findViewById(R.id.picture_field);
        aliases_field = (EditText) findViewById(R.id.aliases_field);
        submit_button = (Button) findViewById(R.id.submit_add_button);
        output_field = (TextView) findViewById(R.id.output_add_field);
    }
    public void initEvents() {
        picture_field.setOnLongClickListener(view -> {
            picture_field.setText("http://" + "/Assets/POI/" + picture_field.getText() + ".png");
            return true;
        });
        submit_button.setOnClickListener(view -> {
            if(name_field.getText().length() == 0 || submit_button.getText().length() == 0 || class_field.getText().length() == 0)
                return;
            output_field.setText("");
            String name = name_field.getText().toString();
            String surname = surname_field.getText().toString();
            String classification = class_field.getText().toString();
            String picutre = picture_field.getText().toString();
            String aliases = aliases_field.getText().toString();
            Utilities.runAsync(() -> {
                try {
                    Map<String, String> data = new HashMap<>();
                    data.put("name", name);
                    data.put("surname", surname);
                    data.put("class", classification);
                    data.put("picture", picutre);
                    data.put("aliases", aliases);
                    String response = Jsoup.connect(Utilities.getBackendURL("addProfile")).data(data).get().text();
                    JsonObject obj = new JsonParser().parse(response).getAsJsonObject();
                    if(obj.get("response").isJsonObject()) {
                        runOnUiThread(() -> output_field.setText(obj.get("response").getAsJsonObject().get("error").getAsString()));
                        return;
                    }else {
                        runOnUiThread(() -> output_field.setText(obj.get("response").getAsString()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }
}

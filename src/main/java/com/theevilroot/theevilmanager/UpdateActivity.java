package com.theevilroot.theevilmanager;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.theevilroot.theevilmanager.utils.Utilities;

import org.jsoup.Jsoup;

import java.io.IOException;

public class UpdateActivity extends AppCompatActivity{

    public EditText uid_field, value_field;
    public Button submit_button;
    public TextView output;
    public Spinner type_chooser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_profile_activity);
        initElements();
        initEvents();
        Intent intent = getIntent();
        uid_field.setText(""+intent.getIntExtra("uid", 0));
    }

    public void initElements() {
        uid_field = (EditText) findViewById(R.id.update_uid_field);
        value_field = (EditText) findViewById(R.id.update_value_field);
        submit_button = (Button) findViewById(R.id.submit_update_button);
        output = (TextView) findViewById(R.id.output);
        type_chooser = (Spinner) findViewById(R.id.update_profile_chooser);
        type_chooser.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{
                "Name", "Surname", "Classification", "Aliases", "UID"
        }));
    }

    public void initEvents() {
        submit_button.setOnClickListener(view -> {
            if(uid_field.length() == 0)
                return;
            output.setText("");
            String uid = uid_field.getText().toString();
            String field = type_chooser.getSelectedItem().toString().toLowerCase();
            String value = value_field.getText().toString();
            Utilities.runAsync(() -> {
                try {
                    String response = Jsoup.connect(Utilities.getBackendURL("updateProfile")).data("uid", uid).data("field", field).data("data", value).get().text();
                    JsonObject obj = new JsonParser().parse(response).getAsJsonObject();
                    if(obj.get("response").isJsonObject()) {
                        runOnUiThread(() -> output.setText(obj.get("response").getAsJsonObject().get("error").getAsString()));
                        return;
                    }else {
                        runOnUiThread(() -> output.setText(obj.get("response").getAsString()));
                        runOnUiThread(() -> {
                            value_field.setText("");
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }
}

package com.theevilroot.theevilmanager;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.theevilroot.theevilmanager.utils.User;
import com.theevilroot.theevilmanager.utils.Utilities;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class QueryActivity extends AppCompatActivity {

    public EditText query_field;
    public TextView output_field;
    public Button submit_button;
    public ListView resultList;
    public Button add_profile_button;
    public Spinner query_type_chooser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.query_activity);
        initElements();
        initEvents();
        try {
            AppConfig.init();
        } catch (Exception e) {
            new AlertDialog.Builder(this).setTitle("Load config error").setCancelable(false).create().show();
            e.printStackTrace();
        }
    }

    public void initElements() {
        query_field = (EditText) findViewById(R.id.query_field);
        output_field = (TextView) findViewById(R.id.output_field);
        submit_button = (Button) findViewById(R.id.submit_button);
        resultList = (ListView) findViewById(R.id.userlist);
        add_profile_button = (Button) findViewById(R.id.add_profile_button);
        query_type_chooser = (Spinner) findViewById(R.id.query_type);
        query_type_chooser.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{
                "Anything","UID", "Name", "Surname", "Classification", "Aliases"
        }));
    }

    public void initEvents() {
        resultList.setOnItemClickListener((adapterView, view, i, l) -> {
            User user = (User) adapterView.getAdapter().getItem(i);
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("name", user.name);
            intent.putExtra("surname", user.surname);
            intent.putExtra("classification", user.classification);
            intent.putExtra("aliases", user.aliases);
            intent.putExtra("picture", user.picture);
            intent.putExtra("uid", user.UID);
            startActivity(intent);
        });
        add_profile_button.setOnClickListener(view -> {
            startActivity(new Intent(this, AddProfileActivity.class));
        });
        submit_button.setOnClickListener(view -> {
            if(query_field.getText().length() == 0)
                return;
            resultList.setAdapter(null);
            output_field.setText("");
            String query = query_field.getText().toString();
            Utilities.runAsync(() -> {
                try {
                    String response;
                    if(query_type_chooser.getSelectedItemPosition() == 0) {
                        response = Jsoup.connect(Utilities.getBackendURL("getProfile")).data("data", query).get().text();
                    }else{
                        response = Jsoup.connect(Utilities.getBackendURL("identifyProfile")).data("data", query).data("type", query_type_chooser.getSelectedItem().toString().toLowerCase()).get().text();
                    }
                    if(query.equals("*")) {
                        response = Jsoup.connect(Utilities.getBackendURL("getProfiles")).get().text();
                    }
                    JsonObject obj = new JsonParser().parse(response).getAsJsonObject().get("response").getAsJsonObject();
                    if (obj.has("error")) {
                        runOnUiThread(() -> output_field.setText("Error: " + obj.get("error").getAsString()));
                        return;
                    }
                    if (!obj.has("users")) {
                        runOnUiThread(() -> output_field.setText("Invalid response"));
                        return;
                    }

                    List<User> users = Utilities.jsonArrayToList(obj.get("users").getAsJsonArray(), e -> {
                        JsonObject user = e.getAsJsonObject();
                        return new User(
                                user.get("uid").getAsInt(),
                                user.get("name").getAsString(),
                                user.get("surname").getAsString(),
                                user.get("classification").getAsString(),
                                user.get("aliases").getAsString(),
                                user.get("picture").getAsString());
                    });
                    runOnUiThread(() -> resultList.setAdapter(new UserlistAdapter(this, R.layout.userlist_entry, users)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }

}

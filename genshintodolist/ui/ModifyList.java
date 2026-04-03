package com.example.genshintodolist.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.genshintodolist.MainActivity;
import com.example.genshintodolist.R;
import com.example.genshintodolist.data.DBManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ModifyList extends AppCompatActivity implements OnClickListener {

    private EditText txtTitle;
    private EditText txtDescription;
    private TextView txtDate;
    private Button btnUpdate;
    private Button btnDelete;

    private DBManager dbManager;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Modify List Detail");
        setContentView(R.layout.activity_modify_list);

        //Database Info
        txtTitle = (EditText) findViewById(R.id.txtTitle2);
        txtDate = (TextView) findViewById(R.id.txtDate2);
        txtDescription = (EditText) findViewById(R.id.txtDescription2);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        //Open Database
        dbManager = new DBManager(this);
        dbManager.open();

        //Intent Info
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String date = intent.getStringExtra("date");

        //Setting On Screen
        txtTitle.setText(title);
        txtDescription.setText(description);
        txtDate.setText(date);

        //Setting Button
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnUpdate) {
            String newtitle = txtTitle.getText().toString();
            String newdescription = txtDescription.getText().toString();
            String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            if (newtitle.isEmpty()) {
                txtTitle.setError("Title cannot be empty");
                return;
            }

            dbManager.update(id, newtitle, newdescription, today);
            setResult(RESULT_OK);
            finish();

        } else if (v.getId() == R.id.btnDelete) {
            dbManager.softDelete(id);
            setResult(RESULT_OK);
            finish();
        }
    }
}


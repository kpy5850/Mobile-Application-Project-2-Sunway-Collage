package com.example.genshintodolist.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.genshintodolist.MainActivity;
import com.example.genshintodolist.R;
import com.example.genshintodolist.data.DBManager;
import com.example.genshintodolist.util.NotificationHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddList extends AppCompatActivity implements View.OnClickListener {

    private Button btnAdd;
    private Button btnCancel;
    private EditText txtTitle;
    private EditText txtDescription;
    private TextView txtDate;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Add List");
        setContentView(R.layout.activity_add_list);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1001);
            }
        }

        txtTitle = (EditText) findViewById(R.id.txtTitle);
        txtDescription = (EditText) findViewById(R.id.txtDescription);
        txtDate = (TextView) findViewById(R.id.txtDate);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        dbManager = new DBManager(this);
        dbManager.open();

        btnAdd.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        EditText multiTaskEdit = findViewById(R.id.txtDescription);

        //add a number column when add a new description list
        multiTaskEdit.addTextChangedListener(new TextWatcher(){
            private String previousText = "";

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){
                previousText = s.toString();
            }

            @Override
            public void afterTextChanged(Editable editable){
                multiTaskEdit.removeTextChangedListener(this);

                String[] lines = editable.toString().split("\n");
                StringBuilder formatted = new StringBuilder();
                for(int i = 0; i < lines.length; i++){
                    String line = lines[i].replaceAll("^\\d+\\.\\s*", "");
                    formatted.append((i+1)).append(". ").append(line).append("\n");
                }

                String newtext = formatted.toString().trim();
                String currentText = editable.toString();

                if(!newtext.equals(previousText.trim())){
                    multiTaskEdit.setText(newtext);
                    multiTaskEdit.setSelection(newtext.length());
                }

                multiTaskEdit.addTextChangedListener(this);
            }
        });

        multiTaskEdit.setOnKeyListener((v, keyCode, event) -> {
            if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                int currentLineCount = multiTaskEdit.getText().toString().split("\n").length;
                String nextLine = "\n" + (currentLineCount + 1 )+ ". ";

                multiTaskEdit.getText().insert(multiTaskEdit.getSelectionStart(),nextLine);

                return  true;
            }
            return false;
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnAdd) {
            final String title = txtTitle.getText().toString();
            final String description = txtDescription.getText().toString();

            if(title.isEmpty()){
                txtTitle.setError("Title cannot be empty");
                return;
            }



            String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            txtDate.setText(today);

            String[] taskLines = description.split("\n");
            for (String task : taskLines) {
                if(!task.trim().isEmpty()) {
                    dbManager.insert(title, task.trim(), today);
                }
            }

            NotificationHelper.updateTodayPinnedNotification(this, dbManager);

            Intent main = new Intent(AddList.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            setResult(RESULT_OK);
            finish();
        }
        else if(v.getId() == R.id.btnCancel){
            finish();
        }
    }
}

package com.example.keep_it_together;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import java.io.IOException;


public class Edit extends AppCompatActivity {
    Client dbConnection = null;
    String task_id;
    String[] description_text;
    String task = "chore";
    Button save_button;
    EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Edit.AsyncTaskRunner runner = null;
        try {
            runner = new Edit.AsyncTaskRunner();
        } catch (IOException e) {
            e.printStackTrace();
        }
        runner.execute();
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private AsyncTaskRunner() throws IOException {}

        @Override
        protected String doInBackground(String... params) {
            try {
                dbConnection = new Client("86.9.93.210", 58934);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String result) {
            SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
            // get the house_id and user_id
            String house_Id = preferences.getString("houseID", "");
            String user_Id = preferences.getString("userID", "");

            // get the task id (chore or transaction)
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                if(bundle.getString("chore") != null){
                    task_id = bundle.getString("chore");
                }else if((bundle.getString("transaction") != null)){
                    task_id = bundle.getString("transaction");
                    task = "transaction";
                }else{
                    System.out.println("error");
                }
            }

            try {
                // getting previous task description
                description_text = dbConnection.select("SELECT description FROM " + task + "s" + " WHERE " + task + "_id = " + task_id);
                description = findViewById(R.id.et_edit_description);
                // setting description to what it was before
                description.setText(description_text[0]);

            } catch (IOException e) {
                e.printStackTrace();
            }


            save_button = findViewById(R.id.bt_edit_save);
            save_button.setOnClickListener(new android.view.View.OnClickListener()  {
                @Override
                public void onClick(View v) {
                    // get the task id from your tasks button

                }
            });


        }


    }
}
package com.example.user.lab1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public EditText edit_student_id,edit_passward;
    public RadioButton rt1,rt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit_student_id = (EditText)findViewById(R.id.edit_student_id);
        edit_passward = (EditText)findViewById(R.id.edit_password);
        rt1 = (RadioButton)findViewById(R.id.radioButton);
        rt2 = (RadioButton)findViewById(R.id.radioButton2);

        findViewById(R.id.button_log).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str;
                if(rt1.isChecked()) str = rt1.getText().toString();
                else str = rt2.getText().toString();
                Toast.makeText(MainActivity.this, String.format("该账户类型为：%s\n学号：%s\n密码：%s",str,edit_student_id.getText().toString(),edit_passward.getText().toString()) ,Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.button_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

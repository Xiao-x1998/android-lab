package com.example.user.lab1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import static com.example.user.lab1.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {
    public TextInputLayout textInputLayout_id,textInputLayout_password;
    EditText edit_student_id,edit_password;
    public RadioButton rt1,rt2;
    public int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);
        textInputLayout_id = (TextInputLayout) findViewById(R.id.textInputLayout_id);
        textInputLayout_password = (TextInputLayout) findViewById(R.id.textInputLayout_password);
        edit_student_id = (EditText) findViewById(R.id.edit_student_id);
        edit_password = (EditText) findViewById(R.id.edit_password);

        findViewById(R.id.img_sysu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String [] array = {"拍摄","从相册选择"};
                AlertDialog.Builder alterDialog = new AlertDialog.Builder(MainActivity.this);
                alterDialog.setTitle("上传头像").setItems(array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,String.format("您选择了[%s]",array[which]), Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,"您选择了[取消]" ,Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });

        rt1 = (RadioButton)findViewById(R.id.radioButton);
        rt2 = (RadioButton) findViewById(R.id.radioButton2);
        if(rt1.isChecked()) flag = 1;
        else flag = -1;
        rt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rt1.isChecked() && flag != 1)
                {
                    Snackbar.make(findViewById(R.id.main_layout),"您选择了学生",Snackbar.LENGTH_SHORT).setAction("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MainActivity.this,"Snackbar 的确定按钮被点击了",Toast.LENGTH_SHORT).show();
                        }
                    }).show();
                }
                flag = 1;

            }
        });
        rt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rt2.isChecked() && flag != -1)
                {
                    Snackbar.make(findViewById(R.id.main_layout),"您选择了教职工",Snackbar.LENGTH_SHORT).setAction("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MainActivity.this,"Snackbar 的确定按钮被点击了",Toast.LENGTH_SHORT).show();
                        }
                    }).show();
                }
                flag = -1;

            }
        });

        edit_student_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textInputLayout_id.setErrorEnabled(false);
            }
        });
        edit_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textInputLayout_password.setErrorEnabled(false);
            }
        });

        findViewById(R.id.button_log).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(edit_student_id.getText().toString()))
                {
                    textInputLayout_id.setErrorEnabled(true);
                    textInputLayout_password.setErrorEnabled(false);
                    textInputLayout_id.setError("学号不能为空");
                }
                else if(TextUtils.isEmpty(edit_password.getText().toString()))
                {
                    textInputLayout_id.setErrorEnabled(false);
                    textInputLayout_password.setErrorEnabled(true);
                    textInputLayout_password.setError("密码不能为空");
                }
                else
                {
                    textInputLayout_id.setErrorEnabled(false);
                    textInputLayout_password.setErrorEnabled(false);
                    if(edit_student_id.getText().toString() == "123456" && edit_password.getText().toString() == "6666")
                        Snackbar.make(findViewById(R.id.main_layout),"登陆成功",Snackbar.LENGTH_SHORT).setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this,"Snackbar 的确定按钮被点击了",Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                    else
                        Snackbar.make(findViewById(R.id.main_layout),"学号或密码错误",Snackbar.LENGTH_SHORT).setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this,"Snackbar 的确定按钮被点击了",Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                }
            }
        });

        findViewById(R.id.button_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == 1) Toast.makeText(MainActivity.this,"学生注册功能尚未启用",Toast.LENGTH_SHORT).show();
                else Toast.makeText(MainActivity.this,"教职工注册功能尚未启用",Toast.LENGTH_SHORT).show();
            }
        });


    }
}

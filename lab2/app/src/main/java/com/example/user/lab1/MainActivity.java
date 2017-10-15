package com.example.user.lab1;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;

import static com.example.user.lab1.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {
    public TextInputLayout textInputLayout_id,textInputLayout_password;
    EditText edit_student_id,edit_password;
    public RadioButton rt1,rt2;
    public int flag;

    public final static int ALBUM_REQUEST_CODE = 1;
    public final static int CAMERA_REQUEST_CODE = 2;
    public static String SAVED_IMAGE_DIR_PATH = Environment.getExternalStorageDirectory().getPath() + "/Pictures/";
    String cameraPath;
    private Context activity;
    public ImageButton imagebutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);
        textInputLayout_id = (TextInputLayout) findViewById(R.id.textInputLayout_id);
        textInputLayout_password = (TextInputLayout) findViewById(R.id.textInputLayout_password);
        edit_student_id = (EditText) findViewById(R.id.edit_student_id);
        edit_password = (EditText) findViewById(R.id.edit_password);

        activity = this.getApplicationContext();
        imagebutton = (ImageButton) findViewById(R.id.img_sysu);

        findViewById(R.id.img_sysu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String [] array = {"拍摄","从相册选择"};
                AlertDialog.Builder alterDialog = new AlertDialog.Builder(MainActivity.this);
                alterDialog.setTitle("上传头像").setItems(array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,String.format("您选择了[%s]",array[which]), Toast.LENGTH_SHORT).show();
                        if(array[which].equals("拍摄")) StartCamera();
                        if(array[which].equals("从相册选择")) ShowPhoto();

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
                    Snackbar.make(findViewById(R.id.main_layout),"您选择了学生",Snackbar.LENGTH_SHORT)
                            .setAction("确定", new View.OnClickListener() {
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
                    Snackbar.make(findViewById(R.id.main_layout),"您选择了教职工",Snackbar.LENGTH_SHORT)
                            .setAction("确定", new View.OnClickListener() {
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
                    if(edit_student_id.getText().toString().equals("123456") && edit_password.getText().toString().equals("6666"))
                        Snackbar.make(findViewById(R.id.main_layout),"登陆成功",Snackbar.LENGTH_SHORT)
                                .setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this,"Snackbar 的确定按钮被点击了",Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                    else
                        Snackbar.make(findViewById(R.id.main_layout),"学号或密码错误",Snackbar.LENGTH_SHORT)
                                .setAction("确定", new View.OnClickListener() {
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
                if(flag == 1) Snackbar.make(findViewById(R.id.main_layout),"学生注册功能尚未启用",Snackbar.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this,"学生注册功能尚未启用",Toast.LENGTH_SHORT).show();
                else Toast.makeText(MainActivity.this,"教职工注册功能尚未启用",Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void StartCamera()
    {
        String state = Environment.getExternalStorageState();
        if(state.equals(Environment.MEDIA_MOUNTED))
        {
            cameraPath = SAVED_IMAGE_DIR_PATH + System.currentTimeMillis() + ".jpg";
            Intent intent = new Intent();
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            File outDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//            if(!outDir.exists()) outDir.mkdirs();
            File outFile = new File(outDir,System.currentTimeMillis()+".jpg");
            Uri uri = Uri.fromFile(outFile);

            intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
            startActivityForResult(intent,CAMERA_REQUEST_CODE);
        }
    }

    private void ShowPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, ALBUM_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ALBUM_REQUEST_CODE) {
            try {
                Uri uri = data.getData();
                final String absolutePath = getAbsolutePath(activity, uri);
                Bitmap bitmap = BitmapFactory.decodeFile(absolutePath);
                imagebutton.setImageBitmap(bitmap);
                imagebutton.setImageURI(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (requestCode == CAMERA_REQUEST_CODE) {
            try {
                Bitmap bitmap = BitmapFactory.decodeFile(cameraPath);
                imagebutton.setImageBitmap(bitmap);
                Uri uri = Uri.fromFile(new File(cameraPath));
                imagebutton.setImageURI(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getAbsolutePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data_new = null;
        if (scheme == null)
            data_new = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data_new = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data_new = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data_new;
    }
}

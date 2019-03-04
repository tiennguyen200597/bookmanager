package com.tiennvph06776.bookmanager.project.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tiennvph06776.bookmanager.project.R;
import com.tiennvph06776.bookmanager.project.model.User;
import com.tiennvph06776.bookmanager.project.sqlite.DatabaseHelper;
import com.tiennvph06776.bookmanager.project.sqlitedao.UserDAO;

import java.util.ArrayList;
import java.util.List;


import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity {

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mID;
    private EditText mPassword;
    private EditText mRePassword;

    private RadioButton mCheck;
    Toolbar toolbarNguoiDung;
    private DatabaseHelper databaseHelper;
    private  Button btnCreat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        toolbarNguoiDung = findViewById(R.id.toolbarNguoiDung);
        setSupportActionBar(toolbarNguoiDung);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        //
        databaseHelper = new DatabaseHelper(this);
        final UserDAO userDAO = new UserDAO(databaseHelper);
        btnCreat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = mID.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String repassword = mRePassword.getText().toString().trim();
                if (password.length() < 6 || userName.isEmpty() || password.isEmpty()||password.equals(repassword)==false) {

                    if (userName.isEmpty()) {
                        mID.setError(getString(R.string.notify_empty_user));
                        return;
                    }

                    if (password.length() < 6) {
                        mPassword.setError(getString(R.string.notify_length_pass));
                        return;
                    }

                    if (password.isEmpty()){
                        mPassword.setError(getString(R.string.notify_empty_pass));
                        return;
                    }
                    if (password.equals(repassword)==false){
                        mRePassword.setError("Mật khẩu nhập lại không đúng");
                        return;
                    }

                }
                else {
                    User user=new User();
                    user.username=userName;
                    user.password=password;
                    userDAO.insertUser(user);
                    Toast.makeText(RegisterActivity.this, "Đăng ký tài khoản thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }


            }
        });

    }
    public void initView(){
        btnCreat=findViewById(R.id.btn_Creat);
        mPassword=findViewById(R.id.edt_password);
        mID=findViewById(R.id.edt_username);
        mRePassword=findViewById(R.id.edt_repassword);
    }
}


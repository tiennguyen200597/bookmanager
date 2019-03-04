package com.tiennvph06776.bookmanager.project.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import com.tiennvph06776.bookmanager.project.sqlite.Constant;
import com.tiennvph06776.bookmanager.project.R;
import com.tiennvph06776.bookmanager.project.model.User;
import com.tiennvph06776.bookmanager.project.sqlite.DatabaseHelper;
import com.tiennvph06776.bookmanager.project.sqlitedao.StatisticsDAO;
import com.tiennvph06776.bookmanager.project.sqlitedao.UserDAO;

public class LoginActivity extends AppCompatActivity implements Constant {

    private EditText edUserName;
    private EditText edPassWord;
    private CheckBox chkRememberPass;
    private TextView tvCreat;
    private Button btnLogin;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHelper = new DatabaseHelper(this);

        UserDAO userDAO = new UserDAO(databaseHelper);

        final StatisticsDAO statisticsDAO = new StatisticsDAO(databaseHelper);
        double day = statisticsDAO.totalBillD(D_DAY);
        double month = statisticsDAO.totalBillD(M_MONTH);
        double year = statisticsDAO.totalBillD(Y_YEAR);
        initViews();
        User user=new User();
        user.username="admin123";
        user.password="admin123";
        /*userDAO.insertUser(user);*/

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = edUserName.getText().toString().trim();
                String password = edPassWord.getText().toString().trim();
                if (password.length() < 6 || userName.isEmpty() || password.isEmpty()) {

                    if (userName.isEmpty())
                        edUserName.setError(getString(R.string.notify_empty_user));

                    if (password.length() < 6)
                        edPassWord.setError(getString(R.string.notify_length_pass));

                    if (password.isEmpty())
                        edPassWord.setError(getString(R.string.notify_empty_pass));


                } else {
                    UserDAO userDAO = new UserDAO(databaseHelper);
                    User user = userDAO.getUser(userName);
                    if (user == null) {
                        Toast.makeText(
                                LoginActivity.this,
                                getString(R.string.notify_wrong_username_password), Toast.LENGTH_SHORT).show();

                    } else {
                        String passwordOnDB = user.password;
                        if (passwordOnDB.equals(password)) {
                            startActivity(new Intent(LoginActivity.this, ActivityHome.class));
                            finish();
                        } else Toast.makeText(
                                LoginActivity.this,
                                getString(R.string.notify_wrong_username_password), Toast.LENGTH_SHORT).show();

                    }


                }
            }
        });
        tvCreat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });


    }

    public void initViews() {
        tvCreat=findViewById(R.id.tv_creat);
        edUserName = findViewById(R.id.edUserName);
        edPassWord = findViewById(R.id.edPassWord);
        chkRememberPass = findViewById(R.id.chkRememberPass);
        btnLogin = findViewById(R.id.btn_login);

    }
}

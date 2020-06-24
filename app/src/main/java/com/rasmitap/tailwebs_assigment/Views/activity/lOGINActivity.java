package com.rasmitap.tailwebs_assigment.Views.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rasmitap.tailwebs_assigment.R;
import com.rasmitap.tailwebs_assigment.api.WebApiClient;
import com.rasmitap.tailwebs_assigment.db.DatabaseHelper;
import com.rasmitap.tailwebs_assigment.model.LoginData;
import com.rasmitap.tailwebs_assigment.model.LoginResponse;
import com.rasmitap.tailwebs_assigment.utils.ConnectionUtil;
import com.rasmitap.tailwebs_assigment.utils.ConstantStore;
import com.rasmitap.tailwebs_assigment.utils.GlobalMethods;
import com.rasmitap.tailwebs_assigment.utils.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class lOGINActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView img_back_login, img_no_avtar;

    TextView txt_login_title, txt_login_desc, btn_login, txt_signup_login;

    EditText edt_email_login, edt_password_login;

    private long lastClickTime = 0;
    private DatabaseHelper databaseHelper;
    LoginData user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l_ogin);
        img_no_avtar = findViewById(R.id.img_no_avtar);
        txt_login_title = findViewById(R.id.txt_login_title);
        txt_login_desc = findViewById(R.id.txt_login_desc);
        btn_login = findViewById(R.id.btn_login);
        edt_email_login = findViewById(R.id.edt_email_login);
        edt_password_login = findViewById(R.id.edt_password_login);
        txt_signup_login = findViewById(R.id.txt_signup_login);

        btn_login.setOnClickListener(this);
        txt_signup_login.setOnClickListener(this);
        databaseHelper = new DatabaseHelper(lOGINActivity.this);
        user = new LoginData();

    }
    private long mLastClickTime = 0;

    @Override
    public void onClick(View view) {

        if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {

        } else {
            switch (view.getId()) {
                case R.id.btn_login:

                     try {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 3000) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (edt_email_login.getText().toString().equalsIgnoreCase("")) {
                    GlobalMethods.Dialog(lOGINActivity.this, "Please enter username");
                } else if (edt_password_login.getText().toString().equalsIgnoreCase("")) {
                    GlobalMethods.Dialog(lOGINActivity.this, "Please enter password");
                } else {
                    if (ConnectionUtil.isInternetAvailable(lOGINActivity.this)) {
                        LoginApi(edt_email_login.getText().toString(), edt_password_login.getText().toString());
                    }
                }
               break;
                case R.id.txt_signup_login:
                    Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                    startActivity(intent);
                    finish();

            }
        }
        lastClickTime = SystemClock.elapsedRealtime();

    }

    public void LoginApi(final String email, final String password) {
        final ProgressDialog progressDialog = new ProgressDialog(lOGINActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        if (databaseHelper.checkUser(email.trim(),password.trim())) {
            GlobalMethods.Dialog(lOGINActivity.this, "User Login Successfully");
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
            Utility.setStringSharedPreference(getApplicationContext(), ConstantStore.is_Login,"true");
            Utility.setStringSharedPreference(getApplicationContext(), ConstantStore.UserName,email);

            progressDialog.dismiss();

        }else{
            progressDialog.dismiss();
            GlobalMethods.Dialog(lOGINActivity.this, "User Name or Password Not Valid");

        }

    }

}

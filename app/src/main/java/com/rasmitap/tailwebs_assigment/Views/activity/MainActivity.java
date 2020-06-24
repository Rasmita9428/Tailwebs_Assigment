package com.rasmitap.tailwebs_assigment.Views.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rasmitap.tailwebs_assigment.R;
import com.rasmitap.tailwebs_assigment.db.DatabaseHelper;
import com.rasmitap.tailwebs_assigment.model.Datamodel;
import com.rasmitap.tailwebs_assigment.utils.ConnectionUtil;
import com.rasmitap.tailwebs_assigment.utils.ConstantStore;
import com.rasmitap.tailwebs_assigment.utils.GlobalMethods;
import com.rasmitap.tailwebs_assigment.utils.Utility;
import com.rasmitap.tailwebs_assigment.utils.Validate;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recycler_viewtree;
    RecyclerView.LayoutManager layoutManager;
    List<Datamodel> ListofData = new ArrayList<>();
    FloatingActionButton fab;
    TextView toolbar_logout;
    DatabaseHelper databaseHelper;
    private long mLastClickTime = 0;
    String Username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler_viewtree = findViewById(R.id.recycler_viewtree);
        toolbar_logout = findViewById(R.id.toolbar_logout);
        recycler_viewtree.setHasFixedSize(true);
        Username = Utility.getStringSharedPreferences(getApplicationContext(), ConstantStore.UserName);


        String abc = (Utility.getStringSharedPreferences(getApplicationContext(), ConstantStore.dataarray));
        databaseHelper = new DatabaseHelper(MainActivity.this);
        ListofData = databaseHelper.getAllData(Username);

        Welcome_Adapter welcome_Adapter = new Welcome_Adapter(MainActivity.this, ListofData);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(MainActivity.this);
        recycler_viewtree.setLayoutManager(manager);
        recycler_viewtree.setAdapter(welcome_Adapter);
        welcome_Adapter.notifyDataSetChanged();

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.activity_form);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setCancelable(true);
                dialog.show();

                final EditText edt_Studentname = dialog.findViewById(R.id.edt_Studentname);
                final EditText edt_subject = dialog.findViewById(R.id.edt_subject);
                final EditText edt_marks = dialog.findViewById(R.id.edt_marks);
                TextView btn_submit = dialog.findViewById(R.id.btn_submit);
                databaseHelper = new DatabaseHelper(MainActivity.this);
                Username = Utility.getStringSharedPreferences(getApplicationContext(), ConstantStore.UserName);
                ListofData = databaseHelper.getAllData(Username);
                btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (edt_Studentname.getText().toString().equalsIgnoreCase("")) {
                            GlobalMethods.Dialog(MainActivity.this, "Student Name Require!");

                        }else if (!Validate.isAlpha(edt_Studentname.getText().toString())) {
                            GlobalMethods.Dialog(MainActivity.this, "Student Name not valid!");

                        } else if (edt_subject.getText().toString().equalsIgnoreCase("")) {
                            GlobalMethods.Dialog(MainActivity.this, "Subject Name Require!");

                        }else if (!Validate.isAlpha(edt_subject.getText().toString())) {
                            GlobalMethods.Dialog(MainActivity.this, "Subject Name not valid!");

                        } else if (edt_marks.getText().toString().equalsIgnoreCase("")) {
                            GlobalMethods.Dialog(MainActivity.this, "Marks Require!");

                        } else {
                            int flag = 0;
                            Datamodel data = new Datamodel();
                            data.setStudentName(edt_Studentname.getText().toString().trim().toLowerCase());
                            data.setSubject(edt_subject.getText().toString().trim().toLowerCase());
                            data.setMarks(edt_marks.getText().toString().trim().toLowerCase());
                            data.setUserName(Username);
                            if (ListofData.size() != 0) {
                                for (int i = 0; i < ListofData.size(); i++) {
                                    if (data.getStudentName().equalsIgnoreCase(ListofData.get(i).getStudentName()) && data.getSubject().equalsIgnoreCase(ListofData.get(i).getSubject())) {
                                        int marks = Integer.parseInt(data.getMarks()) + Integer.parseInt(ListofData.get(i).getMarks());
                                        data.setMarks(String.valueOf(marks));
                                        databaseHelper.updateDAta(data);
                                        flag = 1;
                                        break;
                                    } else {
                                        // databaseHelper.adddata(data);
                                    }
                                }
                                if (flag == 0) {
                                    databaseHelper.adddata(data);
                                }
                            } else {
                                databaseHelper.adddata(data);

                            }
                            // GlobalMethods.Dialog(FormActivity.this, "Record Save Successfully");

                            dialog.dismiss();
                            databaseHelper = new DatabaseHelper(MainActivity.this);
                            ListofData = databaseHelper.getAllData(Username);

                            Welcome_Adapter welcome_Adapter = new Welcome_Adapter(MainActivity.this, ListofData);
                            RecyclerView.LayoutManager manager = new LinearLayoutManager(MainActivity.this);
                            recycler_viewtree.setLayoutManager(manager);
                            recycler_viewtree.setAdapter(welcome_Adapter);
                            welcome_Adapter.notifyDataSetChanged();

                        }


                    }
                });


                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.windowAnimations = R.style.DialogAnimation;
                wlp.gravity = Gravity.CENTER;
                window.setAttributes(wlp);

            }
        });
        toolbar_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                openLogoutConfirmDialog();
            }
        });
    }

    public class Welcome_Adapter extends RecyclerView.Adapter<Welcome_Adapter.MyHolder> {
        List<Datamodel> ListofViewtree = new ArrayList<>();
        Context context;


        public Welcome_Adapter(MainActivity context, List<Datamodel> list) {
            this.ListofViewtree = list;
            this.context = context;
        }


        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_datadetails, parent, false);

            return new MyHolder(view);
        }

        @Override
        public int getItemCount() {

            return ListofViewtree.size();

        }

        public class MyHolder extends RecyclerView.ViewHolder {

            TextView txt_subjectname, txt_studentname, txt_marks;

            public MyHolder(View itemView) {
                super(itemView);
                txt_subjectname = (TextView) itemView.findViewById(R.id.txt_subjectname);
                txt_studentname = (TextView) itemView.findViewById(R.id.txt_studentname);
                txt_marks = (TextView) itemView.findViewById(R.id.txt_marks);

            }
        }

        @Override
        public void onBindViewHolder(final MyHolder holder, final int position) {
            if (ConnectionUtil.isInternetAvailable(context)) {
                holder.txt_studentname.setText(ListofViewtree.get(position).getStudentName());
                holder.txt_subjectname.setText(ListofViewtree.get(position).getSubject());
                holder.txt_marks.setText(ListofViewtree.get(position).getMarks());
            }
        }
    }

    public void openLogoutConfirmDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.logout_confirm_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.show();

        TextView tv_no = (TextView) dialog.findViewById(R.id.tv_no);
        TextView tv_yes = (TextView) dialog.findViewById(R.id.tv_yes);
        TextView txt_logout_title = (TextView) dialog.findViewById(R.id.txt_logout_title);
        TextView txt_logout_tagline = (TextView) dialog.findViewById(R.id.txt_logout_tagline);

        txt_logout_title.setText("Logout");
        txt_logout_tagline.setText("Are you sure you want to logout?");
        tv_yes.setText("Yes");
        tv_no.setText("No");

        tv_no.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }

        });

        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Utility.clearPreference(MainActivity.this);

                dialog.dismiss();
                Intent intent = new Intent(MainActivity.this, lOGINActivity.class);
                startActivity(intent);
                finish();

            }
        });
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.windowAnimations = R.style.DialogAnimation;
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);

    }


}

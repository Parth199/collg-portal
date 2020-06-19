package friendlyitsolution.com.itmconnect;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import friendlyitsolution.com.itmconnect.adapter.order;
import friendlyitsolution.com.itmconnect.adapter.orderAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class myhome extends AppCompatActivity {
    public  boolean takemeoffline=true;
    Spinner sem;
    RecyclerView recy;
    orderAdapter adapter;
    List<order> list;
    List<order> alllist;
    DatabaseReference oldref;
    ValueEventListener vals;
    String semtype="all";
    List<String> dd=new ArrayList<>();

    public static Context mycon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myhome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sem=(Spinner)findViewById(R.id.sem);
        mycon=this;
        dd.add("all");
        dd.addAll(Myapp.sems);
        final ArrayAdapter<String> ad=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,dd);
        sem.setAdapter(ad);

        ImageView chatss=(ImageView) findViewById(R.id.chats);
        chatss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takemeoffline=false;
                Intent i=new Intent(getApplicationContext(),Main2Activity.class);
                startActivity(i);
            }
        });
        ImageView chatss1=(ImageView) findViewById(R.id.document);
        chatss1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takemeoffline=false;
                Intent i=new Intent(getApplicationContext(),documents.class);
                startActivity(i);
            }
        });


        sem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                semtype=dd.get(position);
                setFilter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

try {
    Window window = getWindow();
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    window.setStatusBarColor(ContextCompat.getColor(myhome.this, R.color.app_blue));
}
catch(Exception e)
{

}

        ImageView lgout=(ImageView)findViewById(R.id.lgbtn);
        lgout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                logOut();
            }
        });



        recy=(RecyclerView)findViewById(R.id.recy);
        list=new ArrayList<>();
        alllist=new ArrayList<>();
        adapter=new orderAdapter(list);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Myapp.con);
        recy.setLayoutManager(mLayoutManager);
        recy.setItemAnimator(new DefaultItemAnimator());
        recy.setAdapter(adapter);
        try {

            vals=new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(dataSnapshot.getValue()!=null)
                    {
                        alllist.clear();

                        Map<String,Object> data=(Map<String, Object>)dataSnapshot.getValue();
                        List<String> ll=new ArrayList<>(data.keySet());
                        Collections.sort(ll);
                        for(int i=0;i<ll.size();i++)
                        {
                            Map<String,Object> ss=(Map<String, Object>)data.get(ll.get(i));
                            alllist.add(new order(ss,ll.get(i)));

                        }
                        setFilter();
                    }
                    else
                    {
                     Myapp.showMsg("There is no post");
                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };

        }
        catch(Exception e)
        {
            Myapp.showMsg("There is no post");
        }

        Myapp.ref.child("questions").addValueEventListener(vals);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), mywritepost.class);
                startActivity(i);     }
        });
    }

    void setFilter()
    {
        if(alllist.size()>0)
        {
            list.clear();
            for(int i=0;i<alllist.size();i++)
            {

                order o=alllist.get(i);
                Map<String,Object> temp=o.data;

                if(temp.get("sem").toString().equals(semtype))
                {

                    if(temp.get("qtype").toString().equals("all")||temp.get("qtype").toString().equals(Myapp.type)) {
                        list.add(0, o);
                    }

                }

            }
            if(list.size()==0)
            {
                Myapp.showMsg("There is no post");
            }
            adapter.notifyDataSetChanged();






        }
        else
        {
            Myapp.showMsg("there is no post");
        }

    }


    void logOut()
    {


        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialoug_logout);
        dialog.show();

        final Button btnInDialog = (Button) dialog.findViewById(R.id.btn);
        btnInDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = Myapp.pref.edit();
                editor.clear();
                editor.commit();
                finish();
                Myapp.myphone="";
                Myapp.myname="";
                Myapp.setall="";

                Intent i = new Intent(myhome.this,MainActivity.class);
                startActivity(i);

                finish();
            }
        });
        final ImageView btnClose = (ImageView) dialog.findViewById(R.id.canclebtn);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });




       /* new AwesomeInfoDialog(this)
                .setTitle("Logout")
                .setMessage("Want To Logout?")
                .setColoredCircle(R.color.colorAccent)
                .setDialogIconAndColor(R.drawable.logoutbtn, R.color.white)
                .setCancelable(true)
                .setPositiveButtonText(getString(R.string.dialog_yes_button))
                .setPositiveButtonbackgroundColor(R.color.colorAccent)
                .setPositiveButtonTextColor(R.color.white)
                .setNegativeButtonText(getString(R.string.dialog_no_button))
                .setNegativeButtonbackgroundColor(R.color.colorAccent)
                .setNegativeButtonTextColor(R.color.white)
                .setPositiveButtonClick(new Closure() {
                    @Override
                    public void exec() {
                        //click

                        SharedPreferences sharedPreferences = getSharedPreferences("myinfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        finish();


                        Intent i = new Intent(new_home.this,Login.class);
                        startActivity(i);

                        finish();

                    }
                })
                .setNegativeButtonClick(new Closure() {
                    @Override
                    public void exec() {
                        //click


                    }
                })
                .show();
        */

    }


    protected void onPause() {
        super.onPause();
        if(takemeoffline)
            getmeoffline();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(takemeoffline)
            getmeoffline();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getmeonline();
    }


    static void getmeonline()
    {
        Myapp.ref.child("user").child(Myapp.myphone).child("lastseen").setValue("Online");




    }
    static void getmeoffline()
    {    Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("hh:mm aaa / dd MMM,yyyy");
       String date = df.format(c);

        Myapp.ref.child("user").child(Myapp.myphone).child("lastseen").setValue(date);

    }
}

package friendlyitsolution.com.itmconnect;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by bhaum on 18-03-2017.
 */

public class Myapp  extends Application {

    public static String aid;
    public static String myphone,setall,myname,mysem,type,brc,dpurl;
    public static Context con;
    public static SharedPreferences pref;
    public static DatabaseReference ref,myref;
    public  static FirebaseDatabase fb;
    public static Map<String,Object> userdata;
    public static Context maincontex;
    public static String islock="";
    public static List<String> sems;
    public void onCreate() {
        super.onCreate();
        maincontex=getApplicationContext();
        pref=getSharedPreferences("myinfo",MODE_PRIVATE);
        con=getApplicationContext();
        fb= FirebaseDatabase.getInstance();
        fb.setPersistenceEnabled(true);
        ref=fb.getReference();
         sems=new ArrayList<>();
        sems.add("sem 1");
        sems.add("sem 2");
        sems.add("sem 3");
        sems.add("sem 4");
        sems.add("sem 5");
        sems.add("sem 6");
        sems.add("sem 7");
        sems.add("sem 8");
       brc=pref.getString("brc","");
        type=pref.getString("type","");
        mysem=pref.getString("sem","");
        myname=pref.getString("name","");
        myphone=pref.getString("number","");
        setall=pref.getString("setall","");
        islock=pref.getString("islock","");
        dpurl=pref.getString("dpurl","");
        if(!setall.equals(""))
        {
            getUserdata();
        }
    }
    public static void showMsg(String st)
    {
        Toast.makeText(con,st, Toast.LENGTH_LONG).show();
    }

    void getUserdata()
    {
        myref=fb.getReference("user").child(myphone);
        myref.keepSynced(true);
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                userdata=(Map<String, Object>)dataSnapshot.getValue();
                addLock(userdata.get("status")+"");
                addDpurl(userdata.get("dpurl")+"");
                brc=userdata.get("branch")+"";
             //     Toast.makeText(getApplicationContext(),"Data ->: "+userdata.get("status"),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public static void addType(String num)
    {
        SharedPreferences.Editor edit=pref.edit();
        edit.putString("type",num);
        type=num;
        edit.commit();
    }

    public static void addDpurl(String num)
    {
        SharedPreferences.Editor edit=pref.edit();
        edit.putString("dpurl",num);
        dpurl=num;
        edit.commit();
    }


    public static void addbranch(String num)
    {
        SharedPreferences.Editor edit=pref.edit();
        edit.putString("brc",num);
        brc=num;
        edit.commit();
    }
    public static void addNumber(String num)
    {
        SharedPreferences.Editor edit=pref.edit();
        edit.putString("number",num);
        myphone=num;
        edit.commit();
    }
    public static void setAll(String num)
    {
        SharedPreferences.Editor edit=pref.edit();
        edit.putString("setall",num);
        setall=num;
        edit.commit();
    }
    public static void addSem(String num)
    {
        SharedPreferences.Editor edit=pref.edit();
        edit.putString("sem",num);
        mysem=num;
        edit.commit();
    }
    public static void addName(String num)
    {
        SharedPreferences.Editor edit=pref.edit();
        edit.putString("name",num);
        myname=num;
        edit.commit();
    }
    public static void addLock(String num)
    {
        SharedPreferences.Editor edit=pref.edit();
        edit.putString("islock",num);
        islock=num;
        edit.commit();
    }
}

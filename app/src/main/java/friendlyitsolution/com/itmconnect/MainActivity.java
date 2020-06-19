package friendlyitsolution.com.itmconnect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import android.Manifest;

import friendlyitsolution.com.itmconnect.Signup;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;

//import ss.com.bannerslider.banners.DrawableBanner;
//import ss.com.bannerslider.views.BannerSlider;

public class MainActivity extends AppCompatActivity
{


    //Our button
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String mVerificationId;
    private static final String TAG = "PhoneAuthActivity";


    //Permision code that will be checked in the method onRequestPermissionsResult
    private int STORAGE_PERMISSION_CODE = 23;
    private int STORAGE_PERMISSION_CODE1 = 24;

    private int STORAGE_PERMISSION_CODE2 = 25;
    DatabaseReference myref;
    private int STORAGE_PERMISSION_CODE3 = 26;
LinearLayout ll1;
    Button str,submit;
    EditText phn,otp;
    FirebaseDatabase fdb;
    DatabaseReference myref1;
    SQLiteDatabase mydb=null;
    String otp1;
   ProgressDialog pd;//= new ProgressDialog(MainActivity.this);

    static String dbname="dditinfo.db";
    String dbpath="/data/data/friendlyitsolution.com.itmconnect/databases/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pd= new ProgressDialog(MainActivity.this);

        mysql m=new mysql(this);

        m.checkMydb();

        if(!Myapp.setall.equals(""))
        {
           // Toast.makeText(getApplicationContext(),"Data : "+Myapp.islock,Toast.LENGTH_LONG).show();
            if(Myapp.islock.equals("lock"))
            {
                Intent i = new Intent(getApplicationContext(), lockack.class);
                startActivity(i);
                finish();
            }
            else {
                Intent i = new Intent(getApplicationContext(), myhome.class);
                startActivity(i);
                finish();
            }
        }
        else if(!Myapp.myphone.equals(""))
        {
            Intent i=new Intent(getApplicationContext(),Signup.class);
            startActivity(i);
            finish();
        }

       // checkLogin();
        requestPermission(0);

        // requestPermission(1);
        //requestPermission(2);
        //requestPermission(3);



       /* BannerSlider bannerSlider = (BannerSlider) findViewById(R.id.banner_slider1);
        bannerSlider.addBanner(new DrawableBanner(R.drawable.a));
        bannerSlider.addBanner(new DrawableBanner(R.drawable.ab));
        bannerSlider.addBanner(new DrawableBanner(R.drawable.ac));
        bannerSlider.addBanner(new DrawableBanner(R.drawable.ad)); */
        ll1=(LinearLayout) findViewById(R.id.ll1);
        str=(Button)findViewById(R.id.str);
        submit=(Button)findViewById(R.id.submit);
        phn=(EditText)findViewById(R.id.phn);
        otp=(EditText)findViewById(R.id.otp);
       // CheckedPermision();
        fdb= FirebaseDatabase.getInstance();


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d(TAG, "onVerificationCompleted:" + credential);
                pd.dismiss();
                Toasty.success(getApplicationContext(),"Varified", Toast.LENGTH_LONG).show();
                registerUser();

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    pd.dismiss();
                    phn.setError("Invalid phone number");
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    pd.dismiss();
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent:" + verificationId);
                mVerificationId = verificationId;
                mResendToken = token;
                pd.setMessage("code sucessfully send ");
            }
        };



        str.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!phn.getText().toString().equals("")) {
                        pd.setCancelable(false);
                        pd.setMessage("Sending Otp...");
                        pd.show();
                        startPhoneNumberVerification(phn.getText().toString());
                    }
                    else
                    {
                        phn.setError("Enter number");
                    }


                }
            });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(otp.getText().toString().equals("111")) {




                }else
                {

                  otp.setError("Wrong otp");
                }
                }


        });

        }

    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+phoneNumber,        // Phone number to verify
                120,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }


        void registerUser()
        {

            final ProgressDialog pd= new ProgressDialog(MainActivity.this);
            pd.setCancelable(false);
            pd.setTitle("Registering...");
            pd.setMessage("Please wait");
            pd.show();
            myref=fdb.getReference("user");
            myref=myref.child(phn.getText().toString());
            myref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    myref.removeEventListener(this);

                    if(dataSnapshot.getValue()==null)
                    {
                        myref.setValue(phn.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getApplicationContext(),"Success", Toast.LENGTH_LONG).show();
                              //  MainActivity.phno=phn.getText().toString();
                                Myapp.addNumber(phn.getText().toString());
                                Intent i=new Intent(getApplicationContext(),Signup.class);
                                startActivity(i);
                                finish();
                                pd.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Failure", Toast.LENGTH_LONG).show();
                                pd.dismiss();
                            }
                        });



                    }
                    else
                    {
                        myref.removeEventListener(this);
                        pd.dismiss();
                        Myapp.addNumber(phn.getText().toString());
                        Map<String,Object> m=(Map<String, Object>)dataSnapshot.getValue();
                        Toast.makeText(getApplicationContext(),"existing user : "+m, Toast.LENGTH_LONG).show();
                        if(m.containsKey("name"))
                        {
                            Myapp.setAll("yea");
                            Myapp.addLock(m.get("status").toString());
                            Myapp.addName(m.get("name")+"");
                            Myapp.addNumber(phn.getText().toString());
                            Myapp.addSem(m.get("sem")+"");
                            Myapp.addType(m.get("type")+"");
                            Myapp.addbranch(m.get("branch")+"");

                            Myapp.addDpurl(m.get("dpurl")+"");
                            Myapp.userdata=m;
                            if(Myapp.islock.equals("lock"))
                            {
                                Intent i = new Intent(getApplicationContext(), lockack.class);
                                startActivity(i);
                                finish();
                            }
                            else {
                                Intent i = new Intent(getApplicationContext(), myhome.class);
                                startActivity(i);
                                finish();
                            }


                        }
                    //    addDatatoTable(MainActivity.phno,m.get("name")+"",m.get("email")+"",m.get("sem")+"",m.get("division")+"");

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }





    void CheckedPermision()
    {

        isPermisionAllowed(0);
        isPermisionAllowed(1);
        isPermisionAllowed(2);
        isPermisionAllowed(3);


    }

    //We are calling this method to check the permission status
    private boolean isPermisionAllowed(int x) {
        //Getting the permission status
        if(x==0) {
            int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

            //If permission is granted returning true
            if (result == PackageManager.PERMISSION_GRANTED)
            {


                return true; }
            else {


               requestPermission(0);
            }
        }
        else if(x==1)
        {

            int result = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
            if (result == PackageManager.PERMISSION_GRANTED)
            {


                return true; }
            else {

                requestPermission(1);
                return false;
            }
        }
        else if(x==2)
        {

            int result = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE);
            if (result == PackageManager.PERMISSION_GRANTED)
            {


                return true; }
            else {

                requestPermission(2);
                return false;
            }
        }
        else
        {   int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (result == PackageManager.PERMISSION_GRANTED)
            {


                return true;
            }
            else {

                requestPermission(3);
                return false;

            }
        }
        return false;
    }

    //Requesting permission
    private void requestPermission(int x){

        if(x==0)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                //If the user has denied the permission previously your code will come to this block
                //Here you can explain why you need this permission
                //Explain here why you need this permission
            }

            //And finally ask for the permission
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
        }
        else if(x==1){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)){
                //If the user has denied the permission previously your code will come to this block
                //Here you can explain why you need this permission
                //Explain here why you need this permission
            }

            //And finally ask for the permission
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET},STORAGE_PERMISSION_CODE1);

        }
        else if(x==2)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_WIFI_STATE)){
                //If the user has denied the permission previously your code will come to this block
                //Here you can explain why you need this permission
                //Explain here why you need this permission
            }

            //And finally ask for the permission
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_WIFI_STATE},STORAGE_PERMISSION_CODE2);

        }
        else
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                //If the user has denied the permission previously your code will come to this block
                //Here you can explain why you need this permission
                //Explain here why you need this permission
            }

            //And finally ask for the permission
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE3);

        }


    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if(requestCode == STORAGE_PERMISSION_CODE){

            //If permission is granted
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //Displaying a toast
               // Toast.makeText(this,"Permission granted now you can read the storage",Toast.LENGTH_LONG).show();
            }else{
                //Displaying another toast if permission is not granted
                Toast.makeText(this,"Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
        else if(requestCode == STORAGE_PERMISSION_CODE1){

            //If permission is granted
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //Displaying a toast
                Toast.makeText(this,"Permission granted now you can use internet", Toast.LENGTH_LONG).show();
            }else{
                //Displaying another toast if permission is not granted
                Toast.makeText(this,"Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
        else if(requestCode == STORAGE_PERMISSION_CODE2){

            //If permission is granted
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //Displaying a toast
                Toast.makeText(this,"Permission granted now you can use wifi", Toast.LENGTH_LONG).show();
            }else{
                //Displaying another toast if permission is not granted
                Toast.makeText(this,"Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
        else if(requestCode == STORAGE_PERMISSION_CODE3){

            //If permission is granted
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //Displaying a toast
                Toast.makeText(this,"Permission granted now you can call", Toast.LENGTH_LONG).show();
            }else{
                //Displaying another toast if permission is not granted
                Toast.makeText(this,"Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
       // CheckedPermision();

    }

    public static String random() {

        StringBuilder otp = new StringBuilder();
        try {
            SecureRandom number = SecureRandom.getInstance("SHA1PRNG");
            // Generate 20 integers 0..20
            for (int i = 0; i < 6; i++) {
                otp.append(number.nextInt(9));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return otp.toString();
    }
}




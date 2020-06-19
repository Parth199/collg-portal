package friendlyitsolution.com.itmconnect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import gun0912.tedbottompicker.TedBottomPicker;

public class mywritepost extends AppCompatActivity {

    MaterialEditText que;
    RadioButton all,fac,stu;
    Spinner sem;
    Button btn;
    ProgressDialog pd;
    ImageView img;

    StorageReference mStorageReference;
    Uri imguri=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mywritepost);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mStorageReference= FirebaseStorage.getInstance().getReference();

        img=findViewById(R.id.img);
        ImageView bck=(ImageView)findViewById(R.id.backbtn);
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mywritepost.super.onBackPressed();
            }
        });
        try {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(mywritepost.this, R.color.app_blue));
        }
        catch(Exception e)
        {

        }
        pd=new ProgressDialog(mywritepost.this);
        pd.setCancelable(false);
        pd.setMessage("please wait");
        sem=(Spinner)findViewById(R.id.sem);
        que=(MaterialEditText)findViewById(R.id.etUser);
        all=(RadioButton)findViewById(R.id.all);
        fac=(RadioButton)findViewById(R.id.fac);
        stu=(RadioButton)findViewById(R.id.stu);
        btn=(Button)findViewById(R.id.btn);
        List<String> dd=new ArrayList<>();
        dd.add("all");
        dd.addAll(Myapp.sems);
        ArrayAdapter<String> ad=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,dd);
        sem.setAdapter(ad);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(que.getText().toString().trim().equals(""))
                {
                    que.setError("Enter something");
                }
                else {

                    if(imguri==null) {
                        postQue(pd,"");
                    }
                    else
                    {
                        uploadFile(imguri);
                    }
                }

            }
        });


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(mywritepost.this)
                        .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
                            @Override
                            public void onImageSelected(Uri uri) {

                                if(uri!=null)
                                {

                                    Crop(uri);


                                }

                            }
                        })
                        .create();

                tedBottomPicker.show(getSupportFragmentManager());

            }
        });

    }
    void Crop(Uri uri)
    {


// start cropping activity for pre-acquired image saved on the device
        CropImage.activity(uri).setAspectRatio(1,1).setOutputCompressQuality(50).setCropMenuCropButtonTitle("Crop").setCropShape(CropImageView.CropShape.RECTANGLE)
                .start(this);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                 imguri = result.getUri();
                img.setImageURI(imguri);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
    void  postQue(final ProgressDialog pd,String imgurl)
    {
        pd.show();
        String date, month, year, time, fulldate, day;
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd");
        date = df.format(c);

        df = new SimpleDateFormat("MMM");
        month = df.format(c);

        df = new SimpleDateFormat("yyyy");
        year = df.format(c);


        df = new SimpleDateFormat("hh:mm aaa");
        time = df.format(c);

        df = new SimpleDateFormat("dd MMM,yyyy");
        fulldate = df.format(c);

        df = new SimpleDateFormat("EEEE");
        day = df.format(c);

        final Map<String, Object> data = new HashMap<>();
        data.put("day", day);
        data.put("date", date);
        data.put("time", time);
        data.put("fulldate", fulldate);
        data.put("month", month.toUpperCase());
        data.put("year", year);
        data.put("dpurl",Myapp.dpurl);
        data.put("dept",Myapp.brc);
        data.put("question",que.getText().toString());
        data.put("aid",Myapp.myphone);
        data.put("aname",Myapp.myname);
        if(!imgurl.isEmpty())
        {
            data.put("img",imgurl);
        }
        String type="all";
        if(all.isChecked())
        {
            type="all";

        }
        else if(stu.isChecked())
        {
            type="student";
        }
        else
        {
            type="faculty";
        }
        data.put("type",Myapp.type);
        data.put("qtype",type);
        data.put("sem",sem.getSelectedItem().toString());

       final DatabaseReference myqueref= Myapp.ref.child("user").child(Myapp.myphone).child("myquestion");
       final String key=myqueref.push().getKey();

       Myapp.ref.child("questions").child(key).setValue(data).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               pd.dismiss();
               Myapp.showMsg("try again");
           }
       }).addOnCompleteListener(new OnCompleteListener<Void>() {
           @Override
           public void onComplete(@NonNull Task<Void> task) {

               pd.setMessage("almost done");
               myqueref.child(key).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {

                       pd.dismiss();
                       Myapp.showMsg("Successfully Submited");

                       mywritepost.super.onBackPressed();

                   }
               });

           }
       });



    }


    private void uploadFile(Uri filePath) {
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = pd;
            progressDialog.setTitle("Uploading");

            progressDialog.setCancelable(false);
            progressDialog.show();

            //bnp.setVisibility(View.VISIBLE);
            long time= System.currentTimeMillis();
            StorageReference riversRef = mStorageReference.child("post/IMG"+time+".jpg");

            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           postQue(progressDialog,taskSnapshot.getMetadata().getDownloadUrl()+"");
                         }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            Toasty.error(getApplicationContext(), "Try again later", Toast.LENGTH_SHORT, true).show();
                            progressDialog.dismiss();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }

        else {
            Toasty.warning(getApplicationContext(),"Please select your profile image", Toast.LENGTH_LONG).show();
        }
    }

}

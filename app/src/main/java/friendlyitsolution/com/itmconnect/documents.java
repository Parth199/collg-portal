package friendlyitsolution.com.itmconnect;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class documents extends AppCompatActivity {

    LinearLayout tafl,cn,nis,oose,soa,sem6,sem7,sem8,mydocs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView bck=(ImageView)findViewById(R.id.backbtn);
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                documents.super.onBackPressed();
            }
        });

        tafl=(LinearLayout) findViewById(R.id.tafl);
        nis=(LinearLayout) findViewById(R.id.nis);
        soa=(LinearLayout) findViewById(R.id.soa);
        oose=(LinearLayout) findViewById(R.id.oose);
        cn=(LinearLayout) findViewById(R.id.cn);
        sem6=(LinearLayout)findViewById(R.id.sem6);
        sem7=(LinearLayout)findViewById(R.id.sem7);
        sem8=(LinearLayout)findViewById(R.id.sem8);
        mydocs=(LinearLayout)findViewById(R.id.mydocs);
        mydocs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),documentspost.class);
                i.putExtra("sub","My Document");

                startActivity(i);
            }
        });
        tafl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),documentspost.class);
                i.putExtra("sub","sem2");

                startActivity(i);
            }
        });
        nis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),documentspost.class);
                i.putExtra("sub","sem3");

                startActivity(i);

            }
        });
        oose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),documentspost.class);
                i.putExtra("sub","sem5");

                startActivity(i);


            }
        });

        cn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),documentspost.class);
                i.putExtra("sub","sem4");

                startActivity(i);
            }
        });
        soa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),documentspost.class);
                i.putExtra("sub","sem1");

                startActivity(i);

            }
        });

        sem6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),documentspost.class);
                i.putExtra("sub","sem6");

                startActivity(i);

            }
        });
        sem7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),documentspost.class);
                i.putExtra("sub","sem7");

                startActivity(i);

            }
        });
        sem8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),documentspost.class);
                i.putExtra("sub","sem8");

                startActivity(i);

            }
        });



    }
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

}

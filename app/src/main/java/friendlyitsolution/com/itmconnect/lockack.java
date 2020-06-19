package friendlyitsolution.com.itmconnect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class lockack extends AppCompatActivity {

    CircleImageView iv;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lockack);

        iv=(CircleImageView)findViewById(R.id.lgo);
        name=(TextView)findViewById(R.id.name);

        Glide.with(iv.getContext()).load(""+Myapp.userdata.get("dpurl"))
                .override(70, 70)
                .fitCenter()
                .into(iv);
        name.setText(Myapp.userdata.get("name")+"");


    }

}

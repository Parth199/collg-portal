package friendlyitsolution.com.itmconnect.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import friendlyitsolution.com.itmconnect.Myapp;
import friendlyitsolution.com.itmconnect.R;
import friendlyitsolution.com.itmconnect.answer;
import friendlyitsolution.com.itmconnect.myhome;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Meet on 16-10-2017.
 */

public class orderAdapter extends RecyclerView.Adapter<orderAdapter.MyViewHolder> {

private List<order> moviesList;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView name,time,month,totcom,totview,que,type,sem;
    CircleImageView img;
    ImageView imgpic;
    TextView btn;
    public MyViewHolder(View view) {
        super(view);

        time = (TextView) view.findViewById(R.id.time);
        month = (TextView) view.findViewById(R.id.month);
        totcom = (TextView) view.findViewById(R.id.offer);
        totview = (TextView) view.findViewById(R.id.offlab);

        name = (TextView) view.findViewById(R.id.name);
       que = (TextView) view.findViewById(R.id.que);
        imgpic=view.findViewById(R.id.imgpic);
        btn = (TextView) view.findViewById(R.id.amt);
        sem = (TextView) view.findViewById(R.id.sem);
        type = (TextView) view.findViewById(R.id.type);
        img=(CircleImageView)view.findViewById(R.id.img);
        }
}


    public orderAdapter(List<order> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.que_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final order movie = moviesList.get(position);
        Map<String,Object> data=movie.data;

        holder.time.setText(movie.time);
        holder.type.setText(data.get("type")+"");
        holder.name.setText(""+data.get("aname"));
        holder.que.setText(""+data.get("question"));
        holder.month.setText(""+data.get("fulldate"));
        holder.sem.setText(""+data.get("sem"));
        holder.sem.setVisibility(View.GONE);
        holder.totview.setText(movie.totview);
        holder.totcom.setText(movie.totcom);
        Glide.with(holder.img.getContext()).load(data.get("dpurl")+"")
                .override(100, 100)
                .fitCenter()
                .into(holder.img);

        if(data.containsKey("img"))
        {
            Glide.with(holder.imgpic.getContext()).load(data.get("img")+"")
                    .into(holder.imgpic);
        }

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(Myapp.con,answer.class);
                i.putExtra("qid",movie.key);
                myhome.mycon.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}

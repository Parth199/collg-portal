package friendlyitsolution.com.itmconnect.adapter;

import java.util.Map;

/**
 * Created by Meet on 16-10-2017.
 */

public class order
{
   public String time,year,month,date,day;
   public Map<String,Object> data;

   public String key;
    public String totview,totcom;

    public order(Map<String,Object> data, String key)
    {
        this.data=data;
        this.key=key;

        time=""+data.get("time");
        year=""+data.get("year");
        month=""+data.get("month");
        date=""+data.get("date");
        day=""+data.get("day");

        if(data.containsKey("view"))
        {
            Map<String,String> d=(Map<String, String>)data.get("view");
            totview="view ("+d.size()+")";
        }
        else
        {
            totview="No view";
        }
        if(data.containsKey("answer"))
        { Map<String,String> d=(Map<String, String>)data.get("answer");
            totcom="Answers ("+d.size()+")";

        }
        else
        {
            totcom="No Answer";
        }

    }

}

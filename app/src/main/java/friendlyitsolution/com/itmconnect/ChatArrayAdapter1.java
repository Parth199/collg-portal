package friendlyitsolution.com.itmconnect;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meet on 26-03-2016.
 */

public class ChatArrayAdapter1 extends ArrayAdapter {

    private TextView chatText,chatTime;
    private List<ChatMessage1> chatMessageList = new ArrayList<ChatMessage1>();
    private LinearLayout singleMessageContainer,lr;




    public void add(ChatMessage1 object) {
        chatMessageList.add(object);
        super.add(object);
    }

    public ChatArrayAdapter1(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public int getCount() {
        return this.chatMessageList.size();
    }

    public ChatMessage1 getItem(int index) {
        return this.chatMessageList.get(index);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.activity_chat_singlemessage1, parent, false);
        }

            singleMessageContainer = (LinearLayout) row.findViewById(R.id.singleMessageContainer);
            ChatMessage1 chatMessageObj = getItem(position);

            chatText = (TextView) row.findViewById(R.id.singleMessage);
            lr = (LinearLayout) row.findViewById(R.id.lr);

            chatTime = (TextView) row.findViewById(R.id.time2);
            chatTime.setText(chatMessageObj.time);
            chatText.setText(chatMessageObj.message);

        singleMessageContainer.setBackgroundResource(chatMessageObj.left ? R.drawable.chattheme : R.drawable.chattheme1);

        // singleMessageContainer.setBackgroundColor(chatMessageObj.left ? Color.parseColor("#") : R.drawable.bubble_b);
            lr.setGravity(chatMessageObj.left ? Gravity.LEFT : Gravity.RIGHT);

        return row;
    }
}
package mychatapp.solyombence.com.mychatapp.lobby;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import mychatapp.solyombence.com.mychatapp.R;
import mychatapp.solyombence.com.mychatapp.chatroom.ChatActivity;

// The adapter class for populating the chatroom list in the chatroom lobby activity, following standard implementation
// guidelines
public class ChatroomAdapter extends RecyclerView.Adapter<ChatroomAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<Chatroom> chatrooms;
    private Context mContext;


    public ChatroomAdapter(Context context, ArrayList<Chatroom> mChatrooms) {
        chatrooms = mChatrooms;
        mContext = context;
        Log.d("adapter", "adapter set");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // The list_item_layout XML defines the layout of a single chatroom element in the lobby
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, description;
        public ImageView chevron;
        public RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.chatroom_name);
                description = itemView.findViewById(R.id.chatroom_description);
                chevron = itemView.findViewById(R.id.image);
                parentLayout = itemView.findViewById(R.id.parent_layout);
            }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.name.setText(chatrooms.get(position).getName());
        holder.description.setText(chatrooms.get(position).getDescription());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + chatrooms.get(position).getName());

                Toast.makeText(mContext, chatrooms.get(position).getName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("chatroomname", chatrooms.get(position).getName());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatrooms.size();
    }

}

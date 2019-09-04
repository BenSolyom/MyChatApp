package mychatapp.solyombence.com.mychatapp.chatroom;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import mychatapp.solyombence.com.mychatapp.R;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {


    private List<Message> userMessagesList;
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;


    public MessageAdapter (List<Message> userMessagesList)
    {
        this.userMessagesList = userMessagesList;
        mAuth = FirebaseAuth.getInstance();
    }



    public class MessageViewHolder extends RecyclerView.ViewHolder
    {
        public TextView messageText, usernameText, timeStampText;
        public CircleImageView receiverProfileImage;
        public RelativeLayout parentLayout;


        public MessageViewHolder(@NonNull View itemView)
        {
            super(itemView);

            usernameText = itemView.findViewById(R.id.username_text);
            messageText = itemView.findViewById(R.id.message_text);
            timeStampText = itemView.findViewById(R.id.timestamp_text);
            receiverProfileImage = itemView.findViewById(R.id.message_profile_image);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.message_layout, viewGroup, false);

        return new MessageViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MessageViewHolder holder, final int position)
    {
        Message message = userMessagesList.get(position);

        Log.d("messageText", message.getMessage());
        holder.usernameText.setText(message.getUsername());
        holder.messageText.setText(message.getMessage());
        holder.timeStampText.setText(message.getTimeStamp());

        if (holder.usernameText.getText().equals(mAuth.getCurrentUser().getDisplayName()))
            holder.messageText.setBackgroundColor(Color.RED);

        /*usersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(fromUserID);

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.hasChild("image"))
                {
                    String receiverImage = dataSnapshot.child("image").getValue().toString();

                    Picasso.get().load(receiverImage).placeholder(R.drawable.chat_icon_small).into(holder.receiverProfileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        /*holder.receiverMessageText.setVisibility(View.GONE);
        holder.receiverProfileImage.setVisibility(View.GONE);
        holder.senderMessageText.setVisibility(View.GONE);
        //holder.messageReceiverPicture.setVisibility(View.GONE);

        /*if (fromMessageType.equals("text"))
        {
            if (fromUserID.equals(messageSenderId))
            {
                holder.senderMessageText.setVisibility(View.VISIBLE);

                holder.senderMessageText.setBackgroundResource(R.color.colorPrimary);
                holder.senderMessageText.setTextColor(Color.BLACK);
                holder.senderMessageText.setText(message.getMessage() + "\n \n" + message.getTimeStamp());
            }
            else
            {
                holder.receiverProfileImage.setVisibility(View.VISIBLE);
                holder.receiverMessageText.setVisibility(View.VISIBLE);

                holder.receiverMessageText.setBackgroundResource(R.color.colorAccent);
                holder.receiverMessageText.setTextColor(Color.BLACK);
                holder.receiverMessageText.setText(message.getMessage() + "\n \n" + message.getTimeStamp());
            }
        }*/
    }

    @Override
    public int getItemCount()
    {
        return userMessagesList.size();
    }


    /*private static final String TAG = "RecyclerViewAdapter";
    private String username;
    private ArrayList<String> messages;
    private ArrayList<String> timestamps;
    private Context mContext;

    public MessageAdapter(Context context, ArrayList<String> mMessages) {
        messages = mMessages;
        mContext = context;
        Log.d("messageadapter", "messageadapter set");
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout, parent, false);

        MessageViewHolder vh = new MessageViewHolder(v);
        return vh;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView timestamp;
        TextView message;
        ImageView avatar;
        RelativeLayout parentLayout;

        public MessageViewHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            timestamp = itemView.findViewById(R.id.timestamp);
            message = itemView.findViewById(R.id.message);
            avatar = itemView.findViewById(R.id.avatar);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MessageViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.username.setText(username);
        holder.timestamp.setText(timestamps.get(position));
        holder.message.setText(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }*/

}

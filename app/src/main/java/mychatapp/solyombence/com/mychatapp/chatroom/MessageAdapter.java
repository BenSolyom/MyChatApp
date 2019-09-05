package mychatapp.solyombence.com.mychatapp.chatroom;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import mychatapp.solyombence.com.mychatapp.R;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> userMessagesList;
    private FirebaseAuth mAuth;

    public MessageAdapter (List<Message> userMessagesList)
    {
        this.userMessagesList = userMessagesList;
        mAuth = FirebaseAuth.getInstance();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder
    {
        public TextView messageText, usernameText, timeStampText;
        public ImageView imageView;
        public CircleImageView profileImage;
        public RelativeLayout parentLayout;

        public MessageViewHolder(@NonNull View itemView)
        {
            super(itemView);

            usernameText = itemView.findViewById(R.id.username_text);
            messageText = itemView.findViewById(R.id.message_text);
            imageView = itemView.findViewById(R.id.image_view);
            timeStampText = itemView.findViewById(R.id.timestamp_text);
            profileImage = itemView.findViewById(R.id.message_profile_image);
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

        holder.usernameText.setText(message.getUsername());
        holder.timeStampText.setText(message.getTimeStamp());
        holder.profileImage.setImageResource(R.drawable.avatar1);

        if (message.getType().equals("text")) {
            holder.imageView.setVisibility(View.GONE);
            holder.messageText.setText(message.getMessage());
            if (holder.usernameText.getText().equals(mAuth.getCurrentUser().getDisplayName())) {
                holder.messageText.setBackgroundColor(Color.RED);
                holder.profileImage.setImageResource(R.drawable.avatar2);
            }
        } else {
            holder.messageText.setVisibility(View.GONE);
            holder.imageView.setImageBitmap(message.getImage());
            if (holder.usernameText.getText().equals(mAuth.getCurrentUser().getDisplayName()))
                holder.profileImage.setImageResource(R.drawable.avatar2);
        }
    }

    @Override
    public int getItemCount()
    {
        return userMessagesList.size();
    }
}

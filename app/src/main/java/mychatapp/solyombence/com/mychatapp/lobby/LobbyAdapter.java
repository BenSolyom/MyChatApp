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
import mychatapp.solyombence.com.mychatapp.chatroom.ChatroomActivity;

public class LobbyAdapter extends RecyclerView.Adapter<LobbyAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String> crNames;
    private ArrayList<String> crDescriptions;
    private Context mContext;

    public LobbyAdapter(Context context, ArrayList<String> mcrNames, ArrayList<String> mcrDescriptions) {
        crNames = mcrNames;
        crDescriptions = mcrDescriptions;
        mContext = context;
        Log.d("adapter", "adapter set");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView description;
        ImageView chevron;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.chatroom_name);
                description = itemView.findViewById(R.id.chatroom_description);
                chevron = itemView.findViewById(R.id.image);
                parentLayout = itemView.findViewById(R.id.parent_layout);
            }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        // - get element from your dataset at this position
        // - replace the contents of the view with that element


        holder.name.setText(crNames.get(position));
        holder.description.setText(crDescriptions.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + crNames.get(position));

                Toast.makeText(mContext, crNames.get(position), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, ChatroomActivity.class);
                //intent.putExtra("image_url", mImages.get(position));
                //intent.putExtra("image_name", mImageNames.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return crNames.size();
    }

}

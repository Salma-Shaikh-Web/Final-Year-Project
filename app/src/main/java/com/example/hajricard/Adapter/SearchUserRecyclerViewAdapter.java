package com.example.hajricard.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hajricard.ChatActivity;
import com.example.hajricard.R;
import com.example.hajricard.utils.Android;
import com.example.hajricard.utils.FirebaseUtil;
import com.example.hajricard.utils.Model.UserModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class SearchUserRecyclerViewAdapter extends FirestoreRecyclerAdapter<UserModel, SearchUserRecyclerViewAdapter.UserModelViewHolder> {

    Context context;

    public SearchUserRecyclerViewAdapter(@NonNull FirestoreRecyclerOptions<UserModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @NonNull
    @Override
    public UserModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_user_recycler_view_row, parent, false);
        return new UserModelViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull UserModelViewHolder holder, int position, @NonNull UserModel model) {
        holder.userName.setText(model.getUsername());
        holder.phoneNumber.setText(model.getPhone());
        if (model.getUserId().equals(FirebaseUtil.currentUserId())){
            holder.userName.setText(model.getUsername()+"(Me)");
        }
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChatActivity.class);
            Android.passUserModelAsIntent(intent,model);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    class UserModelViewHolder extends RecyclerView.ViewHolder {

        TextView userName, phoneNumber;
        ImageView profilePic;

        public UserModelViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.userName);
            phoneNumber = itemView.findViewById(R.id.phoneNumber);
            profilePic = itemView.findViewById(R.id.profile_picture_img_view);
        }
    }
}

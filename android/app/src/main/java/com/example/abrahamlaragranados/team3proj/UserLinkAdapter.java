package com.example.abrahamlaragranados.team3proj;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class UserLinkAdapter extends RecyclerView.Adapter<UserLinkAdapter.UserLinkHolder> {

    private final ArrayList<UserLink> userLinkList;
    private Context context;

    public UserLinkAdapter(Context context, ArrayList<UserLink> userLinkList) {
        this.userLinkList = userLinkList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserLinkHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new UserLinkHolder(LayoutInflater.from(context).inflate(R.layout.user_link, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserLinkHolder userLinkHolder, int i) {
        UserLink userLink = userLinkList.get(i);
        userLinkHolder.bindItem(userLink);
    }

    @Override
    public int getItemCount() {
        return userLinkList.size();
    }

    class UserLinkHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView user, project, link;


        public UserLinkHolder(@NonNull View itemView) {
            super(itemView);

            user = itemView.findViewById(R.id.user_name);
            project = itemView.findViewById(R.id.project_title);
            link = itemView.findViewById(R.id.project_link);

            itemView.setOnClickListener((View.OnClickListener) this);
        }

        @Override
        public void onClick(View v) {

            UserLink link = userLinkList.get(getLayoutPosition());

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link.getLink()));
            context.startActivity(intent);
        }

        public void bindItem(UserLink userLink) {
            user.setText(userLink.getUser());
            link.setText(userLink.getLink());
        }

    }
}

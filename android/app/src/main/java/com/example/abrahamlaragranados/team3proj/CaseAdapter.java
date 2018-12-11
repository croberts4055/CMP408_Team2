package com.example.abrahamlaragranados.team3proj;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by luism .
 */

public class CaseAdapter extends RecyclerView.Adapter<CaseAdapter.PostView> {

    public static final String TAG = CaseAdapter.class.getName();


    final private GridItemClickListener listener;


    private static int viewHolderCount;

    private int mNumberItems;
    public ArrayList<Cases> cases;
    public User current_logged_user;


    public interface GridItemClickListener{
        void onGridItemClick(int clickedItemGrid, View v);
    }


    public CaseAdapter(User current_logged_user,ArrayList<Cases>job, int itemSize, GridItemClickListener listener)
    {
        this.current_logged_user =  current_logged_user;
        this.mNumberItems = itemSize;
        this.cases = job;
        this.listener = listener;
        viewHolderCount = 0;
    }
    @Override
    public PostView onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForImageItems = R.layout.tableview_adapter;
        LayoutInflater inflater =  LayoutInflater.from(context);


        //attach to parent immediately boolean
        boolean shouldAttachToParentImmediately = false;

        View view  = inflater.inflate(layoutIdForImageItems,parent,shouldAttachToParentImmediately);
        PostView new_post =  new PostView(context,view);
        new_post.movieViewIndex = viewHolderCount;

        viewHolderCount++;

        return new_post;
    }

    @Override
    public void onBindViewHolder(PostView holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    class PostView extends RecyclerView.ViewHolder
            implements View.OnClickListener, CaseAdapter.GridItemClickListener {

        public int movieViewIndex;

        public TextView date;
        public TextView caseValue;
        public TextView Lawyer;
        public TextView Judge;
        public TextView location;
        public TextView caseType;
        public TextView status;

        public Button update;
        public Button directLocation;
        public Button deleteCase;
        public Button Note;

        //this is the button for the results
        private Context context;
        private View current_view;

        public PostView(Context context,View itemView) {
            super(itemView);
            this.context = context;

            update =  itemView.findViewById(R.id.Delete);
            directLocation =  itemView.findViewById(R.id.DirectLocation);
            deleteCase =  itemView.findViewById(R.id.Delete);

            status =  itemView.findViewById(R.id.Status);
            location =  itemView.findViewById(R.id.Location);
            Judge =  itemView.findViewById(R.id.caseJudge);
            caseType =  itemView.findViewById(R.id.caseType);
            caseValue =  itemView.findViewById(R.id.caseValue);
            date =  itemView.findViewById(R.id.date);
            Lawyer =  itemView.findViewById(R.id.Lawyer);
            Note = itemView.findViewById(R.id.Note);


            itemView.setOnClickListener(this);
            current_view = itemView;

        }



        void bind(int listIndex) {

            //add listener to the buttons
            PostClickListener listener = new PostClickListener(context,current_view);
            Cases CurrentCase =  cases.get(listIndex);
            update.setTag(CurrentCase.getCase_id());
            update.setOnClickListener(listener);
            directLocation.setTag(CurrentCase.getCase_location());
            directLocation.setOnClickListener(listener);
            deleteCase.setTag(CurrentCase.getCase_id());
            deleteCase.setOnClickListener(listener);

            if(current_logged_user.getUserRole().equals(context.getResources().getString(R.string.Secretary))
                    || current_logged_user.getUserRole().equals(context.getResources().getString(R.string.Clerk))
                    || current_logged_user.getUserRole().equals(context.getResources().getString(R.string.Ceo))){
                update.setVisibility(View.VISIBLE);
                update.setOnClickListener(listener);
                deleteCase.setVisibility(View.VISIBLE);
                Note.setVisibility(View.VISIBLE);
            }else{
                update.setVisibility(View.INVISIBLE);
                deleteCase.setVisibility(View.INVISIBLE);
            }
            //get the activity in case we need an reference
            //lets set the id tag for the buttons
            //add a new bid on this job
            date.setText("Date : "+CurrentCase.getCase_date());
            caseValue.setText("$ : "+CurrentCase.getCase_budget());
            Lawyer.setText("Lawyer : "+CurrentCase.getCase_Lawyer());
            Judge.setText("Judge : "+CurrentCase.getCase_judge());
            location.setText("Location : "+CurrentCase.getCase_location());
            caseType.setText("Case Type: "+CurrentCase.getCase_type());
            status.setText("Status : "+CurrentCase.getCase_status());


        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            listener.onGridItemClick(clickedPosition,v);
        }

        @Override
        public void onGridItemClick(int clickedItemGrid, View v) {
            //this is the click listener for the images on the recyclerview of the complaint
        }
    }
}

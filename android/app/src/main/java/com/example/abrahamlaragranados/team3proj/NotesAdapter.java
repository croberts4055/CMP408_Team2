package com.example.abrahamlaragranados.team3proj;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/*author luis manon*/
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.movieView> {

    public static final String TAG = NotesAdapter.class.getName();


    final private NotesAdapter.GridItemClickListener listener;


    private static int viewHolderCount;

    private int mNumberItems;
    private ArrayList<Notes> case_notes;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    public interface GridItemClickListener {
        void onAppointmentClickListener(int clickedItemGrid, View v);
    }


    public NotesAdapter(ArrayList<Notes> data, NotesAdapter.GridItemClickListener listener) {
        this.mNumberItems = data.size();
        this.case_notes = data;
        this.listener = listener;
        viewHolderCount = 0;
    }

    @Override
    public NotesAdapter.movieView onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForImageItems = R.layout.notes_view;
        LayoutInflater inflater = LayoutInflater.from(context);


        //attach to parent immediately boolean
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForImageItems, parent, shouldAttachToParentImmediately);
        NotesAdapter.movieView movie = new NotesAdapter.movieView(context, view);
        movie.movieViewIndex = viewHolderCount;

        viewHolderCount++;

        return movie;
    }

    @Override
    public void onBindViewHolder(NotesAdapter.movieView holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    class movieView extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public int movieViewIndex;

        public TextView user;
        public TextView Role;
        public TextView noteContent;
        public TextView date;
        public Context context;

        public void showTime(TextView time,String val,int hour, int min) {
            String format="";
            if (hour == 0) {
                hour += 12;
                format = "AM";
            } else if (hour == 12) {
                format = "PM";
            } else if (hour > 12) {
                hour -= 12;
                format = "PM";
            } else {
                format = "AM";
            }

            time.setText(new StringBuilder().append(val).append(hour).append(" : ").append(min)
                    .append(" ").append(format));
        }
        public movieView(Context context, View itemView) {
            super(itemView);
            this.context = context;

            date = itemView.findViewById(R.id.NotesDate);
            Role = itemView.findViewById(R.id.Role);
            user = itemView.findViewById(R.id.UserName);
            noteContent = itemView.findViewById(R.id.ContentNote);
            date = itemView.findViewById(R.id.NotesDate);
            itemView.setOnClickListener(this);
        }


        void bind(int listIndex) {
            if(case_notes.size()>0) {
                Notes note = case_notes.get(listIndex);
                date.setText("Date : "+note.getNote_date());
                Role.setText("Role : "+note.getUser_role());
                user.setText("Name : " + note.getUser_name());
                noteContent.setText(note.getUser_note());

            }

        }


        // COMPLETED (6) Override onClick, passing the clicked item's position (getAdapterPosition()) to mOnClickListener via its onListItemClick method

        /**
         * Called whenever a user clicks on an item in the list.
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            listener.onAppointmentClickListener(clickedPosition, v);
        }
    }
}
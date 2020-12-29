package com.raidify.mobi.mycouturier.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.raidify.mobi.mycouturier.R;
import com.raidify.mobi.mycouturier.ROOMDB.model.MeasureEntry;

import java.util.ArrayList;
import java.util.List;

public class MeasureEntryRecyclerAdapter extends RecyclerView.Adapter<MeasureEntryRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<MeasureEntry> measureEntryList = new ArrayList<>();

    public MeasureEntryRecyclerAdapter(Context context, List<MeasureEntry> measureEntryList) {

        Log.i("NDBOY", "Measure entry list count in recycler adapter: " + measureEntryList.size()); //TODO: For delete

        this.context = context;
        this.measureEntryList = measureEntryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mentry_body_part_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.i("NDBOY", "To bind object in Position: " + position + " of "+ measureEntryList.size());
        // bind the view and the data here
        MeasureEntry measureEntry = this.measureEntryList.get(position); //get the entries one by one from the List
        holder.length.setText(measureEntry.getLength().toString());
        holder.bodyPart.setText(measureEntry.getPart());

    }

    @Override
    public int getItemCount() {
        return measureEntryList.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView bodyPart;
        public TextView length;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //Set listeners
            itemView.setOnClickListener(this);

            //Find the textViews on the measure body part row XML file
            bodyPart = itemView.findViewById(R.id.bodyPartTextView);
            length = itemView.findViewById(R.id.lengthTextView);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition(); //To get the current adapter position

            Log.i("NDBOY", "clicked the " + measureEntryList.get(pos).getPart() + " body part");
        }
    }
}

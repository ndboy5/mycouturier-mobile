package com.raidify.mobi.mycouturier.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
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
        //TODO: Make the delete button work
        public Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //Set listeners
            itemView.setOnClickListener(this);

            //Find the textViews on the measure body part row XML file
            bodyPart = itemView.findViewById(R.id.bodyPartTextView);
            length = itemView.findViewById(R.id.lengthTextView);
            deleteButton = itemView.findViewById(R.id.deleteBtn);

            //NOTE: The listener for this button MUST come after being found using the findViewById method
//            deleteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition(); //To get the current adapter position

            Log.i("NDBOY", "clicked the " + measureEntryList.get(pos).getPart() + " body part"); //TODO: for delete


            Bundle bundle = new Bundle();
            bundle.putInt("adtrPosition", pos);
            //navigate to body part entries and send the adapter position/index as a bundle
            Navigation.findNavController(view).navigate(R.id.action_measureBodyPartFragment_to_lengthEntryFragment, bundle);

            //handle delete button in Card TODO: (Not working)
            if (view.getId()==R.id.deleteBtn) measureEntryList.remove(pos);
        }
    }
}

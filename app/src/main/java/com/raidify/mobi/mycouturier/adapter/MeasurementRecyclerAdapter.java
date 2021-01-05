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
import com.raidify.mobi.mycouturier.ROOMDB.model.Measurement;

import java.util.List;

public class MeasurementRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Measurement> measurements;

    public MeasurementRecyclerAdapter(Context context, List<Measurement> measurements) {

        this.context = context;
        this.measurements = measurements;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.measurement_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        // bind the view and the data here

        Measurement measurements = this.measurements.get(position); //get the entries one by one from the List

        ViewHolder viewHolder = (ViewHolder) holder;

        viewHolder.description.setText(measurements.getDescription());
        viewHolder.gender.setText(measurements.getGender());
        viewHolder.unit.setText(measurements.getUnit());
//        viewHolder.lastUpdateDate.setText(measurements.getLastupdateBy().toString());  //TODO: Give a null object ref error
    }

    @Override
    public int getItemCount() {
        return this.measurements.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView description;
        public TextView unit;
        public TextView gender;
        public TextView lastUpdateDate;
        //TODO: Make the delete button work
        public Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //Set listeners
            itemView.setOnClickListener(this);

            //Find the textViews on the measure body part row XML file
            description = itemView.findViewById(R.id.descriptionTextView);
            unit = itemView.findViewById(R.id.unitTextView2);
            gender = itemView.findViewById(R.id.genderTextView);
            lastUpdateDate = itemView.findViewById(R.id.lastUpdateTextView);

            //NOTE: The listener for this button MUST come after being found using the findViewById method
//            deleteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition(); //To get the current adapter position

            Bundle bundle = new Bundle();
            bundle.putInt("argMeId", pos); //Note: The row id was used to identify here and not the MeasureID

            //navigate to body part entries and send the adapter position/index as a bundle
            Navigation.findNavController(view).navigate(R.id.action_measurementBookFragment_to_measurementSummaryFragment, bundle);

            //handle delete button in Card TODO: (Not working)
            if (view.getId()==R.id.deleteBtn) measurements.remove(pos);
        }
    }
}

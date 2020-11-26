package com.raidify.mobi.mycouturier.ui.measurement;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.raidify.mobi.mycouturier.R;
import com.raidify.mobi.mycouturier.api_server.APIServerSingleton;

import org.json.JSONObject;

public class FullBodyFemaleFragment extends Fragment {

    private FullBodyFemaleViewModel mViewModel;

    public static FullBodyFemaleFragment newInstance() {
        return new FullBodyFemaleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.full_body_female_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FullBodyFemaleViewModel.class);
        // TODO: Use the ViewModel
        Button btn = getView().findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get Request Queue from Main activity
                RequestQueue requestQueue =  APIServerSingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue();
                String url = "https://run.mocky.io/v3/c97f48b7-73b9-4672-8ca0-32b6a823f33f";

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (APIServerSingleton.ACTION_GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("NDBOY","Response: " + response.toString());
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error properly
                                Log.e("NDBOY", "ERROR: "+ error);

                            }
                        });

                requestQueue.add(jsonObjectRequest);
            }
        });
    }

}
package nyc.c4q.capstone.favorites;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.capstone.R;
import nyc.c4q.capstone.firebase.FirebaseDataHelper;
import nyc.c4q.capstone.models.DBReturnCampaignModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment implements ChildEventListener {
    private View rootView;
    private RecyclerView recyclerView;
    //In this fragment Muhaimen will put in the logic to display the list of campaigns
    //
    private List<DBReturnCampaignModel> campaignModelList = new ArrayList<>();
    private PaignAdapter listAdapter;

    private static final String TAG = "FIREBASEFAV";

    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        recyclerView = rootView.findViewById(R.id.paignRecyclerview);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase.getInstance().getReference().child("campaigns").addChildEventListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listAdapter = new PaignAdapter(campaignModelList);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager1);
        recyclerView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        campaignModelList = FirebaseDataHelper.getInstance().getCampaignsList(dataSnapshot);
        for (DBReturnCampaignModel ccm : campaignModelList) {
            Log.d(TAG, "onChildAdded: " + ccm.getTitle());
            Log.d(TAG, "onChildAdded:size " + campaignModelList.size());
        }
        listAdapter.setData(campaignModelList);
        listAdapter.notifyDataSetChanged();

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }


}

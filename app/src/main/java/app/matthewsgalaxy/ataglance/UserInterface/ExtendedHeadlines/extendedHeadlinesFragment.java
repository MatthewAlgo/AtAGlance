package app.matthewsgalaxy.ataglance.UserInterface.ExtendedHeadlines;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.matthewsgalaxy.ataglance.AdapterClasses.RecyclerViewForecastAdapter;
import app.matthewsgalaxy.ataglance.AdapterClasses.RecyclerViewHeadlinesAdapter;
import app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions;
import app.matthewsgalaxy.ataglance.R;

public class extendedHeadlinesFragment extends Fragment {

    private Context MContext;
    private View PubView;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        MContext = null;
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        PubView = view;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PubView = inflater.inflate(R.layout.fragment_newsheadlinesfrag, container, false);
        InitRecyclerView();
        return PubView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PubView = null;
    }
    public void InitRecyclerView(){
        ArrayList<String> ArrayListURLValues = new ArrayList<>();
        ArrayList<String> TitlesArrayList;
        ArrayList<String> DescriptionsArrayList= new ArrayList<>();
        ArrayList<String> ImageURLArrayList= new ArrayList<>();

        // Append The values of each and every Title from the list
        TitlesArrayList = DifferentFunctions.ParseJSONWorldNews(DifferentFunctions.ReturnNewsResponseJSON("world"),"news_title");
        TitlesArrayList.addAll(DifferentFunctions.ParseJSONWorldNews(DifferentFunctions.ReturnNewsResponseJSON("science"), "news_title"));
        TitlesArrayList.addAll(DifferentFunctions.ParseJSONWorldNews(DifferentFunctions.ReturnNewsResponseJSON("business"), "news_title"));
        TitlesArrayList.addAll(DifferentFunctions.ParseJSONWorldNews(DifferentFunctions.ReturnNewsResponseJSON("tech"), "news_title"));
        TitlesArrayList.addAll(DifferentFunctions.ParseJSONWorldNews(DifferentFunctions.ReturnNewsResponseJSON("politics"), "news_title"));
        TitlesArrayList.addAll(DifferentFunctions.ParseJSONWorldNews(DifferentFunctions.ReturnNewsResponseJSON("entertainment"), "news_title"));

        RecyclerViewHeadlinesAdapter adapter = new RecyclerViewHeadlinesAdapter(ArrayListURLValues, TitlesArrayList, DescriptionsArrayList, ImageURLArrayList); // Call the Constructor for the adapter
        // TODO:
        // recyclerView.setAdapter(adapter);
        // RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        // recyclerView.setLayoutManager(layoutManager);

    }
}

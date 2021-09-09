package app.matthewsgalaxy.ataglance.UserInterface.ExtendedHeadlines;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;

import app.matthewsgalaxy.ataglance.AdapterClasses.RecyclerViewForecastAdapter;
import app.matthewsgalaxy.ataglance.AdapterClasses.RecyclerViewHeadlinesAdapter;
import app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions;
import app.matthewsgalaxy.ataglance.R;
import app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment;

public class extendedHeadlinesFragment extends Fragment {

    private Context MContext;
    private View PubView;
    private RecyclerView recyclerView;
    private Chip ChipURLLink;



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
        recyclerView = PubView.findViewById(R.id.recyclerviewheadlines_wrld);
        ChipURLLink = PubView.findViewById(R.id.ChipURLLink);

        InitRecyclerView();
        return PubView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PubView = null;
    }
    public void InitRecyclerView(){
        ArrayList<String> ArrayListURLValues;
        ArrayList<String> TitlesArrayList;
        ArrayList<String> DescriptionsArrayList;
        ArrayList<String> ImageURLArrayList;

        // Append The values of each and every Title from the list
        TitlesArrayList = DifferentFunctions.ParseJSONWorldNews(DifferentFunctions.ReturnNewsResponseJSON("world"),"news_title");
        TitlesArrayList.addAll(DifferentFunctions.ParseJSONWorldNews(DifferentFunctions.ReturnNewsResponseJSON("science"), "news_title"));
        TitlesArrayList.addAll(DifferentFunctions.ParseJSONWorldNews(DifferentFunctions.ReturnNewsResponseJSON("business"), "news_title"));
        TitlesArrayList.addAll(DifferentFunctions.ParseJSONWorldNews(DifferentFunctions.ReturnNewsResponseJSON("tech"), "news_title"));
        TitlesArrayList.addAll(DifferentFunctions.ParseJSONWorldNews(DifferentFunctions.ReturnNewsResponseJSON("politics"), "news_title"));
        TitlesArrayList.addAll(DifferentFunctions.ParseJSONWorldNews(DifferentFunctions.ReturnNewsResponseJSON("entertainment"), "news_title"));

        DescriptionsArrayList = DifferentFunctions.ParseJSONWorldNews(DifferentFunctions.ReturnNewsResponseJSON("world"),"news_descr");
        DescriptionsArrayList.addAll(DifferentFunctions.ParseJSONWorldNews(DifferentFunctions.ReturnNewsResponseJSON("science"),"news_descr"));
        DescriptionsArrayList.addAll(DifferentFunctions.ParseJSONWorldNews(DifferentFunctions.ReturnNewsResponseJSON("business"),"news_descr"));
        DescriptionsArrayList.addAll(DifferentFunctions.ParseJSONWorldNews(DifferentFunctions.ReturnNewsResponseJSON("tech"),"news_descr"));
        DescriptionsArrayList.addAll(DifferentFunctions.ParseJSONWorldNews(DifferentFunctions.ReturnNewsResponseJSON("politics"),"news_descr"));
        DescriptionsArrayList.addAll(DifferentFunctions.ParseJSONWorldNews(DifferentFunctions.ReturnNewsResponseJSON("entertainment"),"news_descr"));


        ArrayListURLValues = DifferentFunctions.ParseJSONWorldNews(DifferentFunctions.ReturnNewsResponseJSON("world"),"news_url");
        ArrayListURLValues.addAll(DifferentFunctions.ParseJSONWorldNews(DifferentFunctions.ReturnNewsResponseJSON("science"),"news_url"));
        ArrayListURLValues.addAll(DifferentFunctions.ParseJSONWorldNews(DifferentFunctions.ReturnNewsResponseJSON("business"),"news_url"));
        ArrayListURLValues.addAll(DifferentFunctions.ParseJSONWorldNews(DifferentFunctions.ReturnNewsResponseJSON("tech"),"news_url"));
        ArrayListURLValues.addAll(DifferentFunctions.ParseJSONWorldNews(DifferentFunctions.ReturnNewsResponseJSON("politics"),"news_url"));
        ArrayListURLValues.addAll(DifferentFunctions.ParseJSONWorldNews(DifferentFunctions.ReturnNewsResponseJSON("entertainment"),"news_url"));


        ImageURLArrayList = DifferentFunctions.ParseJSONWorldNews(DifferentFunctions.ReturnNewsResponseJSON("world"),"news_url_to_img");
        ImageURLArrayList.addAll(DifferentFunctions.ParseJSONWorldNews(DifferentFunctions.ReturnNewsResponseJSON("science"),"news_url_to_img"));
        ImageURLArrayList.addAll(DifferentFunctions.ParseJSONWorldNews(DifferentFunctions.ReturnNewsResponseJSON("business"),"news_url_to_img"));
        ImageURLArrayList.addAll(DifferentFunctions.ParseJSONWorldNews(DifferentFunctions.ReturnNewsResponseJSON("tech"),"news_url_to_img"));
        ImageURLArrayList.addAll(DifferentFunctions.ParseJSONWorldNews(DifferentFunctions.ReturnNewsResponseJSON("politics"),"news_url_to_img"));
        ImageURLArrayList.addAll(DifferentFunctions.ParseJSONWorldNews(DifferentFunctions.ReturnNewsResponseJSON("entertainment"),"news_url_to_img"));


        if(ImageURLArrayList.size() == ArrayListURLValues.size() && ArrayListURLValues.size() == DescriptionsArrayList.size() && DescriptionsArrayList.size() == TitlesArrayList.size()) {
            RecyclerViewHeadlinesAdapter adapter = new RecyclerViewHeadlinesAdapter(ArrayListURLValues, TitlesArrayList, DescriptionsArrayList, ImageURLArrayList); // Call the Constructor for the adapter
            recyclerView.setAdapter(adapter);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
        }

    }
}

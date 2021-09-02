package app.matthewsgalaxy.ataglance.ui.ExtendedForecast;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import app.matthewsgalaxy.ataglance.R;
import app.matthewsgalaxy.ataglance.adapterClasses.RecyclerViewForecastAdapter;
import app.matthewsgalaxy.ataglance.databinding.FragmentExtendedforecastBinding;

public class extendedForecastFragment extends Fragment {
    private FragmentExtendedforecastBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentExtendedforecastBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        InitRecyclerView();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void InitRecyclerView(){
        Log.d(TAG, "InitRecyclerView: init recyclerview");
        RecyclerView recyclerView = binding.recyclerviewforecast;


        RecyclerViewForecastAdapter adapter = new RecyclerViewForecastAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }


}
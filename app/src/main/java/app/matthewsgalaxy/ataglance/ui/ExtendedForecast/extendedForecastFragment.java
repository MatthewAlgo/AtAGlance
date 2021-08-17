package app.matthewsgalaxy.ataglance.ui.ExtendedForecast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import app.matthewsgalaxy.ataglance.databinding.FragmentExtendedforecastBinding;

public class extendedForecastFragment extends Fragment {
    private FragmentExtendedforecastBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentExtendedforecastBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
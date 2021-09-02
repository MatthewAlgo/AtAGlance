package app.matthewsgalaxy.ataglance.ui.ExtendedHeadlines;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import app.matthewsgalaxy.ataglance.databinding.FragmentExtendedheadlinesBinding;

public class extendedHeadlinesFragment extends Fragment {

    private FragmentExtendedheadlinesBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentExtendedheadlinesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

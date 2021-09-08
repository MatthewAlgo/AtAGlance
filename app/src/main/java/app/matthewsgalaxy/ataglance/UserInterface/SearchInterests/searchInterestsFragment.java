package app.matthewsgalaxy.ataglance.UserInterface.SearchInterests;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import app.matthewsgalaxy.ataglance.R;

public class searchInterestsFragment extends Fragment {
    private View PubView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_localsearch, container, false);
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        PubView = view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PubView = null;
    }
}

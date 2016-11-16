package kopperkow.soccerstats;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import kopperkow.soccerstats.model.PlayerContent;

/**
 * A fragment representing a single PlayerInfo detail screen.
 * This fragment is either contained in a {@link PlayerInfoListActivity}
 * in two-pane mode (on tablets) or a {@link PlayerInfoDetailActivity}
 * on handsets.
 */
public class PlayerInfoDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private PlayerContent.Player mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PlayerInfoDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = PlayerContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.content);
                appBarLayout.setBackgroundColor(getResources().getColor(mItem.favColor));
                ((Toolbar) activity.findViewById(R.id.detail_toolbar)).setTitleTextColor(getResources().getColor(mItem.textColor));
//                appBarLayout.setStatusBarScrimColor(mItem.favColor);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.playerinfo_detail, container, false);

        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(mItem.content);
            appBarLayout.setBackgroundColor(getResources().getColor(mItem.favColor));
            ((Toolbar) getActivity().findViewById(R.id.detail_toolbar)).setTitleTextColor(getResources().getColor(mItem.textColor));
//            appBarLayout.setStatusBarScrimColor(mItem.favColor);
        }
        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.playerinfo_detail)).setText(mItem.details);
        }

        return rootView;
    }
}

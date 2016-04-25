package hslu.bda.medimemory.fragment.overview;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import hslu.bda.medimemory.R;
import hslu.bda.medimemory.fragment.registration.FragmentRegistration;

/**
 * Created by Loana on 04.03.2016.
 */
public class FragmentOverview extends Fragment {

    private ViewGroup root;
    private ViewGroup overviewView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentOverviewPagerAdapter adapter;
    private int count = 0;
    private ImageButton iBtn_helpOverview;
    private FragmentRegistration fragmentRegistration;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = (ViewGroup) inflater.inflate(R.layout.fragment_overview, container, false);
        getIDs(root);
        setEvents();
        addPage("Medikament1");
        addPage("Medikament2");
        addPage("Medikament3");
        addPage("Medikament4");
        addPage("Medikament5");
        addPage("Medikament6");
        addPage("Medikament7");
        addPage("Medikament8");
        if (count ==0){
           addPage("Noch kein Medikament erfasst");
        }
        viewPager.setCurrentItem(0);

        return root;
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    private void getIDs(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.my_viewpager);
        adapter = new FragmentOverviewPagerAdapter(getFragmentManager(), getActivity());
        viewPager.setAdapter(adapter);
        tabLayout = (TabLayout) view.findViewById(R.id.my_tab_layout);

    }

    int selectedTabPosition;

    private void setEvents() {
        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                viewPager.setCurrentItem(tab.getPosition());
                selectedTabPosition = viewPager.getCurrentItem();
                Log.d("Selected", "Selected " + tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
                Log.d("Unselected", "Unselected " + tab.getPosition());
            }
        });
    }

    public void addPage(String pagename) {
        Bundle bundle = new Bundle();
        bundle.putString("data", pagename);
        FragmentOverviewChild fragmentChild = new FragmentOverviewChild();
        fragmentChild.setArguments(bundle);
        adapter.addFrag(fragmentChild, pagename);
        adapter.notifyDataSetChanged();
        if (adapter.getCount() > 0)
            tabLayout.setupWithViewPager(viewPager);

        viewPager.setCurrentItem(adapter.getCount() - 1);
        setupTabLayout();
        count ++;
    }

    public void setupTabLayout() {
        selectedTabPosition = viewPager.getCurrentItem();
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setCustomView(adapter.getTabView(i));
        }
    }


}
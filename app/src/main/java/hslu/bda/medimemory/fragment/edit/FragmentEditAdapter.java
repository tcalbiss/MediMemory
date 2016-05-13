package hslu.bda.medimemory.fragment.edit;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import hslu.bda.medimemory.R;
import hslu.bda.medimemory.database.DbAdapter;
import hslu.bda.medimemory.entity.Data;
import hslu.bda.medimemory.fragment.MainActivity;
import hslu.bda.medimemory.fragment.overview.FragmentOverviewChild;
import hslu.bda.medimemory.fragment.registration.FragmentRegistration;

/**
 * Created by Loana on 12.05.2016.
 */
public class FragmentEditAdapter extends ArrayAdapter <Data> {
    private Collection<Data> allPills;
    private FragmentRegistration fragmentRegistration;
    private int [] mediId;
    private DbAdapter dbAdapter;
    private TextView pillname;
    private ListView listView;
    private CheckBox chk_active;
    private int position;
    private List<Data> list;
    private int id;
    private View editView;


    public FragmentEditAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        dbAdapter= new DbAdapter(getContext());
        dbAdapter.open();

        allPills = new ArrayList<>();
        allPills = Data.getAllDataFromTable(dbAdapter);
        mediId = new int[allPills.size()];
    }


    public FragmentEditAdapter(Context context, int textViewResourceId, ArrayList<Data> allPills) {
        super(context, textViewResourceId, allPills);
        dbAdapter= new DbAdapter(getContext());
        dbAdapter.open();
        this.allPills = allPills;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_edit_item, null);
            list = new ArrayList(allPills);
            Data data = list.get(position);
            pillname = (TextView) convertView.findViewById(R.id.txt_editItem);
            pillname.setText(data.getDescription());
            //setId(data.getId());
            pillname.setTag(data.getId());
            chk_active = (CheckBox) convertView.findViewById(R.id.chk_active);
            setFocus(false);
        }

        showRegistrationFragment();
        setCheckBoxListener();
        return convertView;
    }

    private void showRegistrationFragment(){
        //final int position = listView.getSelectedItemPosition();
        /*for (Data data : allPills){
            mediId[position] = data.getId() ;
        }*/
        pillname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getContext()).getFab().hide();
                fragmentRegistration = new FragmentRegistration();
                FragmentEdit fragmentEdit = new FragmentEdit();
                setFocus(false);
                Object o = pillname.getTag();
                setMediId(8);
                //pillname.getTag();
                //setMediId(fragmentEdit.getPosition());
                //setMediId(list.get(listView.getSelectedItemPosition()).getId());
                setFocus(true);
                FragmentManager fragmentManager = ((MainActivity) getContext()).getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main, fragmentRegistration, "Fragment_Registration").commit();
            }
        });
    }

    private void setFocus (boolean focus){
        /*pillname.setFocusable(focus);
        pillname.setFocusableInTouchMode(focus);
        pillname.setClickable(focus);*/
        chk_active.setFocusable(focus);
        chk_active.setFocusable(focus);
    }

    private void setPosition(int pos){
        this.position = pos;
    }

    /*private int getPosition(){
        return position;
    }*/

    private void setId(int id){
        this.id = id;
    }



    public void setCheckBoxListener(){
        chk_active.setFocusable(true);
        chk_active.setFocusableInTouchMode(true);
        chk_active.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.toast_active), Toast.LENGTH_LONG).show();
                    //list.get(listView.getSelectedItemPosition()).setActive(1);
                } else {
                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.toast_inactive), Toast.LENGTH_LONG).show();
                    //list.get(listView.getSelectedItemPosition()).setActive(0);
                }
            }
        });
    }

    private void setMediId(int id){
        Bundle bundle = new Bundle();
        bundle.putInt("mediId", id);
        fragmentRegistration.setArguments(bundle);
    }
}

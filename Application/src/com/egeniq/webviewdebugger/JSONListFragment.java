package com.egeniq.webviewdebugger;

import java.util.ArrayList;
import java.util.List;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.egeniq.webviewdebugger.entity.Case;

public class JSONListFragment extends DialogFragment {

    private static final String TAG = JSONListFragment.class.getName();
    private List<Case> _cases = null;
    private WebContainer _webContainer = null;
    private static boolean instanceVisible = false;

    public void setUp(List<Case> cases, WebContainer webContainer) {
        _cases = cases;
        _webContainer = webContainer;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        instanceVisible = false;
        super.onCancel(dialog);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (instanceVisible) {
            Log.w(TAG, "Instance of JSON list dialog already visible!");
        } else {
            instanceVisible = true;
            super.show(manager, tag);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_json_list, container);
        getDialog().setTitle("Select a testing case:");
        if(_cases != null){
            ListView list = (ListView) v.findViewById(R.id.list);
            List<String> names = new ArrayList<String>();
            for (Case c : _cases){
                names.add(c.getName());
            }
            System.out.println(names.toString());
            String[] array = names.toArray(new String[names.size()]);
            list.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, array));
            list.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                    _webContainer.setJsEnabled(_cases.get(position).getJSEnabled());
                    _webContainer.setScrollEnabled(_cases.get(position).getScrollEnabled());
                    _webContainer.setPluginsEnabled(_cases.get(position).getPluginsEnabled());
                    _webContainer.getWebView().loadUrl(_cases.get(position).getUrl());
                    dismiss();
                }
            });
        }
        return v;
    }

}

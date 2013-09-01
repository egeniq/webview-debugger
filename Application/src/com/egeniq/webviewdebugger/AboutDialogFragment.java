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
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AboutDialogFragment extends DialogFragment {

    private static final String TAG = AboutDialogFragment.class.getName();

    WebContainer _webContainer = null;
    private static boolean instanceVisible = false;

    public void setUp(WebContainer webContainer) {
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
            Log.w(TAG, "Instance of About dialog already visible!");
        } else {
            instanceVisible = true;
            super.show(manager, tag);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_json_list, container);
        getDialog().setTitle("Info about the browser:");
        if (_webContainer != null) {
            ListView list = (ListView) v.findViewById(R.id.list);
            List<String> infos = new ArrayList<String>();
            infos.add(_webContainer.getScrollInfo());
            infos.add(_webContainer.getJSInfo());
            infos.add(_webContainer.getPluginInfo());

            String[] array = infos.toArray(new String[infos.size()]);
            list.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, array));
        }
        return v;
    }

}

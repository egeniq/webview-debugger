package com.egeniq.webviewdebugger;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.egeniq.webviewdebugger.entity.Case;

public class JSONListFragment extends DialogFragment {

    List<Case> _cases = null;
    WebView _webView = null;

    public void setUp(List<Case> cases, WebView webView) {
        _cases = cases;
        _webView = webView;
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
            list.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.json_list_row, names));
            list.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                    _webView.setVerticalScrollBarEnabled(_cases.get(position).getScrollState());
                    _webView.setHorizontalScrollBarEnabled(_cases.get(position).getScrollState());
                    _webView.loadUrl(_cases.get(position).getUrl());
                }
            });
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}

package com.egeniq.webviewdebugger;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.egeniq.webviewdebugger.JSONDownloader.JSONListener;
import com.egeniq.webviewdebugger.entity.Case;

public class MainActivity extends FragmentActivity {

    private WebView _webView;
    private EditText _url;
    private Button _goButton;
    private List<Case> _cases = new ArrayList<Case>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _webView = (WebView)findViewById(R.id.webView);
        _url = (EditText) findViewById(R.id.url);
        _goButton = (Button) findViewById(R.id.goButton);
        _webView.getSettings().setJavaScriptEnabled(true);
        _webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (url.endsWith(".json")) {
                    _openJSON(url);
                }
                _url.setText(url);
            }
        });
        _webView.loadUrl(getResources().getString(R.string.homepage));

        _goButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                _startUrl();
            }
        });

        _url.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO){
                    _startUrl();
                }
                return false;
            }
        });
    }

    private void _startUrl() {
        String url = _url.getText().toString();
        if (url != null && url.length() > 0) {
            _webView.loadUrl(url);
        }
    }

    private void _openJSON(String url) {
        new JSONDownloader(url, new JSONListener() {

            @Override
            public void onSuccess(JSONObject json) {
                _parseJSON(json);
            }
        }).execute();
    }

    protected void _parseJSON(JSONObject json) {
        _cases = new ArrayList<Case>();
        JSONArray array = null;
        try {
            array = json.getJSONArray("cases");


        for (int i = 0; i < array.length(); ++i) {
            JSONObject item = (JSONObject)array.get(i);
            String name = item.getString("name");
            String url = item.getString("url");
            boolean enableScroll = true;
            if (item.has("enableScroll")){
                enableScroll = item.getBoolean("enableScroll");
            }
                _cases.add(new Case(name, url, enableScroll));
            }
            _openFragment();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void _openFragment() {
        JSONListFragment frag = new JSONListFragment();
        frag.setUp(_cases, _webView);
        frag.show(getSupportFragmentManager(), "acac");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_homepage) {
            _webView.loadUrl(getResources().getString(R.string.homepage));
        }
        return super.onOptionsItemSelected(item);
    }

}

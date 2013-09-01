package com.egeniq.webviewdebugger;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import com.egeniq.webviewdebugger.JSONDownloader.JSONListener;
import com.egeniq.webviewdebugger.entity.Case;

public class WebContainer {

    private WebView _webView;
    private FragmentActivity _context;
    private List<Case> _cases = new ArrayList<Case>();
    private EditText _url;

    private boolean _scrollEnabled = true;
    private boolean _jsEnabled = true;
    private boolean _pluginsEnabled = true;

    public WebContainer(WebView webView, FragmentActivity context, EditText url) {
        _webView = webView;
        _context = context;
        _url = url;
        _webView.loadUrl(_context.getResources().getString(R.string.homepage));
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
                } else if (url.equals(":about")) {
                    _openAbout();
                }

                _url.setText(url);
            }
        });
    }

    protected void _openAbout() {
        AboutDialogFragment frag = new AboutDialogFragment();
        frag.setUp(this);
        if (_context != null) {
            frag.show(_context.getSupportFragmentManager(), "about");
        }

    }

    public WebView getWebView() {
        return _webView;
    }
    public void setScrollEnabled(boolean scrollEnabled) {
        _scrollEnabled = scrollEnabled;
        _webView.setHorizontalScrollBarEnabled(scrollEnabled);
        _webView.setVerticalScrollBarEnabled(scrollEnabled);
    }

    public void setJsEnabled(boolean jsEnabled) {
        _jsEnabled = jsEnabled;
        _webView.getSettings().setJavaScriptEnabled(jsEnabled);
    }

    @SuppressWarnings("deprecation")
    public void setPluginsEnabled(boolean pluginsEnabled) {
        _pluginsEnabled = pluginsEnabled;
        _webView.getSettings().setPluginState(pluginsEnabled ? PluginState.ON : PluginState.OFF);
    }

    public String getScrollInfo() {
        String result = "Scrolling is currently: ";
        if (_scrollEnabled) {
            result += "ON";
        } else {
            result += "OFF";
        }
        return result;
    }

    public String getJSInfo() {
        String result = "JavaScript is currently: ";
        if (_jsEnabled) {
            result += "ON";
        } else {
            result += "OFF";
        }
        return result;
    }

    public String getPluginInfo() {
        String result = "Plugins are currently: ";
        if (_pluginsEnabled) {
            result += "ON";
        } else {
            result += "OFF";
        }
        return result;
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
                boolean enableJS = true;
                boolean enablePlugins = true;
                if (item.has("enableScroll")) {
                    enableScroll = item.getBoolean("enableScroll");
                }
                if (item.has("enableJS")) {
                    enableScroll = item.getBoolean("enableJS");
                }
                if (item.has("enablePlugins")) {
                    enableScroll = item.getBoolean("enablePlugins");
                }
                _cases.add(new Case(name, url, enableScroll, enableJS, enablePlugins));
            }
            _openFragment();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void _openFragment() {
        JSONListFragment frag = new JSONListFragment();
        frag.setUp(_cases, this);
        if (_context != null) {
            frag.show(_context.getSupportFragmentManager(), "case_select");
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
}

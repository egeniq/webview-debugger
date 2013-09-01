package com.egeniq.webviewdebugger;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class MainActivity extends FragmentActivity {

    private WebContainer _webContainer;
    private EditText _url;
    private Button _goButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView webView = (WebView)findViewById(R.id.webView);
        _url = (EditText) findViewById(R.id.url);
        _goButton = (Button) findViewById(R.id.goButton);
        _webContainer = new WebContainer(webView, this, _url);

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
            _webContainer.getWebView().loadUrl(url);
        }
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
            _webContainer.getWebView().loadUrl(getResources().getString(R.string.homepage));
        }
        return super.onOptionsItemSelected(item);
    }

}

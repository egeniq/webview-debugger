package com.egeniq.webviewdebugger.entity;

public class Case {

    String _name;
    String _url;
    boolean _enableScroll;
    boolean _enableJS;
    boolean _enablePlugins;

    public Case(String name, String url, boolean enableScroll, boolean enableJS, boolean enablePlugins) {
        _name = name;
        _url = url;
        _enableScroll = enableScroll;
        _enableJS = enableJS;
        _enablePlugins = enablePlugins;
    }

    public String getName() {
        return _name;
    }

    public String getUrl() {
        return _url;
    }

    public boolean getScrollEnabled() {
        return _enableScroll;
    }

    public boolean getJSEnabled() {
        return _enableJS;
    }

    public boolean getPluginsEnabled() {
        return _enablePlugins;
    }

}

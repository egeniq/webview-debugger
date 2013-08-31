package com.egeniq.webviewdebugger.entity;

public class Case {

    String _name;
    String _url;
    boolean _enableScroll;

    public Case(String name, String url, boolean enableScroll) {
        _name = name;
        _url = url;
        _enableScroll = enableScroll;
    }

    public String getName() {
        return _name;
    }

    public String getUrl() {
        return _url;
    }

    public boolean getScrollState() {
        return _enableScroll;
    }

}

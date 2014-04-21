package com.lijing.yuerwan;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class CompanyFragment extends Fragment {
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//		WebView webview = (WebView)inflater.inflate(R.layout.company_view, container, false);
//		
//		WebSettings webSettings = webview.getSettings();       
//        webSettings.setJavaScriptEnabled(true); 
//		
//        webview.loadUrl("file:///android_asset/demo.html"); 
        
        //return webview;
		return super.onCreateView(inflater, container, savedInstanceState);
    }
}

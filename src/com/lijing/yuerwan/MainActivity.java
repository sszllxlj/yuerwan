package com.lijing.yuerwan;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {
	private FragmentTabHost mTabHost = null;
	private View indicator = null; 
	   private static final String TAG = "Update";
       public ProgressDialog pBar;
       private int newVerCode = 0;
       private String newVerName = "";
       private int vercode = 0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		
		indicator = getIndicatorView("公司简介", R.layout.tab_indicator); 
		mTabHost.addTab(mTabHost.newTabSpec("company")
                .setIndicator(indicator), CompanyFragment.class, null);  
		mTabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.button_status);
		
		indicator = getIndicatorView("早教视频", R.layout.tab_indicator); 
		mTabHost.addTab(mTabHost.newTabSpec("videolist")
                .setIndicator(indicator), VideoListFragment.class, null);  
		mTabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.button_status);
		
		indicator = getIndicatorView("健康问答", R.layout.tab_indicator); 
		mTabHost.addTab(mTabHost.newTabSpec("asklist")
                .setIndicator(indicator), AskListFragment.class, null);  
		mTabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.button_status);
		
		
		
	}
	
	private View getIndicatorView(String name, int layoutId) { 
        View v = getLayoutInflater().inflate(layoutId, null);
        TextView tv = (TextView) v.findViewById(R.id.tabText);  
        tv.setText(name);
        return v;  
    }
	public void updateApp(View view){
		
		new Thread(){
        	public void run(){
        		Message msg = new Message();       
                msg.what = 0;                          
                if (getServerVerCode()) {
                        vercode = Config.getVerCode(MainActivity.this);
                        if (newVerCode > vercode) {
                        	msg.what = 1;   
                                
                        } else {
                        	msg.what = 0;   
                                
                        }
                }
                mHandler.sendMessage(msg);
        }
        }.start();

	}
    private boolean getServerVerCode() {
        try {
                String verjson = NetworkTool.getContent(Config.UPDATE_SERVER
                                + Config.UPDATE_VERJSON);
                JSONObject obj = new JSONObject(verjson);
                if (obj!=null&&obj.length() > 0) {
                        try {
                                newVerCode = Integer.parseInt(obj.getString("verCode"));
                                newVerName = obj.getString("verName");
                        } catch (Exception e) {
                                newVerCode = -1;
                                newVerName = "";
                                return false;
                        }
                }
        } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                return false;
        }
        return true;
}

private void notNewVersionShow() {
//        int verCode = Config.getVerCode(this);
        String verName = Config.getVerName(this);
        StringBuffer sb = new StringBuffer();
        sb.append("当前版本:");
        sb.append(verName);
        sb.append(" Code:");
        sb.append(vercode);
        sb.append(",\n已是最新版,无需更新!");
        Dialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("软件更新").setMessage(sb.toString())
                        .setPositiveButton("确定",
                                        new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(DialogInterface dialog,
                                                                int which) {
                                                        finish();
                                                }

                                        }).create();
        
        dialog.show();
}

private void doNewVersionUpdate() {
//        int verCode = Config.getVerCode(this);
        String verName = Config.getVerName(this);
        StringBuffer sb = new StringBuffer();
        sb.append("当前版本:");
        sb.append(verName);
        sb.append(" Code:");
        sb.append(vercode);
        sb.append(", 发现新版本:");
        sb.append(newVerName);
        sb.append(" Code:");
        sb.append(newVerCode);
        sb.append(", 是否更新?");
        Dialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("软件更新")
                        .setMessage(sb.toString())
                        
                        .setPositiveButton("更新",
                                        new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(DialogInterface dialog,
                                                                int which) {
                                                        pBar = new ProgressDialog(MainActivity.this);
                                                        pBar.setTitle("正在下载");
                                                        pBar.setMessage("请稍候...");
                                                        pBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                                        downFile(Config.UPDATE_SERVER
                                                                        + Config.UPDATE_APKNAME);
                                                }

                                        })
                        .setNegativeButton("暂不更新",
                                        new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog,
                                                                int whichButton) {
                                                    
                                                }
                                        }).create();
        
        dialog.show();
}

void downFile(final String url) {
        pBar.show();
        new Thread() {
                public void run() {
                        HttpClient client = new DefaultHttpClient();
                        HttpGet get = new HttpGet(url);
                        HttpResponse response;
                        try {
                                response = client.execute(get);
                                HttpEntity entity = response.getEntity();
                                long length = entity.getContentLength();
                                InputStream is = entity.getContent();
                                FileOutputStream fileOutputStream = null;
                                if (is != null) {

                                        File file = new File(
                                                        Environment.getExternalStorageDirectory(),
                                                        Config.UPDATE_SAVENAME);
                                        fileOutputStream = new FileOutputStream(file);

                                        byte[] buf = new byte[1024];
                                        int ch = -1;
                                        int count = 0;
                                        while ((ch = is.read(buf)) != -1) {
                                                fileOutputStream.write(buf, 0, ch);
                                                count += ch;
                                                if (length > 0) {
                                                }
                                        }

                                }
                                fileOutputStream.flush();
                                if (fileOutputStream != null) {
                                        fileOutputStream.close();
                                }
                                down();
                        } catch (ClientProtocolException e) {
                                e.printStackTrace();
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                }

        }.start();

}

void down() {
	mHandler.post(new Runnable() {
                public void run() {
                        pBar.cancel();
                        update();
                }
        });

}

void update() {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(Environment
                        .getExternalStorageDirectory(), Config.UPDATE_SAVENAME)),
                        "application/vnd.android.package-archive");
        startActivity(intent);
}
@SuppressLint("HandlerLeak") private Handler mHandler = new Handler(){    
    
    public void handleMessage(Message msg) {    
        switch (msg.what) {    
        case 0:    
        	notNewVersionShow();
        	break;
        case 1:
        	doNewVersionUpdate();
        	break;
        default:break;
        }    
    };    
};          

}

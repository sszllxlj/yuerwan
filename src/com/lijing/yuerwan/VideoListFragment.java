package com.lijing.yuerwan;

import java.util.List;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

public class VideoListFragment extends Fragment {
	
	int[] button_ids = { R.id.ImageButton0, R.id.ImageButton1,
					  	 R.id.ImageButton2, R.id.ImageButton3,
					  	 R.id.ImageButton4, R.id.ImageButton5};
	Button[] buttons = new Button[button_ids.length];
	
	GridView videoGrid;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
		
		loadApps();
		
        // Inflate the layout for this fragment
		View rootView = inflater.inflate(R.layout.video_list_view,
				container, false);
		
		Log.v("lijing", "length:" + button_ids.length);
		for (int i = 0; i < button_ids.length - 1; i++) {
			Log.v("lijing", "i:" + i);
			buttons[i] = (Button)rootView.findViewById(button_ids[i]);
			if (buttons[i] == null)
				Log.v("lijing", "null");
			buttons[i].setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Log.v("lijing", "buttons: " + v.getId());
					ChangeVideoList();
				}

				private void ChangeVideoList() {
					// TODO Auto-generated method stub
					// Log.v("lijing", "buttons: " + i);
				}
			});
		}
		
		
		videoGrid = (GridView)rootView.findViewById(R.id.VideoGrid);
		videoGrid.setAdapter(new AppsAdapter());
		
        return rootView;
    }
	
	private List<ResolveInfo> mApps;
	
	private void loadApps() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        // mApps = getActivity().getPackageManager().queryIntentActivities(mainIntent, 0);
    }
	
	public class AppsAdapter extends BaseAdapter {
        public AppsAdapter() {
        }

        public View getView(int position, View convertView, ViewGroup parent) {
        	View rootview = (View)getActivity().getLayoutInflater().inflate(R.layout.video_item, null);

            return rootview;
        }


        public final int getCount() {
            return 20;
        }

        public final Object getItem(int position) {
            return null;
        }

        public final long getItemId(int position) {
            return position;
        }
    }
	
}

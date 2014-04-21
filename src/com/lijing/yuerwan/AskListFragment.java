package com.lijing.yuerwan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.readystatesoftware.viewbadger.BadgeView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class AskListFragment extends Fragment {
	private List<Map<String, Object>> mData;
	private List<Map<String, Object>> mCategory;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.ask_layout, container, false);
		mCategory = getCategories();
		mData = getData();
		AskAdapter adapter = new AskAdapter();
		ListView lv = (ListView) rootView.findViewById(R.id.listView1);
		lv.setAdapter(adapter);
		CategoryAdapter cadapter = new CategoryAdapter();
		ListView lvCategory = (ListView) rootView.findViewById(R.id.listView2);
		lvCategory.setAdapter(cadapter);
		return rootView;
    }
    public final class ViewHolder{
		public TextView question;
		public TextView answer;
		public Button buttonPostive;
	}
    public final class ViewCategoryHolder{
    	public TextView category;
    }
    private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("question", "Question 1");
		map.put("answer", "Answer 1");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("queston", "Question 2");
		map.put("answer", "Answer 2");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("question", "Question 3");
		map.put("answer", "Answer 3");
		list.add(map);
		
		map = new HashMap<String, Object>();
		map.put("question", "Question 3");
		map.put("answer", "Answer 3");
		list.add(map);
		
		map = new HashMap<String, Object>();
		map.put("question", "Question 4");
		map.put("answer", "Answer 4");
		list.add(map);
		
		map = new HashMap<String, Object>();
		map.put("question", "Question 5");
		map.put("answer", "Answer 5");
		list.add(map);
		
		map = new HashMap<String, Object>();
		map.put("question", "Question 6");
		map.put("answer", "Answer 6");
		list.add(map);
		
		map = new HashMap<String, Object>();
		map.put("question", "Question 7");
		map.put("answer", "Answer 7");
		list.add(map);
		
		return list;
	}
    private List<Map<String, Object>> getCategories() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("category", "Category 1");
		
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("category", "Category 2");
		
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("category", "Category 3");
		
		list.add(map);
		
		return list;
		
    }
    public class AskAdapter extends BaseAdapter {
        public AskAdapter() {
        }

        public View getView(int position, View convertView, ViewGroup parent) {
        	
        	ViewHolder holder = null;
			if (convertView == null) {
				
				holder=new ViewHolder();  
				convertView = (View)getActivity().getLayoutInflater().inflate(R.layout.ask_item, null);
				holder.question = (TextView)convertView.findViewById(R.id.textQuestion);
				holder.answer = (TextView)convertView.findViewById(R.id.textAnswer);
				holder.buttonPostive = (Button)convertView.findViewById(R.id.buttonPostive);
				
				convertView.setTag(holder);
				
			}else {
				
				holder = (ViewHolder)convertView.getTag();
			}
			holder.question.setText((String)mData.get(position).get("question"));
			holder.answer.setText((String)mData.get(position).get("answer"));
            return convertView;
        }

        public final int getCount() {
            return mData.size();
        }

        public final Object getItem(int position) {
            return mData.get(position);
        }

        public final long getItemId(int position) {
            return position;
        }
    }
    public class CategoryAdapter extends BaseAdapter {
        public CategoryAdapter() {
        }

        public View getView(int position, View convertView, ViewGroup parent) {
        	
        	ViewCategoryHolder holder = null;
			if (convertView == null) {
				
				holder=new ViewCategoryHolder();  
				convertView = (View)getActivity().getLayoutInflater().inflate(R.layout.category, null);
				holder.category = (TextView)convertView.findViewById(R.id.categories);
				
				convertView.setTag(holder);
				
			}else {
				
				holder = (ViewCategoryHolder)convertView.getTag();
			}
			holder.category.setText((String)mCategory.get(position).get("category"));
            return convertView;
        }

        public final int getCount() {
            return mCategory.size();
        }

        public final Object getItem(int position) {
            return mCategory.get(position);
        }

        public final long getItemId(int position) {
            return position;
        }
    }
	
}


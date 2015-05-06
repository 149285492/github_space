package com.train.day38_01surfaceview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TimerAdapter extends BaseAdapter {
	Context context;
	TimerAdapter(Context context){
		this.context=context;
	}
	@Override
	public int getCount() {
		return 20;

	}

	@Override
	public Object getItem(int position) {
		return null;

	}

	@Override
	public long getItemId(int position) {
		return 0;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder=null;
		if(convertView==null){
			holder=new Holder();
			convertView=LayoutInflater.from(context).inflate(R.layout.item, null);
			holder.textView=(TextView) convertView.findViewById(R.id.textView);
			holder.surfaceView=(MySurfaceView) convertView.findViewById(R.id.surfaceView);
			holder.texttureView=(MyTextTureView) convertView.findViewById(R.id.textTrueView);
			convertView.setTag(holder);
		}else{
			holder=(Holder) convertView.getTag();
		}
		
		return convertView;

	}
	class Holder{
		TextView textView;
		MySurfaceView surfaceView;
		MyTextTureView texttureView;
	}
}

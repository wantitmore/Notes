package com.example.notes;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Myadapter extends BaseAdapter {
	
	private Context context;
	private Cursor cursor;
	private LinearLayout layout;
	public Myadapter(Context context, Cursor cursor) {
		this.context = context;
		this.cursor = cursor;
	}

	@Override
	public int getCount() {
		return cursor.getCount();
	}

	@Override
	public Object getItem(int position) {
		return cursor.getPosition();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	//暂时没用convert和viewholder
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		layout = (LinearLayout) inflater.inflate(R.layout.cell, null);
		TextView tvcontent = (TextView) layout.findViewById(R.id.list_content);
		TextView tvtime = (TextView) layout.findViewById(R.id.list_time);
		ImageView ivimg = (ImageView) layout.findViewById(R.id.list_img);
		ImageView ivvideo = (ImageView) layout.findViewById(R.id.list_vedio);
		cursor.moveToPosition(position);
		String content = cursor.getString(cursor.getColumnIndex("content"));
		String time = cursor.getString(cursor.getColumnIndex("time"));
		String url = cursor.getString(cursor.getColumnIndex("path"));
		tvcontent.setText(content);
		tvtime.setText(time);
		ivimg.setImageBitmap(getImageThumBitmap(url, 200, 200));
		return layout;
	}
	public Bitmap getImageThumBitmap(String uri,int width,int height){
		Bitmap bitmap = null;
		BitmapFactory.Options options =  new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		bitmap = BitmapFactory.decodeFile(uri,options);
		options.inJustDecodeBounds = false;
		int beWeith = options.outWidth/width;
		int behheight = options.outHeight/height;
		int be = 1;
		if (beWeith < behheight) {
			be = beWeith;
		}else{
			be = behheight;
		}
		options.inSampleSize = be;
		bitmap = BitmapFactory.decodeFile(uri, options);
		bitmap = ThumbnailUtils.extractThumbnail
				(bitmap, width, behheight,ThumbnailUtils.
						OPTIONS_RECYCLE_INPUT);
		return bitmap;
		
	}
}

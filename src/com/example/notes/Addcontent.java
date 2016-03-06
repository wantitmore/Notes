package com.example.notes;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;

public class Addcontent extends Activity implements OnClickListener {
	private String val;
	private Button savebn, deletebn;
	private EditText ettext;
	private VideoView c_video;
	private ImageView c_img;
	private NotesDB notesDB;
	private SQLiteDatabase dbwriter;
	private File phoneFile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcontent);
		val = getIntent().getStringExtra("flag");
		savebn = (Button) findViewById(R.id.save);
		deletebn = (Button) findViewById(R.id.delete);
		ettext = (EditText) findViewById(R.id.c_ettext);
		c_video = (VideoView) findViewById(R.id.c_vedio);
		c_img = (ImageView) findViewById(R.id.c_img);
		savebn.setOnClickListener(this);
		deletebn.setOnClickListener(this);
		notesDB = new NotesDB(this);
		dbwriter = notesDB.getWritableDatabase();
		initview();
	}

	public void initview() {
		if (val.equals("1")) {// wen zi
			c_img.setVisibility(View.GONE);
			c_video.setVisibility(View.GONE);
		}
		if (val.equals("2")) {// video
			c_img.setVisibility(View.GONE);
			c_video.setVisibility(View.VISIBLE);
		} else if (val.equals("3")) {// img
			c_img.setVisibility(View.VISIBLE);
			c_video.setVisibility(View.GONE);
			Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			/*
			 * phoneFile = new
			 * File(Environment.getExternalStorageDirectory().getAbsoluteFile()
			 * + "/" + gettime() + ".jpg");
			 */
			// xiu gai yu ju
			phoneFile = new File(Environment.getExternalStorageDirectory(),
					"out.jpg");
			intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(phoneFile));
			startActivityForResult(intent2, 1);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.save:
			addDB();
			finish();
			break;
		case R.id.delete:
			finish();
			break;
		default:
			break;
		}
	}

	public void addDB() {
		ContentValues cv = new ContentValues();
		cv.put("content", ettext.getText().toString());
		cv.put("time", gettime());
		cv.put("path", phoneFile + "");
		dbwriter.insert("notes", null, cv);
	}

	private String gettime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String str = sdf.format(date);
		return str;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {

			/*
			 * Bitmap bitmap = BitmapFactory.decodeFile(phoneFile.
			 * getAbsolutePath());
			 */
			Bitmap bitmap = null;
			try {
				bitmap = BitmapFactory.decodeStream(getContentResolver()
						.openInputStream(Uri.fromFile(phoneFile)));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			c_img.setImageBitmap(bitmap);
		}
	}

}

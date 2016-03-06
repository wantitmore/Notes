package com.example.notes;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;


public class MainActivity extends Activity implements OnClickListener{
	private Intent intent;
	Button textbn,imgbn,vediobn;
	private ListView lv;
	private NotesDB notesDB;
	private SQLiteDatabase dbreader;
	/*private SQLiteDatabase dbWriter;
	private NotesDB notesDB;*/
	private Myadapter myadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView)findViewById(R.id.list);
        textbn = (Button)findViewById(R.id.text);
        imgbn = (Button)findViewById(R.id.img);
        vediobn = (Button)findViewById(R.id.vedio);
        intview();
       /* notesDB = new NotesDB(this, null, null, 1);
        dbWriter = notesDB.getWritableDatabase();
        addDB();*/
    }
    public void intview(){
    	
    	imgbn.setOnClickListener(this);
        textbn.setOnClickListener(this);
        vediobn.setOnClickListener(this);
        notesDB = new NotesDB(this);
        dbreader = notesDB.getReadableDatabase();
    }
    
    /*public void addDB(){
    	ContentValues cv = new ContentValues();
    	cv.put("content", "Hello Worle!");
    	cv.put("time", gettime());
    	dbWriter.insert("notes", null, cv);
    }
	private String gettime() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
		Date date = new Date();
		String str = sf.format(date);
		return str;
	}
    */

	@Override
	public void onClick(View v) {
		intent = new Intent(this,Addcontent.class);
		switch (v.getId()) {
		case R.id.text:
			intent.putExtra("flag", "1");
			startActivity(intent);
			break;
		case R.id.vedio:
			intent.putExtra("flag", "2");
			startActivity(intent);
			break;
		case R.id.img:
			intent.putExtra("flag", "3");
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	public void selectDB(){
		Cursor cursor = dbreader.query("notes", null, null, null, null, null, null);
		myadapter = new Myadapter(this, cursor);
		lv.setAdapter(myadapter);
	}
	@Override
	protected void onResume() {
		super.onResume();
		selectDB();
		
	}
}

package iut.tp.projets4;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SuiviActivity extends Activity {
	
	List<String> dateslist;
	List<String> repaslist;
	List<Integer> calorieslist;
	AdapterSuivi adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_suivi);
		
		PlatsDbHelper bdd = new PlatsDbHelper(this);
		SQLiteDatabase db = bdd.getReadableDatabase();
		
		Cursor c = db.rawQuery("SELECT * FROM Repas ORDER BY ID DESC", null);
		dateslist = new ArrayList<String>();
		repaslist = new ArrayList<String>();
		calorieslist = new ArrayList<Integer>();
		if(c.moveToFirst()){
			do{
				dateslist.add(c.getString(3));
				repaslist.add(c.getString(1));
				calorieslist.add(c.getInt(2));
			}while(c.moveToNext());
		}
		
		String[] datestab = dateslist.toArray(new String[dateslist.size()]);
		String[] repastab = repaslist.toArray(new String[repaslist.size()]);
		adapter = new AdapterSuivi(this, datestab, repastab);
		
		ListView liste = (ListView) findViewById(R.id.ListView1);
		liste.setAdapter(adapter);
		
		liste.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long roxid) 
			{
				Toast.makeText(SuiviActivity.this, "Calories : "+calorieslist.get(position), Toast.LENGTH_SHORT).show();
			}});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.suivi, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_apropos) {
			Intent intent = new Intent(SuiviActivity.this, AproposActivity.class);
        	startActivity(intent);
		}else if (id == R.id.action_accueil) {
			Intent intent = new Intent(SuiviActivity.this, MainActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}

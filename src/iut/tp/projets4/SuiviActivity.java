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
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SuiviActivity extends Activity {
	
	List<String> repastab;
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_suivi);
		
		PlatsDbHelper bdd = new PlatsDbHelper(this);
		SQLiteDatabase db = bdd.getReadableDatabase();
		
		Cursor c = db.rawQuery("SELECT * FROM Repas ORDER BY ID DESC", null);
		repastab = new ArrayList<String>();
		if(c.moveToFirst()){
			do{
				repastab.add(c.getString(3)+" Votre repas: "+ c.getString(1)+" Calories : "+c.getInt(2));
			}while(c.moveToNext());
		}
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, repastab);
		
		ListView liste = (ListView) findViewById(R.id.ListView1);
		liste.setAdapter(adapter);
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

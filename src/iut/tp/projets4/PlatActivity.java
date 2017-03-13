package iut.tp.projets4;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


public class PlatActivity extends Activity {
	
	private List<String> repastab;
	private ArrayAdapter<String> adapter;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plat);
		PlatsDbHelper bdd = new PlatsDbHelper(this);
		SQLiteDatabase db = bdd.getReadableDatabase();
		Cursor curs = db.rawQuery("select nom from Plats where type = ?", new String[]{"Entree"});
		
		Toast.makeText(this, curs.getString(0), Toast.LENGTH_SHORT).show();
	
		
		repastab = new ArrayList<String>();
		
		if(curs.moveToFirst()){
			Spinner spinnerEntree =(Spinner) findViewById(R.id.spinnerEntree);
			do{
				repastab.add("--->" + curs.getString(1) +"<----");
			}while(curs.moveToNext());
		}
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, repastab);
		
		Spinner spinnerEntree = (Spinner) findViewById(R.id.spinnerEntree);
		spinnerEntree.setAdapter(adapter);
		
//		if(curs.getCount() > 0){
//			while(curs.moveToNext()){
//			String[] from = new String[]{"nom"};
//			  // create an array of the display item we want to bind our data to
//			int[] to = new int[]{android.R.id.text1};
//	
//		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, curs, from, to, 0);
//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		spinnerEntree.setAdapter(adapter);
//		}
//		}
		
//		while(curs.moveToNext()){
//		tabEntree[i] = curs.getString(curs.getColumnIndexOrThrow("nom"));
//		i++;
//	}
//    Spinner spinnerEntree = (Spinner) findViewById(R.id.spinnerEntree);
//    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tabEntree);
//    spinnerEntree.setAdapter(adapter);
	

		
//			
		}
		



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_apropos) {
			return true;
		}else if (id == R.id.action_suivi){
        	Intent intent = new Intent(PlatActivity.this, SuiviActivity.class);
        	startActivity(intent);
        }
		return super.onOptionsItemSelected(item);
	}
	
	public void clicConseiller(View v){
		Intent intent = new Intent(PlatActivity.this, PropositionActivity.class);
		startActivity(intent);
	}
	
	public void clicValider(View v){
		Intent intent = new Intent(PlatActivity.this, RepasActivity.class);
		startActivity(intent);
	}
}

package iut.tp.projets4;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class PlatActivity extends Activity {
	
	private int TAILLE_MAX = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plat);
		PlatsDbHelper bdd = new PlatsDbHelper(this);
		SQLiteDatabase db = bdd.getReadableDatabase();
		Cursor curs = db.rawQuery("select nom from Plats where calories >= 100", null);
		
		int i = 0;
		String[] tabEntree= new String[TAILLE_MAX];
		while(curs.moveToNext()){
			tabEntree[i] = curs.getString(0);
			i++;
		}
        Spinner spinnerEntree = (Spinner) findViewById(R.id.spinnerEntree);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tabEntree);
        spinnerEntree.setAdapter(adapter);

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

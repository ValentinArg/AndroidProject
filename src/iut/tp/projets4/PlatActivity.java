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
	
	List<String> entreestab, platstab, complementstab, dessertstab;
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plat);
		
		PlatsDbHelper bdd = new PlatsDbHelper(this);
		SQLiteDatabase db = bdd.getReadableDatabase();
		
		//Curseurs pour les spinners
		Cursor cursEntrees = db.rawQuery("SELECT nom FROM Plats WHERE type='Entree' ORDER BY nom ASC", null);
		Cursor cursPlats = db.rawQuery("SELECT nom FROM Plats WHERE type='Plat' ORDER BY nom ASC", null);
		Cursor cursComplements = db.rawQuery("SELECT nom FROM Plats WHERE type='Complement' ORDER BY nom ASC", null);
		Cursor cursDesserts = db.rawQuery("SELECT nom FROM Plats WHERE type='Dessert' ORDER BY nom ASC", null);
		
		//Affichage des entrees de la bd dans le spinner
		entreestab = new ArrayList<String>();
		if(cursEntrees.moveToFirst()){
			do{
				entreestab.add(cursEntrees.getString(0));
			}while(cursEntrees.moveToNext());
		}
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, entreestab);
		
		Spinner spinnerEntrees = (Spinner) findViewById(R.id.spinnerEntree);
		spinnerEntrees.setAdapter(adapter);
		
	//Affichage des plats de la bd dans le spinner	
	platstab = new ArrayList<String>();
	if(cursPlats.moveToFirst()){
		do{
			platstab.add(cursPlats.getString(0));
		}while(cursPlats.moveToNext());
	}
	adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, platstab);
	
	Spinner spinnerPlats= (Spinner) findViewById(R.id.spinnerPlat);
	spinnerPlats.setAdapter(adapter);
	
	//Affichage des complements de la bd dans le spinner	
	complementstab = new ArrayList<String>();
	if(cursComplements.moveToFirst()){
		do{
			complementstab.add(cursComplements.getString(0));
		}while(cursComplements.moveToNext());
	}
	adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, complementstab);
	
	Spinner spinnerComplements= (Spinner) findViewById(R.id.spinnerComplement);
	spinnerComplements.setAdapter(adapter);
	
	
	//Affichage des desserts de la bd dans le spinner	
	dessertstab = new ArrayList<String>();
	if(cursDesserts.moveToFirst()){
		do{
			dessertstab.add(cursDesserts.getString(0));
		}while(cursDesserts.moveToNext());
	}
	adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dessertstab);
	
	Spinner spinnerDesserts= (Spinner) findViewById(R.id.spinnerDessert);
	spinnerDesserts.setAdapter(adapter);
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
		
		Spinner spinnerEntree = (Spinner) findViewById(R.id.spinnerEntree);
		String entree = spinnerEntree.getSelectedItem().toString();
		intent.putExtra("Entree", entree);
		
		Spinner spinnerPlat = (Spinner) findViewById(R.id.spinnerPlat);
		String plat = spinnerPlat.getSelectedItem().toString();
		intent.putExtra("Plat", plat);
		
		Spinner spinnerComplement = (Spinner) findViewById(R.id.spinnerComplement);
		String complement = spinnerComplement.getSelectedItem().toString();
		intent.putExtra("Complement", complement);
		
		Spinner spinnerDessert = (Spinner) findViewById(R.id.spinnerDessert);
		String dessert = spinnerDessert.getSelectedItem().toString();
		intent.putExtra("Dessert", dessert);
		
		startActivity(intent);
	}
	
	public void clicValider(View v){
		Intent intent = new Intent(PlatActivity.this, RepasActivity.class);
		startActivity(intent);
	}
}

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
import android.widget.TextView;
import android.widget.Toast;


public class PlatActivity extends Activity {
	
	List<String> entreestab, platstab, complementstab, dessertstab;
	ArrayAdapter<String> adapterEntree, adapterPlat, adapterComplement, adapterDessert;
	float caloriesRecommandees;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plat);
		
		//calcul de l'IMC
		TextView tvImc = (TextView) findViewById(R.id.textView1);
		int poids = Integer.parseInt(getIntent().getStringExtra("poids"));
		float taille = Float.parseFloat(getIntent().getStringExtra("taille"))/100;
		tvImc.setText("Votre IMC = "+(int)(poids/(Math.pow(taille, 2))));
		
		//calcul des calories recommandées
		TextView tvCal = (TextView) findViewById(R.id.textView2);
		String sexe = getIntent().getStringExtra("sexe");
		String age = getIntent().getStringExtra("age");
		if(age.equals(new String("adulte"))){
			if(sexe.equals(new String("femme"))){
				this.caloriesRecommandees = 805;
			}else{
				this.caloriesRecommandees = 910;
			}
		}else{
			this.caloriesRecommandees = (50*poids)*0.35f;
		}
		tvCal.setText("Calories recommandées pour un repas = "+this.caloriesRecommandees);
		
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
		adapterEntree = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, entreestab);
		
		Spinner spinnerEntrees = (Spinner) findViewById(R.id.spinnerEntree);
		spinnerEntrees.setAdapter(adapterEntree);
		
		//Affichage des plats de la bd dans le spinner	
		platstab = new ArrayList<String>();
		if(cursPlats.moveToFirst()){
			do{
				platstab.add(cursPlats.getString(0));
			}while(cursPlats.moveToNext());
		}
		adapterPlat = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, platstab);
		
		Spinner spinnerPlats= (Spinner) findViewById(R.id.spinnerPlat);
		spinnerPlats.setAdapter(adapterPlat);
		
		//Affichage des complements de la bd dans le spinner	
		complementstab = new ArrayList<String>();
		complementstab.add("Aucun");
		if(cursComplements.moveToFirst()){
			do{
				complementstab.add(cursComplements.getString(0));
			}while(cursComplements.moveToNext());
		}
		adapterComplement = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, complementstab);
		
		Spinner spinnerComplements= (Spinner) findViewById(R.id.spinnerComplement);
		spinnerComplements.setAdapter(adapterComplement);
		
		
		//Affichage des desserts de la bd dans le spinner	
		dessertstab = new ArrayList<String>();
		if(cursDesserts.moveToFirst()){
			do{
				dessertstab.add(cursDesserts.getString(0));
			}while(cursDesserts.moveToNext());
		}
		adapterDessert = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dessertstab);
		
		Spinner spinnerDesserts= (Spinner) findViewById(R.id.spinnerDessert);
		spinnerDesserts.setAdapter(adapterDessert);
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
		
		intent.putExtra("calories", this.caloriesRecommandees);
		
		startActivityForResult(intent, 1);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 1){		
			//changement du spinner entrée
			Spinner spinnerEntree = (Spinner) findViewById(R.id.spinnerEntree);
			String entree = data.getStringExtra("entreeProposee");
			this.entreestab.remove(entree);
			this.entreestab.add(0, entree);
			adapterEntree.notifyDataSetChanged();
			spinnerEntree.setSelection(0);
			
			//changement du spinner plat
			Spinner spinnerPlat = (Spinner) findViewById(R.id.spinnerPlat);
			String plat = data.getStringExtra("platPropose");
			this.platstab.remove(plat);
			this.platstab.add(0, plat);
			adapterPlat.notifyDataSetChanged();
			spinnerPlat.setSelection(0);
			
			//changement du spinner complement
			Spinner spinnerComplement = (Spinner) findViewById(R.id.spinnerComplement);
			String complement = data.getStringExtra("complementPropose");
			this.complementstab.remove(complement);
			this.complementstab.add(0, complement);
			adapterComplement.notifyDataSetChanged();
			spinnerComplement.setSelection(0);
			
			//changement du spinner dessert
			Spinner spinnerDessert = (Spinner) findViewById(R.id.spinnerDessert);
			String dessert = data.getStringExtra("dessertPropose");
			this.dessertstab.remove(dessert);
			this.dessertstab.add(0, dessert);
			adapterDessert.notifyDataSetChanged();
			spinnerDessert.setSelection(0);
		}
	}


	public void clicValider(View v){
		Intent intent = new Intent(PlatActivity.this, RepasActivity.class);
		startActivity(intent);
	}
}

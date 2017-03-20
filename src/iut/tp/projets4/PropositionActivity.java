package iut.tp.projets4;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PropositionActivity extends Activity {
	
	String entreeProposee;
	String platPropose;
	String complementPropose;
	String dessertPropose;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_proposition);
		
		PlatsDbHelper bdd = new PlatsDbHelper(this);
		SQLiteDatabase db = bdd.getReadableDatabase();
		
		int caloriesRecommandees = getIntent().getIntExtra("calories", 1000);
		
		String nomEntreeChoisie = getIntent().getStringExtra("Entree");
		String nomPlatChoisi = getIntent().getStringExtra("Plat");
		String nomComplementChoisi = getIntent().getStringExtra("Complement");
		String nomDessertChoisi = getIntent().getStringExtra("Dessert");
		
		TextView tvEntree = (TextView) findViewById(R.id.textView4);
		TextView tvPlat = (TextView) findViewById(R.id.textView5);
		TextView tvComplement = (TextView) findViewById(R.id.textView6);
		TextView tvDessert = (TextView) findViewById(R.id.textView7);
	
		tvPlat.setText(nomPlatChoisi);
		tvComplement.setText(nomComplementChoisi);
		this.platPropose = nomPlatChoisi;
		this.complementPropose = nomComplementChoisi;
			
		//récupération des sélections et calcul des calories AVANT proposition
		Cursor cursKcalRepas = db.rawQuery("SELECT SUM(calories) FROM Plats WHERE nom='"+nomEntreeChoisie+"'"
																			+" OR nom='"+nomPlatChoisi+"'"
																			+" OR nom='"+nomComplementChoisi+"'"
																			+" OR nom='"+nomDessertChoisi+"';", null);
		int sommeKcal = 0;
		if(cursKcalRepas.moveToFirst()){
			do{
				sommeKcal = cursKcalRepas.getInt(0);
			}while(cursKcalRepas.moveToNext());
		}
		
		TextView sommeAvant = (TextView) findViewById(R.id.textView3);
		sommeAvant.setText("Calories avant "+sommeKcal);
		
		ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar2);
		pb.setMax(1820);
		pb.setProgress(sommeKcal);
		//----------------------------
		
		//génération d'une proposition (on garde le plat et le complément)
		
		Cursor cursKcalPlatComplement = db.rawQuery("SELECT SUM(calories) FROM Plats WHERE nom='"+nomPlatChoisi+"'"
																					+" OR nom='"+nomComplementChoisi+"';", null);
		int sommeKcalPlatComplement = 0;
		if(cursKcalPlatComplement.moveToFirst()){
			do{
				sommeKcalPlatComplement = cursKcalPlatComplement.getInt(0);
			}while(cursKcalPlatComplement.moveToNext());
		}
		
		//Entree ---------------------
		Cursor cursSelectionEntree = db.rawQuery("SELECT nom, calories FROM Plats"
												+" WHERE type='Entree'"
												+" AND calories<=("+caloriesRecommandees+"-"+sommeKcalPlatComplement+")/2"
														+ " ORDER BY calories DESC;" , null);
		String nomEntreeSelection = "";
		int caloriesEntreeSelection = 0;
		if(cursSelectionEntree.moveToFirst()){
			nomEntreeSelection = cursSelectionEntree.getString(0);
			caloriesEntreeSelection = cursSelectionEntree.getInt(1);;
		}
		tvEntree.setText(nomEntreeSelection);
		this.entreeProposee = nomEntreeSelection;
		
		//Dessert --------------------
		Cursor cursSelectionDessert = db.rawQuery("SELECT nom, calories FROM Plats"
										+" WHERE type='Dessert'"
										+" AND calories<=("+caloriesRecommandees+"-"+sommeKcalPlatComplement+"-"+caloriesEntreeSelection+")"
										+ " ORDER BY calories DESC;" , null);
		String nomDessertSelection = "";
		int caloriesDessertSelection = 0;
		if(cursSelectionDessert.moveToFirst()){
			nomDessertSelection = cursSelectionDessert.getString(0);
			caloriesDessertSelection = cursSelectionDessert.getInt(1);;
		}	
		tvDessert.setText(nomDessertSelection);
		this.dessertPropose = nomDessertSelection;
		
		//Affichage calories APRES proposition
		TextView sommeApres = (TextView) findViewById(R.id.textView2);
		sommeApres.setText("Calories après "+(sommeKcalPlatComplement+caloriesEntreeSelection+caloriesDessertSelection));
		
		ProgressBar pb2 = (ProgressBar) findViewById(R.id.progressBar1);
		pb2.setMax(1820);
		pb2.setProgress(sommeKcalPlatComplement+caloriesEntreeSelection+caloriesDessertSelection);
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
        	Intent intent = new Intent(PropositionActivity.this, SuiviActivity.class);
        	startActivity(intent);
        }
		return super.onOptionsItemSelected(item);
	}
	
	public void clicAccepter(View v){
		Intent intent = new Intent(PropositionActivity.this, RepasActivity.class);
		intent.putExtra("Entree", this.entreeProposee);
		intent.putExtra("Plat", this.platPropose);
		intent.putExtra("Complement", this.complementPropose);
		intent.putExtra("Dessert", this.dessertPropose);
		startActivity(intent);
	}
	
	public void clicRefuser(View v){
		Intent intent = new Intent(PropositionActivity.this, PlatActivity.class);
		intent.putExtra("entreeProposee", this.entreeProposee);
		intent.putExtra("platPropose", this.platPropose);
		intent.putExtra("complementPropose", this.complementPropose);
		intent.putExtra("dessertPropose", this.dessertPropose);
		setResult(1, intent);
		finish();
	}
}

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_proposition);
		
		TextView entree = (TextView) findViewById(R.id.textView4);
		String nomEntree = getIntent().getStringExtra("Entree");
		entree.setText(nomEntree);
		
		TextView plat = (TextView) findViewById(R.id.textView5);
		String nomPlat = getIntent().getStringExtra("Plat");
		plat.setText(nomPlat);
		
		TextView complement = (TextView) findViewById(R.id.textView6);
		String nomComplement = getIntent().getStringExtra("Complement");
		complement.setText(nomComplement);
		
		TextView dessert = (TextView) findViewById(R.id.textView7);
		String nomDessert = getIntent().getStringExtra("Dessert");
		dessert.setText(nomDessert);
		
		PlatsDbHelper bdd = new PlatsDbHelper(this);
		SQLiteDatabase db = bdd.getReadableDatabase();
		Cursor cursKcalRepas = db.rawQuery("SELECT SUM(calories) FROM Plats WHERE nom='"+nomEntree+"'"
																			+" OR nom='"+nomPlat+"'"
																			+" OR nom='"+nomComplement+"'"
																			+" OR nom='"+nomDessert+"';", null);
		int sommeKcal = 0;
		if(cursKcalRepas.moveToFirst()){
			do{
				sommeKcal = cursKcalRepas.getInt(0);
			}while(cursKcalRepas.moveToNext());
		}
		
		TextView somme = (TextView) findViewById(R.id.textView2);
		somme.setText(""+sommeKcal);
		
		ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar1);
		pb.setMax(1820);
		pb.setProgress(sommeKcal);
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
		startActivity(intent);
	}
	
	public void clicRefuser(View v){
		Intent intent = new Intent(PropositionActivity.this, PlatActivity.class);
		startActivity(intent);
	}
}

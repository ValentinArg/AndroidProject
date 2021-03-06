package iut.tp.projets4;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class RepasActivity extends Activity {

	String entree, plat, complement, dessert; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_repas);
		
		entree = getIntent().getStringExtra("Entree");
		plat = getIntent().getStringExtra("Plat");
		complement = getIntent().getStringExtra("Complement");
		dessert = getIntent().getStringExtra("Dessert");
		
		TextView tvEntree = (TextView) findViewById(R.id.textView2);
		tvEntree.setText("Entr�e : "+entree);
		TextView tvPlat = (TextView) findViewById(R.id.textView3);
		tvPlat.setText("Plat : "+plat);
		TextView tvComplement = (TextView) findViewById(R.id.textView4);
		tvComplement.setText("Complement : "+complement);
		TextView tvDessert = (TextView) findViewById(R.id.textView5);
		tvDessert.setText("Dessert : "+dessert);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.repas, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_apropos) {
			Intent intent = new Intent(RepasActivity.this, AproposActivity.class);
        	startActivity(intent);
		}else if (id == R.id.action_suivi){
        	Intent intent = new Intent(RepasActivity.this, SuiviActivity.class);
        	startActivity(intent);
        }else if (id == R.id.action_accueil) {
			Intent intent = new Intent(RepasActivity.this, MainActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void clicEnregistrer (View v){
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		String formattedDate = df.format(c.getTime());
		
		PlatsDbHelper bdd = new PlatsDbHelper(this);
		SQLiteDatabase db = bdd.getWritableDatabase();
		
		//calcul de la somme des calories
		Cursor cursKcalRepas = db.rawQuery("SELECT SUM(calories) FROM Plats WHERE nom='"+entree+"'"
				+" OR nom='"+plat+"'"
				+" OR nom='"+complement+"'"
				+" OR nom='"+dessert+"';", null);
		int sommeKcal = 0;
		if(cursKcalRepas.moveToFirst()){
			sommeKcal = cursKcalRepas.getInt(0);
		}
		
		//r�cup�rer les plats enregistr�s, la somme des calories, la date syst�me
		db.execSQL("INSERT INTO Repas(plats, calories, date) VALUES('"+entree+" - "+plat+" - "+complement+" - "+dessert+"',"+sommeKcal+", '"+formattedDate+"');");
		
		Toast.makeText(this, R.string.toastEnregistre, Toast.LENGTH_SHORT).show();
	}
}

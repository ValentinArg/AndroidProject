package iut.tp.projets4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class PlatActivity extends Activity {
	
	private int TAILLE_MAX;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plat);
		
		/*String[] tabEntree = new String[TAILLE_MAX];
		tabEntree[0] = "Fromage";
		tabEntree[1] = "Salade";
        Spinner spinnerEntree = (Spinner) findViewById(R.id.spinnerEntree);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, tabEntree);
        spinnerEntree.setAdapter(adapter);*/
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

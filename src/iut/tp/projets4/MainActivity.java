package iut.tp.projets4;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
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
        	Intent intent = new Intent(MainActivity.this, SuiviActivity.class);
        	startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void clicValider(View v){
    	//transmission taille et poids
    	EditText et1 = (EditText) findViewById(R.id.editText1);
    	String valeurTaille = et1.getText().toString();
    	EditText et2 = (EditText) findViewById(R.id.editText2);
    	String valeurPoids = et2.getText().toString();
    	Intent intent = new Intent(MainActivity.this, PlatActivity.class);
    	intent.putExtra("taille", valeurTaille);
    	intent.putExtra("poids", valeurPoids);
    	
    	//transmissions infos adulte/enfant, homme/femme
    	RadioGroup rgSexe = (RadioGroup) findViewById(R.id.radioGroup1);
    	String sexe = "";
    	if(rgSexe.getCheckedRadioButtonId() == R.id.radio0){
    		sexe = "femme";
    	}else{
    		sexe = "homme";
    	}
    	RadioGroup rgAge = (RadioGroup) findViewById(R.id.radioGroup2);
    	String age = "";
    	if(rgAge.getCheckedRadioButtonId() == R.id.radio2){
    		age = "enfant";
    	}else{
    		age = "adulte";
    	}
    	intent.putExtra("sexe", sexe);
    	intent.putExtra("age", age);
    	startActivity(intent);
    }
    
}

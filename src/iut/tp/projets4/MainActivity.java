package iut.tp.projets4;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        TextView tv = (TextView) findViewById(R.id.textView1);
        PlatsDbHelper bdd = new PlatsDbHelper(this);
        SQLiteDatabase db = bdd.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT COUNT(*) FROM Plats WHERE nom=?", new String[]{"Tagliatelles"});
        while(c.moveToNext()){
        	tv.setText(""+c.getInt(0));
        }	
        c.close();
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
    
    public void clicBtn(View v){
    	PlatsDbHelper bdd = new PlatsDbHelper(this);
    	SQLiteDatabase db = bdd.getWritableDatabase(); 
    	ContentValues values = new ContentValues();
    	values.put("id", 2);
    	values.put("nom", "Tagliatelles");
    	values.put("calories", 500);
    	db.insert("Plats", null, values);
    	db.close();
    }
}

package iut.tp.projets4;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlatsDbHelper extends SQLiteOpenHelper{
	
		public static final int DATABASE_VERSION = 1;
		public static final String DATABASE_NAME="plats.db";
		
		public final String SQL_CREATE = "CREATE TABLE Plats(id INTEGER PRIMARY KEY, nom TEXT, calories INTEGER);"
				+ " INSERT INTO Plats VALUES(1,'Tagliatelles',300);";
		public final String SQL_DELETE = "DROP TABLE IF EXISTS Plats;";
		
		public PlatsDbHelper(Context context){
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		//M�thode appel�e la premi�re fois pafr l'application, quand aucune bd n'est pr�sente
		public void onCreate(SQLiteDatabase db){
			db.execSQL(SQL_CREATE);
		}
		
		//M�thode appel�e quand une bd est pr�sente sur le tel pour ne pas en g�n�rer une neuve
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
			db.execSQL(SQL_DELETE);
			onCreate(db);
		}
			
		public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
			onUpgrade(db, oldVersion, newVersion);
		}
}

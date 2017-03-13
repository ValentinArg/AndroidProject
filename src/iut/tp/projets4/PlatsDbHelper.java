package iut.tp.projets4;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlatsDbHelper extends SQLiteOpenHelper{
	
		public static final int DATABASE_VERSION = 1;
		public static final String DATABASE_NAME = "plats.db";
		
		public final String SQL_CREATE = "CREATE TABLE Plats(id INTEGER PRIMARY KEY, nom TEXT, calories INTEGER, portion INTEGER, type TEXT);";
				
		public final String SQL_DELETE = "DROP TABLE IF EXISTS Plats;";
		
	
		public PlatsDbHelper(Context context){
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		//Méthode appelée la première fois par l'application, quand aucune bd n'est présente
		public void onCreate(SQLiteDatabase db){
			db.execSQL(SQL_CREATE);
			db.execSQL("INSERT INTO Plats VALUES(1,'Tagliatelles', 215 ,115, 'Plat');");
			db.execSQL("INSERT INTO Plats VALUES(2,'Blanquette de veau', 548, 400 , 'Plat');");
			db.execSQL("INSERT INTO Plats VALUES(3,'Kebab', 740, 360, 'Plat');" );
			db.execSQL("INSERT INTO Plats VALUES(4,'Pizza', 272  , 102 , 'Plat');" );
			db.execSQL("INSERT INTO Plats VALUES(5,'Cassoulet', 487 ,420 , 'Plat');" );
			db.execSQL("INSERT INTO Plats VALUES(6,'Burrito', 326  , 200, 'Plat');" );
			db.execSQL("INSERT INTO Plats VALUES(7,'Choucroute', 142  , 200, 'Plat');");
			db.execSQL("INSERT INTO Plats VALUES(8,'Big Mac', 561  ,219 , 'Plat');" );
			db.execSQL("INSERT INTO Plats VALUES(9,'Tomate Cerise', 100 , 100, 'Entree');" );
			db.execSQL("INSERT INTO Plats VALUES(10,'Salade de thon', 383  , 205, 'Entree');" );
			db.execSQL("INSERT INTO Plats VALUES(11,'Saumon', 367 , 178, 'Entree');");
			db.execSQL("INSERT INTO Plats VALUES(12,'Glace au chocolat', 157 , 72, 'Dessert');" );
			db.execSQL("INSERT INTO Plats VALUES(13,'Gauffre', 103 , 33, 'Dessert');" );
			db.execSQL("INSERT INTO Plats VALUES(14,'Tarte au pomme', 290 , 150, 'Dessert');");
		}
		
		//Méthode appelée quand une bd est présente sur le tel pour ne pas en générer une neuve
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
			db.execSQL(SQL_DELETE);
			onCreate(db);
		}
			
		public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
			onUpgrade(db, oldVersion, newVersion);
		}
}

package iut.tp.projets4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterSuivi extends BaseAdapter{
	
	private Context context;
	private String[] dates;
	private String[] repas;
	
	private static LayoutInflater inflater = null;

	public AdapterSuivi(Context context, String[] dates, String[] repas) {
		this.context = context;
		this.dates = dates;
		this.repas = repas;
		
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return dates.length;
	}

	@Override
	public Object getItem(int position) {
		return this.repas[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View previousView, ViewGroup parent) {
		View previous = previousView;
		
		if(previous == null){
			previous = inflater.inflate(R.layout.ligne_suivi, null);
		}
		
		TextView text = (TextView) previous.findViewById(R.id.dateLigne);
		text.setText(dates[position]);
		
		TextView text2 = (TextView) previous.findViewById(R.id.repasLigne);
		text2.setText(repas[position]);
		
		return previous;
	}

}

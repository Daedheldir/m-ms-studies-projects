package pl.edu.pwr.lab.main_project;

import static androidx.recyclerview.widget.RecyclerView.*;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;


public class ObjectManagerBase<T1> {
	public void addRecyclerViewForNotification(RecyclerView recyclerView) {
		notifiableAdapters.add(recyclerView);
		notifyAllAdapters();
	}
	public void notifyAllAdapters(){
		for (RecyclerView recyclerView : notifiableAdapters){
			if (recyclerView == null) {
				continue;
			}
			recyclerView.getRecycledViewPool().clear();

			if(recyclerView.getLayoutManager() != null)
				 recyclerView.getLayoutManager().removeAllViews();
			if(recyclerView.getAdapter() != null)
				recyclerView.getAdapter().notifyDataSetChanged();
		}
	}
	protected ObjectManagerBase(){
		this(new ArrayList<>());
	}
	protected ObjectManagerBase(ArrayList<T1> places){
		this.obj = places;
		notifiableAdapters = new ArrayList<>();
	}
	public T1 add(T1 place){
		obj.add(place);
		notifyAllAdapters();
		return obj.get(obj.size()-1);
	}
	public void remove(int index){
		obj.remove(index);
	}
	public void set(int index, T1 place){
		obj.set(index, place);
		notifyAllAdapters();
	}

	public T1 get(int index) {
		return obj.get(index);
	}
	public int getCount(){
		return obj.size();
	}


	private final ArrayList<T1> obj;
	private static ArrayList<RecyclerView> notifiableAdapters;
}

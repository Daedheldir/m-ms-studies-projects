package pl.edu.pwr.lab.main_project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import pl.edu.pwr.lab.main_project.accommodations.Accommodation;
import pl.edu.pwr.lab.main_project.accommodations.AccommodationsFragment;
import pl.edu.pwr.lab.main_project.accommodations.AccommodationsManager;
import pl.edu.pwr.lab.main_project.data_generators.DataGenerator;
import pl.edu.pwr.lab.main_project.events.Event;
import pl.edu.pwr.lab.main_project.events.EventsFragment;
import pl.edu.pwr.lab.main_project.events.EventsManager;
import pl.edu.pwr.lab.main_project.guided_tours.Tour;
import pl.edu.pwr.lab.main_project.guided_tours.ToursFragment;
import pl.edu.pwr.lab.main_project.guided_tours.ToursManager;
import pl.edu.pwr.lab.main_project.places.Place;
import pl.edu.pwr.lab.main_project.places.PlacesFragment;
import pl.edu.pwr.lab.main_project.places.PlacesManager;

//Functional Requirements
//	1. App should provide information about specific city including events( min. 3), places (min 5) and accommodation(min 3).
//	2. App should provide at least 3 city guided tours
//	3. Events module should include:
//		a. Name of event
//		b. Event description
//		c. Place of event (with map/direction)
//		d. Images (up to 3)
//	4. Places module should include:
//		a. Name of place
//		b. Description
//		c. Voice description (recording or read by assistant)
//		d. Rate and rating functionality with review description
//		e. Place location (with map/direction)
//		f. Images (up to 5)
//		g. Video (up to 30s)
//	5. Accommodation module should include:
//		a. Name
//		b. Description
//		c. Rate and rating functionality with review description
//		d. Place location (with map/direction)
//		e. Images. (up to 3)
//	6. Guided tour should include
//		a. Name
//		b. Description
//		c. List of places to visit
//		d. Map with tour

public class MainActivity extends FragmentActivity {
	private static class MyPagerAdapter extends FragmentStateAdapter {

		public MyPagerAdapter(FragmentActivity fragment) {
			super(fragment);
		}

		@NonNull
		@Override
		public Fragment createFragment(int position) {
			switch (position){
				case 0:  return EventsFragment.newInstance();
				case 1:  return PlacesFragment.newInstance();
				case 2:  return AccommodationsFragment.newInstance();
				default: return ToursFragment.newInstance();
			}
		}

		@Override
		public int getItemCount() {
			return 4;
		}
	}
	private ViewPager2 viewPager;
	private FragmentStateAdapter pagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		AccommodationsManager accommodationsManager = AccommodationsManager.getInstance();
		PlacesManager placesManager = PlacesManager.getInstance();
		EventsManager eventsManager = EventsManager.getInstance();
		ToursManager toursManager = ToursManager.getInstance();

		for(int i = 0; i < 5; ++i){
			Pair<Accommodation, Place> accommodationPlacePair = DataGenerator.createRandomAccomodationPlacePair();
			placesManager.add(accommodationPlacePair.second);
			accommodationsManager.add(accommodationPlacePair.first);
		}

		for(int i = 0; i < 5; ++i){
			Pair<Event, Place> eventPlacePair = DataGenerator.createRandomEventPlacePair();
			placesManager.add(eventPlacePair.second);
			eventsManager.add(eventPlacePair.first);
		}

		for(int i = 0; i < 5; ++i){
			Place place = DataGenerator.createRandomPlace();
			placesManager.add(place);
		}

		for(int i =0; i < 5; ++i){
			Tour tour = DataGenerator.createRandomTour();
			toursManager.add(tour);
		}
		viewPager = findViewById(R.id.viewPager);
		pagerAdapter = new MyPagerAdapter(this);
		viewPager.setAdapter(pagerAdapter);
		String[] tabLayoutTitlesLUT = {
			"Events",
			"Places",
			"Accommodation",
			"Tours"
		};
		TabLayout tabLayout = findViewById(R.id.tab_layout);
		new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(tabLayoutTitlesLUT[position])
		).attach();

	}

	@Override
	public void onBackPressed() {
		if (viewPager.getCurrentItem() == 0) {
			// If the user is currently looking at the first step, allow the system to handle the
			// Back button. This calls finish() on this activity and pops the back stack.
			super.onBackPressed();
		} else {
			// Otherwise, select the previous step.
			viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
		}
	}
}
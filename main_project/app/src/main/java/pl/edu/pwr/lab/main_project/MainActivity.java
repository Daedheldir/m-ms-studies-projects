package pl.edu.pwr.lab.main_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

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
			return new ToursFragment();
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

		viewPager = findViewById(R.id.viewPager);
		pagerAdapter = new MyPagerAdapter(this);
		viewPager.setAdapter(pagerAdapter);

		TabLayout tabLayout = findViewById(R.id.tab_layout);
		new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText("OBJECT " + (position + 1))
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
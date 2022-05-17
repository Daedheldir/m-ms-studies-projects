package pl.edu.pwr.lab.main_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
}
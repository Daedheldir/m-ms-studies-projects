package pl.edu.pwr.lab3.i236468;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;

public class MainActivity extends AppCompatActivity {
	private ActivityResultLauncher<Intent> startForResultFromGallery;
	private ImageView imageView;
	private TextView imageTagText;
	private ImageLabeler autoMLImageLabeler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		startForResultFromGallery  = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
				result -> {
					if (result.getResultCode() == Activity.RESULT_OK) {
						Bitmap bitmap = getImageFromData(result);
						if(bitmap != null) {
							imageView.setImageBitmap(bitmap);
							processImageTagging(bitmap);
						}
					}
				});

		ImageLabelerOptions  labelerOptions = new ImageLabelerOptions.Builder().setConfidenceThreshold(0.7F).build();
		autoMLImageLabeler = ImageLabeling.getClient(labelerOptions);

		Button button = findViewById(R.id.button);
		button.setOnClickListener(v-> pickImage());

		imageView = findViewById(R.id.mainImageView);
		imageTagText = findViewById(R.id.textView);
	}
	@Override
	protected void onDestroy(){
		autoMLImageLabeler.close();
		super.onDestroy();
	}
	private Bitmap getImageFromData(ActivityResult result){
		try {
			if (result.getData() != null) {
				Uri selectedImageUri = result.getData().getData();
				return BitmapFactory.decodeStream(getBaseContext().getContentResolver().openInputStream(selectedImageUri));
			}
		} catch (Exception exception) {
			Log.d("TAG", "" + exception.getLocalizedMessage());
		}
		return null;
	}

	private void processImageTagging(Bitmap bitmap){
		InputImage image = InputImage.fromBitmap(bitmap, 0);

		autoMLImageLabeler.process(image).addOnSuccessListener(imageLabels -> {
			StringBuilder tag = new StringBuilder();
			for (ImageLabel label : imageLabels) {
				tag.append(String.format("%s - %.2f %c\n", label.getText(), label.getConfidence()*100.0f, '%'));
			}
			imageTagText.setText(tag);
		}).addOnFailureListener(e -> {
			Log.wtf("TEST", e);
		});
	}

	private void pickImage(){
		Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startForResultFromGallery.launch(intent);
	}
}
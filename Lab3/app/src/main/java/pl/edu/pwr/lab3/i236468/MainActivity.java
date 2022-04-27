package pl.edu.pwr.lab3.i236468;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.exifinterface.media.ExifInterface;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;
import com.google.mlkit.vision.objects.DetectedObject;
import com.google.mlkit.vision.objects.ObjectDetection;
import com.google.mlkit.vision.objects.ObjectDetector;
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/*
Based on above implementation add functionality to:
a) (3 p.) process images from camera
b) (3 p.) add text recognition functionality and print text in similar way as tags in
samples app
c) (4 p.) add object detector functionality and draw them on the image
 */
public class MainActivity extends AppCompatActivity {
	private ActivityResultLauncher<Intent> startForResultFromGallery;
	private ActivityResultLauncher<Intent> startForResultFromCamera;
	private ImageView imageView;
	private TextView imageTagText;
	private TextView foundTextText;
	private ImageLabeler autoMLImageLabeler;
	private TextRecognizer textRecognizer;
	private ObjectDetector objectDetector;
	private String lastPhotoPath;

	// Storage Permissions
	private static final int REQUEST_EXTERNAL_STORAGE = 1;
	private static final String[] PERMISSIONS_STORAGE = {
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.WRITE_EXTERNAL_STORAGE
	};

	/**
	 * Checks if the app has permission to write to device storage
	 *
	 * If the app does not has permission then the user will be prompted to grant permissions
	 *
	 * @param activity
	 */
	public static void verifyStoragePermissions(Activity activity) {
		// Check if we have write permission
		int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

		if (permission != PackageManager.PERMISSION_GRANTED) {
			// We don't have permission so prompt the user
			ActivityCompat.requestPermissions(
					activity,
					PERMISSIONS_STORAGE,
					REQUEST_EXTERNAL_STORAGE
			);
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		verifyStoragePermissions(this);

		setContentView(R.layout.activity_main);

		startForResultFromGallery = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
				result -> {
					if (result.getResultCode() != Activity.RESULT_OK)
						return;

					Bitmap imageBitmap = getImageFromGalleryData(result);
					if(imageBitmap != null) {
						processResultImage(imageBitmap);
					}
		});
		startForResultFromCamera = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
				result -> {
					if (result.getResultCode() != Activity.RESULT_OK)
						return;

					try {
						File file = new File(lastPhotoPath);
						Uri imageUri = FileProvider.getUriForFile(getApplicationContext(), "pl.edu.pwr.lab3.i236468.fileprovider", file);

						Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

						int rotateImage = getCameraPhotoOrientation(this, imageUri, lastPhotoPath);
						imageView.setRotation(rotateImage);

						if(imageBitmap != null) {
							processResultImage(imageBitmap);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
		});

		ImageLabelerOptions  labelerOptions = new ImageLabelerOptions.Builder().setConfidenceThreshold(0.7F).build();
		autoMLImageLabeler = ImageLabeling.getClient(labelerOptions);
		textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
		// Multiple object detection in static images
		ObjectDetectorOptions objectDetectorOptions =
				new ObjectDetectorOptions.Builder()
						.setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
						.enableMultipleObjects()
						.enableClassification()  // Optional
						.build();
		objectDetector = ObjectDetection.getClient(objectDetectorOptions);

		Button button_openGallery = findViewById(R.id.button_selectImage);
		button_openGallery.setOnClickListener(v-> pickImage());
		Button button_openCamera = findViewById(R.id.button_takePicture);
		button_openCamera.setOnClickListener(v-> openCamera());

		imageView = findViewById(R.id.mainImageView);
		imageTagText = findViewById(R.id.textView_labeling);
		foundTextText = findViewById(R.id.textView_foundText);
	}
	@Override
	protected void onDestroy(){
		autoMLImageLabeler.close();
		textRecognizer.close();
		super.onDestroy();
	}
	private Bitmap getImageFromGalleryData(ActivityResult result){
		try {
			if (result.getData() != null) {
				Uri selectedImageUri = result.getData().getData();
				int rotateImage = getCameraPhotoOrientation(this, selectedImageUri, lastPhotoPath);
				imageView.setRotation(rotateImage);
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
				tag.append(String.format(Locale.getDefault(), "%s - %.2f %c\n", label.getText(), label.getConfidence()*100.0f, '%'));
			}
			imageTagText.setText(tag);
		}).addOnFailureListener(e -> Log.wtf("TEST", e));
	}
	private void processImageText(Bitmap bitmap){
		InputImage image = InputImage.fromBitmap(bitmap, 0);

		textRecognizer.process(image)
				.addOnSuccessListener( visionText -> {
					foundTextText.setText(visionText.getText().equals("") ? getResources().getText(R.string.none) : visionText.getText());
				})
				.addOnFailureListener(e -> Log.wtf("TEST", e));
	}
	private void processObjectsInImage(Bitmap bitmap){
		InputImage image = InputImage.fromBitmap(bitmap, 0);

		Paint paint = new Paint();
		Random r = new Random();
		Bitmap tempBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(tempBitmap);
		canvas.drawBitmap(bitmap, 0, 0, null);

		objectDetector.process(image)
				.addOnSuccessListener(
						detectedObjects -> {
							//draw found objects
							for (DetectedObject detectedObject : detectedObjects) {
								Rect boundingBox = detectedObject.getBoundingBox();
								Integer trackingId = detectedObject.getTrackingId();
								int red= r.nextInt(255);
								int green =r.nextInt(255);
								int blue = r.nextInt(255);
								int color = (255 << 24) | (red << 16) | (green << 8) | blue;

								paint.setColor(color);
								paint.setStrokeWidth(5.f);
								paint.setStyle(Paint.Style.STROKE);

								canvas.drawRoundRect(new RectF(boundingBox), 10.0f, 10.0f, paint);

								for (DetectedObject.Label label : detectedObject.getLabels()) {
									float confidence = label.getConfidence();

									String text = String.format(Locale.getDefault(), "%s %.2f%c",label.getText(), 100*confidence, '%');
									float[] rectBottomCenter = {boundingBox.centerX(), boundingBox.bottom + 50};
									paint.setTextAlign(Paint.Align.CENTER);
									paint.setStyle(Paint.Style.FILL);
									paint.setTextSize(80);
									canvas.drawText(text, rectBottomCenter[0], rectBottomCenter[1], paint);
									paint.setStyle(Paint.Style.STROKE);
									paint.setStrokeWidth(4.f);
									paint.setColor(255<<24);
									canvas.drawText(text, rectBottomCenter[0], rectBottomCenter[1], paint);
									break;
								}
							}
						})
				.addOnFailureListener(
						e -> Log.wtf("TEST", e));

		imageView.setImageDrawable(new BitmapDrawable(getResources(), tempBitmap));
	}
	private void pickImage(){
		Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startForResultFromGallery.launch(intent);
	}

	private void openCamera(){
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			// Create the File where the photo should go
			Uri photoFile = null;
			try {
				photoFile = createImageFile();
			} catch (IOException ex) {
				// Error occurred while creating the File
				Log.i("TAG", "IOException when creating file: " + ex.getLocalizedMessage());
				ex.printStackTrace();
			}
			// Continue only if the File was successfully created
			if (photoFile != null) {
				takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoFile);
				startForResultFromCamera.launch(takePictureIntent);
			}
		}
	}
	public int getCameraPhotoOrientation(Context context, Uri imageUri,
										 String imagePath) {
		int rotate = 0;
		try {
			context.getContentResolver().notifyChange(imageUri, null);
			File imageFile = new File(imagePath);
			ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
			int orientation = exif.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);

			switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_270:
					rotate = 270;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					rotate = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_90:
					rotate = 90;
					break;
			}

			Log.i("RotateImage", "Exif orientation: " + orientation);
			Log.i("RotateImage", "Rotate value: " + rotate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rotate;
	}

	private Uri createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "img_" + timeStamp + "_";
		File image = new File(getExternalFilesDir("my_images"),
				imageFileName+".jpg"
		);
		lastPhotoPath = image.getAbsolutePath();
		return FileProvider.getUriForFile(getApplicationContext(),"pl.edu.pwr.lab3.i236468.fileprovider" ,image);
	}

	private void processResultImage(Bitmap imageBitmap) {
		if (imageBitmap == null)
			return;

		imageView.setImageBitmap(imageBitmap);
		processImageTagging(imageBitmap);
		processImageText(imageBitmap);
		processObjectsInImage(imageBitmap);
	}
}
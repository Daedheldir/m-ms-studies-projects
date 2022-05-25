package pl.edu.pwr.lab.main_project;

import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewViewHolder extends RecyclerView.ViewHolder{
	public RecyclerViewViewHolder(View view) {
		super(view);
		placeLayout = view.findViewById(R.id.row_item_linearLayout);
		placeNameText = view.findViewById(R.id.row_item_name_textView);
		placeRatingBar = view.findViewById(R.id.row_item_ratingBar);
		placeDescriptionText = view.findViewById(R.id.row_item_description_textView);
		webView = view.findViewById(R.id.row_item_webView);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setUseWideViewPort(true);
	}
	private final LinearLayout placeLayout;
	private final TextView placeNameText;
	private final RatingBar placeRatingBar;
	private final TextView placeDescriptionText;
	private final WebView webView;

	public TextView getNameText() {
		return placeNameText;
	}

	public TextView getDescriptionText() {
		return placeDescriptionText;
	}

	public RatingBar getRatingBar() {
		return placeRatingBar;
	}

	public LinearLayout getLayout(){
		return placeLayout;
	}

	public WebView getWebView() { return webView; }
}

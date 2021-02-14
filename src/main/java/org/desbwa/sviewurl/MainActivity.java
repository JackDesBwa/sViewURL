package org.desbwa.sviewurl;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class MainActivity extends Activity {
	private void open(String url, boolean video) {
		TextView label = new TextView(this);
		label.setText("Open sView with:\n" + url);
		setContentView(label);

		Intent sendIntent = new Intent(Intent.ACTION_SEND);
		sendIntent.setClassName("com.sview", video ? "com.sview.StMovieActivity" : "com.sview.StImageActivity");
		sendIntent.setData(Uri.parse(url)); 
		startActivity(sendIntent);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final String url;
		String source;
		if (Intent.ACTION_SEND.equals(getIntent().getAction())) {
			url = getIntent().getStringExtra(Intent.EXTRA_TEXT);
			source = "Shared URL";
		} else {
			ClipboardManager clip = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
			if (clip != null) {
				ClipData data = clip.getPrimaryClip();
				if (data != null && data.getItemCount() > 0) {
					url = data.getItemAt(0).coerceToText(getApplicationContext()).toString();
				} else {
					url = "";
				}
			} else {
				url = "";
			}
			source = "Clipboard";
		}

		if (url != "") {
			LinearLayout l = new LinearLayout(this);
			l.setOrientation(LinearLayout.VERTICAL);
			setContentView(l);

			Button buttonImage = new Button(this);
			buttonImage.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 0.5f));
			buttonImage.setText(source + "\n↓\nsView Image");
			buttonImage.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					open(url, false);
				}
			});
			l.addView(buttonImage);

			Button buttonVideo = new Button(this);
			buttonVideo.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 0.5f));
			buttonVideo.setText(source + "\n↓\nsView Video");
			buttonVideo.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					open(url, true);
				}
			});
			l.addView(buttonVideo);
		} else {
			TextView label = new TextView(this);
			label.setText("To use this app, add an URL in your clipboard and come back, or share an URL directly with the app.");
			setContentView(label);
		}
	}
}

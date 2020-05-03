package org.desbwa.sviewurl;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	private void open(String url) {
		TextView label = new TextView(this);
		label.setText("Open sView with:\n" + url);
		setContentView(label);

		Intent sendIntent = new Intent(Intent.ACTION_SEND);
		sendIntent.setClassName("com.sview", "com.sview.StMovieActivity");
		sendIntent.setData(Uri.parse(url)); 
		startActivity(sendIntent);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (Intent.ACTION_SEND.equals(getIntent().getAction())) {
			open(getIntent().getStringExtra(Intent.EXTRA_TEXT));
		} else {
			Button button = new Button(this);
			button.setText("Open from clipboard");
			button.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					ClipboardManager clip = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
					open(clip.getPrimaryClip().getItemAt(0).coerceToText(getApplicationContext()).toString());
				}
			});
			setContentView(button);
		}

	}
}

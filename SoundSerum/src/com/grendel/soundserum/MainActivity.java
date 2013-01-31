package com.grendel.soundserum;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends Activity {

	public boolean isPaused;
	public Context context;
	public static TextView songTitle, songCreator;
	public ImageButton playButton, nextButton, previousButton, downloadButton, exitButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = getApplicationContext();
		
		// API Level 11 and up does not allow network actions in the main UI. We need to change that.
        // Should probably change this later, because you really aren't supposed to. It's a small file though.
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        isPaused = false;
        
        /*
         * Text
         */
        songTitle = (TextView) findViewById(R.id.currentSong);
        songCreator = (TextView) findViewById(R.id.currentArtist);
        
        /*
         * Set buttons and listeners
         */
        playButton = (ImageButton) findViewById(R.id.playButton);
        nextButton = (ImageButton) findViewById(R.id.nextButton);
        previousButton = (ImageButton) findViewById(R.id.previousButton);
        downloadButton = (ImageButton) findViewById(R.id.downloadButton);
        
        playButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(context, MainService.class);
				i.putExtra("PAUSE_SONG", true);
				startService(i);
				if (isPaused) { 
					playButton.setBackgroundResource(R.drawable.sspause);
					isPaused = false;
				}
				else {
					playButton.setBackgroundResource(R.drawable.ssplay);
					isPaused = true;
				}
			}
		});
		
		previousButton.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN) {
					previousButton.setBackgroundResource(R.drawable.previouspress);
				}
				else if (event.getAction() == MotionEvent.ACTION_UP) {
					previousButton.setBackgroundResource(R.drawable.ssback);
					playButton.setBackgroundResource(R.drawable.sspause);
					Intent i = new Intent(context, MainService.class);
					i.putExtra("PREVIOUS_SONG", true);
					startService(i);
				}
				return false;
			}
		});
		
		nextButton.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN) {
					nextButton.setBackgroundResource(R.drawable.nextpress);
				}
				else if (event.getAction() == MotionEvent.ACTION_UP) {
					nextButton.setBackgroundResource(R.drawable.ssnext);
					playButton.setBackgroundResource(R.drawable.sspause);
					Intent i = new Intent(context, MainService.class);
					i.putExtra("NEXT_SONG", true);
					startService(i);
				}
				return false;
			}
		});
		
		downloadButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(context, MainService.class);
				i.putExtra("DOWNLOAD_SONG", true);
				startService(i);
			}
		});
	}
	
	@Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
        
        startService(new Intent(this, MainService.class));
    }
	
    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
        
        stopService(new Intent(this, MainService.class));
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}

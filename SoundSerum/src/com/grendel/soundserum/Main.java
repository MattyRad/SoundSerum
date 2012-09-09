package com.grendel.soundserum;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity {
	
	// TODO
	// Voting mechanism
	// Previous song
	// Exit option to kill app
	// Seek song bar
	// Wave control
	
	Context context;
	ImageButton playButton;
	ImageButton nextButton;
	ImageButton previousButton;
	ImageButton downloadButton;
	Button exitButton;
	Intent serviceIntent;
	//SeekBar seekSongTime;
	
	static TextView songTitle;
	static TextView songCreator;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = getApplicationContext();
        
        // API Level 11 and up does not allow network actions in the main UI. We need to change that.
        // Should probably change this later, because you really aren't supposed to. It's a small file though.
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        
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
        exitButton = (Button) findViewById(R.id.exitButton);
        //seekSongTime = (SeekBar) findViewById(R.id.seekSongTime);
        
		playButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				boolean paused = MusicService.pause();
				if (paused) playButton.setBackgroundResource(R.drawable.ssplay);
				else playButton.setBackgroundResource(R.drawable.sspause);
			}
		});
			        
		previousButton.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN) {
					previousButton.setBackgroundResource(R.drawable.previouspress);
				}
				else if (event.getAction() == MotionEvent.ACTION_UP) {
					previousButton.setBackgroundResource(R.drawable.ssback);
					MusicService.previous();
				}
				return false;
			}
		});
		
		nextButton.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN) {
					nextButton.setBackgroundResource(R.drawable.nextpress);
				}
				else if (event.getAction() == MotionEvent.ACTION_UP) {
					nextButton.setBackgroundResource(R.drawable.ssnext);
					MusicService.next();
				}
				return false;
			}
		});
		
		downloadButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                DownloadManager.Request request = new DownloadManager.Request(MusicService.songURL);
                request.setDescription(MusicService.creator);
                request.setTitle(MusicService.title);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, MusicService.title);

                // get download service and enqueue file
                DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                manager.enqueue(request);
                Toast.makeText(context, "Downloading", Toast.LENGTH_SHORT).show();
            }
		});
		
		exitButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MusicService.pause();
				stopService(serviceIntent);
				finish();
			}
		});
		
		/*seekSongTime.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			public void onStopTrackingTouch(SeekBar seekBar) {
				//touched = false;
				//if ( mediaReady == true )
				//mediaplayer.seekTo(seekSongTime.getProgress());
				//MusicService.setSongTime(seekSongTime.getProgress());
			}
			
			public void onStartTrackingTouch(SeekBar seekBar) {
				//touched = true;
			}
			
			public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {}
		});*/
        
        /*
         * Start the music service
         */
        if ( !isMusicServiceRunning() ) {
        	serviceIntent = new Intent(this, MusicService.class);
        	startService(serviceIntent);
        }
        else {
        	updateText();
        }
    }
    
    /*
     * I jacked this method from this website:http://www.mobile-web-consulting.de/post/5272654457/android-check-if-a-service-is-running
     * There's probably a more proper way to handle activity-service interaction, but at this point I don't care.
     */
    private boolean isMusicServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("com.grendel.soundserum.MusicService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    
    public static void updateText(){
    	songTitle.setText(MusicService.title);
    	songCreator.setText(MusicService.creator);
    }
}
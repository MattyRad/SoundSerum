package com.grendel.soundserum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity {
	
	// TODO
	// Put in voting mechanism
	// Put in menu, with exit option to kill app
	// Clean up the main layout, it looks cheap
	// Currently every piece of song information is a global var, which is a messy no-no

	// Music objects
	private MediaPlayer mediaplayer;
    private Uri songURL, previousSongURL;
    private String id, recent, hash, location, creator, title, score;
    
    // JSON Objects
    private String raw_json;
    private JSONObject json;
    private JSONObject songInfo;
    private JSONArray tracks;
    
    // Text objects
    private TextView currentSongText, currentArtistText;
    
    // Thread/Race-condition blockers
    private Boolean touched = false; // boolean for whether seek-bar is touched, needed to block race conditions for threads
    private Boolean mediaReady = false; // boolean to indicate whether media actions such as pause and nextSong are available
    
    // Other
    private Context context;
    private Random random = new Random();
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = getApplicationContext();
        
        // API Level 11 and up DOES NOT ALLOW NETWORK ACTIONS IN THE MAIN UI. We need to change that.
        // Should probably change this later, because you really aren't supposed to. It's a small file though
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
        // =========================================
        // INIT
        // =========================================
        
        // Get the online playlist
        raw_json = getPlaylist();
        
        // Put the playlist on a JSON object
        try {
			json = new JSONObject(raw_json);
			tracks = json.getJSONArray("tracks");
		} catch (JSONException e) {
			Toast.makeText(context, "Could not store playlist... could be corrupt", 5).show();
			e.printStackTrace();
		}
        
        // Music Objects
    	mediaplayer = new MediaPlayer();
    	getNewSongInfo();
        
        // Buttons
        final ImageButton playButton = (ImageButton) findViewById(R.id.playButton);
        final ImageButton nextButton = (ImageButton) findViewById(R.id.nextButton);
        final ImageButton previousButton = (ImageButton) findViewById(R.id.previousButton);
        final ImageButton downloadButton = (ImageButton) findViewById(R.id.downloadButton);
        final SeekBar seekSongTime = (SeekBar) findViewById(R.id.seekSongTime);
	        
        // Text Items
        currentSongText = (TextView) findViewById(R.id.currentSong);
        currentArtistText = (TextView) findViewById(R.id.currentArtist);
        
        // =========================================
        // DEFAULT ACTIONS
        // =========================================
        
        loadSong();
        
        // =========================================
        // EVENT LISTENERS
        // =========================================
        
        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	if ( mediaplayer.isPlaying()) {
            		mediaplayer.pause();
            		playButton.setBackgroundResource(R.drawable.ssplay);
            	}
            	else if ( mediaReady == true ){
            		mediaplayer.start();
            		playButton.setBackgroundResource(R.drawable.sspause);
            	}
            }
        });
        
        nextButton.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN) {
		            nextButton.setBackgroundResource(R.drawable.nextpress);
		        } else if (event.getAction() == MotionEvent.ACTION_UP) {
		        	nextButton.setBackgroundResource(R.drawable.ssnext);
		        	getNewSongInfo();
	                loadSong();
		        }
				return false;
			}
		});
        
        previousButton.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN) {
		            previousButton.setBackgroundResource(R.drawable.previouspress);
		        } else if (event.getAction() == MotionEvent.ACTION_UP) {
		        	previousButton.setBackgroundResource(R.drawable.ssback);
		        	songURL = previousSongURL;
	                loadSong();
		        }
				return false;
			}
		});
        
        downloadButton.setOnClickListener(new View.OnClickListener() {
        	
            public void onClick(View v) {
                DownloadManager.Request request = new DownloadManager.Request(songURL);
                request.setDescription(creator);
                request.setTitle(title);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title);

                // get download service and enqueue file
                DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                manager.enqueue(request);
                Toast.makeText(context, "Downloading", 5).show();
            }
        });
        
        mediaplayer.setOnPreparedListener(new OnPreparedListener() {
        	
        	// Once the mediaplayer is ready, set the max value for the seekbar and
        	// start a new thread that constantly updates the seekbar time
        	
        	// NOTE: EFFICIENCY??? Am I starting many new thread here? Come back to this
        	// It may be extremely inefficient. Does the garbage collector handle this?
			
			@Override
			public void onPrepared(MediaPlayer arg0) {
				seekSongTime.setMax(mediaplayer.getDuration());
				mediaReady = true;
				new Thread(new Runnable() {
			        public void run() {
			        	while ( mediaplayer.isPlaying() && touched == false) {
			        		seekSongTime.setProgress(mediaplayer.getCurrentPosition());
			        	}
			        }
			    }).start();
			}
		});
        
        mediaplayer.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				getNewSongInfo();
				loadSong();
			}
		});
        
        seekSongTime.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				touched = false;
				if ( mediaReady == true )
					mediaplayer.seekTo(seekSongTime.getProgress());
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				touched = true;
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
			}
		});
    }
    
    public void loadSong(){
    	if ( mediaplayer.isPlaying() ) {
    		mediaplayer.stop();
    	}
    	mediaReady = false;
    	mediaplayer.reset();
        try {
			mediaplayer.setDataSource(this, songURL);
		} catch (IllegalArgumentException e) {
			Toast.makeText(context, "Something's wrong with the G Diffuser", 5).show();
			e.printStackTrace();
		} catch (SecurityException e) {
			Toast.makeText(context, "Something's wrong with the G Diffuser", 5).show();
			e.printStackTrace();
		} catch (IllegalStateException e) {
			Toast.makeText(context, "Something's wrong with the G Diffuser", 5).show();
			e.printStackTrace();
		} catch (IOException e) {
			Toast.makeText(context, "Something's wrong with the G Diffuser", 5).show();
			e.printStackTrace();
		}
        
        mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        
        new Thread(new Runnable() { // This might also be inefficient
			
			@Override
			public void run() {
				try {
					mediaplayer.prepare();
					mediaplayer.start();
				}
				catch (Exception e) {
					Toast.makeText(context, "Something's wrong with the G Diffuser", 5).show();
				}
			}
		}).start();
        
        setNowPlayingText();
    }
    
    public void getNewSongInfo(){
    	previousSongURL = songURL;
    	try {
			songInfo = tracks.getJSONObject(random.nextInt(848));
			id = songInfo.getString("id");
			recent = songInfo.getString("recent");
			hash = songInfo.getString("hash");
			location = songInfo.getString("location");
			creator = songInfo.getString("creator");
			title = songInfo.getString("title");
			score = songInfo.getString("score");
			songURL = Uri.parse("http://www.soundserum.com/mp3/" + location);
		} catch (JSONException e) {
			Toast.makeText(context, "Tried to load a song that doesn't exist", 5).show();
			e.printStackTrace();
		}
    }
    
    public void setNowPlayingText(){
        currentSongText.setText(title);
		currentArtistText.setText(creator);
    }
    
    public String getPlaylist() {
    	String html = "";
    	try {
	        HttpClient client = new DefaultHttpClient();
	        HttpGet request = new HttpGet("http://www.soundserum.com/playlist.php");
	        HttpResponse response = client.execute(request);
	        
	        InputStream in = response.getEntity().getContent();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	        StringBuilder str = new StringBuilder();
	        String line = null;
	        while((line = reader.readLine()) != null)
	        {
	            str.append(line);
	        }
	        in.close();
	        html = str.toString();
        }
        catch (IOException e) {
        	Toast.makeText(context, "Could get playlist... SoundSerum might be down", 5).show();
			e.printStackTrace();
        }
    	return html;
    }
}
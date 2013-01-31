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

import android.app.DownloadManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

public class MainService extends Service {
	
	public String id, recent, hash, location, creator, title, score;
	public Uri songURL;
	public JSONObject songInfo, json;
	public JSONArray tracks;
	public Random random;
	public MediaPlayer player;
	public PendingIntent mNotificationAction;
	public Context context;
	
	public boolean isPlaying;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		context = getApplicationContext();
		random = new Random();
		player = new MediaPlayer();
		isPlaying = false;
		
		// Get raw JSON-formatted playlist as string
		String raw_json_playlist = getPlaylist();
		
		// Covert string to JSON Object list of tracks
		try {
			json = new JSONObject(raw_json_playlist);
			tracks = json.getJSONArray("tracks");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
        player.setOnPreparedListener(new OnPreparedListener() {

			public void onPrepared(MediaPlayer mp) {
				mp.start();
				isPlaying = true;
				setNotification();
			}
		});
        
        player.setOnCompletionListener(new OnCompletionListener() {

			public void onCompletion(MediaPlayer mp) {
				isPlaying = false;
				loadNewSong();
				setNotification();
			}
		});
		
        loadNewSong();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
	    // We want this service to continue running until it is explicitly
	    // stopped, so return sticky.
		
		boolean NEXT_SONG = intent.getBooleanExtra("NEXT_SONG", false);
		boolean PREVIOUS_SONG = intent.getBooleanExtra("PREVIOUS_SONG", false);
		boolean PAUSE_SONG = intent.getBooleanExtra("PAUSE_SONG", false);
		boolean DOWNLOAD_SONG = intent.getBooleanExtra("DOWNLOAD_SONG", false);
		
		if (NEXT_SONG == true){
			next();
		}
		else if (PREVIOUS_SONG == true){
			previous();
		}
		else if (PAUSE_SONG == true){
			pause();
		}
		else if (DOWNLOAD_SONG == true){
			downloadSong();
		}
		
	    return START_NOT_STICKY;
	}
	
	public void setNotification() {
		Intent notificationIntent = new Intent(context, MainService.class);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		Intent aintent = new Intent(this, MainActivity.class);
		aintent.setAction(Intent.ACTION_MAIN);
		aintent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		aintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT );
		mNotificationAction = PendingIntent.getActivity(this, 0, aintent, 0);
	    Notification notification = new Notification();
	    notification.tickerText = title;
	    notification.icon = R.drawable.icon;
	    notification.flags |= Notification.FLAG_ONGOING_EVENT;
	    notification.setLatestEventInfo(context, title, creator, mNotificationAction);
	    startForeground(5411, notification);
	}
	
	public boolean pause(){
		if (isPlaying) {
			stopForeground(true); 
			player.pause();
			isPlaying = false;
		}
		else {
			setNotification();
			player.start();
			isPlaying = true;
		}
		return true;
	}
	
	public void loadNewSong(){
		getRandomSong();
		MainActivity.songTitle.setText(title);
		MainActivity.songCreator.setText(creator);
		play();
	}
	
	public void play(){
	    player.reset();
        try {
        	player.setDataSource(context, songURL);
		} catch (IllegalArgumentException e) {
			Toast.makeText(context, "Something's wrong with the G Diffuser", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (SecurityException e) {
			Toast.makeText(context, "Something's wrong with the G Diffuser", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (IllegalStateException e) {
			Toast.makeText(context, "Something's wrong with the G Diffuser", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (IOException e) {
			Toast.makeText(context, "Something's wrong with the G Diffuser", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}

        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.prepareAsync();
	}
	
	public void next(){
		isPlaying = false;
		loadNewSong();
	}
	
	public void previous(){
		//isPlaying = false;
	}
	
	public void getRandomSong(){
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
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
    }
	
	public void downloadSong(){
		DownloadManager.Request request = new DownloadManager.Request(songURL);
        request.setDescription(creator);
        request.setTitle(title);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title);

        // get download service and enqueue file
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
        Toast.makeText(context, "Downloading", Toast.LENGTH_SHORT).show();
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
			e.printStackTrace();
        }
    	return html;
    }

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
    public void onDestroy() {
        player.stop();
        player.release();
    }

}

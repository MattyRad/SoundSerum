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
import android.os.IBinder;
import android.widget.Toast;

public class MusicService extends Service {
	
	public static JSONObject songInfo, json;
	public static JSONArray tracks;
	public static String id, hash, recent, location, creator, title, score;
	public static Uri previousSongURL, songURL;
	public static Random random;
	public static Context context;
	public static MediaPlayer player;
	public PendingIntent mNotificationAction;
	
	@Override
    public void onCreate() {
		context = getApplicationContext();
		random = new Random();
		player = new MediaPlayer();
		songURL = previousSongURL = null;
		
		// Get play-list
        String raw_json_playlist = getPlaylist();
        
        // Put the play-list on a JSON object
        try {
			json = new JSONObject(raw_json_playlist);
			tracks = json.getJSONArray("tracks");
		} catch (JSONException e) {
			Toast.makeText(context, "Could not store playlist... could be corrupt", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
        
        // Get a song
        getNewSongInfo();
        
        // Set onPreparedListener
        player.setOnPreparedListener(new OnPreparedListener() {
			
			public void onPrepared(MediaPlayer mp) {
				mp.start();
				setNotification();
			}
		});
        
        player.setOnCompletionListener(new OnCompletionListener() {
			
			public void onCompletion(MediaPlayer mp) {
				getNewSongInfo();
				play();
				setNotification();
			}
		});
        
    }
	
	@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
		play();
		setNotification();
		
		return startId;
    }
	
	public static void getNewSongInfo(){
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
			Toast.makeText(context, "Tried to load a song that doesn't exist", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
    }
	
	private void setNotification() {
		Intent notificationIntent = new Intent(context, MusicService.class);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		Intent aintent = new Intent(this, Main.class);
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
	
	public static void play(){
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
        Main.updateText();
	}
	
	public static boolean pause(){
		if ( player.isPlaying() ){
			player.pause();
			return true;
		}
		else {
			player.start();
			return false;
		}
	}
	
	public static void next(){
		getNewSongInfo();
		play();
	}
	
	public static void previous(){
		songURL = previousSongURL;
		play();
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
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	/*public static void setSongTime(int position){
	
	}
	
	public static int getSongLength(){
		return player.getDuration();
	}*/
}

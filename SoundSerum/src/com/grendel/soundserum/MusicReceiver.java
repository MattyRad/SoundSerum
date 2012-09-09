package com.grendel.soundserum;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MusicReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		/*if (Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction())) {
			KeyEvent event = intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
			boolean handled = processKey(context, event);
			if (handled && isOrderedBroadcast())
				abortBroadcast();
		}*/
	}
	
	/*public static boolean processKey(Context context, KeyEvent event)
	{
		if (event == null) return false;
		int action = event.getAction();

		switch (event.getKeyCode()) {
			case KeyEvent.KEYCODE_HEADSETHOOK:
			case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
				if (action == KeyEvent.ACTION_DOWN) MusicService.pause();
				break;
			case KeyEvent.KEYCODE_MEDIA_NEXT:
				if (action == KeyEvent.ACTION_DOWN) MusicService.next();
				break;
			default:
				return false;
		}
		
		return true;
	}*/

}

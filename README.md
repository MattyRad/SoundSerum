SoundSerum
==========

Android app to stream music from soundserum.com.

Basically it just has a single View and a single Service: the view has the buttons that send actions to the Service. Should be relatively straightforward.

The only oddball thing is that the two TextViews in the View are declared and accessed statically so the Service can handle it easier.

Notes -
The previousSong button doesn't work yet.
The app is locked in portrait for now. The service restarts when it switches to landscape for some reason.

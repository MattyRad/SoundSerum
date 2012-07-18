package com.grendel.soundserum;

import java.io.IOException;
import java.util.Random;

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
	// Get dynamic song list instead of static
	// Properly extract metadata
	// Put in controls to handle impatient users (namely seekbar impatience)
	
    
    private String[] songs = {"http://www.soundserum.com/mp3/1990-The_Phase.mp3"
 , "http://www.soundserum.com/mp3/Above_APlace-YOU-TEE-IGH.mp3"
 , "http://www.soundserum.com/mp3/Above_the_Clouds-Dj-Immune.mp3"
 , "http://www.soundserum.com/mp3/Above_the_Limit_2-NIGHTkilla.mp3"
 , "http://www.soundserum.com/mp3/Above_the_Sky-Spot1techno.mp3"
 , "http://www.soundserum.com/mp3/Accelerate_3-A_New_Decade.mp3"
 , "http://www.soundserum.com/mp3/Activation-Dj_Jager.mp3"
 , "http://www.soundserum.com/mp3/Adamant-axeFX.mp3"
 , "http://www.soundserum.com/mp3/Adrenaline-axeFX.mp3"
 , "http://www.soundserum.com/mp3/Adrenaline-Goukisan.mp3"
 , "http://www.soundserum.com/mp3/Advance_303-EON.mp3"
 , "http://www.soundserum.com/mp3/Aesthetic_Failure-Elfire.mp3"
 , "http://www.soundserum.com/mp3/Airless-DJ-Immune.mp3"
 , "http://www.soundserum.com/mp3/Air_of_Apollo-Rellika.mp3"
 , "http://www.soundserum.com/mp3/Akibahara-Eology.mp3"
 , "http://www.soundserum.com/mp3/Alchemy-axeFX.mp3"
 , "http://www.soundserum.com/mp3/Alien-xKore_and_Nechura.mp3"
 , "http://www.soundserum.com/mp3/Alls_Lost-dj-Nate.mp3"
 , "http://www.soundserum.com/mp3/All_At_Once-NIGHTkilla.mp3"
 , "http://www.soundserum.com/mp3/All_Systems_Go-Elfire.mp3"
 , "http://www.soundserum.com/mp3/All_the_Kings_Drums-BloomingAudioLife.mp3"
 , "http://www.soundserum.com/mp3/Alpha_Nightfall-Avizura.mp3"
 , "http://www.soundserum.com/mp3/Alpha_Starlight-2DPolygon.mp3"
 , "http://www.soundserum.com/mp3/Altered_Reality-MICHhimself.mp3"
 , "http://www.soundserum.com/mp3/Alternate_Attention-TIMarbury.mp3"
 , "http://www.soundserum.com/mp3/Ambient_Thought-Cloud_Demaniow.mp3"
 , "http://www.soundserum.com/mp3/Android_Tears-SirSirius.mp3"
 , "http://www.soundserum.com/mp3/Angel-BeefourMusic.mp3"
 , "http://www.soundserum.com/mp3/Angel_Of_Destiny-ZeRo_BaSs.mp3"
 , "http://www.soundserum.com/mp3/Annihilation_999-KyvoX.mp3"
 , "http://www.soundserum.com/mp3/Another_Dimension-DJ_Epsilon.mp3"
 , "http://www.soundserum.com/mp3/Anthem_of_Glory-NeXuS.mp3"
 , "http://www.soundserum.com/mp3/Anxiety-ZENON.mp3"
 , "http://www.soundserum.com/mp3/Anxiety_Cure-Approaching_Nirvana.mp3"
 , "http://www.soundserum.com/mp3/Apodis-DJ_Fire-Black.mp3"
 , "http://www.soundserum.com/mp3/Appropriately_Strange-ZENON.mp3"
 , "http://www.soundserum.com/mp3/Are_We_In_Heaven-DJ_BrixX.mp3"
 , "http://www.soundserum.com/mp3/Assimilation-NemesisTheory.mp3"
 , "http://www.soundserum.com/mp3/Asteroid-WildeKeizer.mp3"
 , "http://www.soundserum.com/mp3/As_Long_as_Forever-MrFijiWiji.mp3"
 , "http://www.soundserum.com/mp3/Ather-Kazmo.mp3"
 , "http://www.soundserum.com/mp3/Atomic_Drugs-Speedsound.mp3"
 , "http://www.soundserum.com/mp3/Audio_Adventure-15thDimension.mp3"
 , "http://www.soundserum.com/mp3/Aura-DJ_BrixX.mp3"
 , "http://www.soundserum.com/mp3/Aurora_Theory-Blackhole12.mp3"
 , "http://www.soundserum.com/mp3/Awaken-X_Sentinel.mp3"
 , "http://www.soundserum.com/mp3/Awakening-TIMarbury.mp3"
 , "http://www.soundserum.com/mp3/Axon_Discharge-TranQuillus.mp3"
 , "http://www.soundserum.com/mp3/A_Darker_Heaven-Clayface.mp3"
 , "http://www.soundserum.com/mp3/A_Fraction_of_Light-OMGL33TH4X0R.mp3"
 , "http://www.soundserum.com/mp3/A_Light_in_the_Darkness-Phyrnna.mp3"
 , "http://www.soundserum.com/mp3/A_New_Climate-MrFijiWiji.mp3"
 , "http://www.soundserum.com/mp3/A_New_Energy-F-777.mp3"
 , "http://www.soundserum.com/mp3/A_Question_of_Fate-Dimrain47.mp3"
 , "http://www.soundserum.com/mp3/A_Space_Odyssey-Bafana.mp3"
 , "http://www.soundserum.com/mp3/A_Spectrum_Wind-Dimrain47.mp3"
 , "http://www.soundserum.com/mp3/A_State_of_Revolution-DJ-Babokon.mp3"
 , "http://www.soundserum.com/mp3/A_Travelers_Journey-OpenLight.mp3"
 , "http://www.soundserum.com/mp3/Bad_Memories-DJ_Fire-Black.mp3"
 , "http://www.soundserum.com/mp3/Bass_Cure-Sunshaft.mp3"
 , "http://www.soundserum.com/mp3/Bass_Trip-Spaze.mp3"
 , "http://www.soundserum.com/mp3/Bastion-Rellika.mp3"
 , "http://www.soundserum.com/mp3/Battery-DustDevil.mp3"
 , "http://www.soundserum.com/mp3/Battle-Mikey_Geiger.mp3"
 , "http://www.soundserum.com/mp3/Battlefield_Theme-Mitch_van_Hayden.mp3"
 , "http://www.soundserum.com/mp3/Battlefront-iNfliktioN.mp3"
 , "http://www.soundserum.com/mp3/Beast_Mode-xKore.mp3"
 , "http://www.soundserum.com/mp3/Beautiful_Dreams-PrEmoEffect.mp3"
 , "http://www.soundserum.com/mp3/Beautiful_Reality-shades90.mp3"
 , "http://www.soundserum.com/mp3/Before_Mydnite-F-777.mp3"
 , "http://www.soundserum.com/mp3/Behind_the_Lies-rwert.mp3"
 , "http://www.soundserum.com/mp3/Believe-Etalo.mp3"
 , "http://www.soundserum.com/mp3/Believe_Again-JohnnyFrizz.mp3"
 , "http://www.soundserum.com/mp3/Better_This_Way-MrFijiWiji.mp3"
 , "http://www.soundserum.com/mp3/Beyond_Compulsion-MrFijiWiji.mp3"
 , "http://www.soundserum.com/mp3/Beyond_Infinity-Kieda.mp3"
 , "http://www.soundserum.com/mp3/Beyond_Reach -DJ_BrixX.mp3"
 , "http://www.soundserum.com/mp3/Beyond_the_Light-Spot1techno.mp3"
 , "http://www.soundserum.com/mp3/Be_With_Me-Johaness_Gilther.mp3"
 , "http://www.soundserum.com/mp3/BioSeed-NemesisTheory.mp3"
 , "http://www.soundserum.com/mp3/Birthplace_(Revived)-Neodrummer.mp3"
 , "http://www.soundserum.com/mp3/Bittersweet_Symphony-cornandbeans.mp3"
 , "http://www.soundserum.com/mp3/Blackhole-arjanM.mp3"
 , "http://www.soundserum.com/mp3/Blacklight-NIGHTkilla.mp3"
 , "http://www.soundserum.com/mp3/Bleeding_Heart-B0UNC3.mp3"
 , "http://www.soundserum.com/mp3/Blind_Affection-Faygo232.mp3"
 , "http://www.soundserum.com/mp3/Blind_Beauty-ZENON.mp3"
 , "http://www.soundserum.com/mp3/Blippblipp-Bunnymajs.mp3"
 , "http://www.soundserum.com/mp3/Bloodwax-API.mp3"
 , "http://www.soundserum.com/mp3/Blue-Nightmare-YouriX.mp3"
 , "http://www.soundserum.com/mp3/Blue_Marine-ParagonX9.mp3"
 , "http://www.soundserum.com/mp3/Blue_Roses-BrokenCrossfader.mp3"
 , "http://www.soundserum.com/mp3/Body_And_Soul-Madrox.mp3"
 , "http://www.soundserum.com/mp3/Boogie_Remix-ShadowFox2.mp3"
 , "http://www.soundserum.com/mp3/Borderlands-Masq.mp3"
 , "http://www.soundserum.com/mp3/Both_Sides_of_the_Moon-durn.mp3"
 , "http://www.soundserum.com/mp3/Breakdown-darthduba.mp3"
 , "http://www.soundserum.com/mp3/Break_Free-Jason_Horecky.mp3"
 , "http://www.soundserum.com/mp3/Breathe-Dan_Roth.mp3"
 , "http://www.soundserum.com/mp3/Breathe-TMM43.mp3"
 , "http://www.soundserum.com/mp3/Brisk-DISTRIX.mp3"
 , "http://www.soundserum.com/mp3/Brkn_Agls-xKore.mp3"
 , "http://www.soundserum.com/mp3/Brutal_Bass-TIMarbury.mp3"
 , "http://www.soundserum.com/mp3/Burning_Summer-Kr1z.mp3"
 , "http://www.soundserum.com/mp3/Burst-Waterflame.mp3"
 , "http://www.soundserum.com/mp3/Bushido_Blade-zirconmusic.mp3"
 , "http://www.soundserum.com/mp3/Buzzsaw-MrFijiWiji.mp3"
 , "http://www.soundserum.com/mp3/Buzztone_Symphony-Dimrain47.mp3"
 , "http://www.soundserum.com/mp3/By_the_Gods-sumguy720.mp3"
 , "http://www.soundserum.com/mp3/Caelum-Dan_Roth.mp3"
 , "http://www.soundserum.com/mp3/Call_Me-Spaze.mp3"
 , "http://www.soundserum.com/mp3/Cant_Be_Still-NIGHTkilla.mp3"
 , "http://www.soundserum.com/mp3/Can_Not_Handle_It-Spaze.mp3"
 , "http://www.soundserum.com/mp3/Caseface-Rellika.mp3"
 , "http://www.soundserum.com/mp3/Celebration-Envy.mp3"
 , "http://www.soundserum.com/mp3/Celestial_Night-DJ_Link.mp3"
 , "http://www.soundserum.com/mp3/Center_of_Existance-dj-Nate.mp3"
 , "http://www.soundserum.com/mp3/Change-Speedsound.mp3"
 , "http://www.soundserum.com/mp3/Change_2_Survive-T-Free_+_Caine1989.mp3"
 , "http://www.soundserum.com/mp3/Changing_Seasons-DJ_Cloud.mp3"
 , "http://www.soundserum.com/mp3/Chao-kirja100.mp3"
 , "http://www.soundserum.com/mp3/Chaos_Overkill-Waterflame.mp3"
 , "http://www.soundserum.com/mp3/Chaotic-Waterflame.mp3"
 , "http://www.soundserum.com/mp3/Chaoz_Airflow-ParagonX9.mp3"
 , "http://www.soundserum.com/mp3/Chaoz_Fantasy-ParagonX9.mp3"
 , "http://www.soundserum.com/mp3/Chaoz_Impact-ParagonX9.mp3"
 , "http://www.soundserum.com/mp3/Chaoz_Twilight-ParagonX9.mp3"
 , "http://www.soundserum.com/mp3/Chasing_The_Dragon-Bafana.mp3"
 , "http://www.soundserum.com/mp3/Chasing_Time-BeefourMusic.mp3"
 , "http://www.soundserum.com/mp3/Cheer_Up-Bafana.mp3"
 , "http://www.soundserum.com/mp3/Chinese_Dance_Machine_3-F-777.mp3"
 , "http://www.soundserum.com/mp3/Choon-Bafana.mp3"
 , "http://www.soundserum.com/mp3/Circadian_Rhythm-nal1200.mp3"
 , "http://www.soundserum.com/mp3/Circuitry-MrFijiWiji.mp3"
 , "http://www.soundserum.com/mp3/Clouds-DJ_Cloud.mp3"
 , "http://www.soundserum.com/mp3/Clouds-Waterflame.mp3"
 , "http://www.soundserum.com/mp3/Color_This_World-YouriX.mp3"
 , "http://www.soundserum.com/mp3/Come_Get_Some-Kr1z.mp3"
 , "http://www.soundserum.com/mp3/Come_Home-Envy.mp3"
 , "http://www.soundserum.com/mp3/Complexity-SKILLZmakeKILLZ.mp3"
 , "http://www.soundserum.com/mp3/Compunction-DaGrahamCraka.mp3"
 , "http://www.soundserum.com/mp3/ConFab-TMM43.mp3"
 , "http://www.soundserum.com/mp3/Contingency_of_Music-dj-Jo.mp3"
 , "http://www.soundserum.com/mp3/Control_Yourself-wildekeizer.mp3"
 , "http://www.soundserum.com/mp3/Control_Yourself-WildeKeizer.mp3"
 , "http://www.soundserum.com/mp3/Corruption-Herdunculus.mp3"
 , "http://www.soundserum.com/mp3/Cosmic_Junction-Bafana.mp3"
 , "http://www.soundserum.com/mp3/Cosmos-xKore.mp3"
 , "http://www.soundserum.com/mp3/CraZe-Michael425.mp3"
 , "http://www.soundserum.com/mp3/Creek-arjanM.mp3"
 , "http://www.soundserum.com/mp3/Crisis_Pt.3-Dj-Immune.mp3"
 , "http://www.soundserum.com/mp3/Critical_Error-Schrader1234.mp3"
 , "http://www.soundserum.com/mp3/Crossfire-A_New_Decade.mp3"
 , "http://www.soundserum.com/mp3/Crowd_Control-Waterflame.mp3"
 , "http://www.soundserum.com/mp3/Crying_Soul__Remix__8-nal1200.mp3"
 , "http://www.soundserum.com/mp3/Crystal_Skies-Exotec.mp3"
 , "http://www.soundserum.com/mp3/Crystal_Warriors-Pretonika.mp3"
 , "http://www.soundserum.com/mp3/Cultivating_Madness-Elfire.mp3"
 , "http://www.soundserum.com/mp3/CY2V9-Elfire.mp3"
 , "http://www.soundserum.com/mp3/Cybernetic_Castle-Prodigal.mp3"
 , "http://www.soundserum.com/mp3/Cybertronic_Excursion-ShadowFox2.mp3"
 , "http://www.soundserum.com/mp3/D-J-Speedsound.mp3"
 , "http://www.soundserum.com/mp3/D-Maker-DjWalom.mp3"
 , "http://www.soundserum.com/mp3/Damage_Control-Waterflame.mp3"
 , "http://www.soundserum.com/mp3/Dancing_Angel-CrimzonWolf777.mp3"
 , "http://www.soundserum.com/mp3/Dark_Carousel-Dj-Rippa.mp3"
 , "http://www.soundserum.com/mp3/Dark_Dreams-Waterflame.mp3"
 , "http://www.soundserum.com/mp3/Dark_Lit_Sky-StaticKatana.mp3"
 , "http://www.soundserum.com/mp3/Dark_Subconscious-Elfire.mp3"
 , "http://www.soundserum.com/mp3/Darth_Raver-Waterflame.mp3"
 , "http://www.soundserum.com/mp3/Das_Bezerker-Bafana.mp3"
 , "http://www.soundserum.com/mp3/Daydream_Remix-A_New_Decade.mp3"
 , "http://www.soundserum.com/mp3/Deadbolt-arjanM.mp3"
 , "http://www.soundserum.com/mp3/DeadLocked-TIMarbury.mp3"
 , "http://www.soundserum.com/mp3/Deep_Blue-Mrmilkcarton.mp3"
 , "http://www.soundserum.com/mp3/Deep_Inside-Etalo.mp3"
 , "http://www.soundserum.com/mp3/Defcon_Zero-nal1200.mp3"
 , "http://www.soundserum.com/mp3/Defection-ParagonX9.mp3"
 , "http://www.soundserum.com/mp3/Delirium-DJ_Link.mp3"
 , "http://www.soundserum.com/mp3/DeludeBeats-Waterflame.mp3"
 , "http://www.soundserum.com/mp3/Delusion-DJ_Link.mp3"
 , "http://www.soundserum.com/mp3/Demo_8-DJ_Double-k&Olley.mp3"
 , "http://www.soundserum.com/mp3/Desire-DJ-VIZ.mp3"
 , "http://www.soundserum.com/mp3/Desolated_Winter-PrEmoEffect.mp3"
 , "http://www.soundserum.com/mp3/Detonate-AndyJ.mp3"
 , "http://www.soundserum.com/mp3/Devious_Angel-TIMarbury.mp3"
 , "http://www.soundserum.com/mp3/Digital_Abuse-TMM43.mp3"
 , "http://www.soundserum.com/mp3/Disappear-Clayface.mp3"
 , "http://www.soundserum.com/mp3/Discover-Mrmilkcarton.mp3"
 , "http://www.soundserum.com/mp3/Dismantled-arjanM.mp3"
 , "http://www.soundserum.com/mp3/Disorientanion-2Invention.mp3"
 , "http://www.soundserum.com/mp3/Distorted-xKore.mp3"
 , "http://www.soundserum.com/mp3/Distorted_Reality-VeXillum.mp3"
 , "http://www.soundserum.com/mp3/Divine-Xiron.mp3"
 , "http://www.soundserum.com/mp3/Don._B-DjWalom.mp3"
 , "http://www.soundserum.com/mp3/Dont_Cry-TIMarbury.mp3"
 , "http://www.soundserum.com/mp3/Dont_Forget-dj-skeetskeet-69.mp3"
 , "http://www.soundserum.com/mp3/Do_it-DJT3chnic.mp3"
 , "http://www.soundserum.com/mp3/Do_You_Feel-durn.mp3"
 , "http://www.soundserum.com/mp3/Drastically_Sunny-TIMarbury.mp3"
 , "http://www.soundserum.com/mp3/Dreaming_of_You-Sp1r1T.mp3"
 , "http://www.soundserum.com/mp3/DreamScape-Cloud_Demaniow.mp3"
 , "http://www.soundserum.com/mp3/Dream_Addiction-gregaaron89.mp3"
 , "http://www.soundserum.com/mp3/Dream_Among_The_Stars-Blackhole12.mp3"
 , "http://www.soundserum.com/mp3/Dream_Trance-GrayZ.mp3"
 , "http://www.soundserum.com/mp3/Drop_that_Beat-DJT3chnic.mp3"
 , "http://www.soundserum.com/mp3/Drop_The_Bass-Bahdshah.mp3"
 , "http://www.soundserum.com/mp3/Dual_Noise-Speedsound.mp3"
 , "http://www.soundserum.com/mp3/Dynamic-DJ_Link.mp3"
 , "http://www.soundserum.com/mp3/East_Clubbers_Drop-Sakura_Girl.mp3"
 , "http://www.soundserum.com/mp3/Ebbing_Waves-DJ-Babokon.mp3"
 , "http://www.soundserum.com/mp3/Echoes_in_the_Silence-Dj_DO.mp3"
 , "http://www.soundserum.com/mp3/Echoes_of_Signal-BertycoX.mp3"
 , "http://www.soundserum.com/mp3/Ecstasy_of_Gold-NIGHTkilla.mp3"
 , "http://www.soundserum.com/mp3/Ecstatic_Flow-Clayface.mp3"
 , "http://www.soundserum.com/mp3/Eden-xKore.mp3"
 , "http://www.soundserum.com/mp3/Effects_of_Karma-Senn.mp3"
 , "http://www.soundserum.com/mp3/Effusion-AudioAnode.mp3"
 , "http://www.soundserum.com/mp3/Electric_WaveGraver-L.N.KrigG.mp3"
 , "http://www.soundserum.com/mp3/Electrodispenser-zirconmusic.mp3"
 , "http://www.soundserum.com/mp3/Electronica_Dreaming-Bafana.mp3"
 , "http://www.soundserum.com/mp3/Electronic_Supremacy- LK412.mp3"
 , "http://www.soundserum.com/mp3/Electronic_Symphony-PsyWoofers.mp3"
 , "http://www.soundserum.com/mp3/Electro_Pirates-Fionnhodgson.mp3"
 , "http://www.soundserum.com/mp3/Elemental__Future_2.0_Mix-A_New_Decade.mp3"
 , "http://www.soundserum.com/mp3/Elements-TerraNation.mp3"
 , "http://www.soundserum.com/mp3/Element_of_Dance-F-777.mp3"
 , "http://www.soundserum.com/mp3/Elfire-Elfire.mp3"
 , "http://www.soundserum.com/mp3/Ellipse-broove.mp3"
 , "http://www.soundserum.com/mp3/Elliptical-Bahdshah.mp3"
 , "http://www.soundserum.com/mp3/Elysium-757irish.mp3"
 , "http://www.soundserum.com/mp3/Elysium_Universe-DJ-Babokon.mp3"
 , "http://www.soundserum.com/mp3/Emotion_Burst_Locomotion-Waterflame.mp3"
 , "http://www.soundserum.com/mp3/Enchanter-Rellika.mp3"
 , "http://www.soundserum.com/mp3/Endless_Fall-nal1200.mp3"
 , "http://www.soundserum.com/mp3/Endless_Space-Clayface.mp3"
 , "http://www.soundserum.com/mp3/Endurance-Envy.mp3"
 , "http://www.soundserum.com/mp3/Enlightenment-oscillist.mp3"
 , "http://www.soundserum.com/mp3/Entering_the_Stronghold-Denny_Schneidemesser.mp3"
 , "http://www.soundserum.com/mp3/Entrance_Me-Mrmilkcarton.mp3"
 , "http://www.soundserum.com/mp3/EoY-DjTrancite.mp3"
 , "http://www.soundserum.com/mp3/Epidemic-axeFX.mp3"
 , "http://www.soundserum.com/mp3/Epixture-TheWeebl.mp3"
 , "http://www.soundserum.com/mp3/Epsylon-Fionnhodgson.mp3"
 , "http://www.soundserum.com/mp3/Equal_Crystals-Hadriani.mp3"
 , "http://www.soundserum.com/mp3/Escape-DJ_Cloud.mp3"
 , "http://www.soundserum.com/mp3/Escort-VeXillum.mp3"
 , "http://www.soundserum.com/mp3/Essence_of_Creativity-DJ_Link.mp3"
 , "http://www.soundserum.com/mp3/Eternal-CrimzonWolf777.mp3"
 , "http://www.soundserum.com/mp3/Eternal-darthduba.mp3"
 , "http://www.soundserum.com/mp3/Eternal_Dreams-T-Free.mp3"
 , "http://www.soundserum.com/mp3/Eternal_Fire-Bjra.mp3"
 , "http://www.soundserum.com/mp3/Eternal_Love-Stephen_Stripling.mp3"
 , "http://www.soundserum.com/mp3/Eternion-Rellika.mp3"
 , "http://www.soundserum.com/mp3/Euphoria-LK412.mp3"
 , "http://www.soundserum.com/mp3/Euphoric_Fluctuation_47-Fionnhodgson.mp3"
 , "http://www.soundserum.com/mp3/Euphorikk-Dj-Rippa.mp3"
 , "http://www.soundserum.com/mp3/Euphorium-EON.mp3"
 , "http://www.soundserum.com/mp3/Everywhere-Envy.mp3"
 , "http://www.soundserum.com/mp3/Every_End-Dimrain47.mp3"
 , "http://www.soundserum.com/mp3/Evolution_of_Music-dj-Nate.mp3"
 , "http://www.soundserum.com/mp3/Existe-Speedsound.mp3"
 , "http://www.soundserum.com/mp3/Experienced-Senn.mp3"
 , "http://www.soundserum.com/mp3/Experiment_7-Elfire.mp3"
 , "http://www.soundserum.com/mp3/Explore-Clayface.mp3"
 , "http://www.soundserum.com/mp3/Exthera_2-Paulysaurus.mp3"
 , "http://www.soundserum.com/mp3/Eye_To_Eye-Avizura.mp3"
 , "http://www.soundserum.com/mp3/Factory-cornandbeans.mp3"
 , "http://www.soundserum.com/mp3/Fallen-The_Phase.mp3"
 , "http://www.soundserum.com/mp3/Falling_Into_You-DaGrahamCraka.mp3"
 , "http://www.soundserum.com/mp3/Far_Side_Of_The_Moon_-Kazmo.mp3"
 , "http://www.soundserum.com/mp3/Fear_Me-NIGHTkilla.mp3"
 , "http://www.soundserum.com/mp3/Feeling_Trance-Dj-Brand0.mp3"
 , "http://www.soundserum.com/mp3/Feeling_Trance-Dj-Rippa.mp3"
 , "http://www.soundserum.com/mp3/Feels_Like_Meh-BrokenCrossfader.mp3"
 , "http://www.soundserum.com/mp3/Feel_of_Flight-Senn.mp3"
 , "http://www.soundserum.com/mp3/Feel_the_Power-DJT3chnic.mp3"
 , "http://www.soundserum.com/mp3/Final_Fight-Spot1techno.mp3"
 , "http://www.soundserum.com/mp3/Final_Thought-BrokenCrossfader.mp3"
 , "http://www.soundserum.com/mp3/Fine_with_me-darthduba.mp3"
 , "http://www.soundserum.com/mp3/Fire-xKore.mp3"
 , "http://www.soundserum.com/mp3/Fire_in_the_Sky-DJT3chnic.mp3"
 , "http://www.soundserum.com/mp3/Flames_of_the_Sky-XsToRmEr1.mp3"
 , "http://www.soundserum.com/mp3/Fleeting_Moments-PrEmoEffect.mp3"
 , "http://www.soundserum.com/mp3/Flight-PrototypeRaptor.mp3"
 , "http://www.soundserum.com/mp3/Fluorescence-Clayface.mp3"
 , "http://www.soundserum.com/mp3/Fly-Dj_B@ss_Louder.mp3"
 , "http://www.soundserum.com/mp3/Flying_Home-Kr1z.mp3"
 , "http://www.soundserum.com/mp3/Following_Your_Star-Phyrnna.mp3"
 , "http://www.soundserum.com/mp3/Follow_the_Light-Dj_DO.mp3"
 , "http://www.soundserum.com/mp3/Forest_of_Hate-Acid_Paradox.mp3"
 , "http://www.soundserum.com/mp3/Forever-Kr1z.mp3"
 , "http://www.soundserum.com/mp3/Forever_Gone-Etalo.mp3"
 , "http://www.soundserum.com/mp3/For_Science-Or4nges.mp3"
 , "http://www.soundserum.com/mp3/Fracture-NIGHTkilla.mp3"
 , "http://www.soundserum.com/mp3/Fragile_Love-Shandu.mp3"
 , "http://www.soundserum.com/mp3/Freaky_Motivation-TIMarbury.mp3"
 , "http://www.soundserum.com/mp3/Freedom_of_Trance-dj-Nate.mp3"
 , "http://www.soundserum.com/mp3/Freezing_Flames-DustDevil.mp3"
 , "http://www.soundserum.com/mp3/Frost-15thDimension.mp3"
 , "http://www.soundserum.com/mp3/Frozen-DaGrahamCraka.mp3"
 , "http://www.soundserum.com/mp3/Frozen_Oranges-AlanaSaysHey.mp3"
 , "http://www.soundserum.com/mp3/Frozen_Sun-DJ_Fire-Black.mp3"
 , "http://www.soundserum.com/mp3/Frozen_Words-Kr1z.mp3"
 , "http://www.soundserum.com/mp3/Full_Power-Speedsound.mp3"
 , "http://www.soundserum.com/mp3/Fusion-4x4blue.mp3"
 , "http://www.soundserum.com/mp3/Game_of_Survival-ZENON.mp3"
 , "http://www.soundserum.com/mp3/Game_Over-Mrmilkcarton.mp3"
 , "http://www.soundserum.com/mp3/GEN-eRiK-Phyrnna.mp3"
 , "http://www.soundserum.com/mp3/Generic_Dance_Song_3_V4-SuperRaWR.mp3"
 , "http://www.soundserum.com/mp3/German_Hard_Trance-Teck071.mp3"
 , "http://www.soundserum.com/mp3/Get_In-TIMarbury.mp3"
 , "http://www.soundserum.com/mp3/Get_Ready_To_Dance-durn.mp3"
 , "http://www.soundserum.com/mp3/Get_Real-NIGHTkilla.mp3"
 , "http://www.soundserum.com/mp3/Ghost-VeXillum.mp3"
 , "http://www.soundserum.com/mp3/Ghostwave-NemesisTheory.mp3"
 , "http://www.soundserum.com/mp3/GloW-Sp1r1T.mp3"
 , "http://www.soundserum.com/mp3/Glowsticks-NIGHTkilla.mp3"
 , "http://www.soundserum.com/mp3/Glympse-Rellika.mp3"
 , "http://www.soundserum.com/mp3/Gods_Fist-YouriX.mp3"
 , "http://www.soundserum.com/mp3/Good_Day_Sir-DJFiendO.mp3"
 , "http://www.soundserum.com/mp3/Gotta_Start_Moving-JohnnyFrizz.mp3"
 , "http://www.soundserum.com/mp3/Gravity-DJ-Babokon.mp3"
 , "http://www.soundserum.com/mp3/Greenlight-API.mp3"
 , "http://www.soundserum.com/mp3/Green_Plums-PrEmoEffect.mp3"
 , "http://www.soundserum.com/mp3/Green_Skyscrapers-TiGeR.mp3"
 , "http://www.soundserum.com/mp3/Groove_To_This-i9incher.mp3"
 , "http://www.soundserum.com/mp3/Hallow-X_Sentinel.mp3"
 , "http://www.soundserum.com/mp3/Hanging_On-Speedsound.mp3"
 , "http://www.soundserum.com/mp3/Hardstyle_Revolution-Bjra_ft._Julia.mp3"
 , "http://www.soundserum.com/mp3/Hard_Twist-demont_rider.mp3"
 , "http://www.soundserum.com/mp3/Harmony_Intent-DjZeaklous.mp3"
 , "http://www.soundserum.com/mp3/Har_Har_Har-Or4nges.mp3"
 , "http://www.soundserum.com/mp3/Haunting-Envy.mp3"
 , "http://www.soundserum.com/mp3/Head_Up-DjSc00p.mp3"
 , "http://www.soundserum.com/mp3/Hearing_is_Believing-oscillist.mp3"
 , "http://www.soundserum.com/mp3/Heartbeats-Azoic.mp3"
 , "http://www.soundserum.com/mp3/Heaven-DJ_Cloud.mp3"
 , "http://www.soundserum.com/mp3/Heavens_Heart-YouriX.mp3"
 , "http://www.soundserum.com/mp3/Heavens_Storm-ApproachingNirvana.mp3"
 , "http://www.soundserum.com/mp3/Heaven_of_Inspiration-Argyros.mp3"
 , "http://www.soundserum.com/mp3/Heaven_Rd.2-Envy.mp3"
 , "http://www.soundserum.com/mp3/Hercules-ZENON.mp3"
 , "http://www.soundserum.com/mp3/Here_We_Go-B0UNC3.mp3"
 , "http://www.soundserum.com/mp3/High_Pressure_System-zirconmusic.mp3"
 , "http://www.soundserum.com/mp3/High_Tide-DaGrahamCraka.mp3"
 , "http://www.soundserum.com/mp3/Holding_On-Mrmilkcarton.mp3"
 , "http://www.soundserum.com/mp3/Hold_One_Last_Breath-MrFijiWiji.mp3"
 , "http://www.soundserum.com/mp3/Hollow_Drop-td6d.mp3"
 , "http://www.soundserum.com/mp3/Hope_Will_Come_Again-TranceCrafter.mp3"
 , "http://www.soundserum.com/mp3/Horizon_Chill_Out-Speedsound.mp3"
 , "http://www.soundserum.com/mp3/Hungry-BrokenCrossfader.mp3"
 , "http://www.soundserum.com/mp3/Hybrid-Pocketpod.mp3"
 , "http://www.soundserum.com/mp3/Hydra-DJ_Fire-Black.mp3"
 , "http://www.soundserum.com/mp3/Hypebeast-TIMarbury.mp3"
 , "http://www.soundserum.com/mp3/Hyperstructure-Eology.mp3"
 , "http://www.soundserum.com/mp3/Hyper_Bounce-NIGHTkilla.mp3"
 , "http://www.soundserum.com/mp3/Hypnose_(NG_Cut)-landscapers1.mp3"
 , "http://www.soundserum.com/mp3/Hypnotic_Sound-DJs_Team_Project.mp3"
 , "http://www.soundserum.com/mp3/Hypnotize-Dj-Rippa.mp3"
 , "http://www.soundserum.com/mp3/Hypnotized-API.mp3"
 , "http://www.soundserum.com/mp3/Ibiza-ClarkBrazda.mp3"
 , "http://www.soundserum.com/mp3/Idle-NIGHTkilla.mp3"
 , "http://www.soundserum.com/mp3/Ifertehno-Shinrog.mp3"
 , "http://www.soundserum.com/mp3/Imagination-Envy.mp3"
 , "http://www.soundserum.com/mp3/Imagination-Speedsound.mp3"
 , "http://www.soundserum.com/mp3/Immune-Dj-Immune.mp3"
 , "http://www.soundserum.com/mp3/Impossible_to_Convey-PrEmoEffect.mp3"
 , "http://www.soundserum.com/mp3/Im_A_Raver-ZeRo_BaSs.mp3"
 , "http://www.soundserum.com/mp3/Im_Back-DJT3chnic.mp3"
 , "http://www.soundserum.com/mp3/Im_Blue-T-Free_+_Caine1989.mp3"
 , "http://www.soundserum.com/mp3/Im_Flying-Bjra.mp3"
 , "http://www.soundserum.com/mp3/In10city-Tempest09.mp3"
 , "http://www.soundserum.com/mp3/Ina-Envy.mp3"
 , "http://www.soundserum.com/mp3/Infablue-ZENON.mp3"
 , "http://www.soundserum.com/mp3/Infected-g-r4ve.mp3"
 , "http://www.soundserum.com/mp3/Infernal_Signs-T-Free.mp3"
 , "http://www.soundserum.com/mp3/Infernoplex-Dimrain47.mp3"
 , "http://www.soundserum.com/mp3/Infiltration-ParagonX9.mp3"
 , "http://www.soundserum.com/mp3/Infinite_Eldorado-Scrovinsky.mp3"
 , "http://www.soundserum.com/mp3/Infinity-TIMarbury.mp3"
 , "http://www.soundserum.com/mp3/Infinity-zero-project.mp3"
 , "http://www.soundserum.com/mp3/Infused-MrFijiWiji.mp3"
 , "http://www.soundserum.com/mp3/Insomnia-darthduba.mp3"
 , "http://www.soundserum.com/mp3/Insomnia_2.8-Dj_H@rd_Tune_!.mp3"
 , "http://www.soundserum.com/mp3/Insomnia_Remix-Fijiwiji.mp3"
 , "http://www.soundserum.com/mp3/Insomnia_Remix_2-MrFijiWiji.mp3"
 , "http://www.soundserum.com/mp3/Intense_Beauty-TIMarbury.mp3"
 , "http://www.soundserum.com/mp3/Intercept-dj-Nate.mp3"
 , "http://www.soundserum.com/mp3/Interstate-xKore.mp3"
 , "http://www.soundserum.com/mp3/Into The Mind-DaGrahamCraka.mp3"
 , "http://www.soundserum.com/mp3/Into_the_Light-NIGHTkilla.mp3"
 , "http://www.soundserum.com/mp3/Into_the_Night-DJ-VIZ.mp3"
 , "http://www.soundserum.com/mp3/Into_the_Night-TranceCrafter.mp3"
 , "http://www.soundserum.com/mp3/Into_the_Sun-Exotec.mp3"
 , "http://www.soundserum.com/mp3/Invincible-Michael425.mp3"
 , "http://www.soundserum.com/mp3/In_A_Trance-Nostrap.mp3"
 , "http://www.soundserum.com/mp3/Ion_Channels-TranQuillus.mp3"
 , "http://www.soundserum.com/mp3/Is_Anyone_Out_There-Eyuh.mp3"
 , "http://www.soundserum.com/mp3/Its_a_beauty-TheWeebl.mp3"
 , "http://www.soundserum.com/mp3/Its_a_Cold_World-Mrmilkcarton.mp3"
 , "http://www.soundserum.com/mp3/Its_Gone-Tempest09.mp3"
 , "http://www.soundserum.com/mp3/I_Believe-Bjra.mp3"
 , "http://www.soundserum.com/mp3/I_Can_Fly-Mrmilkcarton.mp3"
 , "http://www.soundserum.com/mp3/I_Dont_Know-Mrmilkcarton.mp3"
 , "http://www.soundserum.com/mp3/I_Need_This-Speedsound.mp3"
 , "http://www.soundserum.com/mp3/I_Still_Love_You_2-dj_skeetskeet_69.mp3"
 , "http://www.soundserum.com/mp3/I_Thank_You-ZENON.mp3"
 , "http://www.soundserum.com/mp3/I_Wanna_Fly-T-Free.mp3"
 , "http://www.soundserum.com/mp3/I_Wanna_Know-Speedsound.mp3"
 , "http://www.soundserum.com/mp3/Japanology-Spiriax.mp3"
 , "http://www.soundserum.com/mp3/Jinglebells-Kezsonaj.mp3"
 , "http://www.soundserum.com/mp3/Journey_by_Night-Spot1techno.mp3"
 , "http://www.soundserum.com/mp3/Journey_Into_Twilight-F-777.mp3"
 , "http://www.soundserum.com/mp3/Judgment_Dance-NemesisTheory.mp3"
 , "http://www.soundserum.com/mp3/Junction-iNfliktioN.mp3"
 , "http://www.soundserum.com/mp3/Just_Tell_Me-Pixelsaur.mp3"
 , "http://www.soundserum.com/mp3/K.c.c.-Speedsound.mp3"
 , "http://www.soundserum.com/mp3/Kaifuku_suru_kizu-Azoic.mp3"
 , "http://www.soundserum.com/mp3/Karmelya_Legend-NeXuS.mp3"
 , "http://www.soundserum.com/mp3/Killstealer-Waterflame.mp3"
 , "http://www.soundserum.com/mp3/Kindred_Soul-2DPolygon.mp3"
 , "http://www.soundserum.com/mp3/L.E.D_There_Be_Light-D-Chain.mp3"
 , "http://www.soundserum.com/mp3/L33t-Exotec.mp3"
 , "http://www.soundserum.com/mp3/Landfall-cycerin.mp3"
 , "http://www.soundserum.com/mp3/Last_Breath-Soulero.mp3"
 , "http://www.soundserum.com/mp3/Last_Forever-dj-Nate.mp3"
 , "http://www.soundserum.com/mp3/Last_Words-xKore.mp3"
 , "http://www.soundserum.com/mp3/La_Luna-Minimoonstra.mp3"
 , "http://www.soundserum.com/mp3/Lead_The_Way-Senn.mp3"
 , "http://www.soundserum.com/mp3/Lets_Play_Pirates-Argyros.mp3"
 , "http://www.soundserum.com/mp3/Let_The_Bass_Kick-r4bbit.mp3"
 , "http://www.soundserum.com/mp3/Liberated-nal1200.mp3"
 , "http://www.soundserum.com/mp3/Lifeswitch-ZENON.mp3"
 , "http://www.soundserum.com/mp3/Lifes_An_Opportunity-Spot1techno.mp3"
 , "http://www.soundserum.com/mp3/Lift_(Tuneit)-Etalo.mp3"
 , "http://www.soundserum.com/mp3/Lightspeed-Waterflame.mp3"
 , "http://www.soundserum.com/mp3/Lightwalk-Nav.mp3"
 , "http://www.soundserum.com/mp3/Like_a_Saturday-Sakura_Girl.mp3"
 , "http://www.soundserum.com/mp3/Limelight-Exotec.mp3"
 , "http://www.soundserum.com/mp3/Little_Boomer-Slug-Salt.mp3"
 , "http://www.soundserum.com/mp3/Live_On-Mrmilkcarton.mp3"
 , "http://www.soundserum.com/mp3/Live_on_Risa-NeXuS.mp3"
 , "http://www.soundserum.com/mp3/Living_The_Dream-DjWalom.mp3"
 , "http://www.soundserum.com/mp3/Londons_on_fire-g-r4ve.mp3"
 , "http://www.soundserum.com/mp3/Lonely_Energy-TIMarbury.mp3"
 , "http://www.soundserum.com/mp3/Lost_Hope-Mrmilkcarton.mp3"
 , "http://www.soundserum.com/mp3/Lost_in_Thought-Stephen_Stripling.mp3"
 , "http://www.soundserum.com/mp3/Lost_In_Time-DJ_Link.mp3"
 , "http://www.soundserum.com/mp3/Lost_In_Translation-xKore.mp3"
 , "http://www.soundserum.com/mp3/Love_Equation-NeXuS.mp3"
 , "http://www.soundserum.com/mp3/Madness4-cheshyre.mp3"
 , "http://www.soundserum.com/mp3/Madness6(remix)-cheshyre.mp3"
 , "http://www.soundserum.com/mp3/Madness7-cheshyre.mp3"
 , "http://www.soundserum.com/mp3/Madness_6.5-cheshyre.mp3"
 , "http://www.soundserum.com/mp3/Madness_Divinity-Bjra.mp3"
 , "http://www.soundserum.com/mp3/Magenta_Mist-xKore.mp3"
 , "http://www.soundserum.com/mp3/Magestik_Symphonya-NeXuS.mp3"
 , "http://www.soundserum.com/mp3/Magnum-xKore.mp3"
 , "http://www.soundserum.com/mp3/Make_Believe-Spot1techno.mp3"
 , "http://www.soundserum.com/mp3/Make_Me_Anime-X_Sentinel.mp3"
 , "http://www.soundserum.com/mp3/Maniac_Despair-TIMarbury.mp3"
 , "http://www.soundserum.com/mp3/Mantra-darthduba.mp3"
 , "http://www.soundserum.com/mp3/Marina-Para.mp3"
 , "http://www.soundserum.com/mp3/Marsh_Mission-Speedsound.mp3"
 , "http://www.soundserum.com/mp3/Massive-DrillNB.mp3"
 , "http://www.soundserum.com/mp3/Mass_Effect-Spot1techno.mp3"
 , "http://www.soundserum.com/mp3/Mechanical-Razorman.mp3"
 , "http://www.soundserum.com/mp3/Meet_The_Earth-darthduba.mp3"
 , "http://www.soundserum.com/mp3/Megara_911-NeXuS.mp3"
 , "http://www.soundserum.com/mp3/Melancholia-PrEmoEffect.mp3"
 , "http://www.soundserum.com/mp3/Melancholy_Heart-vwvSTATICvwv.mp3"
 , "http://www.soundserum.com/mp3/Melody_Attack-Asteroide.mp3"
 , "http://www.soundserum.com/mp3/Meltdown-NIGHTkilla.mp3"
 , "http://www.soundserum.com/mp3/Meltdown-Orr.mp3"
 , "http://www.soundserum.com/mp3/Memoirs_Of_A_Raver-Bafana.mp3"
 , "http://www.soundserum.com/mp3/Memories-BeefourMusic.mp3"
 , "http://www.soundserum.com/mp3/Memories-DJ_Cloud.mp3"
 , "http://www.soundserum.com/mp3/Memories-Johaness_Gilther_vs_Kristian_Leens.mp3"
 , "http://www.soundserum.com/mp3/Mental_Madness-Cameron_Graybill.mp3"
 , "http://www.soundserum.com/mp3/Meteor_Love-Spot1techno.mp3"
 , "http://www.soundserum.com/mp3/Metropolis-Masq.mp3"
 , "http://www.soundserum.com/mp3/Micron-D-dazweeks.mp3"
 , "http://www.soundserum.com/mp3/Midnight_Psycho-td6d.mp3"
 , "http://www.soundserum.com/mp3/Midnight_Sun-Bahdshah.mp3"
 , "http://www.soundserum.com/mp3/Military_Storm-Bjra.mp3"
 , "http://www.soundserum.com/mp3/Missing_You-F-777.mp3"
 , "http://www.soundserum.com/mp3/Mixed_Emotions-Exnotic.mp3"
 , "http://www.soundserum.com/mp3/Moar-Rig.mp3"
 , "http://www.soundserum.com/mp3/Moments-xKore.mp3"
 , "http://www.soundserum.com/mp3/Monster-Acid_Paradox.mp3"
 , "http://www.soundserum.com/mp3/Montara_redux-Kami.mp3"
 , "http://www.soundserum.com/mp3/Morning_Coffee-xKore.mp3"
 , "http://www.soundserum.com/mp3/Motion-Spot1techno.mp3"
 , "http://www.soundserum.com/mp3/Motion_of_Movement-Sp1r1T.mp3"
 , "http://www.soundserum.com/mp3/Moutain_Flowers-F-777.mp3"
 , "http://www.soundserum.com/mp3/Move_On-Sequenced.mp3"
 , "http://www.soundserum.com/mp3/Move_Your_Brain-Psychoactive.mp3"
 , "http://www.soundserum.com/mp3/MS_Oscillation-Mortal_Symphony.mp3"
 , "http://www.soundserum.com/mp3/Mysterious_Notion-DJ_Link.mp3"
 , "http://www.soundserum.com/mp3/Mysterious_Sleeper-NemesisTheory.mp3"
 , "http://www.soundserum.com/mp3/Mystical_Traveler-Spot1techno.mp3"
 , "http://www.soundserum.com/mp3/My_Black_and_White_Keys-DJFiendO.mp3"
 , "http://www.soundserum.com/mp3/My_Life_Is_Good-Dj_DO.mp3"
 , "http://www.soundserum.com/mp3/My_Time-darthduba.mp3"
 , "http://www.soundserum.com/mp3/Nachtmusik I.mp3"
 , "http://www.soundserum.com/mp3/Necromancy-zirconmusic.mp3"
 , "http://www.soundserum.com/mp3/Necropolis-TIMarbury.mp3"
 , "http://www.soundserum.com/mp3/Necro_Syncrosis-Elfire.mp3"
 , "http://www.soundserum.com/mp3/Need_You-xKore.mp3"
 , "http://www.soundserum.com/mp3/Neural_Action_Potentials-TranQuillus.mp3"
 , "http://www.soundserum.com/mp3/Nevertheless-TIMarbury.mp3"
 , "http://www.soundserum.com/mp3/Never_Enough-xKore.mp3"
 , "http://www.soundserum.com/mp3/New_Awakening-Dj_DO.mp3"
 , "http://www.soundserum.com/mp3/New_Divide-dj-Jo.mp3"
 , "http://www.soundserum.com/mp3/New_Divide_Remix-DJQ_and_LT.mp3"
 , "http://www.soundserum.com/mp3/New_Journey-15thDimension.mp3"
 , "http://www.soundserum.com/mp3/Nfangled-Shenkhar.mp3"
 , "http://www.soundserum.com/mp3/NFX-Elfire.mp3"
 , "http://www.soundserum.com/mp3/Nightfall-NIGHTkilla.mp3"
 , "http://www.soundserum.com/mp3/No.5_(NG Edit)-ParagonX9.mp3"
 , "http://www.soundserum.com/mp3/Nocturne-DJ_Cloud.mp3"
 , "http://www.soundserum.com/mp3/Nomore-The_Phase.mp3"
 , "http://www.soundserum.com/mp3/Nostalgia_In_Space-BrokenCrossfader.mp3"
 , "http://www.soundserum.com/mp3/Nostalgia_Remake-DJ_Link.mp3"
 , "http://www.soundserum.com/mp3/Not_Afraid-F-777.mp3"
 , "http://www.soundserum.com/mp3/Not_Alone-dj-Nate.mp3"
 , "http://www.soundserum.com/mp3/Not_Like_Them-BrokenCrossfader.mp3"
 , "http://www.soundserum.com/mp3/Novus_Vitate-nal1200.mp3"
 , "http://www.soundserum.com/mp3/No_6-helix6.mp3"
 , "http://www.soundserum.com/mp3/Nuclear_Mutation-cheshyre.mp3"
 , "http://www.soundserum.com/mp3/Nuclear_Warhead-Sunshaft.mp3"
 , "http://www.soundserum.com/mp3/Obelisk-X_Sentinel.mp3"
 , "http://www.soundserum.com/mp3/Ocean_Star-durn.mp3"
 , "http://www.soundserum.com/mp3/Office_Based-BeefourMusic.mp3"
 , "http://www.soundserum.com/mp3/Off_Peak-BeefourMusic.mp3"
 , "http://www.soundserum.com/mp3/On._My._Mind.-Exotec.mp3"
 , "http://www.soundserum.com/mp3/Once_Again-DJT3chnic.mp3"
 , "http://www.soundserum.com/mp3/Once_More-F-777.mp3"
 , "http://www.soundserum.com/mp3/One_Must_Rise-NemesisTheory.mp3"
 , "http://www.soundserum.com/mp3/Only_One-dj-Nate.mp3"
 , "http://www.soundserum.com/mp3/Only_Sometimes-Or4nges.mp3"
 , "http://www.soundserum.com/mp3/On_My_Own-dj-skeetskeet-69.mp3"
 , "http://www.soundserum.com/mp3/On_The_Move-Envy.mp3"
 , "http://www.soundserum.com/mp3/On_the_Way-Para.mp3"
 , "http://www.soundserum.com/mp3/On_Your_Day_Off-BrokenCrossfader.mp3"
 , "http://www.soundserum.com/mp3/Operation_Evolution-Dimrain47.mp3"
 , "http://www.soundserum.com/mp3/Orange_Level-Fionnhodgson.mp3"
 , "http://www.soundserum.com/mp3/Orchestral_Angels-dj-Nate.mp3"
 , "http://www.soundserum.com/mp3/Outrun-Waterflame.mp3"
 , "http://www.soundserum.com/mp3/Overall_Control-Fionnhodgson.mp3"
 , "http://www.soundserum.com/mp3/Overcome-Stephen_Stripling.mp3"
 , "http://www.soundserum.com/mp3/Overture_For_The_Dead-JohnnyFrizz.mp3"
 , "http://www.soundserum.com/mp3/Oxidoze-Speedsound.mp3"
 , "http://www.soundserum.com/mp3/Pacemaker-BeefourMusic.mp3"
 , "http://www.soundserum.com/mp3/Pain-Fredgy.mp3"
 , "http://www.soundserum.com/mp3/Pandemonium-Spot1techno.mp3"
 , "http://www.soundserum.com/mp3/Panic_Attack-Florrie.mp3"
 , "http://www.soundserum.com/mp3/Paradise_on_E-API.mp3"
 , "http://www.soundserum.com/mp3/Paradise_on_E-B0UNC3.mp3"
 , "http://www.soundserum.com/mp3/Paralyzed-Skyplanet.mp3"
 , "http://www.soundserum.com/mp3/Pathway_to_Dreams-AzureVixen.mp3"
 , "http://www.soundserum.com/mp3/Pedal2TheMetal-L.N.KrigG.mp3"
 , "http://www.soundserum.com/mp3/Phalanx-Shenkhar.mp3"
 , "http://www.soundserum.com/mp3/Phantom-F-777.mp3"
 , "http://www.soundserum.com/mp3/Phaser-4x4blue.mp3"
 , "http://www.soundserum.com/mp3/Phoenix_Feather-g-r4ve.mp3"
 , "http://www.soundserum.com/mp3/Piece_of_Mind-PrEmoEffect.mp3"
 , "http://www.soundserum.com/mp3/Pipe_Dream-X_Sentinel.mp3"
 , "http://www.soundserum.com/mp3/Pixel_Decay-AlanaSaysHey.mp3"
 , "http://www.soundserum.com/mp3/Platform-Or4nges.mp3"
 , "http://www.soundserum.com/mp3/Please-TIMarbury.mp3"
 , "http://www.soundserum.com/mp3/Polynator-B0UNC3.mp3"
 , "http://www.soundserum.com/mp3/Popcorn_Remix-EON.mp3"
 , "http://www.soundserum.com/mp3/Positech-NIGHTkilla.mp3"
 , "http://www.soundserum.com/mp3/Positive_Vibe-NIGHTkilla.mp3"
 , "http://www.soundserum.com/mp3/Pre-Valent-Phyrnna.mp3"
 , "http://www.soundserum.com/mp3/Prelude-L.N.KrigG.mp3"
 , "http://www.soundserum.com/mp3/Press_Start-Acid_Paradox.mp3"
 , "http://www.soundserum.com/mp3/Problematic-NIGHTkilla.mp3"
 , "http://www.soundserum.com/mp3/Progger-DjZeaklous.mp3"
 , "http://www.soundserum.com/mp3/Progger_Remix-DjZeaklous.mp3"
 , "http://www.soundserum.com/mp3/Progressive-Acid_Paradox.mp3"
 , "http://www.soundserum.com/mp3/Pulsar-Khuskan.mp3"
 , "http://www.soundserum.com/mp3/Pump_It_Up-Spot1techno.mp3"
 , "http://www.soundserum.com/mp3/Pure_Dream-Speedsound.mp3"
 , "http://www.soundserum.com/mp3/Pure_Scientific_Fact-BrokenCrossfader.mp3"
 , "http://www.soundserum.com/mp3/Push-Or4nges.mp3"
 , "http://www.soundserum.com/mp3/Pyro-F[]X(Resistance_Unit).mp3"
 , "http://www.soundserum.com/mp3/Quadricore-NeXuS.mp3"
 , "http://www.soundserum.com/mp3/Radioactive-Waterflame.mp3"
 , "http://www.soundserum.com/mp3/Rageous_7-ShadowFox2.mp3"
 , "http://www.soundserum.com/mp3/Rainforest-ZooSafari.mp3"
 , "http://www.soundserum.com/mp3/Rainy_Day-Dj_DO.mp3"
 , "http://www.soundserum.com/mp3/Rain_Forests-D-Chain.mp3"
 , "http://www.soundserum.com/mp3/Rave_Of_Eternity-EpicSwe.mp3"
 , "http://www.soundserum.com/mp3/Raving_in_the_Rain-BeefourMusic.mp3"
 , "http://www.soundserum.com/mp3/Raving_Melodies-DJ_Epsilon.mp3"
 , "http://www.soundserum.com/mp3/RDO-BeefourMusic.mp3"
 , "http://www.soundserum.com/mp3/Re-Creation-Azoic.mp3"
 , "http://www.soundserum.com/mp3/Reaching_For_You-Kr1z.mp3"
 , "http://www.soundserum.com/mp3/Reality-DJ_Link.mp3"
 , "http://www.soundserum.com/mp3/Really-Exnotic.mp3"
 , "http://www.soundserum.com/mp3/Reapers_Rave-NIGHTkilla.mp3"
 , "http://www.soundserum.com/mp3/Red_13-ParagonX9.mp3"
 , "http://www.soundserum.com/mp3/Red_Steel-YouriX.mp3"
 , "http://www.soundserum.com/mp3/Regret-dj-Nate.mp3"
 , "http://www.soundserum.com/mp3/Reincarnation-Johaness_Gilther.mp3"
 , "http://www.soundserum.com/mp3/Release_Me-Dj_Bjra.mp3"
 , "http://www.soundserum.com/mp3/Remember-BertycoX.mp3"
 , "http://www.soundserum.com/mp3/ReMotion-Kr1z.mp3"
 , "http://www.soundserum.com/mp3/Renascent-Blackhole.mp3"
 , "http://www.soundserum.com/mp3/Renegade-A_New_Decade.mp3"
 , "http://www.soundserum.com/mp3/Requiem-broove.mp3"
 , "http://www.soundserum.com/mp3/Requiem_at_Dusk-NIGHTkilla.mp3"
 , "http://www.soundserum.com/mp3/Resonare-Senn.mp3"
 , "http://www.soundserum.com/mp3/Resound-15thDimension.mp3"
 , "http://www.soundserum.com/mp3/Restart-Speedsound.mp3"
 , "http://www.soundserum.com/mp3/RetroPolis-darthduba.mp3"
 , "http://www.soundserum.com/mp3/Returning_Home-Stephen_Stripling.mp3"
 , "http://www.soundserum.com/mp3/Return_to_Earth-Spot1techno.mp3"
 , "http://www.soundserum.com/mp3/Revelation-JebbaL.mp3"
 , "http://www.soundserum.com/mp3/Revolution-NIGHTkilla.mp3"
 , "http://www.soundserum.com/mp3/RevolutioN-Sp1r1T.mp3"
 , "http://www.soundserum.com/mp3/Revolutions-Dimrain47.mp3"
 , "http://www.soundserum.com/mp3/RIOT-X_Sentinel.mp3"
 , "http://www.soundserum.com/mp3/Rising_Waves-DJ-Babokon.mp3"
 , "http://www.soundserum.com/mp3/Rock_Will_Never_Die-Bafana.mp3"
 , "http://www.soundserum.com/mp3/Rose-cornandbeans.mp3"
 , "http://www.soundserum.com/mp3/Rude_machine-kickboxer89.mp3"
 , "http://www.soundserum.com/mp3/Runaway-DaGrahamCraka.mp3"
 , "http://www.soundserum.com/mp3/Run_Away-Nutronic.mp3"
 , "http://www.soundserum.com/mp3/Run_Away-xKore.mp3"
 , "http://www.soundserum.com/mp3/Run_Away_With_Me-dj-skeetskeet-69.mp3"
 , "http://www.soundserum.com/mp3/Sad_Melodies-TMM43.mp3"
 , "http://www.soundserum.com/mp3/Satisfied-Nutronic.mp3"
 , "http://www.soundserum.com/mp3/Savanna-ZooSafari.mp3"
 , "http://www.soundserum.com/mp3/SCK-Robot-DJFiendO.mp3"
 , "http://www.soundserum.com/mp3/Scratched-15thDimension.mp3"
 , "http://www.soundserum.com/mp3/Second_Sky-Soulero.mp3"
 , "http://www.soundserum.com/mp3/Sensations-Clayface.mp3"
 , "http://www.soundserum.com/mp3/Sentient-nal1200.mp3"
 , "http://www.soundserum.com/mp3/Serenity-Rellika.mp3"
 , "http://www.soundserum.com/mp3/Session_One-BeefourMusic.mp3"
 , "http://www.soundserum.com/mp3/Session_Two-BeefourMusic.mp3"
 , "http://www.soundserum.com/mp3/Seventeen_Dreams-Rellika.mp3"
 , "http://www.soundserum.com/mp3/Shades_of_Green-NemesisTheory.mp3"
 , "http://www.soundserum.com/mp3/ShiftOut-sumguy720.mp3"
 , "http://www.soundserum.com/mp3/Shimasen_N_Bass-YouriX.mp3"
 , "http://www.soundserum.com/mp3/Shining_Sky-Dj_DO.mp3"
 , "http://www.soundserum.com/mp3/ShuffleDub-td6d.mp3"
 , "http://www.soundserum.com/mp3/Side_Chain-TMM43.mp3"
 , "http://www.soundserum.com/mp3/Silver_Lining-API.mp3"
 , "http://www.soundserum.com/mp3/Simple_Sight-RealFaction.mp3"
 , "http://www.soundserum.com/mp3/Sincere_Serenity-NemesisTheory.mp3"
 , "http://www.soundserum.com/mp3/Singularity-Exotec.mp3"
 , "http://www.soundserum.com/mp3/Sketcha-Dj_Jager.mp3"
 , "http://www.soundserum.com/mp3/Skydivin_Remix-DaGrahamCraka.mp3"
 , "http://www.soundserum.com/mp3/Sleep_With_One_Eye_Open-i9incher.mp3"
 , "http://www.soundserum.com/mp3/Snapdragon-NemesisTheory.mp3"
 , "http://www.soundserum.com/mp3/Soar-Blackhole12.mp3"
 , "http://www.soundserum.com/mp3/Soar_Away-Shandu.mp3"
 , "http://www.soundserum.com/mp3/Solara-JebbaL.mp3"
 , "http://www.soundserum.com/mp3/Solemns_Reach-AzureVixen.mp3"
 , "http://www.soundserum.com/mp3/Solstice-Exotec.mp3"
 , "http://www.soundserum.com/mp3/Someday-Rellika.mp3"
 , "http://www.soundserum.com/mp3/Soundwave-Mr-Ultimate.mp3"
 , "http://www.soundserum.com/mp3/Sound_Of_Goodbye-PrEmoEffect.mp3"
 , "http://www.soundserum.com/mp3/Spaceman-NIGHTkilla.mp3"
 , "http://www.soundserum.com/mp3/Space_Sequence-Nav.mp3"
 , "http://www.soundserum.com/mp3/Speed_of_Sound-EON.mp3"
 , "http://www.soundserum.com/mp3/Spire-PrEmoEffect.mp3"
 , "http://www.soundserum.com/mp3/Splintered_Dreams-sumguy720.mp3"
 , "http://www.soundserum.com/mp3/Stadiums-BeefourMusic.mp3"
 , "http://www.soundserum.com/mp3/Stardust-2DPolygon.mp3"
 , "http://www.soundserum.com/mp3/Starlight-DjZeaklous.mp3"
 , "http://www.soundserum.com/mp3/Startide-cycerin.mp3"
 , "http://www.soundserum.com/mp3/Starve-xKore.mp3"
 , "http://www.soundserum.com/mp3/States_of_Pleasure-NeXuS.mp3"
 , "http://www.soundserum.com/mp3/Steadfast-Stephen_Stripling.mp3"
 , "http://www.soundserum.com/mp3/Steel_Train-Waterflame.mp3"
 , "http://www.soundserum.com/mp3/Still_Blastin-NemesisTheory.mp3"
 , "http://www.soundserum.com/mp3/Stomp-Dj-Rippa.mp3"
 , "http://www.soundserum.com/mp3/Storm-Johaness_Gilther.mp3"
 , "http://www.soundserum.com/mp3/Streams_of_Time-T-Free.mp3"
 , "http://www.soundserum.com/mp3/Streetlights-BrokenCrossfader.mp3"
 , "http://www.soundserum.com/mp3/Stroma-PrEmoEffect.mp3"
 , "http://www.soundserum.com/mp3/Subconscious-MrFijiWiji.mp3"
 , "http://www.soundserum.com/mp3/Submissive-DJ-HLS.mp3"
 , "http://www.soundserum.com/mp3/SubTrance-DJ-Babokon.mp3"
 , "http://www.soundserum.com/mp3/Summer-LK412.mp3"
 , "http://www.soundserum.com/mp3/SummerRave-Waterflame.mp3"
 , "http://www.soundserum.com/mp3/Summers_Fate-XenoxX.mp3"
 , "http://www.soundserum.com/mp3/Summer_is_Here-Exnotic.mp3"
 , "http://www.soundserum.com/mp3/Summer_Rain-Pyroific.mp3"
 , "http://www.soundserum.com/mp3/Summer_Sounds-Envy.mp3"
 , "http://www.soundserum.com/mp3/Sundance-TMM43.mp3"
 , "http://www.soundserum.com/mp3/Sunday_of_Flavya-NeXuS.mp3"
 , "http://www.soundserum.com/mp3/Sungazer-Kr1z.mp3"
 , "http://www.soundserum.com/mp3/Sunrise_In_Paradise-Bafana.mp3"
 , "http://www.soundserum.com/mp3/Sunscape-cycerin.mp3"
 , "http://www.soundserum.com/mp3/Surface-Waterflame.mp3"
 , "http://www.soundserum.com/mp3/Surrender-TIMarbury.mp3"
 , "http://www.soundserum.com/mp3/Susurration-TiGeR.mp3"
 , "http://www.soundserum.com/mp3/Sway_Yourself-TIMarbury.mp3"
 , "http://www.soundserum.com/mp3/Sweet_Lemon_IceTea-TIMarbury.mp3"
 , "http://www.soundserum.com/mp3/Syntax-xKore.mp3"
 , "http://www.soundserum.com/mp3/Synthetica-Bahdshah.mp3"
 , "http://www.soundserum.com/mp3/Syst3m_Sh0ck- Sirdangolot5.mp3"
 , "http://www.soundserum.com/mp3/System_Split-F-777.mp3"
 , "http://www.soundserum.com/mp3/Sytrus-Mrmilkcarton.mp3"
 , "http://www.soundserum.com/mp3/Taio_Cruz- BeatSource.mp3"
 , "http://www.soundserum.com/mp3/Take_Me_Away-NIGHTkilla.mp3"
 , "http://www.soundserum.com/mp3/Take_Over_Control-X_Sentinel.mp3"
 , "http://www.soundserum.com/mp3/Taking_A_Nap_In_The_Jungle-Fantomenk.mp3"
 , "http://www.soundserum.com/mp3/Talk_To_Me-Exnotic.mp3"
 , "http://www.soundserum.com/mp3/Tears_In_My_Eyes-dj-Nate.mp3"
 , "http://www.soundserum.com/mp3/Tear_Drops-Terror-Proof.mp3"
 , "http://www.soundserum.com/mp3/Technology-xKore.mp3"
 , "http://www.soundserum.com/mp3/Technoraptorical-ZENON.mp3"
 , "http://www.soundserum.com/mp3/Techno_Canon-Rellika.mp3"
 , "http://www.soundserum.com/mp3/Techno_Crusade-Spot1techno.mp3"
 , "http://www.soundserum.com/mp3/Techno_Rocker-TMM43.mp3"
 , "http://www.soundserum.com/mp3/Temptation-BeefourMusic.mp3"
 , "http://www.soundserum.com/mp3/Tendril-Blackhole12.mp3"
 , "http://www.soundserum.com/mp3/Test_Subject_G7-Acid_Paradox.mp3"
 , "http://www.soundserum.com/mp3/Tetris_Kills-DJ_Clyme.mp3"
 , "http://www.soundserum.com/mp3/THCity-Prisma.mp3"
 , "http://www.soundserum.com/mp3/Theory_of_Everything-dj-Nate.mp3"
 , "http://www.soundserum.com/mp3/The_7th_Day-TMM43l.mp3"
 , "http://www.soundserum.com/mp3/The_Abyss-API.mp3"
 , "http://www.soundserum.com/mp3/The_Approach-Spot1techno.mp3"
 , "http://www.soundserum.com/mp3/The_Aural_Virus-PrototypeRaptor.mp3"
 , "http://www.soundserum.com/mp3/The_Beginning_of_Time-dj-Nate.mp3"
 , "http://www.soundserum.com/mp3/The_Burning_Earth-DJ-Babokon.mp3"
 , "http://www.soundserum.com/mp3/The_Edge-darthduba.mp3"
 , "http://www.soundserum.com/mp3/The_Escape-picklesandwichh.mp3"
 , "http://www.soundserum.com/mp3/The_Escape_Velocity-Slug-Salt.mp3"
 , "http://www.soundserum.com/mp3/The_Euphoria-dj-Nate.mp3"
 , "http://www.soundserum.com/mp3/The_Expelled-T-Free.mp3"
 , "http://www.soundserum.com/mp3/The_Fall_of_Cheshyre-cheshyre.mp3"
 , "http://www.soundserum.com/mp3/The_Journey-i9incher.mp3"
 , "http://www.soundserum.com/mp3/The_Kriotube-Dimrain47.mp3"
 , "http://www.soundserum.com/mp3/The_Land_of_the_Forgotten-DustDevil.mp3"
 , "http://www.soundserum.com/mp3/The_Last_Day_On_Earth-Hopeku.mp3"
 , "http://www.soundserum.com/mp3/The_Last_Journey-AzureVixen.mp3"
 , "http://www.soundserum.com/mp3/The_Maze-NIGHTkilla.mp3"
 , "http://www.soundserum.com/mp3/The_Missing_Piece-X_Sentinel.mp3"
 , "http://www.soundserum.com/mp3/The_Next_Chapter-TranceCrafter.mp3"
 , "http://www.soundserum.com/mp3/The_Nightrunner-2DPolygon.mp3"
 , "http://www.soundserum.com/mp3/The_Pianists_Journey-HalcyonicFalconX.mp3"
 , "http://www.soundserum.com/mp3/The_Pianists_Revelment-Phyrnna.mp3"
 , "http://www.soundserum.com/mp3/The_Signal-BertycoX.mp3"
 , "http://www.soundserum.com/mp3/The_Signal_(CWF_remix)-BertycoX.mp3"
 , "http://www.soundserum.com/mp3/The_Sound_of_Happiness-larrylarrybb.mp3"
 , "http://www.soundserum.com/mp3/The_Source-darthduba.mp3"
 , "http://www.soundserum.com/mp3/The_Starless_Night-Exotec.mp3"
 , "http://www.soundserum.com/mp3/The_Stone_of_Shadows-DustDevil.mp3"
 , "http://www.soundserum.com/mp3/The_Unmaker-X_Sentinel.mp3"
 , "http://www.soundserum.com/mp3/Third_Element-Treysen.mp3"
 , "http://www.soundserum.com/mp3/Thunderheads_at_sunset-Shenkhar.mp3"
 , "http://www.soundserum.com/mp3/Thundersocks-Slug-Salt.mp3"
 , "http://www.soundserum.com/mp3/Tied_Up-BeefourMusic.mp3"
 , "http://www.soundserum.com/mp3/Times_Up-xKore.mp3"
 , "http://www.soundserum.com/mp3/Time_Flies-xKore.mp3"
 , "http://www.soundserum.com/mp3/Time_Paradox-Pyroific.mp3"
 , "http://www.soundserum.com/mp3/Tokyo_Overdrive-Eology.mp3"
 , "http://www.soundserum.com/mp3/Tomorrow-Sp1r1T.mp3"
 , "http://www.soundserum.com/mp3/Tonight-sumguy720.mp3"
 , "http://www.soundserum.com/mp3/Toronto_Sunrise-durn.mp3"
 , "http://www.soundserum.com/mp3/Tough_Love-Schrader1234.mp3"
 , "http://www.soundserum.com/mp3/To_the_End_of_Eternity-TranceCrafter.mp3"
 , "http://www.soundserum.com/mp3/To_The_Stars-Dreamscape.mp3"
 , "http://www.soundserum.com/mp3/Tradition-Cutting-Christ.mp3"
 , "http://www.soundserum.com/mp3/Train_Madness-cheshyre.mp3"
 , "http://www.soundserum.com/mp3/Tranced-Atomic_Inc.mp3"
 , "http://www.soundserum.com/mp3/Trance_Act-B0UNC3.mp3"
 , "http://www.soundserum.com/mp3/Trance_Anthem-SRT-M1tch.mp3"
 , "http://www.soundserum.com/mp3/Trance_Delight-TripleStar.mp3"
 , "http://www.soundserum.com/mp3/Trance_Overdrive-ZeD.mp3"
 , "http://www.soundserum.com/mp3/Trance_symphony-Atomic_cat.mp3"
 , "http://www.soundserum.com/mp3/Transdemonium-Nebulis.mp3"
 , "http://www.soundserum.com/mp3/Tribex-Fredgy.mp3"
 , "http://www.soundserum.com/mp3/Tropical-Atomic_Inc.mp3"
 , "http://www.soundserum.com/mp3/Turbulences-2Invention.mp3"
 , "http://www.soundserum.com/mp3/Turn_the_Beat_Back-Spot1techno.mp3"
 , "http://www.soundserum.com/mp3/Twilight_Techno-Dimrain47.mp3"
 , "http://www.soundserum.com/mp3/Twisted-Elfire.mp3"
 , "http://www.soundserum.com/mp3/Two-Render-Error.mp3"
 , "http://www.soundserum.com/mp3/Ultimate_Destruction-TMM43.mp3"
 , "http://www.soundserum.com/mp3/Uncharted_Worlds-dj-Nate.mp3"
 , "http://www.soundserum.com/mp3/Unfortunate_Truth-PrEmoEffect.mp3"
 , "http://www.soundserum.com/mp3/United_Sway-BeefourMusic.mp3"
 , "http://www.soundserum.com/mp3/Universe_City-EON.mp3"
 , "http://www.soundserum.com/mp3/Unreeeal_Superhero-Bjra.mp3"
 , "http://www.soundserum.com/mp3/Urban_Train_(remix)-Kazm0.mp3"
 , "http://www.soundserum.com/mp3/Utopia-2DPolygon.mp3"
 , "http://www.soundserum.com/mp3/Utopia-BertycoX.mp3"
 , "http://www.soundserum.com/mp3/Vacancy-BeefourMusic.mp3"
 , "http://www.soundserum.com/mp3/Valley_of_Death-ZENON.mp3"
 , "http://www.soundserum.com/mp3/Vanquish-iNfliktioN.mp3"
 , "http://www.soundserum.com/mp3/Vashti_Epic_Remix-vwvSTATICvwv.mp3"
 , "http://www.soundserum.com/mp3/Velocity-Clayface.mp3"
 , "http://www.soundserum.com/mp3/Verano_Del_Amor-Bafana.mp3"
 , "http://www.soundserum.com/mp3/Vermilion-Kazmo.mp3"
 , "http://www.soundserum.com/mp3/Village_Up_North-Waterflame.mp3"
 , "http://www.soundserum.com/mp3/Visionary-Kazmo.mp3"
 , "http://www.soundserum.com/mp3/Vital_Victory-TIMarbury.mp3"
 , "http://www.soundserum.com/mp3/Voices-Speedsound.mp3"
 , "http://www.soundserum.com/mp3/Voyager-B0UNC3.mp3"
 , "http://www.soundserum.com/mp3/Walking_in_the_Air-Kazmo.mp3"
 , "http://www.soundserum.com/mp3/Wander_of_Thought-F-777.mp3"
 , "http://www.soundserum.com/mp3/Warhead-zirconmusic.mp3"
 , "http://www.soundserum.com/mp3/Wasted-Michael425.mp3"
 , "http://www.soundserum.com/mp3/Waterfall-Approaching_Nirvana .mp3"
 , "http://www.soundserum.com/mp3/Waves_of_Rain-Antoine_gagaman92.mp3"
 , "http://www.soundserum.com/mp3/Waves_of_Thought-BeefourMusic.mp3"
 , "http://www.soundserum.com/mp3/Wave_Fusion-td6d.mp3"
 , "http://www.soundserum.com/mp3/Weekend_Has_Come-Envy.mp3"
 , "http://www.soundserum.com/mp3/Welcome_To_The_Wild-ZooSafari.mp3"
 , "http://www.soundserum.com/mp3/Welcome_To_Utopia-2dpolygon.mp3"
 , "http://www.soundserum.com/mp3/WeWe-DjWalom.mp3"
 , "http://www.soundserum.com/mp3/We_Believe_Ep1-F-777.mp3"
 , "http://www.soundserum.com/mp3/We_Play-eghcvg.mp3"
 , "http://www.soundserum.com/mp3/We_Run_And_Fight-mooseymaniac.mp3"
 , "http://www.soundserum.com/mp3/What_Ive_Done-dj-Jo.mp3"
 , "http://www.soundserum.com/mp3/What_You_Do_To_Me-T-Free.mp3"
 , "http://www.soundserum.com/mp3/When_Angels_Cry-DJ_Cloud.mp3"
 , "http://www.soundserum.com/mp3/When_Light_Conquers_Darkness-Sakura_Girl.mp3"
 , "http://www.soundserum.com/mp3/Whispering_Dreams-CrimzonWolf777.mp3"
 , "http://www.soundserum.com/mp3/Whispers_in_the_White-PrEmoEffect.mp3"
 , "http://www.soundserum.com/mp3/Whistle_Dance-T-Free.mp3"
 , "http://www.soundserum.com/mp3/Wildsoul-DjWalom.mp3"
 , "http://www.soundserum.com/mp3/Will_Be_It-Exnotic.mp3"
 , "http://www.soundserum.com/mp3/Winterbliss-cycerin.mp3"
 , "http://www.soundserum.com/mp3/Winter_Wonderland-DJ-HLS.mp3"
 , "http://www.soundserum.com/mp3/Win_the_Race,_Go_to_Space-DISTRIX.mp3"
 , "http://www.soundserum.com/mp3/Wrecked_By_Deja_Vu-ApproachingNirvana.mp3"
 , "http://www.soundserum.com/mp3/X.-Azalea-XenoxX.mp3"
 , "http://www.soundserum.com/mp3/X.-Hyperlogic-XenoxX.mp3"
 , "http://www.soundserum.com/mp3/X._Silence-XenoxX.mp3"
 , "http://www.soundserum.com/mp3/You-TripleStar.mp3"
 , "http://www.soundserum.com/mp3/Your_Little_Moment-BrokenCrossfader.mp3"
 , "http://www.soundserum.com/mp3/Your_Melody-DJT3chnic.mp3"
 , "http://www.soundserum.com/mp3/Your_Soul-DJT3chnic.mp3"
 , "http://www.soundserum.com/mp3/You_Know_I_Know_Techno-nal1200.mp3"
 , "http://www.soundserum.com/mp3/Yukai-Senn.mp3"
 , "http://www.soundserum.com/mp3/Zero_Hour-L.N.KrigG.mp3"
 , "http://www.soundserum.com/mp3/ZuneInc-Enter_The_Sun.mp3"};
	
	private MediaPlayer mediaplayer;
    private Context context;
    private Random random = new Random();
    private Uri songURL, previousSongURL;
    private TextView currentSongText, currentArtistText;
    private String currentArtist, currentSong;
    private int touched = 0; // boolean for whether seek-bar is touched, needed to block race conditions for threads
    private int mediaReady = 0;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = getApplicationContext();
        
        // =========================================
        // INIT
        // =========================================
        
        // Music Objects
    	mediaplayer = new MediaPlayer();
    	songURL = getRandomSongURL();
    	previousSongURL = songURL; // previous song and first song are the same
        
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
        
        loadSong(songURL);
        
        // =========================================
        // EVENTS
        // =========================================
        
        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	if ( mediaplayer.isPlaying()) {
            		mediaplayer.pause();
            		playButton.setBackgroundResource(R.drawable.ssplay);
            	}
            	else if ( mediaReady == 1 ){
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
		        	songURL = getRandomSongURL();
	                loadSong(songURL);
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
	                loadSong(songURL);
		        }
				return false;
			}
		});
        
        downloadButton.setOnClickListener(new View.OnClickListener() {
        	
            public void onClick(View v) {
                DownloadManager.Request request = new DownloadManager.Request(songURL);
                request.setDescription(currentArtist);
                request.setTitle(currentSong);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, currentSong.concat(".mp3"));

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
				mediaReady = 1;
				new Thread(new Runnable() {
			        public void run() {
			        	while ( mediaplayer.isPlaying() && touched == 0) {
			        		seekSongTime.setProgress(mediaplayer.getCurrentPosition());
			        	}
			        }
			    }).start();
			}
		});
        
        mediaplayer.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				songURL = getRandomSongURL();
				loadSong(songURL);
			}
		});
        
        seekSongTime.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				touched = 0;
				if ( mediaReady == 1)
					mediaplayer.seekTo(seekSongTime.getProgress());
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				touched = 1;
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
			}
		});
    }
    
    public void loadSong(Uri song){
    	if ( mediaplayer.isPlaying() ) {
    		mediaplayer.stop();
    	}
    	mediaReady = 0;
    	mediaplayer.reset();
        try {
			mediaplayer.setDataSource(this, song);
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
    
    public Uri getRandomSongURL(){
    	previousSongURL = songURL;
    	return Uri.parse(songs[random.nextInt(848)]);
    }
    
    public void setNowPlayingText(){
    	currentSong = extractSongName();
        currentSongText.setText(currentSong);
		currentArtist = extractSongArtist();
		currentArtistText.setText(currentArtist);
    }
    
    public String extractSongArtist(){
    	String stripped, artistName;
    	stripped = songURL.toString().replace("http://www.soundserum.com/mp3/", "");
    	stripped = stripped.replace(".mp3", "");
    	int hypthen = stripped.lastIndexOf("-");
    	artistName = stripped.substring(hypthen+1, stripped.length() );
    	artistName = artistName.replace("_", " ");
    	
    	return artistName;
    }
    
    public String extractSongName(){
    	String stripped, songName;
    	stripped = songURL.toString().replace("http://www.soundserum.com/mp3/", "");
    	stripped = stripped.replace(".mp3", "");
    	int hypthen = stripped.indexOf("-");
    	songName = stripped.substring(0, hypthen);
    	songName = songName.replace("_", " ");
    	
    	return songName;
    }
}
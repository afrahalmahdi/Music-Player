package com.example.music_player;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_GAIN;
import static android.media.AudioManager.AUDIOFOCUS_LOSS;

public class NewMusicActivity extends AppCompatActivity {

    ArrayList<Song> songs;

    RecyclerView recyclerView;

    SongAdapter adapter;

    // Handles audio focus when playing a sound file
    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_list);

        Intent intent = getIntent();

        songs = new ArrayList<Song>();
        songs.add(new Song("Enrique Iglesias", "Too Good", R.drawable.english_too_good, R.raw.top_little_me));
        songs.add(new Song("Clean Bandit", "Symphony ", R.drawable.english_symphony, R.raw.top_little_me));
        songs.add(new Song("Ali Gatie", "It's You", R.drawable.it_is_you, R.raw.top_little_me));
        songs.add(new Song("إليسا", "حلوة يا بلدي", R.drawable.new_music_helwa_ya_baladi, R.raw.top_little_me));
        songs.add(new Song("Camila Cabello", "Havana", R.drawable.new_music_havana, R.raw.top_little_me));
        songs.add(new Song("Taylor Swift", "Blank Space", R.drawable.new_music_blank_space, R.raw.top_little_me));
        songs.add(new Song("Kelly Clarkson", "Because of You", R.drawable.new_music_because_of_you, R.raw.top_little_me));
        songs.add(new Song("David Guetta ft Justin Bieber", "2U (SING OFF vs. Olly Murs)", R.drawable.new_music_2u, R.raw.top_little_me));
        songs.add(new Song("Zayn", "Dusk To Dawn", R.drawable.new_music_dusk_till_dawn, R.raw.top_little_me));
        songs.add(new Song("Rihanna", "Love The Way You Lie", R.drawable.new_music_love_the_way_you_lie, R.raw.top_little_me));


        recyclerView = findViewById(R.id.word_list_rv);

        UpdateUI();

        // Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        /**
         * This listener gets triggered whenever the audio focus changes
         * (i.e., we gain or lose audio focus because of another app or device).
         */
        mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {

                        /* The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                          short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                          our app is allowed to continue playing sound but at a lower volume. We'll treat
                          both cases the same way because our app is playing short sound files. */

                        /* Pause playback and reset player to the start of the file. That way, we can
                         play the word from the beginning when we resume playback. */
                    SongViewHolder.mediaPlayer.pause();
                    //WordViewHolder.mediaPlayer.seekTo((int) WordAdapter.mediaPlayer.getCurrentPosition());
                    SongViewHolder.mediaPlayer.seekTo(0);
                } else if (focusChange == AUDIOFOCUS_GAIN) {
                    // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                    SongViewHolder.mediaPlayer.start();
                } else if (focusChange == AUDIOFOCUS_LOSS) {
                        /* The AUDIOFOCUS_LOSS case means we've lost audio focus and
                          Stop playback and clean up resources */
                    SongViewHolder.releaseMediaPlayer();
                }
            }
        };
        mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener
                , AudioManager.STREAM_MUSIC
                , AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

    }

    private void UpdateUI() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new SongAdapter(songs, getBaseContext());

        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        /* When the activity is stopped, release the media player resources because we won't
         be playing any more sounds. */
        SongViewHolder.releaseMediaPlayer();

        mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
    }
}
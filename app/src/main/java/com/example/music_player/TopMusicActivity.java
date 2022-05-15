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

public class TopMusicActivity extends AppCompatActivity {

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
        songs.add(new Song("Little Mix", "Little Me", R.drawable.top_little_me, R.raw.top_little_me));
        songs.add(new Song("Alan Walker & Ina Wroldsen", "Strongest", R.drawable.top_strongest, R.raw.top_little_me));
        songs.add(new Song("بلقيس", "جبّار", R.drawable.top_jabbar, R.raw.top_little_me));
        songs.add(new Song("Alan Walker", "Faded", R.drawable.top_faded, R.raw.top_little_me));
        songs.add(new Song("Adele", "Someone Like You", R.drawable.top_someone_like_you, R.raw.top_little_me));
        songs.add(new Song("إليسا", "موطني", R.drawable.top_mawtini, R.raw.top_little_me));
        songs.add(new Song("Sia", "Angel By The Wings", R.drawable.english_angel_by_the_wings, R.raw.top_little_me));
        songs.add(new Song("Rihanna", "I'm Alive", R.drawable.top_i_am_alive, R.raw.top_little_me));
        songs.add(new Song("Justin Bieber & Ed Sheeran", "I Don't Care", R.drawable.english_i_do_not_care, R.raw.top_little_me));
        songs.add(new Song("", "I'm a Mes", R.drawable.top_i_am_a_mess, R.raw.top_little_me));
        songs.add(new Song("Charlie Puth", "We Don't Talk Anymore", R.drawable.top_we_dont_talk_anymore, R.raw.top_little_me));

//        for (Word word : FakeSongs.songsList) {
//            if (word.getSongType() == SongType.ArabicSong) {
//                words.add(word);
//            }
//        }

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
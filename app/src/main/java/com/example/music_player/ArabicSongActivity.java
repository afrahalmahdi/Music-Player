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

public class ArabicSongActivity extends AppCompatActivity {

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
        songs.add(new Song("محمد عادل", "بحب أمي", R.drawable.arabic_bahib_aumi, R.raw.top_little_me));
        songs.add(new Song("فيروز", "بعدك على بالي", R.drawable.arabic_baadak_ala_bali, R.raw.top_little_me));
        songs.add(new Song("كارلا شمعون", "يا عاشقة الورد", R.drawable.arabic_ya_aashikat_al_wardi, R.raw.top_little_me));
        songs.add(new Song("محمد حماقي", "حاجه مستخبيه", R.drawable.arabic_haga_mestakhabeya, R.raw.top_little_me));
        songs.add(new Song("رامي عياش", "قصة حب", R.drawable.english_qesset_hob, R.raw.top_little_me));
        songs.add(new Song("اصالة نصري", "60 دقيقة حياة", R.drawable.arabic_60_dqiqa_haiah, R.raw.top_little_me));
        songs.add(new Song("عبدالرحمن محمد & مهاب عمر", "بروحي_فتاةٌ", R.drawable.arabic_a_girl_within_my_soul, R.raw.top_little_me));
        songs.add(new Song("ابو", "ثلاث دقات(مع يسرا)", R.drawable.arabic_3_daqat, R.raw.top_little_me));
        songs.add(new Song("كاظم الساهر", "أحبيني بلا عقد", R.drawable.arabic_ahebini, R.raw.top_little_me));
        songs.add(new Song("حسين الجسمي", "أبشرك", R.drawable.arabic_abshrek, R.raw.top_little_me));
        songs.add(new Song("زينة عماد", "لو على قلبي", R.drawable.arabic_law_ala_albi, R.raw.top_little_me));
        songs.add(new Song("يارا", "ما بعرف", R.drawable.arabic_ma_baaref, R.raw.top_little_me));
        songs.add(new Song("نانسي عجرم", "قلبي يا قلبي", R.drawable.arabic_albi_ya_albi, R.raw.top_little_me));


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
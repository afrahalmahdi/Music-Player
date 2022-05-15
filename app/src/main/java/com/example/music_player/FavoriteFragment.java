package com.example.music_player;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_GAIN;
import static android.media.AudioManager.AUDIOFOCUS_LOSS;

public class FavoriteFragment extends Fragment {

    public static ArrayList<Song> words = new ArrayList<Song>();

    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;

    SongAdapter adapter;

    // Handles audio focus when playing a sound file
    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener;

    Context context;


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.song_list, container, false);

//        for (Word word : FakeSongs.songsList) {
//            if (word.isFavourite() == true) {
//                words.add(word);
//            }
//        }

        linearLayoutManager = new LinearLayoutManager(context);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.word_list_rv);

        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new SongAdapter(words, getActivity());

        recyclerView.setAdapter(adapter);
        // Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

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

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();

        /* When the activity is stopped, release the media player resources because we won't
         be playing any more sounds. */
        SongViewHolder.releaseMediaPlayer();

        mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
    }
}
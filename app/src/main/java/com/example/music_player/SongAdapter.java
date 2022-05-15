package com.example.music_player;

import android.content.Context;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


/*
 * {@link SongAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
 * based on a data source, which is a list of {@link Song} objects.
 * */
public class SongAdapter extends RecyclerView.Adapter<SongViewHolder> {

    public ArrayList<Song> mSongList;

    Context mContext;

    /**
     * Resource ID for the background color for this list of songs
     */
    public int mColor;


    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     * <p>
     * //@param context The current context. Used to inflate the layout file.
     *
     * @param songs A List of Song objects to display in a list
     * @param context
     */
    public SongAdapter(ArrayList<Song> songs, Context context) {
        mSongList = songs;
        mContext = context;
    }

    public SongAdapter() {

    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new SongViewHolder(LayoutInflater.from(mContext), parent);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {

        Song song = mSongList.get(position);

        holder.bind(song);
    }

    @Override
    public int getItemCount() {
        return mSongList.size();
    }
}

    // {@link SongViewHolder} is a new class
     class SongViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView artistsTextView,nameOfSongTextView;

        ImageView imageView;

        LinearLayout linearLayout;
        CheckBox favouriteCheckBox;

        static MediaPlayer mediaPlayer;

        Context context;

        Song songObject;

        static int result;

        // This is our own custom constructor
        public SongViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item, parent, false));

            artistsTextView = itemView.findViewById(R.id.artists_text_view);
            nameOfSongTextView = itemView.findViewById(R.id.song_text_view);
            imageView = itemView.findViewById(R.id.image);
            linearLayout = itemView.findViewById(R.id.text_container);
            favouriteCheckBox = itemView.findViewById(R.id.favouriteCheckBox);

            favouriteCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    SongAdapter wordAdapter = new SongAdapter();
                    if(!songObject.isFavourite() && b)
                    {
                        Log.v("song favourite true", "true");
                        songObject.setFavourite(true);
                        favouriteCheckBox.setChecked(true);
                        FavoriteFragment.words.add(songObject);
                        Log.v("favourite list size ", String.valueOf(FavoriteFragment.words.size()));
                    } else if(b == false && songObject.isFavourite()){
                        Log.v("song favourite false", "false");
                        songObject.setFavourite(false);
                        favouriteCheckBox.setChecked(false);

                        FavoriteFragment.words.remove(songObject);
                        Log.v("favourite list size ", String.valueOf(FavoriteFragment.words.size()));

                    }
                }
            });
            context = inflater.getContext();
            itemView.setOnClickListener(this);
        }

        public void bind(Song songParm) {
            songObject = songParm;

            if(songObject != null)
            favouriteCheckBox.setChecked(songObject.isFavourite());

           // linearLayout.setBackgroundResource(color);
            artistsTextView.setText(songParm.getArtistName());
            nameOfSongTextView.setText(songParm.getNameOfSong());

            // Get the image resource ID from the current Song object and
            // Check if an image is provided for this song or not
            if (songParm.hasImage()) {
                // If an image is available, display the provided image based on the resource ID
                imageView.setImageResource(songParm.getImageResourceId());
                Functions.getRoundedImage(imageView);

                // Make sure the view is visible
                imageView.setVisibility(View.VISIBLE);
            } else {
                // Otherwise hide the ImageView (set visibility to GONE)
                imageView.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View view) {
            releaseMediaPlayer();

            if(result == AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
                mediaPlayer = MediaPlayer.create(context, songObject.getAudioResourceId());

                mediaPlayer.start();

                /**
                 * This listener gets triggered when the {@link MediaPlayer} has completed
                 * playing the audio file.
                 */
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {

                        // Now that the sound file has finished playing, release the media player resources.
                        releaseMediaPlayer();
                    }
                });
            }
        }

    /**
     * Clean up the media player by releasing its resources.
     */
    public static void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {

            /* Regardless of the current state of the media player, release its resources
                because we no longer need it.
             */
            mediaPlayer.release();

            /* Set the media player back to null. For our code, we've decided that
                setting the media player to null is an easy way to tell that the media player
                is not configured to play an audio file at the moment.
             */
            mediaPlayer = null;

            /* Regardless of whether or not we were granted audio focus, abandon it. This also
                unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
             */
            //mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
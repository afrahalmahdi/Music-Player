package com.example.music_player.Adapter;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.music_player.Functions;
import com.example.music_player.R;
import com.example.music_player.Song;

import java.util.List;


public class HorizontalRecyclerAdapter extends RecyclerView.Adapter<MainViewHolder> {

    public List<Song> listItems;

    Context mContext;

    public HorizontalRecyclerAdapter(List<Song> listItems, Context context) {
        this.listItems = listItems;
        mContext = context;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MainViewHolder(LayoutInflater.from(mContext), parent);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        Song song = listItems.get(position);
        holder.bind(song);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

}

    class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textView1, textView2;
        ImageView imageView;
        LinearLayout linearLayout;
        static int result;
        static MediaPlayer mediaPlayer;
        Song songObject;
        Context context;

        public MainViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.recycler_items_horizontal, parent, false));

            textView1 = itemView.findViewById(R.id.song_text_view1);
            textView2 = itemView.findViewById(R.id.artists_text_view2);
            imageView = itemView.findViewById(R.id.horizontal_image);
            linearLayout = itemView.findViewById(R.id.horizontal_text_container);
            context = inflater.getContext();
            itemView.setOnClickListener(this);
        }

        public void bind(Song songParm) {
            songObject = songParm;

            textView1.setText(songParm.getArtistName());
            textView2.setText(songParm.getNameOfSong());
            imageView.setImageResource(songParm.getImageResourceId());
            Functions.getRoundedImage(imageView);
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

            }
        }
}

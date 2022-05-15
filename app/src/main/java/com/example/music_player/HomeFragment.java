package com.example.music_player;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.music_player.Adapter.HorizontalRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private RecyclerView trendingRecyclerView, sharedRecyclerView;
    List<Song> listItems;
    Context context;
    LinearLayoutManager linearLayoutManager;
    HorizontalRecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Find the view that shows the *Search Activity*
        TextView search = (TextView) rootView.findViewById(R.id.search_TextView);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);

                startActivity(intent);
            }
        });


        // Find the view that shows the *New Music*
        TextView new_music = (TextView) rootView.findViewById(R.id.new_music_id);

        // Set a click listener on that view
        new_music.setOnClickListener(new View.OnClickListener() {

            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {

                // Create a new intent to open the {@link NewMusicActivity}
                Intent intent = new Intent(getActivity(), NewMusicActivity.class);

                // Start the new activity
                startActivity(intent);
            }
        });
        // End of *New Music*

        // Find the view that shows the *Top Activity*
        TextView top = (TextView) rootView.findViewById(R.id.top_id);

        // Set a click listener on that view
        top.setOnClickListener(new View.OnClickListener() {

            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {

                // Create a new intent to open the {@link TopActivity}
                Intent intent = new Intent(getActivity(), TopMusicActivity.class);

                // Start the new activity
                startActivity(intent);
            }
        });
        // End of *Top Activity*

        // Find the view that shows the *Arabic Song Activity*
        TextView arabic = (TextView) rootView.findViewById(R.id.arabic_id);

        // Set a click listener on that view
        arabic.setOnClickListener(new View.OnClickListener() {

            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {

                // Create a new intent to open the {@link ArabicSongActivity}
                Intent intent = new Intent(getActivity(), ArabicSongActivity.class);

                // Start the new activity
                startActivity(intent);
            }
        });
        // End of *Arabic Song Activity*

        // Find the view that shows the *English Song Activity*
        TextView english = (TextView) rootView.findViewById(R.id.english_id);

        // Set a click listener on that view
        english.setOnClickListener(new View.OnClickListener() {

            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {

                // Create a new intent to open the {@link EnglishSongActivity}
                Intent intent = new Intent(getActivity(), EnglishSongActivity.class);

                // Start the new activity
                startActivity(intent);
            }
        });
        // End of *English Song Activity*

        // Trending RecyclerView
        trendingRecyclerView = rootView.findViewById(R.id.main_recycler_view);

        linearLayoutManager = new LinearLayoutManager(context);

        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        trendingRecyclerView.setLayoutManager(linearLayoutManager);

        trendingRecyclerView.setHasFixedSize(true);

        listItems = new ArrayList<>();
        listItems.add(new Song("Charlie Puth", "Attention", R.drawable.attention, R.raw.top_little_me));
        listItems.add(new Song("Ed Sheeran", "Perfect", R.drawable.perfect, R.raw.top_little_me));
        listItems.add(new Song("Sean Paul & Anne Marie", "Rockabye", R.drawable.rockabye, R.raw.top_little_me));
        listItems.add(new Song("Enrique Iglesias", "Somebody's Me", R.drawable.somebody_is_me, R.raw.top_little_me));
        listItems.add(new Song("Alec Benjamin", "Let Me Down Slowly", R.drawable.english_let_me_down_slowly, R.raw.top_little_me));
        listItems.add(new Song("Alan Walker", "On My Way", R.drawable.on_my_way, R.raw.top_little_me));

        adapter = new HorizontalRecyclerAdapter(listItems, getContext());
        trendingRecyclerView.setAdapter(adapter);

        //trendingRecyclerView.setAdapter(new HorizontalRecyclerAdapter(listItems));



        // Most Shared RecyclerView
        sharedRecyclerView = rootView.findViewById(R.id.main_recycler_view2);

        linearLayoutManager = new LinearLayoutManager(context);

        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        sharedRecyclerView.setLayoutManager(linearLayoutManager);

        sharedRecyclerView.setHasFixedSize(true);

        listItems = new ArrayList<>();
        listItems.add(new Song("إليسا", "موطني", R.drawable.top_mawtini, R.raw.top_little_me));
        listItems.add(new Song("Sia", "Angel By The Wings", R.drawable.english_angel_by_the_wings, R.raw.top_little_me));
        listItems.add(new Song("Rihanna", "I'm Alive", R.drawable.top_i_am_alive, R.raw.top_little_me));
        listItems.add(new Song("Justin Bieber & Ed Sheeran", "I Don't Care", R.drawable.english_i_do_not_care, R.raw.top_little_me));
        listItems.add(new Song("", "I'm a Mes", R.drawable.top_i_am_a_mess, R.raw.top_little_me));
        listItems.add(new Song("Charlie Puth", "We Don't Talk Anymore", R.drawable.top_we_dont_talk_anymore, R.raw.top_little_me));

        adapter = new HorizontalRecyclerAdapter(listItems, getContext());
        sharedRecyclerView.setAdapter(adapter);

        return rootView;

    }

}
package com.example.ljap;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorsFragment extends Fragment {


    public ColorsFragment() {
        // Required empty public constructor
    }

    private MediaPlayer med;

    private AudioManager mAudioManager;

    /**
     * This listener gets triggered whenever the audio focus changes
     * (i.e., we gain or lose audio focus because of another app or device).
     */
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                med.pause();
                med.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                med.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };

    public void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (med != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            med.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            med = null;

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);


        }
    }

    private MediaPlayer.OnCompletionListener mcheckstop = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();

        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.words_list, container, false);




        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);


        final ArrayList<liste> numarr = new ArrayList<>();


        numarr.add(new liste("Orange","Orenji",R.drawable.color_orange,R.raw.colour_orange));
        numarr.add(new liste("Yellow","Ki-iro",R.drawable.color_yellow,R.raw.colour_yellow));
        numarr.add(new liste("Blue","Ao",R.drawable.color_blue,R.raw.colour_blue));
        numarr.add(new liste("Green","Midori",R.drawable.color_green,R.raw.colour_green));
        numarr.add(new liste("Red","Aka",R.drawable.color_red,R.raw.colour_red));
        numarr.add(new liste("Black","Kuro",R.drawable.color_black,R.raw.colour_black));
        numarr.add(new liste("Pink","Pinku",R.drawable.color_pink,R.raw.colour_pink));
        numarr.add(new liste("Rainbow","Tashokuno",R.drawable.color_rainbow,R.raw.colour_rainbow));


        listadapter numadap = new listadapter(getActivity(), numarr, R.color.lred);

        ListView numlist = rootView.findViewById(R.id.numroot);

        numlist.setAdapter(numadap);


        numlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                liste list = numarr.get(position);
                releaseMediaPlayer();


                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    med = MediaPlayer.create(getActivity(), list.getMaudres());


                    med.start();

                    med.setOnCompletionListener(mcheckstop);


                }


            }
        });




        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}

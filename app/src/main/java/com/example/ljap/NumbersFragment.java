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
public class NumbersFragment extends Fragment {


    public NumbersFragment() {
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


        numarr.add(new liste("One", "ichi", R.drawable.number_one, R.raw.number_one));
        numarr.add(new liste("Two", "ni", R.drawable.number_two, R.raw.number_two));
        numarr.add(new liste("Three", "san", R.drawable.number_three, R.raw.number_three));
        numarr.add(new liste("Four", "yon", R.drawable.number_four, R.raw.number_four));
        numarr.add(new liste("Five", "go", R.drawable.number_five, R.raw.number_five));
        numarr.add(new liste("Six", "roku", R.drawable.number_six, R.raw.number_six));
        numarr.add(new liste("Seven", "nana", R.drawable.number_seven, R.raw.number_seven));
        numarr.add(new liste("Eight", "hachi", R.drawable.number_eight, R.raw.number_eight));
        numarr.add(new liste("Nine", "kyuu", R.drawable.number_nine, R.raw.number_nine));
        numarr.add(new liste("Ten", "juu", R.drawable.number_ten, R.raw.number_ten));
        numarr.add(new liste("100", "hyaku", R.raw.number_100));
        numarr.add(new liste("1000", "sen", R.raw.number_1000));
        numarr.add(new liste("157", "hyaku go-juu nana", R.raw.number_157));


        listadapter numadap = new listadapter(getActivity(), numarr, R.color.lorange);

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

package edu.orangecoastcollege.cs273.rmillett.northernlightsanimation;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * <code>AnimationActivity</code> allows the user to interact with an animation by toggling or
 * adjusting various
 *
 */
public class AnimationActivity extends AppCompatActivity {

    public static final String TAG = "NorthernLightsAnimation";

    private AnimationDrawable frameAnim; // AnimationDrawable class used for frame animations
    private Animation rotateAnim; // Animation class used for tween(ed) animations
    private Animation shakeAnim;
    private Animation customAnim;

    private ImageView lightsImageView; // link to VIEW

    /**
     * Creates an instance of <code>AnimationActivity</code> in the view
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        lightsImageView = (ImageView) findViewById(R.id.lightsImageView);
    }

    /**
     * Toggles between a still image and a frame animation
     * @param view
     */
    public void toggleFrameAnim(View view) {
        // if frameAnim hasn't been initialized...
        if (frameAnim == null) {
            lightsImageView.setBackgroundResource(R.drawable.frame_anim);
            frameAnim = (AnimationDrawable) lightsImageView.getBackground();
        }

        // if frameAnim is running, stop it
        if (frameAnim.isRunning()) frameAnim.stop();
        // else, stop it
        else frameAnim.start();
    }

    /**
     * Toggles image rotation on or off
     * @param view
     */
    public void toggleRotateAnim(View view) {
        // if rotateAnim hasn't been initialized...
        if (rotateAnim == null)
            rotateAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);

        // if hasn't started or has completed
        if (!rotateAnim.hasStarted() || rotateAnim.hasEnded())
            lightsImageView.startAnimation(rotateAnim);
    }

    /**
     * Quickly shakes the image view left-to-right
     * @param view
     */
    public void toggleShakeAnim(View view) {
        shakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake_anim);
        lightsImageView.startAnimation(shakeAnim);
    }

    /**
     * Triggers a custom animation
     * @param view
     */
    public void toggleCustomAnim(View view) {
        if (customAnim == null) {
            customAnim = AnimationUtils.loadAnimation(this, R.anim.custom_anim);
            Log.e(TAG, "customAnim Initialized");
        }

        if (!customAnim.hasStarted() || customAnim.hasEnded()) {
            lightsImageView.startAnimation(customAnim);
            Log.e(TAG, "customAnim Started");
        }
        else {
            lightsImageView.clearAnimation();
            Log.e(TAG, "customAnim Cleared");
        }
    }
}

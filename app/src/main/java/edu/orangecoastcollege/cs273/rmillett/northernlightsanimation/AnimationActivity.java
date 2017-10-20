package edu.orangecoastcollege.cs273.rmillett.northernlightsanimation;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
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

    private Handler handler;

    private Button frameAnimButton;
    private Button rotateAnimButton;
    private Button shakeAnimButton;
    private Button customAnimButton;

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

        frameAnimButton = (Button) findViewById(R.id.frameAnimButton);
        rotateAnimButton = (Button) findViewById(R.id.rotateAnimButton);
        shakeAnimButton = (Button) findViewById(R.id.shakeAnimButton);
        customAnimButton = (Button) findViewById(R.id.customAnimButton);

        handler = new Handler();
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

        if (frameAnim.isRunning()) {
            frameAnim.stop();
            frameAnimButton.setText(getString(R.string.frame_anim_button_text_off));
        }
        else {
            frameAnim.start();
            frameAnimButton.setText(getString(R.string.frame_anim_button_text_on));
        }
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
        if (!rotateAnim.hasStarted() || rotateAnim.hasEnded()) {
            lightsImageView.startAnimation(rotateAnim);
            shakeAnimButton.setEnabled(false);
            customAnimButton.setEnabled(false);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    shakeAnimButton.setEnabled(true);
                    customAnimButton.setEnabled(true);
                }
            }, 2000);
        }
    }

    /**
     * Quickly shakes the image view left-to-right
     * @param view
     */
    public void toggleShakeAnim(View view) {
        shakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake_anim);
        lightsImageView.startAnimation(shakeAnim);

        if (!shakeAnim.hasStarted() || shakeAnim.hasEnded()) {
            rotateAnimButton.setEnabled(false);
            customAnimButton.setEnabled(false);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    rotateAnimButton.setEnabled(true);
                    customAnimButton.setEnabled(true);
                }
            }, 1200);
        }
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
            customAnimButton.setText(getString(R.string.custom_anim_button_text_on));
            enterVoid();
            Log.e(TAG, "customAnim Started");
        }
        else {
            lightsImageView.clearAnimation();
            customAnimButton.setText(getString(R.string.custom_anim_button_text_off));
            exitVoid();
            Log.e(TAG, "customAnim Cleared");
        }
    }

    private void enterVoid() {
        // disable other buttons
        frameAnimButton.setEnabled(false);
        rotateAnimButton.setEnabled(false);
        shakeAnimButton.setEnabled(false);

        // start frame animation
        if (frameAnim == null) {
            lightsImageView.setBackgroundResource(R.drawable.frame_anim);
            frameAnim = (AnimationDrawable) lightsImageView.getBackground();
        }
        if (!frameAnim.isRunning()) {
            frameAnim.start();
            frameAnimButton.setText(getString(R.string.frame_anim_button_text_on));
        }

        // TODO: change global theme to dark
    }

    private void exitVoid() {
        // enable other buttons
        frameAnimButton.setEnabled(true);
        rotateAnimButton.setEnabled(true);
        shakeAnimButton.setEnabled(true);

        // stop frame animation
        frameAnim.stop();
        frameAnimButton.setText(getString(R.string.frame_anim_button_text_off));

        // TODO: change global theme back to normal
    }
}

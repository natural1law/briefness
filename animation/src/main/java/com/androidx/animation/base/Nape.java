package com.androidx.animation.base;

import android.util.Log;

import com.androidx.animation.figure.ChartRect;
import com.androidx.animation.figure.Circle;
import com.androidx.animation.figure.Clock;
import com.androidx.animation.figure.DoubleCircle;
import com.androidx.animation.figure.ElasticBall;
import com.androidx.animation.figure.InfectionBall;
import com.androidx.animation.figure.Intertwine;
import com.androidx.animation.figure.Leaf;
import com.androidx.animation.figure.MusicPath;
import com.androidx.animation.figure.PacMan;
import com.androidx.animation.figure.RotateCircle;
import com.androidx.animation.figure.SearchPath;
import com.androidx.animation.figure.SingleCircle;
import com.androidx.animation.figure.SnakeCircle;
import com.androidx.animation.figure.StairsPath;
import com.androidx.animation.figure.StairsRect;
import com.androidx.animation.figure.Star;
import com.androidx.animation.figure.Text;

public final class Nape {

    private Nape() {
    }

    private static final Class<?>[] BASES = {
            Circle.class,
            Clock.class,
            Star.class,
            Leaf.class,
            DoubleCircle.class,
            PacMan.class,
            ElasticBall.class,
            InfectionBall.class,
            Intertwine.class,
            Text.class,
            SearchPath.class,
            RotateCircle.class,
            SingleCircle.class,
            SnakeCircle.class,
            StairsPath.class,
            MusicPath.class,
            StairsRect.class,
            ChartRect.class
    };

    @SuppressWarnings("unchecked")
    public static <T extends BaseAnimator> T newInstance(int type) {
        try {
            return (T) BASES[type].newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            Log.e("Nape异常", Log.getStackTraceString(e));
            return null;
        }
    }
}

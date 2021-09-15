package com.androidx.animation.base;

import android.util.Log;

import com.androidx.animation.figure.ball.ElasticBallBuilder;
import com.androidx.animation.figure.ball.InfectionBallBuilder;
import com.androidx.animation.figure.ball.IntertwineBuilder;
import com.androidx.animation.figure.circle.DoubleCircleBuilder;
import com.androidx.animation.figure.circle.PacManBuilder;
import com.androidx.animation.figure.circle.RotateCircleBuilder;
import com.androidx.animation.figure.circle.SingleCircleBuilder;
import com.androidx.animation.figure.circle.SnakeCircleBuilder;
import com.androidx.animation.figure.clock.CircleBuilder;
import com.androidx.animation.figure.clock.ClockBuilder;
import com.androidx.animation.figure.path.MusicPathBuilder;
import com.androidx.animation.figure.path.SearchPathBuilder;
import com.androidx.animation.figure.path.StairsPathBuilder;
import com.androidx.animation.figure.rect.ChartRectBuilder;
import com.androidx.animation.figure.rect.StairsRectBuilder;
import com.androidx.animation.figure.star.LeafBuilder;
import com.androidx.animation.figure.star.StarBuilder;
import com.androidx.animation.figure.text.TextBuilder;

public enum ProgressType {
    CIRCLE(CircleBuilder.class),
    CIRCLE_CLOCK(ClockBuilder.class),
    STAR_LOADING(StarBuilder.class),
    LEAF_ROTATE(LeafBuilder.class),
    DOUBLE_CIRCLE(DoubleCircleBuilder.class),
    PAC_MAN(PacManBuilder.class),
    ELASTIC_BALL(ElasticBallBuilder.class),
    INFECTION_BALL(InfectionBallBuilder.class),
    INTERTWINE(IntertwineBuilder.class),
    TEXT(TextBuilder.class),
    SEARCH_PATH(SearchPathBuilder.class),
    ROTATE_CIRCLE(RotateCircleBuilder.class),
    SINGLE_CIRCLE(SingleCircleBuilder.class),
    SNAKE_CIRCLE(SnakeCircleBuilder.class),
    STAIRS_PATH(StairsPathBuilder.class),
    MUSIC_PATH(MusicPathBuilder.class),
    STAIRS_RECT(StairsRectBuilder.class),
    CHART_RECT(ChartRectBuilder.class);

    private final Class<?> builder;

    ProgressType(Class<?> builderClass) {
        this.builder = builderClass;
    }

    @SuppressWarnings("unchecked")
    public <T extends BaseAnimator> T newInstance() {
        try {
            return (T) builder.newInstance();
        } catch (Exception e) {
            Log.e("ProgressType异常", Log.getStackTraceString(e));
            return null;
        }
    }
}

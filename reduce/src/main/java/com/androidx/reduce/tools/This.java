package com.androidx.reduce.tools;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.androidx.reduce.config.ThisListener;

import static android.content.Context.BIND_AUTO_CREATE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * @date 2021/06/09
 */
@SuppressWarnings("unused")
public final class This implements ThisListener {

    private volatile Handler handler;
    private static volatile Runnable run;
    private static volatile This instance;

    private This() {
        handler = new Handler(Looper.myLooper());
    }

    public static This build() {
        if (instance == null) synchronized (This.class) {
            if (instance == null) instance = new This();
        }
        return instance;
    }

    public void delay(Runnable run, long t) {
        handler.postDelayed(run, t);
    }

    public void atTime(Runnable run, long t) {
        handler.postAtTime(run, t);
    }

    @Override
    public void start() {
        if (run != null && handler != null) handler.post(run);
    }

    @Override
    public void reset() {
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
            if (run != null) handler.post(run);
        }
    }

    @Override
    public void stop() {
        if (run != null) handler.removeCallbacks(run);
        handler.removeCallbacksAndMessages(null);
        handler = null;
    }

    /**
     * 启动activity（无传递参数,默认动画）
     */
    public ThisListener activity(@NonNull Context a, @NonNull Class<? extends Context> c) {
        run = () -> a.startActivity(new Intent(a, c).addFlags(FLAG_ACTIVITY_NEW_TASK));
        return this;
    }

    /**
     * 启动activity（无传递参数,默认动画）
     */
    public ThisListener activity(@NonNull Activity a, @NonNull Class<? extends Context> c) {
        run = () -> a.startActivity(new Intent(a, c));
        return this;
    }

    /**
     * 启动activity（无传递参数,默认动画）
     */
    public ThisListener activity(@NonNull AppCompatActivity a, @NonNull Class<? extends Context> c) {
        run = () -> a.startActivity(new Intent(a, c));
        return this;
    }

    /**
     * 启动activity（无传递参数,默认动画）
     */
    public ThisListener activity(@NonNull FragmentActivity a, @NonNull Class<? extends Context> c) {
        run = () -> a.startActivity(new Intent(a, c));
        return this;
    }

    /**
     * 启动activity（无传递参数,默认动画）
     */
    public ThisListener activity(@NonNull Activity a, @NonNull Class<? extends Context> c, boolean animation) {
        if (animation) {
            run = () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivity(intent);
            };
            return this;
        } else {
            return activity(a, c);
        }
    }

    /**
     * 启动activity（无传递参数,默认动画）
     */
    public ThisListener activity(@NonNull AppCompatActivity a, @NonNull Class<? extends Context> c, boolean animation) {
        if (animation) {
            run = () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivity(intent);
            };
            return this;
        } else {
            return activity(a, c);
        }
    }

    /**
     * 启动activity（无传递参数,默认动画）
     */
    public ThisListener activity(@NonNull FragmentActivity a, @NonNull Class<? extends Context> c, boolean animation) {
        if (animation) {

            run = () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivity(intent);
            };
            return this;
        } else {
            return activity(a, c);
        }
    }


    /**
     * 启动activity（无传递参数,默认动画,完成）
     */
    public ThisListener activityFinish(@NonNull Activity a, @NonNull Class<? extends Context> c) {
        run = () -> {
            a.startActivity(new Intent(a, c));
            a.finish();
        };
        return this;
    }

    /**
     * 启动activity（无传递参数,默认动画,完成）
     */
    public ThisListener activityFinish(@NonNull AppCompatActivity a, @NonNull Class<? extends Context> c) {
        run = () -> {
            a.startActivity(new Intent(a, c));
            a.finish();
        };
        return this;
    }

    /**
     * 启动activity（无传递参数,默认动画,完成）
     */
    public ThisListener activityFinish(@NonNull FragmentActivity a, @NonNull Class<? extends Context> c) {
        run = () -> {
            a.startActivity(new Intent(a, c));
            a.finish();
        };
        return this;
    }

    /**
     * 启动activity（无传递参数,动画,完成）
     */
    public ThisListener activityFinish(@NonNull Activity a, @NonNull Class<? extends Context> c, boolean animation) {
        if (animation) {
            run = () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivity(intent);
                a.finishAfterTransition();
            };
            return this;
        } else {
            return activity(a, c);
        }
    }

    /**
     * 启动activity（无传递参数,动画,完成）
     */
    public ThisListener activityFinish(@NonNull AppCompatActivity a, @NonNull Class<? extends Context> c, boolean animation) {
        if (animation) {
            run = () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivity(intent);
                a.finishAfterTransition();
            };
            return this;
        } else {
            return activityFinish(a, c);
        }
    }

    /**
     * 启动activity（无传递参数,动画,完成）
     */
    public ThisListener activityFinish(@NonNull FragmentActivity a, @NonNull Class<? extends Context> c, boolean animation) {
        if (animation) {
            run = () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivity(intent);
                a.finishAfterTransition();
            };
            return this;
        } else {
            return activityFinish(a, c);
        }
    }

    /**
     * 启动activity（传递参数,动画,完成）
     */
    public ThisListener activity(@NonNull Context a, @NonNull Class<? extends Context> c, Bundle b) {
        run = () -> a.startActivity(new Intent(a, c).addFlags(FLAG_ACTIVITY_NEW_TASK).putExtras(b));
        return this;
    }

    /**
     * 启动activity（传递参数,默认动画）
     */
    public ThisListener activityParam(@NonNull Activity a, @NonNull Class<? extends Context> c, Bundle b) {
        run = () -> a.startActivity(new Intent(a, c).putExtras(b));
        return this;
    }

    /**
     * 启动activity（传递参数,默认动画）
     */
    public ThisListener activityParam(@NonNull AppCompatActivity a, @NonNull Class<? extends Context> c, Bundle b) {
        run = () -> a.startActivity(new Intent(a, c).putExtras(b));
        return this;
    }

    /**
     * 启动activity（传递参数,默认动画）
     */
    public ThisListener activityParam(@NonNull FragmentActivity a, @NonNull Class<? extends Context> c, Bundle b) {
        run = () -> a.startActivity(new Intent(a, c).putExtras(b));
        return this;
    }


    /**
     * 启动activity（无传递参数,动画,完成）
     */
    public ThisListener activityParam(@NonNull Activity a, @NonNull Class<? extends Context> c, Bundle b, boolean animation) {
        if (animation) {
            run = () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(b);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivity(intent);
            };
            return this;
        } else {
            return activityParam(a, c, b);
        }
    }

    /**
     * 启动activity（无传递参数,动画,完成）
     */
    public ThisListener activityParam(@NonNull AppCompatActivity a, @NonNull Class<? extends Context> c, Bundle b, boolean animation) {
        if (animation) {
            run = () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(b);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivity(intent);
            };
            return this;
        } else {
            return activityParam(a, c, b);
        }
    }

    /**
     * 启动activity（无传递参数,动画,完成）
     */
    public ThisListener activityParam(@NonNull FragmentActivity a, @NonNull Class<? extends Context> c, Bundle b, boolean animation) {
        if (animation) {
            run = () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(b);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivity(intent);
            };
            return this;
        } else {
            return activityParam(a, c, b);
        }
    }

    /**
     * 启动activity（传递参数,默认动画,完成）
     */
    public ThisListener activityParamFinish(@NonNull Activity a, @NonNull Class<? extends Context> c, Bundle b) {
        run = () -> {
            a.startActivity(new Intent(a, c).putExtras(b));
            a.finish();
        };
        return this;
    }

    /**
     * 启动activity（传递参数,默认动画,完成）
     */
    public ThisListener activityParamFinish(@NonNull AppCompatActivity a, @NonNull Class<? extends Context> c, Bundle b) {
        run = () -> {
            a.startActivity(new Intent(a, c).putExtras(b));
            a.finish();
        };
        return this;
    }

    /**
     * 启动activity（传递参数,默认动画,完成）
     */
    public ThisListener activityParamFinish(@NonNull FragmentActivity a, @NonNull Class<? extends Context> c, Bundle b) {
        run = () -> {
            a.startActivity(new Intent(a, c).putExtras(b));
            a.finish();
        };
        return this;
    }

    /**
     * 启动activity（传递参数,动画,完成）
     */
    public ThisListener activityParamFinish(@NonNull Activity a, @NonNull Class<? extends Context> c, Bundle b, boolean animation) {
        if (animation) {
            run = () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(b);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivity(intent);
                a.finishAfterTransition();
            };
            return this;
        } else {
            return activityParamFinish(a, c, b);
        }
    }

    /**
     * 启动activity（传递参数,动画,完成）
     */
    public ThisListener activityParamFinish(@NonNull AppCompatActivity a, @NonNull Class<? extends Context> c, Bundle b, boolean animation) {
        if (animation) {
            run = () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(b);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivity(intent);
                a.finishAfterTransition();
            };
            return this;
        } else {
            return activityParamFinish(a, c, b);
        }
    }

    /**
     * 启动activity（传递参数,动画,完成）
     */
    public ThisListener activityParamFinish(@NonNull FragmentActivity a, @NonNull Class<? extends Context> c, Bundle b, boolean animation) {
        if (animation) {
            run = () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(b);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivity(intent);
                a.finishAfterTransition();
            };
            return this;
        } else {
            return activityParamFinish(a, c, b);
        }
    }

    /**
     * 启动service（无参）
     */
    public ThisListener service(@NonNull Context c, @NonNull Class<?> c1) {
        run = () -> c.startService(new Intent(c, c1).addFlags(FLAG_ACTIVITY_NEW_TASK));
        return this;
    }

    /**
     * 启动service（带参）
     */
    public ThisListener service(@NonNull Context c, @NonNull Class<?> c1, Bundle b) {
        run = () -> {
            Intent intent = new Intent(c, c1)
                    .addFlags(FLAG_ACTIVITY_NEW_TASK)
                    .putExtras(b);
            c.startService(intent);
        };
        return this;
    }

    /**
     * 停止service（无参）
     */
    public ThisListener serviceStop(@NonNull Context c, @NonNull Class<?> c1) {
        run = () -> c.stopService(new Intent(c, c1).addFlags(FLAG_ACTIVITY_NEW_TASK));
        return this;
    }

    /**
     * 启动service（无参）
     */
    public ThisListener bindService(@NonNull Context c, @NonNull Class<?> c1, ServiceConnection c2) {
        run = () -> c.bindService(new Intent(c, c1), c2, BIND_AUTO_CREATE);
        return this;
    }

    /**
     * 启动service（带参）
     */
    public ThisListener bindService(@NonNull Context c, @NonNull Class<?> c1, Bundle b, ServiceConnection c2) {
        run = () -> {
            Intent intent = new Intent(c, c1).putExtras(b);
            c.bindService(intent, c2, BIND_AUTO_CREATE);
        };
        return this;
    }

    /**
     * 启动sendBroadcast（无参）
     */
    public ThisListener broadcast(@NonNull Context c, String action) {
        run = () -> c.sendBroadcast(new Intent(action));
        return this;
    }

    /**
     * 启动sendBroadcast（带参）
     */
    public ThisListener broadcast(@NonNull Context c, String action, Bundle b) {
        run = () -> c.sendBroadcast(new Intent(action).putExtras(b));
        return this;
    }

    /**
     * 启动activity（无参带返回）
     */
    public ThisListener resultActivity(@NonNull Activity a, @NonNull Class<?> c, int code) {
        run = () -> a.startActivityForResult(new Intent(a, c), code);
        return this;
    }

    /**
     * 启动activity（无参带返回）
     */
    public ThisListener resultActivity(@NonNull AppCompatActivity a, @NonNull Class<?> c, ActivityResultLauncher<Intent> launcher) {
        run = () -> launcher.launch(new Intent(a, c));
        return this;
    }

    /**
     * 启动activity（无参带返回）
     */
    public ThisListener resultActivity(@NonNull FragmentActivity a, @NonNull Class<?> c, ActivityResultLauncher<Intent> launcher) {
        run = () -> launcher.launch(new Intent(a, c));
        return this;
    }

    /**
     * 启动activity（传递参数,动画,完成）
     */
    public ThisListener resultActivity(@NonNull Activity a, @NonNull Class<? extends Context> c, int code, boolean animation) {
        if (animation) {
            run = () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivityForResult(intent, code);
            };
            return this;
        } else {
            return resultActivity(a, c, code);
        }
    }

    /**
     * 启动activity（传递参数,动画,完成）
     */
    public ThisListener resultActivity(@NonNull AppCompatActivity a, @NonNull Class<? extends Context> c, ActivityResultLauncher<Intent> launcher, boolean animation) {
        if (animation) {
            run = () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                launcher.launch(intent);
            };
            return this;
        } else {
            return resultActivity(a, c, launcher);
        }
    }

    /**
     * 启动activity（传递参数,动画,完成）
     */
    public ThisListener resultActivity(@NonNull FragmentActivity a, @NonNull Class<? extends Context> c, ActivityResultLauncher<Intent> launcher, boolean animation) {
        if (animation) {
            run = () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                launcher.launch(intent);
            };
            return this;
        } else {
            return resultActivity(a, c, launcher);
        }
    }

    /**
     * 启动activity（无参带返回）
     */
    public ThisListener resultActivityFinish(@NonNull Activity a, @NonNull Class<?> c, int code) {
        run = () -> {
            a.startActivityForResult(new Intent(a, c), code);
            a.finish();
        };
        return this;
    }

    /**
     * 启动activity（无参带返回）
     */
    public ThisListener resultActivityFinish(@NonNull AppCompatActivity a, @NonNull Class<?> c, ActivityResultLauncher<Intent> launcher) {
        run = () -> {
            launcher.launch(new Intent(a, c));
            a.finish();
        };
        return this;
    }

    /**
     * 启动activity（无参带返回）
     */
    public ThisListener resultActivityFinish(@NonNull FragmentActivity a, @NonNull Class<?> c, ActivityResultLauncher<Intent> launcher) {
        run = () -> {
            launcher.launch(new Intent(a, c));
            a.finish();
        };
        return this;
    }

    /**
     * 启动activity（传递参数,动画,完成）
     */
    public ThisListener resultActivityFinish(@NonNull Activity a, @NonNull Class<? extends Context> c, int code, boolean animation) {
        if (animation) {
            run = () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivityForResult(intent, code);
                a.finishAfterTransition();
            };
            return this;
        } else {
            return resultActivityFinish(a, c, code);
        }
    }

    /**
     * 启动activity（传递参数,动画,完成）
     */
    public ThisListener resultActivityFinish(@NonNull AppCompatActivity a, @NonNull Class<? extends Context> c, ActivityResultLauncher<Intent> launcher, boolean animation) {
        if (animation) {
            run = () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                launcher.launch(intent);
                a.finishAfterTransition();
            };
            return this;
        } else {
            return resultActivityFinish(a, c, launcher);
        }
    }

    /**
     * 启动activity（传递参数,动画,完成）
     */
    public ThisListener resultActivityFinish(@NonNull FragmentActivity a, @NonNull Class<? extends Context> c, ActivityResultLauncher<Intent> launcher, boolean animation) {
        if (animation) {
            run = () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                launcher.launch(intent);
                a.finishAfterTransition();
            };
            return this;
        } else {
            return resultActivityFinish(a, c, launcher);
        }
    }

    /**
     * 启动activity（带参带返回）
     */
    public ThisListener resultActivity(@NonNull Activity a, @NonNull Class<?> c, Bundle b, int code) {
        run = () -> a.startActivityForResult(new Intent(a, c).putExtras(b), code);
        return this;
    }

    /**
     * 启动activity（带参带返回）
     */
    public ThisListener resultActivity(@NonNull AppCompatActivity a, @NonNull Class<?> c, Bundle b, ActivityResultLauncher<Intent> launcher) {
        run = () -> launcher.launch(new Intent(a, c).putExtras(b));
        return this;
    }

    /**
     * 启动activity（带参带返回）
     */
    public ThisListener resultActivity(@NonNull FragmentActivity a, @NonNull Class<?> c, Bundle b, ActivityResultLauncher<Intent> launcher) {
        run = () -> launcher.launch(new Intent(a, c).putExtras(b));
        return this;
    }

    /**
     * 启动activity（传递参数,动画,完成）
     */
    public ThisListener resultActivity(@NonNull Activity a, @NonNull Class<? extends Context> c, Bundle b, int code, boolean animation) {
        if (animation) {
            run = () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(b);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivityForResult(intent, code);
            };
            return this;
        } else {
            return resultActivity(a, c, b, code);
        }
    }

    /**
     * 启动activity（传递参数,动画,完成）
     */
    public ThisListener resultActivity(@NonNull AppCompatActivity a, @NonNull Class<? extends Context> c, Bundle b, ActivityResultLauncher<Intent> launcher, boolean animation) {
        if (animation) {
            run = () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(b);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                launcher.launch(intent);
            };
            return this;
        } else {
            return resultActivity(a, c, b, launcher);
        }
    }

    /**
     * 启动activity（传递参数,动画,完成）
     */
    public ThisListener resultActivity(@NonNull FragmentActivity a, @NonNull Class<? extends Context> c, Bundle b, ActivityResultLauncher<Intent> launcher, boolean animation) {
        if (animation) {
            run = () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(b);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                launcher.launch(intent);
            };
            return this;
        } else {
            return resultActivity(a, c, b, launcher);
        }
    }

    /**
     * 启动activity（带参带返回）
     */
    public ThisListener resultActivityFinish(@NonNull Activity a, @NonNull Class<?> c, Bundle b, int code) {
        run = () -> {
            a.startActivityForResult(new Intent(a, c).putExtras(b), code);
            a.finish();
        };
        return this;
    }

    /**
     * 启动activity（带参带返回）
     */
    public ThisListener resultActivityFinish(@NonNull AppCompatActivity a, @NonNull Class<?> c, Bundle b, ActivityResultLauncher<Intent> launcher) {
        run = () -> {
            launcher.launch(new Intent(a, c).putExtras(b));
            a.finish();
        };
        return this;
    }

    /**
     * 启动activity（带参带返回）
     */
    public ThisListener resultActivityFinish(@NonNull FragmentActivity a, @NonNull Class<?> c, Bundle b, ActivityResultLauncher<Intent> launcher) {
        run = () -> {
            launcher.launch(new Intent(a, c).putExtras(b));
            a.finish();
        };
        return this;
    }

    /**
     * 启动activity（传递参数,动画,完成）
     */
    public ThisListener resultActivityFinish(@NonNull Activity a, @NonNull Class<? extends Context> c, Bundle b, int code, boolean animation) {
        if (animation) {
            run = () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(b);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivityForResult(intent, code);
                a.finishAfterTransition();
            };
            return this;
        } else {
            return resultActivityFinish(a, c, b, code);
        }
    }

    /**
     * 启动activity（传递参数,动画,完成）
     */
    public ThisListener resultActivityFinish(@NonNull AppCompatActivity a, @NonNull Class<? extends Context> c, Bundle b, ActivityResultLauncher<Intent> launcher, boolean animation) {
        if (animation) {
            run = () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(b);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                launcher.launch(intent);
                a.finishAfterTransition();
            };
            return this;
        } else {
            return resultActivityFinish(a, c, b, launcher);
        }
    }

    /**
     * 启动activity（传递参数,动画,完成）
     */
    public ThisListener resultActivityFinish(@NonNull FragmentActivity a, @NonNull Class<? extends Context> c, Bundle b, ActivityResultLauncher<Intent> launcher, boolean animation) {
        if (animation) {
            run = () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(b);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                launcher.launch(intent);
                a.finishAfterTransition();
            };
            return this;
        } else {
            return resultActivityFinish(a, c, b, launcher);
        }
    }

}

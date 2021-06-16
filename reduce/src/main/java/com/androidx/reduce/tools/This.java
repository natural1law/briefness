package com.androidx.reduce.tools;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import static android.content.Context.BIND_AUTO_CREATE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * @date 2021/06/09
 */
@SuppressWarnings("unused")
public final class This {

    private static final Handler handler = new Handler(Looper.getMainLooper());

    private This() {
    }

    public static void startDelay(Runnable run, long t) {
        handler.postDelayed(run, t);
    }

    public static void startAtTime(Runnable run, long t) {
        handler.postAtTime(run, t);
    }

    public static void start(Runnable run) {
        handler.post(run);
    }

    public static void destroy(Runnable run) {
        handler.removeCallbacks(run);
    }

    public static void destroyToken(Runnable run, Object o) {
        handler.removeCallbacks(run, o);
    }

    /**
     * 启动activity（无传递参数,默认动画）
     */
    public static Runnable activity(@NonNull Context a, @NonNull Class<? extends Context> c) {
        return () -> a.startActivity(new Intent(a, c).addFlags(FLAG_ACTIVITY_NEW_TASK));
    }

    /**
     * 启动activity（无传递参数,默认动画）
     */
    public static Runnable activity(@NonNull Activity a, @NonNull Class<? extends Context> c) {
        return () -> a.startActivity(new Intent(a, c));
    }

    /**
     * 启动activity（无传递参数,默认动画）
     */
    public static Runnable activity(@NonNull AppCompatActivity a, @NonNull Class<? extends Context> c) {
        return () -> a.startActivity(new Intent(a, c));
    }

    /**
     * 启动activity（无传递参数,默认动画）
     */
    public static Runnable activity(@NonNull FragmentActivity a, @NonNull Class<? extends Context> c) {
        return () -> a.startActivity(new Intent(a, c));
    }

    /**
     * 启动activity（无传递参数,默认动画）
     */
    public static Runnable activity(@NonNull Activity a, @NonNull Class<? extends Context> c, boolean animation) {
        if (animation) {
            return () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivity(intent);
            };
        } else {
            return activity(a, c);
        }
    }

    /**
     * 启动activity（无传递参数,默认动画）
     */
    public static Runnable activity(@NonNull AppCompatActivity a, @NonNull Class<? extends Context> c, boolean animation) {
        if (animation) {
            return () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivity(intent);
            };
        } else {
            return activity(a, c);
        }
    }

    /**
     * 启动activity（无传递参数,默认动画）
     */
    public static Runnable activity(@NonNull FragmentActivity a, @NonNull Class<? extends Context> c, boolean animation) {
        if (animation) {

            return () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivity(intent);
            };
        } else {
            return activity(a, c);
        }
    }


    /**
     * 启动activity（无传递参数,默认动画,完成）
     */
    public static Runnable activityFinish(@NonNull Activity a, @NonNull Class<? extends Context> c) {
        return () -> {
            a.startActivity(new Intent(a, c));
            a.finish();
        };
    }

    /**
     * 启动activity（无传递参数,默认动画,完成）
     */
    public static Runnable activityFinish(@NonNull AppCompatActivity a, @NonNull Class<? extends Context> c) {
        return () -> {
            a.startActivity(new Intent(a, c));
            a.finish();
        };
    }

    /**
     * 启动activity（无传递参数,默认动画,完成）
     */
    public static Runnable activityFinish(@NonNull FragmentActivity a, @NonNull Class<? extends Context> c) {
        return () -> {
            a.startActivity(new Intent(a, c));
            a.finish();
        };
    }

    /**
     * 启动activity（无传递参数,动画,完成）
     */
    public static Runnable activityFinish(@NonNull Activity a, @NonNull Class<? extends Context> c, boolean animation) {
        if (animation) {
            return () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivity(intent);
                a.finishAfterTransition();
            };
        } else {
            return activity(a, c);
        }
    }

    /**
     * 启动activity（无传递参数,动画,完成）
     */
    public static Runnable activityFinish(@NonNull AppCompatActivity a, @NonNull Class<? extends Context> c, boolean animation) {
        if (animation) {
            return () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivity(intent);
                a.finishAfterTransition();
            };
        } else {
            return activityFinish(a, c);
        }
    }

    /**
     * 启动activity（无传递参数,动画,完成）
     */
    public static Runnable activityFinish(@NonNull FragmentActivity a, @NonNull Class<? extends Context> c, boolean animation) {
        if (animation) {
            return () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivity(intent);
                a.finishAfterTransition();
            };
        } else {
            return activityFinish(a, c);
        }
    }

    /**
     * 启动activity（传递参数,动画,完成）
     */
    public static Runnable activity(@NonNull Context a, @NonNull Class<? extends Context> c, Bundle b) {
        return () -> a.startActivity(new Intent(a, c).addFlags(FLAG_ACTIVITY_NEW_TASK).putExtras(b));
    }

    /**
     * 启动activity（传递参数,默认动画）
     */
    public static Runnable activityParam(@NonNull Activity a, @NonNull Class<? extends Context> c, Bundle b) {
        return () -> a.startActivity(new Intent(a, c).putExtras(b));
    }

    /**
     * 启动activity（传递参数,默认动画）
     */
    public static Runnable activityParam(@NonNull AppCompatActivity a, @NonNull Class<? extends Context> c, Bundle b) {
        return () -> a.startActivity(new Intent(a, c).putExtras(b));
    }

    /**
     * 启动activity（传递参数,默认动画）
     */
    public static Runnable activityParam(@NonNull FragmentActivity a, @NonNull Class<? extends Context> c, Bundle b) {
        return () -> a.startActivity(new Intent(a, c).putExtras(b));
    }


    /**
     * 启动activity（无传递参数,动画,完成）
     */
    public static Runnable activityParam(@NonNull Activity a, @NonNull Class<? extends Context> c, Bundle b, boolean animation) {
        if (animation) {
            return () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(b);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivity(intent);
            };
        } else {
            return activityParam(a, c, b);
        }
    }

    /**
     * 启动activity（无传递参数,动画,完成）
     */
    public static Runnable activityParam(@NonNull AppCompatActivity a, @NonNull Class<? extends Context> c, Bundle b, boolean animation) {
        if (animation) {
            return () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(b);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivity(intent);
            };
        } else {
            return activityParam(a, c, b);
        }
    }

    /**
     * 启动activity（无传递参数,动画,完成）
     */
    public static Runnable activityParam(@NonNull FragmentActivity a, @NonNull Class<? extends Context> c, Bundle b, boolean animation) {
        if (animation) {
            return () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(b);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivity(intent);
            };
        } else {
            return activityParam(a, c, b);
        }
    }

    /**
     * 启动activity（传递参数,默认动画,完成）
     */
    public static Runnable activityParamFinish(@NonNull Activity a, @NonNull Class<? extends Context> c, Bundle b) {
        return () -> {
            a.startActivity(new Intent(a, c).putExtras(b));
            a.finish();
        };
    }

    /**
     * 启动activity（传递参数,默认动画,完成）
     */
    public static Runnable activityParamFinish(@NonNull AppCompatActivity a, @NonNull Class<? extends Context> c, Bundle b) {
        return () -> {
            a.startActivity(new Intent(a, c).putExtras(b));
            a.finish();
        };
    }

    /**
     * 启动activity（传递参数,默认动画,完成）
     */
    public static Runnable activityParamFinish(@NonNull FragmentActivity a, @NonNull Class<? extends Context> c, Bundle b) {
        return () -> {
            a.startActivity(new Intent(a, c).putExtras(b));
            a.finish();
        };
    }

    /**
     * 启动activity（传递参数,动画,完成）
     */
    public static Runnable activityParamFinish(@NonNull Activity a, @NonNull Class<? extends Context> c, Bundle b, boolean animation) {
        if (animation) {
            return () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(b);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivity(intent);
                a.finishAfterTransition();
            };
        } else {
            return activityParamFinish(a, c, b);
        }
    }

    /**
     * 启动activity（传递参数,动画,完成）
     */
    public static Runnable activityParamFinish(@NonNull AppCompatActivity a, @NonNull Class<? extends Context> c, Bundle b, boolean animation) {
        if (animation) {
            return () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(b);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivity(intent);
                a.finishAfterTransition();
            };
        } else {
            return activityParamFinish(a, c, b);
        }
    }

    /**
     * 启动activity（传递参数,动画,完成）
     */
    public static Runnable activityParamFinish(@NonNull FragmentActivity a, @NonNull Class<? extends Context> c, Bundle b, boolean animation) {
        if (animation) {
            return () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(b);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivity(intent);
                a.finishAfterTransition();
            };
        } else {
            return activityParamFinish(a, c, b);
        }
    }

    /**
     * 启动service（无参）
     */
    public static Runnable service(@NonNull Context c, @NonNull Class<?> c1) {
        return () -> c.startService(new Intent(c, c1).addFlags(FLAG_ACTIVITY_NEW_TASK));
    }

    /**
     * 启动service（带参）
     */
    public static Runnable service(@NonNull Context c, @NonNull Class<?> c1, Bundle b) {
        return () -> {
            Intent intent = new Intent(c, c1)
                    .addFlags(FLAG_ACTIVITY_NEW_TASK)
                    .putExtras(b);
            c.startService(intent);
        };
    }

    /**
     * 停止service（无参）
     */
    public static Runnable serviceStop(@NonNull Context c, @NonNull Class<?> c1) {
        return () -> c.stopService(new Intent(c, c1).addFlags(FLAG_ACTIVITY_NEW_TASK));
    }

    /**
     * 启动service（无参）
     */
    public static Runnable bindService(@NonNull Context c, @NonNull Class<?> c1, ServiceConnection c2) {
        return () -> c.bindService(new Intent(c, c1), c2, BIND_AUTO_CREATE);
    }

    /**
     * 启动service（带参）
     */
    public static Runnable bindService(@NonNull Context c, @NonNull Class<?> c1, Bundle b, ServiceConnection c2) {
        return () -> {
            Intent intent = new Intent(c, c1).putExtras(b);
            c.bindService(intent, c2, BIND_AUTO_CREATE);
        };
    }

    /**
     * 启动sendBroadcast（无参）
     */
    public static Runnable broadcast(@NonNull Context c, String action) {
        return () -> c.sendBroadcast(new Intent(action));
    }

    /**
     * 启动sendBroadcast（带参）
     */
    public static Runnable broadcast(@NonNull Context c, String action, Bundle b) {
        return () -> c.sendBroadcast(new Intent(action).putExtras(b));
    }

    /**
     * 启动activity（无参带返回）
     */
    public static Runnable resultActivity(@NonNull Activity a, @NonNull Class<?> c, int code) {
        return () -> a.startActivityForResult(new Intent(a, c), code);
    }

    /**
     * 启动activity（无参带返回）
     */
    public static Runnable resultActivity(@NonNull AppCompatActivity a, @NonNull Class<?> c, int code) {
        return () -> a.startActivityForResult(new Intent(a, c), code);
    }

    /**
     * 启动activity（无参带返回）
     */
    public static Runnable resultActivity(@NonNull FragmentActivity a, @NonNull Class<?> c, int code) {
        return () -> a.startActivityForResult(new Intent(a, c), code);
    }

    /**
     * 启动activity（传递参数,动画,完成）
     */
    public static Runnable resultActivity(@NonNull Activity a, @NonNull Class<? extends Context> c, int code, boolean animation) {
        if (animation) {
            return () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivityForResult(intent, code);
            };
        } else {
            return resultActivity(a, c, code);
        }
    }

    /**
     * 启动activity（传递参数,动画,完成）
     */
    public static Runnable resultActivity(@NonNull AppCompatActivity a, @NonNull Class<? extends Context> c, int code, boolean animation) {
        if (animation) {
            return () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivityForResult(intent, code);
            };
        } else {
            return resultActivity(a, c, code);
        }
    }

    /**
     * 启动activity（传递参数,动画,完成）
     */
    public static Runnable resultActivity(@NonNull FragmentActivity a, @NonNull Class<? extends Context> c, int code, boolean animation) {
        if (animation) {
            return () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivityForResult(intent, code);
            };
        } else {
            return resultActivity(a, c, code);
        }
    }

    /**
     * 启动activity（无参带返回）
     */
    public static Runnable resultActivityFinish(@NonNull Activity a, @NonNull Class<?> c, int code) {
        return () -> {
            a.startActivityForResult(new Intent(a, c), code);
            a.finish();
        };
    }

    /**
     * 启动activity（无参带返回）
     */
    public static Runnable resultActivityFinish(@NonNull AppCompatActivity a, @NonNull Class<?> c, int code) {
        return () -> {
            a.startActivityForResult(new Intent(a, c), code);
            a.finish();
        };
    }

    /**
     * 启动activity（无参带返回）
     */
    public static Runnable resultActivityFinish(@NonNull FragmentActivity a, @NonNull Class<?> c, int code) {
        return () -> {
            a.startActivityForResult(new Intent(a, c), code);
            a.finish();
        };
    }

    /**
     * 启动activity（传递参数,动画,完成）
     */
    public static Runnable resultActivityFinish(@NonNull Activity a, @NonNull Class<? extends Context> c, int code, boolean animation) {
        if (animation) {
            return () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivityForResult(intent, code);
                a.finishAfterTransition();
            };
        } else {
            return resultActivityFinish(a, c, code);
        }
    }

    /**
     * 启动activity（传递参数,动画,完成）
     */
    public static Runnable resultActivityFinish(@NonNull AppCompatActivity a, @NonNull Class<? extends Context> c, int code, boolean animation) {
        if (animation) {
            return () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivityForResult(intent, code);
                a.finishAfterTransition();
            };
        } else {
            return resultActivityFinish(a, c, code);
        }
    }

    /**
     * 启动activity（传递参数,动画,完成）
     */
    public static Runnable resultActivityFinish(@NonNull FragmentActivity a, @NonNull Class<? extends Context> c, int code, boolean animation) {
        if (animation) {
            return () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivityForResult(intent, code);
                a.finishAfterTransition();
            };
        } else {
            return resultActivityFinish(a, c, code);
        }
    }

    /**
     * 启动activity（带参带返回）
     */
    public static Runnable resultActivity(@NonNull Activity a, @NonNull Class<?> c, Bundle b, int code) {
        return () -> a.startActivityForResult(new Intent(a, c).putExtras(b), code);
    }

    /**
     * 启动activity（带参带返回）
     */
    public static Runnable resultActivity(@NonNull AppCompatActivity a, @NonNull Class<?> c, Bundle b, int code) {
        return () -> a.startActivityForResult(new Intent(a, c).putExtras(b), code);
    }

    /**
     * 启动activity（带参带返回）
     */
    public static Runnable resultActivity(@NonNull FragmentActivity a, @NonNull Class<?> c, Bundle b, int code) {
        return () -> a.startActivityForResult(new Intent(a, c).putExtras(b), code);
    }

    /**
     * 启动activity（传递参数,动画,完成）
     */
    public static Runnable resultActivity(@NonNull Activity a, @NonNull Class<? extends Context> c, Bundle b, int code, boolean animation) {
        if (animation) {
            return () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(b);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivityForResult(intent, code);
            };
        } else {
            return resultActivity(a, c, b, code);
        }
    }

    /**
     * 启动activity（传递参数,动画,完成）
     */
    public static Runnable resultActivity(@NonNull AppCompatActivity a, @NonNull Class<? extends Context> c, Bundle b, int code, boolean animation) {
        if (animation) {
            return () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(b);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivityForResult(intent, code);
            };
        } else {
            return resultActivity(a, c, b, code);
        }
    }

    /**
     * 启动activity（传递参数,动画,完成）
     */
    public static Runnable resultActivity(@NonNull FragmentActivity a, @NonNull Class<? extends Context> c, Bundle b, int code, boolean animation) {
        if (animation) {
            return () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(b);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivityForResult(intent, code);
            };
        } else {
            return resultActivity(a, c, b, code);
        }
    }

    /**
     * 启动activity（带参带返回）
     */
    public static Runnable resultActivityFinish(@NonNull Activity a, @NonNull Class<?> c, Bundle b, int code) {
        return () -> {
            a.startActivityForResult(new Intent(a, c).putExtras(b), code);
            a.finish();
        };
    }

    /**
     * 启动activity（带参带返回）
     */
    public static Runnable resultActivityFinish(@NonNull AppCompatActivity a, @NonNull Class<?> c, Bundle b, int code) {
        return () -> {
            a.startActivityForResult(new Intent(a, c).putExtras(b), code);
            a.finish();
        };
    }

    /**
     * 启动activity（带参带返回）
     */
    public static Runnable resultActivityFinish(@NonNull FragmentActivity a, @NonNull Class<?> c, Bundle b, int code) {
        return () -> {
            a.startActivityForResult(new Intent(a, c).putExtras(b), code);
            a.finish();
        };
    }

    /**
     * 启动activity（传递参数,动画,完成）
     */
    public static Runnable resultActivityFinish(@NonNull Activity a, @NonNull Class<? extends Context> c, Bundle b, int code, boolean animation) {
        if (animation) {
            return () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(b);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivityForResult(intent, code);
                a.finishAfterTransition();
            };
        } else {
            return resultActivityFinish(a, c, b, code);
        }
    }

    /**
     * 启动activity（传递参数,动画,完成）
     */
    public static Runnable resultActivityFinish(@NonNull AppCompatActivity a, @NonNull Class<? extends Context> c, Bundle b, int code, boolean animation) {
        if (animation) {
            return () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(b);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivityForResult(intent, code);
                a.finishAfterTransition();
            };
        } else {
            return resultActivityFinish(a, c, b, code);
        }
    }

    /**
     * 启动activity（传递参数,动画,完成）
     */
    public static Runnable resultActivityFinish(@NonNull FragmentActivity a, @NonNull Class<? extends Context> c, Bundle b, int code, boolean animation) {
        if (animation) {
            return () -> {
                Intent intent = new Intent(a, c);
                intent.putExtras(b);
                intent.putExtras(ActivityOptions.makeSceneTransitionAnimation(a).toBundle());
                a.startActivityForResult(intent, code);
                a.finishAfterTransition();
            };
        } else {
            return resultActivityFinish(a, c, b, code);
        }
    }

}

package com.inlocal.restaurantapp.util;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NavUtil {
    private NavUtil() {

    }

    public static Intent getIntent(Context context, Class<? extends Activity> c) {
        return new Intent(context, c);
    }

    public static class ForActivity {

        static void navTo(@NonNull Activity activity, @NonNull Class<? extends Activity> c,
                          @Nullable ActivityOptions activityOptions,
                          @Nullable Bundle options,
                          @Nullable Integer requestCode,
                          boolean noHistory) {

            Intent intent = getIntent(activity, c);

            if (noHistory)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

            if (options != null)
                intent.putExtras(options);

            if (activityOptions == null)
                activityOptions = ActivityOptions.makeCustomAnimation(activity, android.R.anim.fade_in,
                        android.R.anim.fade_out);

            if (requestCode != null)
                activity.startActivityForResult(intent, requestCode, activityOptions.toBundle());
            else
                activity.startActivity(intent, activityOptions.toBundle());

        }

        public static void navTo(@NonNull Activity activity, @NonNull Class<? extends Activity> c, boolean noHistory, @Nullable Bundle options) {
            navTo(activity, c, null, options, null, noHistory);
        }

        public static void navToWithTaskStack(@NonNull Activity activity, @NonNull Class<? extends Activity> c, @Nullable Bundle options, @NonNull Class<? extends Activity> stack) {
            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(activity);
            taskStackBuilder.addParentStack(stack);
            taskStackBuilder.addNextIntent(new Intent(activity, stack));
            Intent intent = new Intent(activity, c);

            if (options != null)
                intent.putExtras(options);

            taskStackBuilder.addNextIntent(intent);

            ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(activity, android.R.anim.fade_in,
                    android.R.anim.fade_out);

            activity.startActivities(taskStackBuilder.getIntents(), activityOptions.toBundle());
        }

        public static void navToForResult(@NonNull Activity activity, @NonNull Class<? extends Activity> c, int requestCode, @Nullable Bundle options) {
            navTo(activity, c, null, options, requestCode, false);
        }

        public static void navToWithOptions(@NonNull Activity activity, @NonNull Class<? extends Activity> c, @NonNull ActivityOptions activityOptions, @Nullable Bundle options) {
            navTo(activity, c, activityOptions, options, null, false);
        }

    }

    public static class ForFragment {

        static void navTo(@NonNull Fragment fragment, @NonNull Class<? extends Activity> c,
                          @Nullable ActivityOptions activityOptions,
                          @Nullable Bundle options,
                          @Nullable Integer requestCode,
                          boolean noHistory) {

            Intent intent = getIntent(fragment.getContext(), c);

            if (noHistory)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

            if (options != null)
                intent.putExtras(options);

            if (activityOptions == null)
                activityOptions = ActivityOptions.makeCustomAnimation(fragment.getActivity(), android.R.anim.fade_in,
                        android.R.anim.fade_out);

            if (requestCode != null)
                fragment.startActivityForResult(intent, requestCode, activityOptions.toBundle());
            else
                fragment.startActivity(intent, activityOptions.toBundle());

        }

        public static void navTo(@NonNull Fragment fragment, @NonNull Class<? extends Activity> c, boolean noHistory, @Nullable Bundle options) {
            navTo(fragment, c, null, options, null, noHistory);
        }

        public static void navToWithTaskStack(@NonNull Fragment fragment, @NonNull Class<? extends Activity> c, @Nullable Bundle options, @NonNull Class<? extends Activity>... stack) {
            if (stack.length == 0)
                return;

            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(fragment.requireContext());

            taskStackBuilder.addParentStack(stack[0]);
            for (Class<? extends Activity> task : stack)
                taskStackBuilder.addNextIntent(new Intent(fragment.getContext(), task));

            Intent intent = new Intent(fragment.getContext(), c);

            if (options != null)
                intent.putExtras(options);

            taskStackBuilder.addNextIntent(intent);

            ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(fragment.getActivity(), android.R.anim.fade_in,
                    android.R.anim.fade_out);

            fragment.requireActivity().startActivities(taskStackBuilder.getIntents(), activityOptions.toBundle());
        }

        public static void navToForResult(@NonNull Fragment fragment, @NonNull Class<? extends Activity> c, int requestCode, @Nullable Bundle options) {
            navTo(fragment, c, null, options, requestCode, false);
        }

        public static void navToWithOptions(@NonNull Fragment fragment, @NonNull Class<? extends Activity> c, @NonNull ActivityOptions activityOptions, @Nullable Bundle options) {
            navTo(fragment, c, activityOptions, options, null, false);
        }

    }
}

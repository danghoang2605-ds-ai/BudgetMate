package com.example.savemoneytime.util;

import android.app.Activity;
import androidx.fragment.app.Fragment;

/** Runs work on the main thread only while the fragment still has a view. */
public final class UiThread {

    private UiThread() {}

    /**
     * Background callbacks (Room, OkHttp, export) can finish after the user leaves the screen.
     * Calling requireContext() or touching views then crashes, so skip the action instead.
     */
    public static void post(Fragment fragment, Runnable action) {
        Activity activity = fragment.getActivity();
        if (activity == null) return;
        activity.runOnUiThread(() -> {
            if (fragment.isAdded() && fragment.getView() != null) action.run();
        });
    }
}

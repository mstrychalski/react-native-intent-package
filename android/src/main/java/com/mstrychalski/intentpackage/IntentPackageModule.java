/**
 * Copyright (c) 2017-present, Michał Strychalski.
 * All rights reserved.
 *
 */

package com.mstrychalski.ipmanager;

import android.app.Activity;
import android.content.Intent;

import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

/**
 * Intent module. Open Package.
 */
@ReactModule(name = "IntentPackageModule")
public class IntentPackageModule extends ReactContextBaseJavaModule {

    public IntentPackageModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "IntentPackageModule";
    }

    /**
     * Starts a corresponding external package for the given package name.
     *
     * @param pkgName the Package name to open
     */
    @ReactMethod
    public void openPackage(String pkgName, Promise promise) {
        if (pkgName == null || pkgName.isEmpty()) {
            promise.reject(new JSApplicationIllegalArgumentException("Invalid Package: " + pkgName));
            return;
        }

        try {
            Activity currentActivity = getCurrentActivity();
            Intent intent = getReactApplicationContext().getPackageManager().getLaunchIntentForPackage(pkgName);

            String selfPackageName = getReactApplicationContext().getPackageName();

            // If there is no currentActivity or we are launching to a different package we need to set
            // the FLAG_ACTIVITY_NEW_TASK flag
            if (currentActivity == null || !selfPackageName.equals(pkgName)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }

            if (currentActivity != null) {
                currentActivity.startActivity(intent);
            } else {
                getReactApplicationContext().startActivity(intent);
            }

            promise.resolve(true);
        } catch (Exception e) {
            promise.reject(new JSApplicationIllegalArgumentException(
                    "Could not open Package '" + pkgName + "': " + e.getMessage()));
        }
    }
}

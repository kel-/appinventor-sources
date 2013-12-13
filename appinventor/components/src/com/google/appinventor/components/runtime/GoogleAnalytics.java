package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.common.YaVersion;
import com.google.appinventor.components.runtime.util.AsynchUtil;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.TextViewUtil;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;

@DesignerComponent(version = 1, // TODO - create YaVersion
    description = "<p>A non-visible component that enables communication "
    + "with a user's Google Analytics Account. "
    + "Once a user has provided AppInventor with their tracking ID, the user's application will begin "
    + "sending data to Google Analytics. A Google Analytics account is needed to use this function </p>",
    category = ComponentCategory.SOCIAL, //TODO put in category for advertisements/analytics (not yet created)
    nonVisible = true,
    iconName = "images/googleAnalytics.png")
@SimpleObject
@UsesLibraries(libraries = "libGoogleAnalyticsServices.jar")
@UsesPermissions(permissionNames =
      "android.permission.INTERNET, "
    + "android.permission.ACCESS_NETWORK_STATE")


public class GoogleAnalytics extends AndroidNonvisibleComponent implements Component,
   OnStopListener, OnStartListener {

  //Indicates whether the google analytics should record events
  private boolean enableGoogleAnalytics = true;
  private String uaTrackingID = "";
  private Form form;

  public GoogleAnalytics(ComponentContainer container) {
    super(container.$form());
    form.registerForOnStop(this);
    form = container.$form();
    //uaTrackingID = retrieveUA(); //must receive UA to place into analytics.xml
  }

 /**
   * Returns whether Google Analytics should be allowed
   */
  @SimpleProperty(category = PropertyCategory.BEHAVIOR,
      description = "Whether Google Analytics is enabled.")
  public boolean Enable() {
    return enableGoogleAnalytics;
  }

  /**
   * Specifies whether Google Analytics should be allowed
   */
  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN,
      defaultValue = "true")
  @SimpleProperty
  public void Enable(boolean enableGoogleAnalytics) {
    this.enableGoogleAnalytics = enableGoogleAnalytics;
  }

  /**
   * Google Analytics UATrackingID property getter method.
   */
  @SimpleProperty(category = PropertyCategory.BEHAVIOR)
  public String UATrackingID() {
    return uaTrackingID;
  }

  /**
   * Google Analytics UA Tracking ID property setter method: sets the UA Tracking ID key to be used when
   * syncing with Google Analytics
   *
   * @param uaTrackingID
   *          the tracking ID for use in Google Analytics
   */
  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING, defaultValue = "")
  @SimpleProperty
  public void UATrackingID(String uaTrackingID) {
    this.uaTrackingID = uaTrackingID;
  }


  //OnStart implementation
  @Override
  public void onStart() {
    if (enableGoogleAnalytics) {
      EasyTracker.getInstance(form).activityStart(form);
    }
  }

  // onStopListener implementation
  @Override
  public void onStop() {
    if (enableGoogleAnalytics) {
      EasyTracker.getInstance(form).activityStop(form);
    }
  }


} //end


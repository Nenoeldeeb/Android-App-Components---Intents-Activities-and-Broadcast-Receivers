package vandy.mooc.aad2.assignment.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.URLUtil;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import vandy.mooc.aad2.assignment.R;
import vandy.mooc.aad2.framework.application.activities.GalleryActivityBase;
import vandy.mooc.aad2.framework.utils.UriUtils;
import vandy.mooc.aad2.framework.utils.ViewUtils;


/**
 * This activity class contains helper methods to support different was you can
 * use Intents to communicate between activities (in the assignments case
 * between the MainActivity and this GalleryActivity).
 * <p/>
 * The MainActivity constructs a starting intent containing the
 * displayed list of remote image URLs and then uses this intent to start this
 * GalleryActivity.
 */
public class GalleryActivity
        extends GalleryActivityBase {
    /**
     * Intent "extra" string key used to identify the list of image URLs
     */
    public static final String INTENT_EXTRA_URLS = "extra_urls";
    /**
     * Debug logging tag.
     */
    private static final String TAG = "GalleryActivity";

    /*
     * Activity Lifecycle methods.
     */

    /**
     * Factory method that creates and returns an explicit intent that can be
     * used to start this activity.
     *
     * @param context   The context of the calling activity
     * @param inputUrls A list list of input URLs to include as intent extras.
     * @return An intent that can be used to start this activity
     */
    public static Intent makeStartIntent(
            Context context,
            ArrayList<Uri> inputUrls) {
        // Create a new intent for starting this activity
        // using the passed context along with the class identifier
        // for this class.
        // See this guide if you have any difficulties.
        // https://developer.android.com/training/basics/firstapp/starting-activity.html

        Intent intent = new Intent (context,GalleryActivity.class);
        intent.setAction (Intent.ACTION_VIEW);


        // Put the received list of input URLs as an intent
        // use putParcelableArrayListExtra(String, ArrayList<Uri>) on the intent
        // using the predefined INTENT_EXTRA_URLS extra name.
        intent.putParcelableArrayListExtra (INTENT_EXTRA_URLS,inputUrls);
        

        // Return the intent.
        return intent;
    }

    /*
     * Factory methods.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Always call super class method first. Normally you would follow this
        // call with a call to inflate the activity's layout from XML, but this
        // is not necessary here because the assignment super class method will
        // do that for you.
        super.onCreate(savedInstanceState);

        // When savedInstanceState is null, the activity is being started for
        // the first time, and when not null, the activity is being recreated
        // after a configuration change.
        if (savedInstanceState == null) {
            // The activity is being started for the first time.

            // Call local help method to extract the URLs from the activity's
            // starting intent and pass these URLs into the super class using
            // the setItems() helper method.

            Intent intent = getIntent ();
            List<Uri> uris = extractInputUrlsFromIntent (intent);
            super.setItems (uris);
        }
    }

    /**
     * Extract, validate, and returns the image urls received in the starting
     * Intent.
     *
     * @return A list of image URLs.
     */
    private List<Uri> extractInputUrlsFromIntent(Intent intent) {
        // First extract the list of input urls from the passed
        // intent extras using the provided INTENT_EXTRA_URLS name string.
        // Next, validate the extracted list URL strings by calling the local
        // validateInput() helper method. If the entire list of received URLs
        // are valid, then return this list. Otherwise return an empty list.
        ArrayList<Uri> urisList = intent.getParcelableArrayListExtra (INTENT_EXTRA_URLS);
        boolean validURLS = validateInput (urisList);
        if(validURLS)return urisList;
        else return new ArrayList<Uri>();
    }

    /**
     * Ensures that the input to this activity is valid and reports errors if
     * the received URL list is null, empty, or contains any mal-formed URLs.
     *
     * @param inputUrls A List of image URLs to validate.
     * @return {@code true} if the input URL list is valid; {@code false} on
     * first error encountered.
     */
    private boolean validateInput(ArrayList<Uri> inputUrls) {
        // Validate the passed URL.
        //
        // If the list is null call ViewUtils.showToast() to display the
        // string R.string.input_url_list_is_null.
        //
        // If the list has a size of 0 then call ViewUtils.showToast()
        // to display the the string R.string.input_url_list_is_empty
        //
        // Otherwise check if each list entry is valid using the
        // UrlUtils.isValidUrl() helper and if any URL is not valid
        // return false.
        //
        // Return true if all the URLs are valid.

        if (inputUrls == null) {
            ViewUtils.showToast(getApplicationContext (),R.string.input_url_list_is_null);
            return false;
        } else if (inputUrls.isEmpty ()) {
            ViewUtils.showToast(getApplicationContext (),R.string.input_url_list_is_empty);
            return false;
        }
        for (Uri uri : inputUrls) {
            if(!UriUtils.isValidUrl(uri.toString ()))return false;
        }
        return true;
    }

    /**
     * Creates a data results intent, sets the activity result to this intent,
     * and finishes this activity.
     *
     * @param urls The currently displayed list of image URLs.
     */
    protected void createAndReturnResultsIntent(
            @NonNull ArrayList<Uri> urls) {
        Log.d(TAG, "Setting a result intent.");
        // This method isn't required in this assignment.
    }


    /**
     * Creates a data results intent, broadcasts this the intent intent (to the
     * MainActivity), and finishes this activity.
     *
     * @param outputUrls A list of local image URLs to broadcast.
     */
    protected void createAndBroadcastResultsIntent(
            @NonNull ArrayList<Uri> outputUrls) {
        Log.d(TAG, "Sending a broadcast Intent.");
        // This method isn't required in this assignment.
    }
}

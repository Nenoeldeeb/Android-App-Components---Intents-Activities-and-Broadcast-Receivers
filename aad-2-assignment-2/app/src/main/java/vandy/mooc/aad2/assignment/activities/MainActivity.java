package vandy.mooc.aad2.assignment.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vandy.mooc.aad2.assignment.R;
import vandy.mooc.aad2.framework.application.activities.MainActivityBase;
import vandy.mooc.aad2.framework.utils.ViewUtils;

/**
 * This is the main activity class for the application and will be created and
 * be displayed when the application is first started. It extends the base class
 * MainActivityBase which supports add and download FABs and a fragment that
 * contains a RecyclerView that manages a list of strings. This base class also
 * manages all configuration changes and also saving and restoring the displayed
 * list of strings between application sessions.
 * <p/>
 * In addition to managing the string list, the base class expects this sub
 * class to handle creating intents that are used to start a GalleryActivity. In
 * some assignments, there will also be a requirement to have the
 * GalleryActivity return a result intent back to the MainActivity so that the
 * two activities can keep the displayed lists in sync.
 */
public class MainActivity extends MainActivityBase {
    /**
     * Id used to identify a the source of a result intent sent to this
     * activity's onActivityResult() hook.
     * <p/>
     * Declared public for instrumentation testing.
     */
    @SuppressWarnings({"WeakerAccess", "unused"})
    public static final int DOWNLOAD_REQUEST_CODE = 2;

    /**
     * String used in logging output.
     */
    private static final String TAG = "MainActivity";

    /*
     * Activity lifecycle callback methods.
     */

    /**
     * Hook method called when a new instance of Activity is created. One time
     * initialization code goes here, e.g., UI layout initialization.
     *
     * @param savedInstanceState A Bundle object that contains saved state
     *                           information.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Always call super class method first unless you need to request
        // window features using the requestWindowFeature() method which always
        // must precede any call to super.onCreate(). Note that the framework's
        // super class implementation will automatically load the XML layout for
        // this activity.
        super.onCreate(savedInstanceState);
    }

    /**
     * Template method hook method required for starting a download operation
     * for returning a result.
     *
     * @param urls list of URLs to download.
     */
    @Override
    protected void startDownloadForResult(ArrayList<Uri> urls) {
        // Start the Gallery Activity for result with the passed in Uri(s)
        Intent intent = GalleryActivity.makeStartIntent (this,urls);
        startActivityForResult (intent,DOWNLOAD_REQUEST_CODE);
    }

    /*
     * Activity starting helper methods.
     */

    /**
     * Hook method called by the application framework when a started activity
     * has completed and has a status to report and possible results to
     * receive.
     *
     * @param requestCode The request code identifying the started activity has
     *                    finished.
     * @param resultCode  The result code (RESULT_OK or RESULT_CANCELED)
     * @param data        An intent containing the results of the started
     *                    activity.
     */
    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data) {
        Log.d (TAG, "onActivityResults() called.");

        // Check if the request code matches the expected
        // static DOWNLOAD_REQUEST_CODE field value. If so then ...
        if (requestCode == DOWNLOAD_REQUEST_CODE) {
            // If the result code is RESULT_OK, then
            // call a local helper method to extract and display the returned
            // list of image URLs. Otherwise, call the ViewUtils show toast
            // helper to display the string resource with id
            // R.string.download_activity_cancelled. In either case, return
            // without calling the super class method.
            if (resultCode == RESULT_OK) {

                // Extract and display the downloaded images ...
                extractAndUpdateUrls (data);
            } else {
                // Show a toast ...
                ViewUtils.showToast (getApplicationContext (), R.string.download_activity_cancelled);
            }
        // Return ...
        return;
    }
            // DownloadActivity was not started with correct request code.
        else {

            // Allow super class to handle results from unknown origins.
            super.onActivityResult (requestCode,resultCode,data);
        }
        
    }

    /**
     * Helper method that extracts a received list image URLs in the passed
     * intent and then updates the layout to show those URLs.
     *
     * @param intent A data intent containing a list of image URLs as an intent
     *               extra.
     */
    private void extractAndUpdateUrls(Intent intent) {
        // Extract the list of downloaded image URLs from the
        // passed intent.
        ArrayList<Uri> uris = intent.getParcelableArrayListExtra ("data");

        // If the list is empty, call ViewUtils show toast helper
        // to display the string resource R.string.no_images_received.
        if (uris.isEmpty ()) {
            ViewUtils.showToast (getApplicationContext (),R.string.no_images_received);
        }

        // Always call the base class setItems() helper which will
        // refresh the layout to display the list contents (or nothing if the
        // list is empty)
        super.setItems (uris);
    }


    /**
     * Called from adapter when items are being pending loads. Loading is not
     * supported for a this adapter so we can safely ignore this callback (it
     * will never be made).
     *
     * @param notUsed not used
     */
    @Override
    public void onShowRefresh(boolean notUsed) {
        // this method is not used in this assignment.
    }
}

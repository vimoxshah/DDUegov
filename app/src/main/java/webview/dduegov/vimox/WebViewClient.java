package webview.dduegov.vimox;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;


public class WebViewClient extends Activity {

    WebView web;
    final Activity activity = this;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        web = (WebView) findViewById(R.id.webview01);
        web.setWebViewClient(new myWebClient());


        WebSettings settings = web.getSettings();
        web.clearCache(true);
        web.getSettings().setUseWideViewPort(true);
      //  web.setInitialScale(10);
        web.getSettings().setBuiltInZoomControls(true);
        web.clearHistory();
        web.getSettings().setAllowFileAccess(true);
        web.getSettings().setAllowFileAccessFromFileURLs(true);
        web.getSettings().setAllowUniversalAccessFromFileURLs(true);
        web.getSettings().setDomStorageEnabled(true);
        web.getSettings().setPluginState(WebSettings.PluginState.ON);
        web.getSettings().setLoadWithOverviewMode(true);
        web.getSettings().setUseWideViewPort(true);

       // settings.setSupportMultipleWindows(true);
       settings.setJavaScriptEnabled(true);

       settings.setJavaScriptCanOpenWindowsAutomatically(true);
        web.loadUrl("http://egov.ddit.ac.in/index.php?r=site/login");
    }

    public class myWebClient extends android.webkit.WebViewClient
    {
	@Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }
	    
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub

		/* if (url.endsWith(".pdf") || url.endsWith(".xls") || url.endsWith(".ppt")) {
         		   Log.i( "Download TEst", "download: " + url);
            // In this place you should handle the download


		    	new DownloadTask().execute(url);
            		return true;
        }*/
       
        if(url.endsWith(".pdf")) {
            web.setDownloadListener(new DownloadListener() {
                public void onDownloadStart(String url, String userAgent,
                                            String contentDisposition, String mimetype,
                                            long contentLength) {

                    DownloadManager.Request request = new DownloadManager.Request(
                            Uri.parse(url));
                    Log.i("Download TEst", "download: " + url);

                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "download");
                    DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    dm.enqueue(request);

                }


            });
        }
            view.loadUrl(url);
            return true;
        }

    }


    // To handle "Back" key press event for WebView to go back to previous screen.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()) {
            web.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

 /*private class DownloadTask extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... sUrl) {
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(sUrl[0]);
            String filename = url.getFile();
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.e("connectionChecking", "Server didn't return 200. ");
                // handle error
                return null;
            }
		
            input = connection.getInputStream();
	   
           output = new FileOutputStream("/sdcard/download/" + filename); // where to save file

            byte data[] = new byte[4096];
            int count;
            while ((count = input.read(data)) != -1) {
                if (isCancelled()) {
                    return null;
                }
                output.write(data, 0, count);
            }
        } catch (Exception e) {
            // handle error
            Log.e("Download Checking", "Exception: ", e);
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException e) {
                Log.e("IOEXCEPTION", "IOException: ", e);
            }

            if (connection != null)
                connection.disconnect();
        }
        return null;
    }
}*/

}

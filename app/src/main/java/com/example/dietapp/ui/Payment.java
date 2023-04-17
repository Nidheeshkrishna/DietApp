package com.example.dietapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.dietapp.R;

public class Payment extends AppCompatActivity {

    WebView webView;
    Toolbar toolbar;
    String URL, title;
    ProgressDialog dialog;
    public static String s;
    private boolean errorWeb,isLoaded;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        webView=(WebView)findViewById(R.id.webview2);
        WebSettings settings = webView.getSettings();
        settings.setDomStorageEnabled(true);

       // webView.getSettings().setAppCacheMaxSize(5 * 1024 * 1024); // 5MB
        //webView.getSettings().setAppCachePath(Payment.this.getCacheDir().getAbsolutePath());
        webView.getSettings().setAllowFileAccess(true);
       // webView.getSettings().setAppCacheEnabled(true);
        settings.setJavaScriptEnabled(true);
        URL = constant.url+"payment/payment.php?gid="+ SignInActivity.uid+"&"+"shid="+ShopAdapter.shpid+"&"+"pid="+Productadapter.id+"&"+"name="+Productadapter.prname+"&"+"price="+Productadapter.prprice;

       //  URL = constant.url+"cart_online_payment/cart_online_payment.php?rid="+ MainActivity.uid+"&"+"amount="+ AllShop_Adapter.amount;
        // URL = constant.url+"payment/payment.php?";

        //     Toast.makeText(this, ""+URL, Toast.LENGTH_SHORT).show();
        dialog = new ProgressDialog(Payment.this);
        Payment.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webView.setWebViewClient(new Callback());
                webView.loadUrl(URL);
            }
        });
    }
    private class Callback extends WebViewClient {  //HERE IS THE MAIN CHANGE.
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            String webUrl = url;
            // System.out.println(webUrl + "   Results   ");
            if (webUrl != null && !webUrl.isEmpty() && webUrl.contains("tel"))
            {
                // IF A TELEPHONE NUMBER , CALL TO THAT NUMBER
                try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse(webUrl));

                    try
                    {
                        startActivity(callIntent);

                    }
                    catch (Exception e)
                    {

                    }
                    finally
                    {
                        view.stopLoading();

                    }
                }
                catch (Exception e)
                {
                }
            }
            else if (webUrl != null && !webUrl.isEmpty() && webUrl.contains("mailto"))
            {
                // IF A MAIL ID, OPEN COMPOSE MAIL WINDOW
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                int len = webUrl.length();
                System.out.println("Last index  " + webUrl.lastIndexOf("mailto:"));
                webUrl = (String) webUrl.subSequence(webUrl.lastIndexOf("mailto:") + 7, len);
                System.out.println("MAIL  " + webUrl);
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{webUrl});
                i.putExtra(Intent.EXTRA_SUBJECT, "");
                i.putExtra(Intent.EXTRA_TEXT, "");
                try
                {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                }
                catch (android.content.ActivityNotFoundException ex)
                {
                    Toast.makeText(Payment.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();

                }
                finally
                {
                    view.stopLoading();

                }

            }
            else if (webUrl != null && !webUrl.isEmpty() && webUrl.contains("http://"))
            {
                view.loadUrl(url);
            }
            else if (webUrl != null && !webUrl.isEmpty() && webUrl.contains("https://"))
            {

                view.loadUrl(url);

            }
            return (false);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon)
        {
            super.onPageStarted(view, url, favicon);
            //Log.e("WV","Loading....");
            isLoaded = true;
            String text = "Loading.....";
            dialog.setMessage("Loading....");
            dialog.show();
            errorWeb = false;
        }
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error)
        {
         /*   Log.e("WVError", "error");
            errorWeb = true;
            view.setVisibility(View.GONE);
            if (lang.equals("ar"))
            Toast.makeText(getApplicationContext(), "Your Internet Connection May not be active Or " + error, Toast.LENGTH_SHORT).show();
           else
            Toast.makeText(getApplicationContext(), "Your Internet Connection May not be active Or " + error, Toast.LENGTH_SHORT).show();
            onBackPressed();
            */
        }

        @Override
        public void onPageFinished(WebView view, String url)
        {
            super.onPageFinished(view, url);
            if (dialog.isShowing())
            {
                dialog.dismiss();
                dialog.cancel();
            }
            //Log.e("WV","Finished....");
            if (!errorWeb)
            {
                view.setVisibility(View.VISIBLE);
            }
            errorWeb = false;
        }
    }
}

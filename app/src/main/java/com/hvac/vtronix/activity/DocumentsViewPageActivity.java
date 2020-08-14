package com.hvac.vtronix.activity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.hvac.vtronix.R;

public class DocumentsViewPageActivity extends AppCompatActivity {

    String TAG = DocumentsViewPageActivity.class.getSimpleName();

    String stringName,stringProductRequest,stringDesc,stringSpecs,stringDiagramURL;
    private String stringPostName,stringPostContact,stringPostEmail,stringPostMessgae;
    WebView webView;
    private ProgressDialog pDialog;
    TextView mTitleTextView;
    ImageView imageViewBackPressed;
    //RelativeLayout relativeLayout;
    LinearLayout linearLayout;
    public TextView textViewProductChosen,textViewProductSubmitOption;

    //AppCompatEditText editTextName,editTextEmail,editTextContact,editTextOptionalMessage;

    EditText editTextName,editTextEmail,editTextContact,editTextOptionalMessage;
    Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_documents_view_page_details);

        pDialog = new ProgressDialog(DocumentsViewPageActivity.this);
        pDialog.setCancelable(false);

        ActionBar mActionBar = getSupportActionBar();
        if(mActionBar!=null) {
            mActionBar.setDisplayHomeAsUpEnabled(false);
            mActionBar.setDisplayShowHomeEnabled(false);
            mActionBar.setDisplayShowTitleEnabled(false);
        }
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView            =   mInflater.inflate(R.layout.custom_actionbar_final_page, null);
        mTitleTextView              =   (TextView) mCustomView.findViewById(R.id.title_text_inner_doc_page);
        imageViewBackPressed        =   (ImageView)mCustomView.findViewById(R.id.imageView_back_icon_inner_doc);
        textViewProductSubmitOption =   (TextView) mCustomView.findViewById(R.id.imageButton_inner_submit);


        imageViewBackPressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if(mActionBar!=null)
        {
            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
        }

        webView                 =   findViewById(R.id.webView_documents);

        linearLayout            =   findViewById(R.id.user_filled_product_page);

        editTextName            =   findViewById(R.id.user_editText_name);
        editTextContact         =   findViewById(R.id.user_editText_contact);
        editTextEmail           =   findViewById(R.id.user_editText_email);
        editTextOptionalMessage =   findViewById(R.id.user_editText_message);

        textViewProductChosen   =    findViewById(R.id.user_chosen_product);

       // buttonSubmit            =    findViewById(R.id.product_submit);

        Intent iin      = getIntent();
        Bundle extras   = iin.getExtras();
        if (extras != null) {
            stringName          = (String) extras.get("ProductName");
            stringDiagramURL    = (String) extras.get("ProductDiagram");
            stringSpecs         = (String) extras.get("ProductSpec");

            stringProductRequest    = (String) extras.get("ProductRequest");

            Log.e(TAG, "onCreate: Product Page name :" + stringName+"\t Specs:"+stringSpecs
                    + "\n productRequest fromm user :" + stringProductRequest+"\t Diagram :"+stringDiagramURL);
        }

        if(stringProductRequest!=null)
        {
            mTitleTextView.setText("Request Product");
            textViewProductChosen.setText(stringName);

        }
        else
        {
            linearLayout.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            mTitleTextView.setText(stringName);
        }

        // For API level below 18 (This method was deprecated in API level 18)
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                // pDialog.show();
                showDialog();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // pDialog.dismiss();
                hideDialog();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                //  setProgressBarVisibility(View.GONE);
                Log.e(TAG, "onReceivedError: Error:"+error.toString() );
            }
        });


        if(stringSpecs!=null)
        {
            linearLayout.setVisibility(View.GONE);
            textViewProductSubmitOption.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            Log.e(TAG, "onCreate: product Specification :"+ stringSpecs);

            webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" +stringSpecs);

        }
        else if(stringDiagramURL!= null)
        {
           // mTitleTextView.setText(stringName);
            linearLayout.setVisibility(View.GONE);
            textViewProductSubmitOption.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            Log.e(TAG, "onCreate: Diagram View:"+ stringDiagramURL);

            webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" +stringDiagramURL);

        }
        else if(stringProductRequest!= null && stringProductRequest.equals("click"))
        {
            linearLayout.setVisibility(View.VISIBLE);
            textViewProductSubmitOption.setVisibility(View.VISIBLE);

            Log.e(TAG, "onCreate: Chosen is:"+stringName);

            //textViewProductChosen.setText(stringName);
            webView.setVisibility(View.GONE);
           // Toast.makeText(this, "Enter Request", Toast.LENGTH_SHORT).show();

        }
        else {
            webView.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);
        }


        //Click to send product Request for particular E-mail Address
        textViewProductSubmitOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stringPostName      =   editTextName.getText().toString().trim();
                stringPostContact   =   editTextContact.getText().toString().trim();
                stringPostEmail     =   editTextEmail.getText().toString().trim();
                stringPostMessgae   =   editTextOptionalMessage.getText().toString().trim();

                if(isEmpty(editTextName)){
                    editTextName.setError("Required UserName");
                }
                else if(isEmail(editTextContact))
                {
                    editTextContact.setError("Required Contact Number");
                }
                else if(!isEmail(editTextEmail) && editTextEmail.getText().toString().trim().contains("@"))
                {
                    editTextEmail.setError("Enter valid Email...!");
                }
                else
                {
                    Log.e(TAG, "onClick: All-Fields" );
                    PostProductPurchase(stringPostName,stringPostContact,stringPostEmail,stringName,stringPostMessgae);
                }


            }
        });

    }

    private void PostProductPurchase(String Name, String Contact, String Email, String ProductName, String MSG) {

        //Mail ID: sales@vtronix.com (request mail)

        //Refer URL:    https://stackoverflow.com/questions/54115452/how-to-send-mail-on-button-clicked-android-studio/54115640

//        String stringMailContent =  "Hi,\n "
//                                    +"User Name              : "+Name+", \n"
//                                    +"User Contact Number    : "+Contact+", \n"
//                                    +"User E-mail Address    : "+Email+", \n"
//                                    +"Chosen Product         : "+ProductName+", \n"
//                                    +"Message                : "+MSG;

// Refer :  https://www.w3schools.com/html/tryit.asp?filename=tryhtml_table_intro
        String sendMSG = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<style>\n" +
                "table {\n" +
                "  font-family: arial, sans-serif;\n" +
                "  border-collapse: collapse;\n" +
                "  width: 100%;\n" +
                "}\n" +
                "\n" +
                "td, th {\n" +
                "  border: 1px solid #dddddd;\n" +
                "  text-align: left;\n" +
                "  padding: 8px;\n" +
                "}\n" +
                "\n" +
                "tr:nth-child(even) {\n" +
                "  background-color: #dddddd;\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h2>Product Request Details</h2>\n" +
                "\n" +
                "<table>\n" +
                "  <tr>\n" +
                "    <td>User Name</td>\n" +
                "    <td>Contact Number</td>\n" +
                "    <td>Email Address</td>\n" +
                "    <td>Chosen Product</td>\n" +
                "    <td>Message</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>"+Name+"</td>\n" +
                "    <td>"+Contact+"</td>\n" +
                "    <td>"+Email+"</td>\n" +
                "    <td>"+ProductName+"</td>\n" +
                "    <td>"+MSG+"</td>\n" +
                "  </tr>\n" +
                "</table>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n"
                ;
        //vnandha001@gmail.com

        String mailto = "mailto:info.growtechnologies@gmail.com" +
                "?cc=" +
                "&subject=" + Uri.encode("Product Request") +
                "&body=" + Uri.encode(sendMSG);
                //"&body=" + Html.fromHtml(stringMailContent);
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse(mailto));

        try {
            startActivity(emailIntent);
            Toast.makeText(this, "Mail Sent SuccessFully", Toast.LENGTH_SHORT).show();
            finish();

        } catch (ActivityNotFoundException e) {
            Toast.makeText(DocumentsViewPageActivity.this, "Error to open email app", Toast.LENGTH_SHORT).show();
        }


    }

    boolean isEmail(EditText text)
    {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text)
    {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

//    boolean isEmail(AppCompatEditText text)
//    {
//        CharSequence email = text.getText().toString();
//        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
//    }
//
//    boolean isEmpty(AppCompatEditText text)
//    {
//        CharSequence str = text.getText().toString();
//        return TextUtils.isEmpty(str);
//    }

    private void showDialog() {
        pDialog.setMessage("Please wait Loading in...");
        pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


}

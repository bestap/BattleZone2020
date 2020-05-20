package com.bwithhkj.battle.zone.tournaments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bwithhkj.battle.zone.tournaments.config.config;
import com.google.android.material.button.MaterialButton;
import com.khalti.checkout.helper.Config;
import com.khalti.checkout.helper.KhaltiCheckOut;
import com.khalti.checkout.helper.OnCheckOutListener;
import com.khalti.checkout.helper.PaymentPreference;
import com.khalti.widget.KhaltiButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bwithhkj.battle.zone.tournaments.config.config.merchant_public_key;

public class Integrationforkhalti extends AppCompatActivity {
    @BindView(R.id.kbPay)
    KhaltiButton khaltiButton;
    @BindView(R.id.btnMore)
    MaterialButton btnMore;
    @BindView(R.id.kbKhalti)
    KhaltiButton kbKhalti;
    @BindView(R.id.kbEBanking)
    KhaltiButton kbEBanking;
    @BindView(R.id.kbMobileBanking)
    KhaltiButton kbMobileBanking;
    @BindView(R.id.kbSct)
    KhaltiButton kbSct;
    @BindView(R.id.kbConnectIps)
    KhaltiButton kbConnectIps;

    private final JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";

    //user
    private static final String TAG_USERID = "userid";
    private static final String TAG_USERNAME = "username";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_MOBILE = "mobile";

    private String order_id;
    private int success, success1;

    //progress Dialouge
    private ProgressDialog pDialog;

    //balance
    private static final String TAG_USERBALANCE = "balance";

    //instamojo
    private static final String TAG_INSTA_ORDERID = "instaorderid";
    private static final String TAG_INSTA_TXNID = "instatxnid";
    private static final String TAG_INSTA_PAYMENTID = "instapaymentid";
    private static final String TAG_INSTA_TOKEN = "instatoken";


    private static final String khalti = config.mainurl + "khalti.php";
    private static final String url = config.mainurl + "payment.php";
   private HashMap<String, Object> tempjsondata =new HashMap<String, Object>();

    //Prefrance
    private static PrefManager prf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khalti);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String amt = intent.getExtras().getString("Amount");
        long amount= (Integer.parseInt(amt))*100;



        prf = new PrefManager(Integrationforkhalti.this);
        final int min = 1000;
        final int max = 10000;
        final int random = new Random().nextInt((max - min) + 1) + min;
        order_id = prf.getString(TAG_USERNAME) +random;

       /* walletBalance = (TextView) findViewById(R.id.walletBalance);
        main = (LinearLayout) findViewById(R.id.mainLayout);
        balance = prf.getString(TAG_USERBALANCE);
        username = prf.getString(TAG_USERNAME);
        email = prf.getString(TAG_EMAIL);
        number = prf.getString(TAG_MOBILE);
        walletBalance.setText("Rs "+balance);*/



        HashMap<String, Object> map = new HashMap<String, Object>() {{
            put("username", "username");
            put("Khalti_phonenumber", "phonenumber");
        }};


                Config mainConfig = new Config.Builder(merchant_public_key, order_id, "Khalti", amount, new OnCheckOutListener() {
            @Override
            public void onError(@NonNull String action, @NonNull Map<String, String> errorMap) {
                Log.i(action, errorMap.toString());
                //show Toast and go to mywallet activity
            }

            @Override
            public void onSuccess(@NonNull Map<String, Object> data) {
                Log.i("success", data.toString());
                tempjsondata = (HashMap<String, Object>)data;
                new OneLoadProducts().execute();
            }
        })
                .paymentPreferences(new ArrayList<PaymentPreference>() {{
                    add(PaymentPreference.KHALTI);
                     add(PaymentPreference.EBANKING);
                     add(PaymentPreference.MOBILE_BANKING);
                     add(PaymentPreference.CONNECT_IPS);
                     add(PaymentPreference.SCT);
                }})
                .additionalData(map)
                .build();

        Config khaltiConfig = new Config.Builder(merchant_public_key, order_id, "Khalti", amount, new OnCheckOutListener() {
            @Override
            public void onError(@NonNull String action, @NonNull Map<String, String> errorMap) {
                Log.i(action, errorMap.toString());
                onBtnMoreLoadClick();
            }

            @Override
            public void onSuccess(@NonNull Map<String, Object> data) {
                Log.i("success", data.toString());
                tempjsondata = (HashMap<String, Object>)data;
                new OneLoadProducts().execute();

            }
        })
                .paymentPreferences(new ArrayList<PaymentPreference>() {{
                    add(PaymentPreference.KHALTI);
                }})
                .additionalData(map)
                .build();

        Config eBankingConfig = new Config.Builder(merchant_public_key, order_id, "E Banking", amount, new OnCheckOutListener() {
            @Override
            public void onError(@NonNull String action, @NonNull Map<String, String> errorMap) {
                Log.i(action, errorMap.toString());
            }

            @Override
            public void onSuccess(@NonNull Map<String, Object> data) {
                Log.i("success", data.toString());
            }
        })
                .paymentPreferences(new ArrayList<PaymentPreference>() {{
                    add(PaymentPreference.EBANKING);
                }})
                .additionalData(map)
                .build();

        Config mBankingConfig = new Config.Builder(merchant_public_key, order_id, "M Banking", amount, new OnCheckOutListener() {
            @Override
            public void onError(@NonNull String action, @NonNull Map<String, String> errorMap) {
                Log.i(action, errorMap.toString());
            }

            @Override
            public void onSuccess(@NonNull Map<String, Object> data) {
                Log.i("success", data.toString());
            }
        })
                .paymentPreferences(new ArrayList<PaymentPreference>() {{
                    add(PaymentPreference.MOBILE_BANKING);
                }})
                .additionalData(map)
                .build();

        Config sctConfig = new Config.Builder(merchant_public_key, order_id, "SCT", amount, new OnCheckOutListener() {
            @Override
            public void onError(@NonNull String action, @NonNull Map<String, String> errorMap) {
                Log.i(action, errorMap.toString());
            }

            @Override
            public void onSuccess(@NonNull Map<String, Object> data) {
                Log.i("success", data.toString());
            }
        })
                .paymentPreferences(new ArrayList<PaymentPreference>() {{
                    add(PaymentPreference.SCT);
                }})
                .additionalData(map)
                .build();

        Config connectIpsConfig = new Config.Builder(merchant_public_key, order_id, "Connect IPS", amount, new OnCheckOutListener() {
            @Override
            public void onError(@NonNull String action, @NonNull Map<String, String> errorMap) {
                Log.i(action, errorMap.toString());
            }

            @Override
            public void onSuccess(@NonNull Map<String, Object> data) {
                Log.i("success", data.toString());
            }
        })
                .paymentPreferences(new ArrayList<PaymentPreference>() {{
                    add(PaymentPreference.CONNECT_IPS);
                }})
                .additionalData(map)
                .build();

        KhaltiCheckOut khaltiCheckOut = new KhaltiCheckOut(this, mainConfig);
        khaltiButton.setOnClickListener(view -> {
            khaltiCheckOut.show();
        });

        KhaltiCheckOut khaltiCheckOut1 = new KhaltiCheckOut(this, khaltiConfig);
        kbKhalti.setOnClickListener(v -> khaltiCheckOut1.show());

        kbEBanking.setCheckOutConfig(eBankingConfig);

        kbMobileBanking.setCheckOutConfig(mBankingConfig);

        kbSct.setCheckOutConfig(sctConfig);

        kbConnectIps.setCheckOutConfig(connectIpsConfig);
    }

    @OnClick(R.id.btnMore)
    public void onBtnMoreLoadClick() {
        startActivity(new Intent(this, HomeActivity.class));
    }



    class OneLoadProducts extends AsyncTask<String, String, String> {


        HashMap<String,Object> data = (HashMap<String,Object>)tempjsondata;


        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Integrationforkhalti.this);
            pDialog.setMessage("Loading Please wait...");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {

        String temp = data.get("amount").toString();
        Double tempint = Double.parseDouble(temp)/100;
        temp = tempint.toString();
        String paymentid = data.get("product_name").toString();
        String tokenid = data.get("token").toString();
        //updating value to database
        Map<String, String> params = new HashMap<>();
        params.put(TAG_USERID, prf.getString(TAG_USERID));
        params.put("addamount", temp);
        params.put(TAG_INSTA_ORDERID, order_id);
        params.put(TAG_INSTA_TXNID, "txnid");
        params.put(TAG_INSTA_PAYMENTID, paymentid);
        params.put(TAG_INSTA_TOKEN, tokenid);
        params.put("status", "Unverified - "+ data.get("product_name").toString());

        // getting JSON string from URL
        JSONObject json1 = jsonParser.makeHttpRequest(url, "POST", params);
        JSONObject json2 = jsonParser.makeHttpRequest(khalti, "POST", params);



        // Check your log cat for JSON reponse
//            Log.d("All jsonarray: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                success = json1.getInt(TAG_SUCCESS);
                success1 = json2.getInt(TAG_SUCCESS);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;

            /*HashMap<String, String> esewa = new HashMap<String, String>();
            params.put(""TAG_USERID"", prf.getString(TAG_USERID));
            params.put("addamount", temp);*/
        }



        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();

            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {

                    String temp = data.get("amount").toString();
                    Double tempint = Double.parseDouble(temp)/100;
                    temp = tempint.toString();
                    /*
                      Updating parsed JSON data into ListView
                     */
                    if (success == 1 && success1 == 1) {
                        // jsonarray found
                        // Getting Array of jsonarray
                        String s = temp;
                        double d = Double.parseDouble(s);
                        int i = (int) d;

                        int bal = Integer.parseInt(prf.getString(TAG_USERBALANCE))+ i;
                        prf.setString(TAG_USERBALANCE,Integer.toString(bal));

                        Intent intent = new Intent(Integrationforkhalti.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);


                        Toast.makeText(Integrationforkhalti.this,"Payment done. Now join match",Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(Integrationforkhalti.this,"Something went wrong. Try again!",Toast.LENGTH_LONG).show();

                    }

                }
            });

        }
    }
}

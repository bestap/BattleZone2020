package com.bwithhkj.battle.zone.tournaments.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.pm.PackageManager;

import com.bwithhkj.battle.zone.tournaments.Integrationforkhalti;
import com.bwithhkj.battle.zone.tournaments.PrefManager;
import com.bwithhkj.battle.zone.tournaments.R;
import com.bwithhkj.battle.zone.tournaments.config.config;


public class AddMoneyFragment extends Fragment {

    //user
    private static final String TAG_FIRSTNAME = "firstname";
    private static final String TAG_USERNAME = "username";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_MOBILE = "mobile";

    private Button addmoney, contactFacebook;
    private EditText amount;
    private String email;
    private TextView errorMessage;
    private String name;
    private String number;
    private final String paymentGateway="khalti";
    private String username;

    //Prefrance
    private static PrefManager prf;

    public AddMoneyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prf = new PrefManager(getActivity());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootViewone = inflater.inflate(R.layout.fragment_addmoney, container, false);

        amount = (EditText) rootViewone.findViewById(R.id.amountEditText);
        addmoney = (Button) rootViewone.findViewById(R.id.addButton);
        contactFacebook = (Button) rootViewone.findViewById(R.id.contactFacebook);
        errorMessage = (TextView) rootViewone.findViewById(R.id.errorMessage);

        username = prf.getString(TAG_USERNAME);
        email = prf.getString(TAG_EMAIL);
        name = prf.getString(TAG_FIRSTNAME);
        number = prf.getString(TAG_MOBILE);

        addmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String obj = amount.getText().toString();
                if (!obj.isEmpty()) {
                    int amt = Integer.parseInt(obj);
                    if (amt < 20) {
                        errorMessage.setVisibility(View.VISIBLE);
                    } else if (amt > 20 || amt == 20) {
                        errorMessage.setVisibility(View.GONE);

                        if (paymentGateway.equals("khalti")) {
                           // ((MyWalletActivity) getActivity()).PaytmAddMoney(email, number, obj, "Add Money to Wallet", name);

                            Intent intent = new Intent(getActivity(), Integrationforkhalti.class);
                            intent.putExtra("Amount",obj);
                            startActivity(intent);

                        }
                    }
                }/* else if (obj.isEmpty()) {
                    errorMessage.setVisibility(View.VISIBLE);
                    errorMessage.setText("Enter minimum Rs 20");
                    errorMessage.setTextColor(Color.parseColor("#ff0000"));
                }*/
            }
        });
        contactFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //add fb page
                PackageManager pm = getActivity().getPackageManager();
                Intent intent = pm.getLaunchIntentForPackage("com.facebook.katana");
                Intent intentlite = pm.getLaunchIntentForPackage("com.facebook.lite");
                try {
                    if (intent != null) {
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(config.fbpage));
                        startActivity(intent);
                    } else if (intentlite != null) {
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(config.fbpage));
                        startActivity(intent);

                    } else {
                        // Bring user to the market or let them choose an app?
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setData(Uri.parse("market://details?id=" + "com.facebook.katana"));
                        if (intent != null) {
                            startActivity(intent);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



        return rootViewone;
    }

}

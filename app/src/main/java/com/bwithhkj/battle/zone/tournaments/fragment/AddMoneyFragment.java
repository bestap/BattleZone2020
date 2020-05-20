package com.bwithhkj.battle.zone.tournaments.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bwithhkj.battle.zone.tournaments.Integrationforkhalti;
import com.bwithhkj.battle.zone.tournaments.PrefManager;
import com.bwithhkj.battle.zone.tournaments.R;


public class AddMoneyFragment extends Fragment {

    //user
    private static final String TAG_FIRSTNAME = "firstname";
    private static final String TAG_USERNAME = "username";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_MOBILE = "mobile";

    private Button addmoney;
    private EditText amount;
    private String email;
    private TextView errorMessage;
    private String name;
    private String number;
    private final String paymentGateway="paytm";
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
        errorMessage = (TextView) rootViewone.findViewById(R.id.errorMessage);

        username = prf.getString(TAG_USERNAME);
        email = prf.getString(TAG_EMAIL);
        name = prf.getString(TAG_FIRSTNAME);
        number = prf.getString(TAG_MOBILE);
//        paymentGateway = prf.getString("paymentGateway", "paytm");

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

//                        if (paymentGateway.equals("instamojo")) {
//                            ((MyWalletActivity) getActivity()).callInstamojoPay(email, number, obj, "Add Money to Wallet", name);
//                        }

                        if (paymentGateway.equals("paytm")) {
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

        return rootViewone;
    }

}

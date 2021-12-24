package com.example.myapplication1;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.example.myapplication1.DataBase.DbHandler;
import com.google.zxing.Result;


public class ScannerFragment extends Fragment {
    SendDataInterFace sendDataInterFace;
    DbHandler dbHandler;
    boolean flag =false;
    TextView tv ;
    public interface SendDataInterFace{
         void sendData(String qrCode);
    }


    private CodeScanner mCodeScanner;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final Activity activity = getActivity();
        flag =false;
        View root = inflater.inflate(R.layout.fragment_scanner, container, false);
        CodeScannerView scannerView = root.findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(activity, scannerView);
        mCodeScanner.setScanMode(ScanMode.CONTINUOUS);
        mCodeScanner.setAutoFocusMode(AutoFocusMode.SAFE);
        dbHandler =new DbHandler(getContext());
        tv =root.findViewById(R.id.scannerMassage);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       try {
                           for (int i =0; i<dbHandler.getAllQrCode().size();i++){
                               if(dbHandler.getAllQrCode().get(i).getCode().equals(result.getText())){
                                   sendDataInterFace.sendData(result.getText());
                                   tv.setText("Product Found");
                                   flag =true;
                                   break;
                               }
                           }
                           if(!flag){
                               tv.setText("UnKnown Product");
                           }
                       }catch (Exception e){
                           e.printStackTrace();
                       }
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        try {
            sendDataInterFace = (SendDataInterFace) activity;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
package com.hack.dumkahackathon;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class QRScan extends Activity {

    private IntentIntegrator mIntentIntegrator;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mIntentIntegrator = new IntentIntegrator(this);
        mIntentIntegrator.setCaptureActivity(CaptureActivityAnyOrientation.class);
        mIntentIntegrator.setPrompt("Scan your Aadhar QE Code");
        mIntentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        mIntentIntegrator.setOrientationLocked(false);
        mIntentIntegrator.initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        Log.e("scan", "result given");
        final IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            XmlPullParser parser = null;
            InputStream stream = null;
            try {
                Log.e("scan", "parser tried");
                parser = Xml.newPullParser();
                //stream = new ByteArrayInputStream(scanResult.getContents().getBytes("UTF-8"));
                stream = new ByteArrayInputStream(scanResult.getContents().getBytes("UTF-8"));

                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(stream, null);
                parser.nextTag();
                if (!parser.getName().equals("PrintLetterBarcodeData")) {
                    mIntentIntegrator.initiateScan();
                    return;
                }
                Log.e("scan", "parsed");
                String uid = parser.getAttributeValue(null, "uid");
                String name = parser.getAttributeValue(null, "name");
                String pincode = parser.getAttributeValue(null, "pc");
                String gender = parser.getAttributeValue(null, "gender");
                String dist = parser.getAttributeValue(null, "dist");
                session = new SessionManager(getApplicationContext());
                session.createLoginSession(name, uid, pincode, dist, gender);
                Log.e("scan", "user created");
//                Intent faceIntent = new Intent(this, faceActivity.class);
//
//                SharedPreferences settings = getSharedPreferences(PREFS, 0); // 0 - for private mode
//                SharedPreferences.Editor editor = settings.edit();
//                editor.putBoolean("hasLoggedIn", true);
//                editor.commit();
//                startActivity(faceIntent);
                //finish();
                Intent mainIntent = new Intent(this, MainActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mainIntent);


            } catch(XmlPullParserException xppe) {
                mIntentIntegrator.initiateScan();
            } catch (IOException ioe) {
                mIntentIntegrator.initiateScan();
            } finally {
                try {
                    if (stream != null) {
                        stream.close();
                    }
                } catch (IOException ioe) {
                    mIntentIntegrator.initiateScan();
                }

            }
        }
    }
}

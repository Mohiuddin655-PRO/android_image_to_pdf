package com.picon.utils.apis;

import android.os.AsyncTask;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.picon.utils.constains.Code;
import com.picon.utils.response.Response;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;

public class TranslateApi extends AsyncTask<Void, Void, Response<String>> {

    private String defaultLang = "en";
    private String translateLang;
    private String text;

    private OnTranslationCompleteListener listener;

    public TranslateApi setDefaultLang(@NonNull String defaultLang) {
        this.defaultLang = defaultLang;
        return this;
    }

    public TranslateApi setTranslateLang(@NonNull String translateLang) {
        this.translateLang = translateLang;
        return this;
    }

    public TranslateApi setTranslateText(@NonNull String text) {
        this.text = text;
        return this;
    }

    public TranslateApi setListener(OnTranslationCompleteListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    protected Response<String> doInBackground(Void... voids) {

        final Response<String> response = new Response<>(Code.TRANSLATE);
        response.setResult("");

        if (!TextUtils.isEmpty(defaultLang) && !TextUtils.isEmpty(translateLang) && !TextUtils.isEmpty(text)) {

            try {

                String encode = URLEncoder.encode(text, "utf-8");

                String uri = String.format("https://translate.googleapis.com/translate_a/single?client=gtx&sl=%s&tl=%s&dt=t&q=%s",
                        defaultLang, translateLang, encode);

                HttpResponse execute = new DefaultHttpClient().execute(new HttpGet(uri));
                StatusLine statusLine = execute.getStatusLine();

                if (statusLine.getStatusCode() == 200) {

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    execute.getEntity().writeTo(outputStream);

                    JSONArray jsonArray = new JSONArray(outputStream.toString()).getJSONArray(0);

                    outputStream.close();

                    StringBuilder finalText = new StringBuilder();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONArray json = jsonArray.getJSONArray(i);
                        finalText.append(json.get(0).toString());
                    }

                    return response.setResult(String.valueOf(finalText));
                }

                execute.getEntity().getContent().close();

                throw new IOException(statusLine.getReasonPhrase());

            } catch (Exception e) {
                return response.setException(e);
            }
        }

        return response;
    }

    @Override
    protected void onPostExecute(Response<String> response) {
        listener.onCompleted(response);
    }

    public void setOnTranslationCompleteListener(@NonNull OnTranslationCompleteListener listener) {
        this.listener = listener;
    }

    public interface OnTranslationCompleteListener {
        void onCompleted(@NonNull Response<String> response);
    }

}

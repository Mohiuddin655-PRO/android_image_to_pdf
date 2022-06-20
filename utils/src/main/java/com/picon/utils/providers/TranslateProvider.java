package com.picon.utils.providers;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.picon.utils.apis.TranslateApi;
import com.picon.utils.helpers.LocaleHelper;
import com.picon.utils.validators.Validator;

public class TranslateProvider {

    public static LiveData<String> getTranslatingText(@NonNull String language, @NonNull String original_text) {

        final MutableLiveData<String> liveData = new MutableLiveData<>();

        if (Validator.isValidString(original_text)) {

            TranslateApi translateApi = new TranslateApi()
                    .setDefaultLang(LocaleHelper.getDetectedISO2Language(original_text))
                    .setTranslateLang(language)
                    .setTranslateText(original_text);

            translateApi.setOnTranslationCompleteListener(task -> {
                if (Validator.isValidString(task.getResult())) {
                    liveData.setValue(task.getResult());
                }
            });

            translateApi.execute();
        }

        return liveData;
    }

}

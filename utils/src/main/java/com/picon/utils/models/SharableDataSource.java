package com.picon.utils.models;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.picon.utils.constains.ShareType;

import java.io.Serializable;
import java.util.ArrayList;

public class SharableDataSource implements Serializable {

    @ShareType
    private String type;

    private String chooser_title;
    private String subject;
    private String text;
    private String html_text;
    private String uid;
    private Uri stream;
    private ArrayList<Uri> streams;
    private String[] to_addresses;
    private String[] bcc_addresses;
    private String[] cc_addresses;

    public SharableDataSource() {
        this.type = ShareType.PLAIN_TEXT;
    }

    public SharableDataSource(@ShareType String type) {
        this.type = type;
    }

    @ShareType
    public String getType() {
        return type;
    }

    public SharableDataSource setType(@ShareType String type) {
        this.type = type;
        return this;
    }

    public String getChooser_title() {
        return chooser_title != null ? chooser_title : "Share to...";
    }

    public SharableDataSource setChooser_title(@NonNull String chooser_title) {
        this.chooser_title = chooser_title;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public SharableDataSource setSubject(@NonNull String subject) {
        this.subject = subject;
        return this;
    }

    public String getText() {
        return text;
    }

    public SharableDataSource setText(@NonNull String text) {
        this.text = text;
        return this;
    }

    public String getHtml_text() {
        return html_text;
    }

    public SharableDataSource setHtml_text(@NonNull String html_text) {
        this.html_text = html_text;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public SharableDataSource setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public Uri getStream() {
        return stream;
    }

    public SharableDataSource setStream(@NonNull Uri stream) {
        this.stream = stream;
        return this;
    }

    public ArrayList<Uri> getStreams() {
        return streams;
    }

    public SharableDataSource setStreams(@NonNull ArrayList<Uri> streams) {
        this.streams = streams;
        return this;
    }

    public String[] getTo_addresses() {
        return to_addresses;
    }

    public SharableDataSource setTo_addresses(@NonNull String... to_addresses) {
        this.to_addresses = to_addresses;
        return this;
    }

    public String[] getBcc_addresses() {
        return bcc_addresses;
    }

    public SharableDataSource setBcc_addresses(@NonNull String... bcc_addresses) {
        this.bcc_addresses = bcc_addresses;
        return this;
    }

    public String[] getCc_addresses() {
        return cc_addresses;
    }

    public SharableDataSource setCc_addresses(@NonNull String... cc_addresses) {
        this.cc_addresses = cc_addresses;
        return this;
    }
}

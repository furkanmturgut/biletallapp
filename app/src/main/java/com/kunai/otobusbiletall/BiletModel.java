package com.kunai.otobusbiletall;

public class BiletModel {

    String seferler;
    String tarih;

    public BiletModel() {
    }

    public BiletModel(String seferler, String tarih) {
        this.seferler = seferler;
        this.tarih = tarih;
    }

    public String getSeferler() {
        return seferler;
    }

    public void setSeferler(String seferler) {
        this.seferler = seferler;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }
}

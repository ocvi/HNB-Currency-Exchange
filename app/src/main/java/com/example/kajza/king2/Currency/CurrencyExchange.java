package com.example.kajza.king2.Currency;


import android.os.Parcel;
import android.os.Parcelable;

public class CurrencyExchange implements Parcelable {


    protected CurrencyExchange(Parcel in) {
        broj_tecajnice = in.readString();
        datum = in.readString();
        drzava = in.readString();
        sifra_valute = in.readString();
        valuta = in.readString();
        if (in.readByte() == 0) {
            jedinica = null;
        } else {
            jedinica = in.readInt();
        }
        kupovni_tecaj = in.readString();
        srednji_tecaj = in.readString();
        prodajni_tecaj = in.readString();
    }

    public static final Creator<CurrencyExchange> CREATOR = new Creator<CurrencyExchange>() {
        @Override
        public CurrencyExchange createFromParcel(Parcel in) {
            return new CurrencyExchange(in);
        }

        @Override
        public CurrencyExchange[] newArray(int size) {
            return new CurrencyExchange[size];
        }
    };

    public String getBroj_tecajnice() {
        return broj_tecajnice;
    }

    public void setBroj_tecajnice(String broj_tecajnice) {
        this.broj_tecajnice = broj_tecajnice;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    public String getSifra_valute() {
        return sifra_valute;
    }

    public void setSifra_valute(String sifra_valute) {
        this.sifra_valute = sifra_valute;
    }

    public String getValuta() {
        return valuta;
    }

    public void setValuta(String valuta) {
        this.valuta = valuta;
    }

    public Integer getJedinica() {
        return jedinica;
    }

    public void setJedinica(Integer jedinica) {
        this.jedinica = jedinica;
    }

    public String getKupovni_tecaj() {
        return kupovni_tecaj;
    }

    public void setKupovni_tecaj(String kupovni_tecaj) {
        this.kupovni_tecaj = kupovni_tecaj;
    }

    public String getSrednji_tecaj() {
        return srednji_tecaj;
    }

    public void setSrednji_tecaj(String srednji_tecaj) {
        this.srednji_tecaj = srednji_tecaj;
    }

    public String getProdajni_tecaj() {
        return prodajni_tecaj;
    }

    public void setProdajni_tecaj(String prodajni_tecaj) {
        this.prodajni_tecaj = prodajni_tecaj;
    }

    String broj_tecajnice;
    String datum;
    String drzava;
    String sifra_valute;
    String valuta;
    Integer jedinica;
    String kupovni_tecaj;
    String srednji_tecaj;
    String prodajni_tecaj;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(broj_tecajnice);
        parcel.writeString(datum);
        parcel.writeString(drzava);
        parcel.writeString(sifra_valute);
        parcel.writeString(valuta);
        if (jedinica == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(jedinica);
        }
        parcel.writeString(kupovni_tecaj);
        parcel.writeString(srednji_tecaj);
        parcel.writeString(prodajni_tecaj);
    }
}

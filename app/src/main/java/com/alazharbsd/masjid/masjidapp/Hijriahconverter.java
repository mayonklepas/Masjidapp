package com.alazharbsd.masjid.masjidapp;

import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Minami on 14/05/2017.
 */

public class Hijriahconverter {

    public Hijriahconverter(){

    }

    public String gettglhijriah(){
        UmmalquraCalendar cal = new UmmalquraCalendar();
        int tahun=cal.get(Calendar.YEAR);
        int bulan=cal.get(Calendar.MONTH)+1;
        int tanggal=cal.get(Calendar.DAY_OF_MONTH);
        String stbulan="";
        switch (bulan){
            case 1 : stbulan="Muharram";
                break;
            case 2 : stbulan="Safar";
                break;
            case 3 : stbulan="Rabiul Awal";
                break;
            case 4 : stbulan="Rabiul Akhir";
                break;
            case 5 : stbulan="Jumadil Awal";
                break;
            case 6 : stbulan="Jumadil Akhir";
                break;
            case 7 : stbulan="Rajab";
                break;
            case 8 : stbulan="Sya'ban";
                break;
            case 9 : stbulan="Ramadhan";
                break;
            case 10 : stbulan="Syawal";
                break;
            case 11 : stbulan="Dzulkaidah";
                break;
            case 12 : stbulan="Dzulhijjah";
                break;
        }

        return tanggal+" "+stbulan+" "+tahun +" H";

    }


    public String gettglhijriah(Date date){
        Calendar cl=Calendar.getInstance();
        cl.setTime(date);
        UmmalquraCalendar cal = new UmmalquraCalendar();
        int tahun=cal.get(Calendar.YEAR);
        int bulan=cal.get(Calendar.MONTH)+1;
        int tanggal=cal.get(Calendar.DAY_OF_MONTH);
        String stbulan="";
        switch (bulan){
            case 1 : stbulan="Muharram";
                break;
            case 2 : stbulan="Safar";
                break;
            case 3 : stbulan="Rabiul Awal";
                break;
            case 4 : stbulan="Rabiul Akhir";
                break;
            case 5 : stbulan="Jumadil Awal";
                break;
            case 6 : stbulan="Jumadil Akhir";
                break;
            case 7 : stbulan="Rajab";
                break;
            case 8 : stbulan="Sya'ban";
                break;
            case 9 : stbulan="Ramadhan";
                break;
            case 10 : stbulan="Syawal";
                break;
            case 11 : stbulan="Dzulkaidah";
                break;
            case 12 : stbulan="Dzulhijjah";
                break;
        }

        return tanggal+" "+stbulan+" "+tahun +" H";

    }



}

package com.alazharbsd.masjid.masjidapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
//import android.app.AlertDialog;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Minami on 11/05/2017.
 */

public class LainnyaFragment extends Fragment {

    ImageView imgkegiatan,imgkhutbah,imgimam,imgartikel,imgramadhan,imgvideo,imggaleri,imgprofile,imginfomasjid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_lainnya,container,false);
        imgkegiatan=(ImageView) v.findViewById(R.id.imgkegiatan);
        imgkhutbah=(ImageView) v.findViewById(R.id.imgkhutbah);
        imgimam=(ImageView) v.findViewById(R.id.imgimam);
        imgramadhan=(ImageView) v.findViewById(R.id.imgramadhan);
        imgartikel=(ImageView) v.findViewById(R.id.imgartikel);
        imggaleri=(ImageView) v.findViewById(R.id.imggaleri);
        imgvideo=(ImageView) v.findViewById(R.id.imgvideo);
        imgprofile=(ImageView) v.findViewById(R.id.imgprofile);
        imginfomasjid=(ImageView) v.findViewById(R.id.imginfomasjid);
        kegiatan();
        khutbah();
        imam();
        ramadhan();
        artikel();
        galeri();
        video();
        profile();
        infomasjid();
        return v;
    }


    public void kegiatan(){
        imgkegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity().getApplicationContext(),KegiatanActivity.class);
                startActivity(i);
            }
        });
    }

    private void khutbah(){
        imgkhutbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity().getApplicationContext(),KhutbahActivity.class);
                startActivity(i);
            }
        });
    }

    private void imam(){
        imgimam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(),ImamActivity.class);
                startActivity(i);
            }
        });
    }


    private void ramadhan(){
        imgramadhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(),RamadhanActivity.class);
                startActivity(i);
            }
        });
    }

    private void artikel(){
        imgartikel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(),ArtikelkategoriActivity.class);
                startActivity(i);
            }
        });
    }

    private void galeri(){
        imggaleri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(),GaleriActivity.class);
                startActivity(i);
            }
        });
    }

    private void video(){
        imgvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(),VideoActivity.class);
                startActivity(i);
            }
        });
    }


    private void profile(){
        imgprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Config.statuslogin==0){
                    Intent i=new Intent(v.getContext(),LoginActivity.class);
                    startActivity(i);
                }else{
                    Intent i=new Intent(v.getContext(),ProfileActivity.class);
                    i.putExtra("iduser",Config.iduser);
                    i.putExtra("namauser",Config.namauser);
                    i.putExtra("emailuser",Config.emailuser);
                    i.putExtra("nohpuser",Config.nohpuser);
                    startActivity(i);
                }

            }
        });

    }

    private void infomasjid(){
        imginfomasjid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb=new AlertDialog.Builder(getActivity());
                adb.setTitle("Informasi Masjid");
                adb.setMessage(Config.infomasjid);
                adb.setNeutralButton("Tutup",null);
                adb.show();
            }
        });
    }





}

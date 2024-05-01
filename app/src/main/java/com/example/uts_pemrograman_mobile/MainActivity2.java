package com.example.uts_pemrograman_mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Inisialisasi TextView untuk menampilkan data
        TextView etNIK = findViewById(R.id.etNIK);
        TextView etNamaLengkap = findViewById(R.id.etNamaLengkap);
        TextView etTanggalLahir = findViewById(R.id.etTanggalLahir);
        TextView etTempatLahir = findViewById(R.id.etTempatLahir);
        TextView etAlamat = findViewById(R.id.etAlamat);
        TextView etUsia = findViewById(R.id.etUsia);
        TextView etJenisKelamin = findViewById(R.id.etJenisKelamin);
        TextView etKewarganegaraan = findViewById(R.id.etKewarganegaraan);
        TextView BidangKompetensi = findViewById(R.id.BidangKompetensi);
        TextView etEmail = findViewById(R.id.etEmail);

        // Ambil data yang dikirim dari MainActivity
        Intent intent = getIntent();
        String nik = intent.getStringExtra("NIK");
        String namaLengkap = intent.getStringExtra("NamaLengkap");
        String tanggalLahir = intent.getStringExtra("TanggalLahir");
        String tempatLahir = intent.getStringExtra("TempatLahir");
        String alamat = intent.getStringExtra("Alamat");
        String usia = intent.getStringExtra("Usia");
        String jenisKelamin = intent.getStringExtra("JenisKelamin");
        String kewarganegaraan = intent.getStringExtra("Kewarganegaraan");
        String bidangKompetensi = intent.getStringExtra("BidangKompetensi");
        String email = intent.getStringExtra("Email");

        // Setel nilai TextView dengan data yang diterima
        etNIK.setText(nik);
        etNamaLengkap.setText(namaLengkap);
        etTanggalLahir.setText(tanggalLahir);
        etTempatLahir.setText(tempatLahir);
        etAlamat.setText(alamat);
        etUsia.setText(usia);
        etJenisKelamin.setText(jenisKelamin);
        etKewarganegaraan.setText(kewarganegaraan);
        BidangKompetensi.setText(bidangKompetensi);
        etEmail.setText(email);

        // Kembali ke MainActivity jika tombol "Kembali" ditekan
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }
}

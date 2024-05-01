package com.example.uts_pemrograman_mobile;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends Activity {

    private EditText etNIK, etNamaLengkap, etTempatLahir, etAlamat, etUsia, etEmail, etTanggalLahir;
    private Spinner etJenisKelamin;
    String[] bankNames = {"Laki-laki", "Perempuan"};
    private RadioGroup etKewarganegaraan;
    private CheckBox cbDevelopmentAndIT, cbAIServices, cbDesignCreative, cbWriting, cbFinanceAndAccounting;
    private Button btnReset, btnSubmit;
    private Calendar myCalendar;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNIK = findViewById(R.id.etNIK);
        etNamaLengkap = findViewById(R.id.etNamaLengkap);
        etTempatLahir = findViewById(R.id.etTempatLahir);
        etAlamat = findViewById(R.id.etAlamat);
        etUsia = findViewById(R.id.etUsia);
        etEmail = findViewById(R.id.etEmail);
        etTanggalLahir = findViewById(R.id.etTanggalLahir);
        etJenisKelamin = findViewById(R.id.etJenisKelamin);
        etKewarganegaraan = findViewById(R.id.etKewarganegaraan);
        cbDevelopmentAndIT = findViewById(R.id.Development_and_IT);
        cbAIServices = findViewById(R.id.AI_Services);
        cbDesignCreative = findViewById(R.id.Design_Creative);
        cbWriting = findViewById(R.id.Writng);
        cbFinanceAndAccounting = findViewById(R.id.Finance_and_Acounting);
        btnReset = findViewById(R.id.btnReset);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Set up Spinner for Gender
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bankNames);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etJenisKelamin.setAdapter(arrayAdapter);

        // Initialize Calendar instance
        myCalendar = Calendar.getInstance();

        // Set up OnClickListener for Date EditText
        etTanggalLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        // Set up OnClickListener for Reset Button
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetForm();
            }
        });

        // Set up OnClickListener for Submit Button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });


        // Initialize DatePickerDialog
        datePickerDialog = new DatePickerDialog(
                this,
                date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
        );

        // Set min date for DatePickerDialog to today's date
        datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
    }

    private void showDatePickerDialog() {
        datePickerDialog.show();
    }

    // DatePickerDialog.OnDateSetListener
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
            calculateAge();
        }
    };

    private void calculateAge() {
        // Get current date
        Calendar today = Calendar.getInstance();

        // Calculate age based on selected date of birth
        int age = today.get(Calendar.YEAR) - myCalendar.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < myCalendar.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        // Set the calculated age to the EditText
        etUsia.setText(String.valueOf(age));
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        etTanggalLahir.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void resetForm() {
        etNIK.setText("");
        etNamaLengkap.setText("");
        etTempatLahir.setText("");
        etAlamat.setText("");
        etUsia.setText("");
        etEmail.setText("");

        // Clear selected date
        etTanggalLahir.setText("");

        etJenisKelamin.setSelection(0);
        etKewarganegaraan.clearCheck();
        cbDevelopmentAndIT.setChecked(false);
        cbAIServices.setChecked(false);
        cbDesignCreative.setChecked(false);
        cbWriting.setChecked(false);
        cbFinanceAndAccounting.setChecked(false);
    }

    private void submitForm() {
        // Retrieve data from input fields
        String nik = etNIK.getText().toString();
        String namaLengkap = etNamaLengkap.getText().toString();
        String tanggalLahirStr = etTanggalLahir.getText().toString();
        String tempatLahir = etTempatLahir.getText().toString();
        String alamat = etAlamat.getText().toString();
        String usiaStr = etUsia.getText().toString(); // Get age directly from EditText
        String email = etEmail.getText().toString();
        String jenisKelamin = etJenisKelamin.getSelectedItem().toString();
        String kewarganegaraan = ((RadioButton)findViewById(etKewarganegaraan.getCheckedRadioButtonId())).getText().toString();

        // Validasi NIK
        if (nik.isEmpty()) {
            Toast.makeText(this, "NIK tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validasi Nama Lengkap
        if (namaLengkap.isEmpty()) {
            Toast.makeText(this, "Nama lengkap tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validasi Tanggal Lahir
        if (tanggalLahirStr.isEmpty()) {
            Toast.makeText(this, "Tanggal lahir tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validasi Tanggal Lahir tidak lebih dari tanggal hari ini
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy", Locale.US);
        try {
            Date tanggalLahir = dateFormat.parse(tanggalLahirStr);
            Date today = new Date();
            if (tanggalLahir.after(today)) {
                Toast.makeText(this, "Tanggal lahir tidak boleh lebih dari hari ini", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        // Validasi Tempat Lahir
        if (tempatLahir.isEmpty()) {
            Toast.makeText(this, "Tempat lahir tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validasi Alamat
        if (alamat.isEmpty()) {
            Toast.makeText(this, "Alamat tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validasi Usia
        if (usiaStr.isEmpty()) {
            Toast.makeText(this, "Usia tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }
        int usia = Integer.parseInt(usiaStr);
        if (usia > 150) {
            Toast.makeText(this, "Usia tidak boleh lebih dari 150 tahun", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validasi Jenis Kelamin
        if (jenisKelamin.isEmpty()) {
            Toast.makeText(this, "Jenis kelamin tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validasi Bidang Kompetensi
        if (!cbDevelopmentAndIT.isChecked() && !cbAIServices.isChecked() && !cbDesignCreative.isChecked() &&
                !cbWriting.isChecked() && !cbFinanceAndAccounting.isChecked()) {
            Toast.makeText(this, "Pilih setidaknya satu bidang kompetensi", Toast.LENGTH_SHORT).show();
            return;
        }

        if (kewarganegaraan.isEmpty()) {
            Toast.makeText(this, "Kewarganegaraan tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validasi Email
        if (email.isEmpty() || !isValidEmail(email)) {
            Toast.makeText(this, "Email tidak valid", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder bidangKompetensiBuilder = new StringBuilder();
        if (cbDevelopmentAndIT.isChecked()) {
            bidangKompetensiBuilder.append("Development & IT, ");
        }
        if (cbAIServices.isChecked()) {
            bidangKompetensiBuilder.append("AI Services, ");
        }
        if (cbDesignCreative.isChecked()) {
            bidangKompetensiBuilder.append("Design Creative, ");
        }
        if (cbWriting.isChecked()) {
            bidangKompetensiBuilder.append("Writing, ");
        }
        if (cbFinanceAndAccounting.isChecked()) {
            bidangKompetensiBuilder.append("Finance & Accounting, ");
        }

        // Remove trailing comma and space
        String bidangKompetensi = bidangKompetensiBuilder.toString().trim();
        if (bidangKompetensi.isEmpty()) {
            Toast.makeText(this, "Pilih setidaknya satu bidang kompetensi", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(email)) {
            Toast.makeText(MainActivity.this, "Format email tidak valid", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate email domain
        if (!isValidEmailDomain(email)) {
            Toast.makeText(MainActivity.this, "Domain email tidak diizinkan", Toast.LENGTH_SHORT).show();
            return;
        }

        // Jika semua validasi berhasil, maka tampilkan data yang telah di-submit
        // dan start activity baru untuk menampilkan data tersebut
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        intent.putExtra("NIK", nik);
        intent.putExtra("NamaLengkap", namaLengkap);
        intent.putExtra("TanggalLahir", tanggalLahirStr);
        intent.putExtra("TempatLahir", tempatLahir);
        intent.putExtra("Alamat", alamat);
        intent.putExtra("Usia", usiaStr);
        intent.putExtra("JenisKelamin", jenisKelamin);
        intent.putExtra("Kewarganegaraan", kewarganegaraan); // Menambahkan kewarganegaraan ke dalam intent
        intent.putExtra("BidangKompetensi", bidangKompetensi); // Menambahkan bidang kompetensi ke dalam intent
        intent.putExtra("Email", email); // Menambahkan email ke dalam intent
        startActivity(intent);

        // Tampilkan data yang telah di-submit dalam bentuk toast
        String submittedData = "NIK: " + nik + "\n" +
                "Nama Lengkap: " + namaLengkap + "\n" +
                "Tempat Lahir: " + tempatLahir + "\n" +
                "Alamat: " + alamat + "\n" +
                "Usia: " + usiaStr + "\n" +
                "Jenis Kelamin: " + jenisKelamin + "\n" +
                "Kewarganegaraan: " + kewarganegaraan + "\n" +
                "Bidang Kompetensi: " + bidangKompetensi + "\n" + // Menampilkan bidang kompetensi
                "Email: " + email;
        Toast.makeText(this, submittedData, Toast.LENGTH_LONG).show();
    }

    // Method to validate email address
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidEmailDomain(String email) {
        String[] allowedDomains = {"@gmail.com", "@mail.com"};
        for (String domain : allowedDomains) {
            if (email.endsWith(domain)) {
                return true;
            }
        }
        return false;
    }
}

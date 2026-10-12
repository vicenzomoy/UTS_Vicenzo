package edu.uph.m24si2.uts_vicenzo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import edu.uph.m24si2.uts_vicenzo.model.Kegiatan

class TambahKegiatanActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tambah_kegiatan)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        db = FirebaseFirestore.getInstance()
        val edtWaktuKegiatan: EditText = findViewById(R.id.edtWaktuKegiatan)
        val edtNamaKegiatan: EditText = findViewById(R.id.edtNamaKegiatan)
        val btnSimpan: Button = findViewById(R.id.btnSimpan)

        btnSimpan.setOnClickListener {
            val WaktuKegiatan = edtWaktuKegiatan.text.toString()
            val NamaKegiatan = edtNamaKegiatan.text.toString()
            if (edtWaktuKegiatan.text.isEmpty() || edtNamaKegiatan.text.isEmpty()) {
                Toast.makeText(this, "Waktu dan Nama Kegiatan Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val kegiatan = Kegiatan(NamaKegiatan, WaktuKegiatan)
            db.collection("kegiatan")
                .add(kegiatan)
                .addOnSuccessListener {
                    Toast.makeText(this, " Berhasil Menyimpan data", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener { error ->
                    Toast.makeText(this, "Gagal Menyimpan data : ${error.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
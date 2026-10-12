package edu.uph.m24si2.uts_vicenzo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import edu.uph.m24si2.uts_vicenzo.adapter.KegiatanAdapter
import edu.uph.m24si2.uts_vicenzo.model.Kegiatan

class MainActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var rcyKegiatan: RecyclerView
    private lateinit var adapter: KegiatanAdapter
    private val kegiatanList = ArrayList<Kegiatan>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        rcyKegiatan = findViewById(R.id.rcyKegiatan)
        val btnTambah =  findViewById<Button>(R.id.btnTambah)

        rcyKegiatan.layoutManager = LinearLayoutManager(this)
        adapter = KegiatanAdapter(kegiatanList) {
                kegiatan -> konfirmasiHapus(kegiatan)
        }
        rcyKegiatan.adapter = adapter

        btnTambah.setOnClickListener {
            startActivity(Intent(this, TambahKegiatanActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }
    private fun loadData() {
        val userId = auth.currentUser?.uid?: return

        db.collection("kegiatan")
            .get()
            .addOnSuccessListener {
                    documents-> kegiatanList.clear()

                for(doc in documents) {
                    val kegiatan = doc.toObject(Kegiatan::class.java)
                    kegiatan.documentId = doc.id
                    kegiatanList.add(kegiatan)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { error ->
                Toast.makeText(this, "Gagal Mengambil data : ${error.message}", Toast.LENGTH_SHORT).show()
            }
    }
    private fun konfirmasiHapus(kegiatan: Kegiatan) {
        AlertDialog.Builder(this)
            .setTitle("Hapus Kegiatan")
            .setMessage("Apakah anda yakin ingin menghapus" + "${kegiatan.NamaKegiatan}")
            .setPositiveButton("Hapus") {
                    _, _ -> hapusKegiatan(kegiatan)
            }
            .setNegativeButton("Batal", null)
            .show()
    }
    private fun hapusKegiatan(kegiatan: Kegiatan) {
        db.collection("kegiatan")
            .document(kegiatan.documentId)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this, " Berhasil Menghapus data", Toast.LENGTH_SHORT).show()
                loadData()
            }
            .addOnFailureListener { error ->
                Toast.makeText(this, "Gagal Mengambil data : ${error.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
package edu.uph.m24si2.uts_vicenzo.model

import com.google.firebase.firestore.Exclude

class Kegiatan (
    var NamaKegiatan: String = "",
    var WaktuKegiatan: String = "",

    @get:Exclude
    var documentId: String = ""
)
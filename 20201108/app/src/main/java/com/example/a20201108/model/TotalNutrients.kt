package com.example.a20201108.model


import com.google.gson.annotations.SerializedName

data class TotalNutrients(
    @SerializedName("CA")
    val cA: CAX,
    @SerializedName("CHOCDF")
    val cHOCDF: CHOCDFX,
    @SerializedName("CHOLE")
    val cHOLE: CHOLEX,
    @SerializedName("ENERC_KCAL")
    val eNERCKCAL: ENERCKCALX,
    @SerializedName("FAMS")
    val fAMS: FAMS,
    @SerializedName("FAPU")
    val fAPU: FAPU,
    @SerializedName("FASAT")
    val fASAT: FASATX,
    @SerializedName("FAT")
    val fAT: FATX,
    @SerializedName("FATRN")
    val fATRN: FATRN,
    @SerializedName("FE")
    val fE: FEX,
    @SerializedName("FIBTG")
    val fIBTG: FIBTGX,
    @SerializedName("FOLAC")
    val fOLAC: FOLAC,
    @SerializedName("FOLDFE")
    val fOLDFE: FOLDFEX,
    @SerializedName("FOLFD")
    val fOLFD: FOLFD,
    @SerializedName("K")
    val k: KX,
    @SerializedName("MG")
    val mG: MGX,
    @SerializedName("NA")
    val nA: NAX,
    @SerializedName("NIA")
    val nIA: NIAX,
    @SerializedName("P")
    val p: PX,
    @SerializedName("PROCNT")
    val pROCNT: PROCNTX,
    @SerializedName("RIBF")
    val rIBF: RIBFX,
    @SerializedName("SUGAR")
    val sUGAR: SUGAR,
    @SerializedName("THIA")
    val tHIA: THIAX,
    @SerializedName("TOCPHA")
    val tOCPHA: TOCPHAX,
    @SerializedName("VITA_RAE")
    val vITARAE: VITARAEX,
    @SerializedName("VITB12")
    val vITB12: VITB12X,
    @SerializedName("VITB6A")
    val vITB6A: VITB6AX,
    @SerializedName("VITC")
    val vITC: VITCX,
    @SerializedName("VITD")
    val vITD: VITDX,
    @SerializedName("VITK1")
    val vITK1: VITK1X,
    @SerializedName("WATER")
    val wATER: WATER,
    @SerializedName("ZN")
    val zN: ZNX
)
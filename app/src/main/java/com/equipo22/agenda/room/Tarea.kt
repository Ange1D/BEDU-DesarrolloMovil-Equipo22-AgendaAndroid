package com.equipo22.agenda.room

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tarea(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "Titulo") val titulo: String = "Tarea genérica",
    @ColumnInfo(name = "Fecha") val fecha: String = "",
    @ColumnInfo(name = "Hora") val hora: String = "",
    @ColumnInfo(name = "Descripcion") val descripcion: String = "",
    @ColumnInfo(name = "Tarea_Previa") val tareaPrevia: String = "Ninguna",
    @ColumnInfo(name = "Repetir") val repetir: String = "Nunca",
    @ColumnInfo(name = "Prioridad") val prioridad: Int = 5,
    @ColumnInfo(name = "Estado") val estado: Boolean = false
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(titulo)
        parcel.writeString(fecha)
        parcel.writeString(hora)
        parcel.writeString(descripcion)
        parcel.writeString(tareaPrevia)
        parcel.writeString(repetir)
        parcel.writeInt(prioridad)
        parcel.writeByte(if (estado) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Tarea> {
        override fun createFromParcel(parcel: Parcel): Tarea {
            return Tarea(parcel)
        }

        override fun newArray(size: Int): Array<Tarea?> {
            return arrayOfNulls(size)
        }
    }
}

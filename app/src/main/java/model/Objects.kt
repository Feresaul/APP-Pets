package model

import com.google.gson.annotations.SerializedName

class ResponseT<T>{
    @SerializedName("tieneError")
    var error: Boolean ?= null
    @SerializedName("mensaje")
    var mensaje: String ?= null
    @SerializedName("modelo")
    var modelo: T ?= null
}

class User{
    @SerializedName("nombreCompleto")
    var nombre: String ?= null
    @SerializedName("usuario")
    var usuario: String ?= null
    @SerializedName("email")
    var correo: String ?= null
    @SerializedName("telefono")
    var telefono: String ?= null
    @SerializedName("contrasenia")
    var contrasenia: String ?= null
}

class ServiceHours{
    @SerializedName("idTipoServicio")
    var id: Int ?= null
    @SerializedName("fechaInicio")
    var date: String ?= null
}

class ServiceHoursOut{
    @SerializedName("inicio")
    var opens: String ?= null
    @SerializedName("termino")
    var closes: String ?= null
    @SerializedName("tiempo")
    var time: Int ?= null
    @SerializedName("arrayHoras")
    var data: ArrayList<String> ?= null
}

class AddServiceItem{
    @SerializedName("idServicio")
    var idService: Int ?= null
    @SerializedName("idUsuario")
    var idUser: Int ?= null
    @SerializedName("fechaInicio")
    var starts: String ?= null
}

class ServiceItem{
    @SerializedName("id")
    var id: Int ?= null
    @SerializedName("tipoServicio")
    var type: String ?= null
    @SerializedName("nombre")
    var service: String ?= null
    @SerializedName("status")
    var status: String ?= null
    @SerializedName("precio")
    var price: Float ?= null
    @SerializedName("tiempo")
    var time: Int ?= null
    @SerializedName("fechaInicio")
    var starts: String ?= null
    @SerializedName("fechaTermino")
    var ends: String ? =null
}
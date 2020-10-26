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
    var opens: String ?= null
    var closes: String ?= null
    var time: Int ?= null
    var data: ArrayList<String> ?= null

    constructor(opens: String, closes: String, time: Int, data: ArrayList<String>){
        this.opens = opens
        this.closes = closes
        this.time = time
        this.data = data
    }
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
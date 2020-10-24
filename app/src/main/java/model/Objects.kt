package model

import com.google.gson.annotations.SerializedName

class ResponseT<T>{
    @SerializedName("tieneError")
    var respuesta: Boolean ?= null
    @SerializedName("mensaje")
    var mensaje: String ?= null
    @SerializedName("modelo")
    var modelo: T ?= null
}

class Usuario{
    var idUsuario: Int ?= null
    var nombreUsuario: String ?= null
    var nombres: String ?= null
    var apellidos: String ?= null
    var contrasenia: String ?= null
    var activo: Boolean ?= null
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

    constructor(id: Int, type: String, service: String, status: String, price: Float, time: Int, starts: String, ends: String){
        this.id = id
        this.type = type
        this.service = service
        this.status = status
        this.price = price
        this.time = time
        this.starts = starts
        this.ends = ends
    }
}
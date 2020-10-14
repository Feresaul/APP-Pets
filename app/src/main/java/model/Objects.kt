package model

import com.google.gson.annotations.SerializedName

class ResponseT<T>{
    @SerializedName("tieneResultado")
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

class ServiceItem{
    var id: Int ?= null
    var type: String ?= null
    var service: String ?= null
    var status: String ?= null
    var price: Float ?= null
    var time: Int ?= null
    var starts: String ?= null
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
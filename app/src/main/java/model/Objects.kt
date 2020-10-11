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
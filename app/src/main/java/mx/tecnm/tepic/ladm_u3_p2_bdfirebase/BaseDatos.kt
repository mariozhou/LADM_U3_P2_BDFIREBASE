package mx.tecnm.tepic.ladm_u3_p2_bdfirebase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDatos (
    context : Context?,
    name:String?,
    factory: SQLiteDatabase.CursorFactory?,
    version:Int,
    //context = activity solo ese activivy tiene permiso de acceso a la BD
    //name = nombre deBD
    //cursos
): SQLiteOpenHelper(context,name,factory, version) {
    override fun onCreate(p0: SQLiteDatabase) {
        //tabla persona (id,nombre varchar,domicilio,telefono)
    //    p0.execSQL("CREATE TABLE CLIENTE(IdCliente INTEGER PRIMARY KEY AUTOINCREMENT,nombreCliente VARCHAR(200) ,fecha VARCHAR(200), celular VARCHAR(20))")

        // p0.execSQL()// create table inset delete update alter table
        //  p0.rawQuery() // select

        /*codigo parametros tipo objeto
        p0.insert()
        p0.delete()
        p0.query()
        p0
         */
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p3: Int) {
        //se invoca solo cuando cambiar el num de ver en el constructor
    }

}
package mx.tecnm.tepic.ladm_u3_p2_bdfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import com.google.type.DateTime
import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

class MainActivity : AppCompatActivity() {
    var baseRemota = FirebaseFirestore.getInstance()
    var datalista = ArrayList<String>()
    var listaId = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnpedido = findViewById<Button>(R.id.hacerpedido)

        var timer = object : CountDownTimer(2000,380){
            override fun onTick(p0: Long) {
                cargalista()
            }
            override fun onFinish() {
                start()
            }
        }.start()

        btnpedido.setOnClickListener {
            var intent = Intent(this, MainActivity2::class.java)
            startActivity((intent))
        }
    }


    private fun cargalista() {
        val lista = findViewById<ListView>(R.id.listapedido)

        baseRemota.collection("pedidos")
            .addSnapshotListener { querySnapshot, error ->
                if (error != null){
                    mensaje(error.message!!)
                    return@addSnapshotListener
                }
                datalista.clear()

                for (document in querySnapshot!!){
                    var cadena = "Id:${document.getId()}\n" +
                            "Nombre:${document.getString("nombre")}\n" +
                            "Celular:${document.getString("celular")}\n"+
                            "Entregado:${document.get("entrega")}\n"

                    datalista.add(cadena)
                    listaId.add(document.id.toString())
                }
            }
        lista.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datalista)

        lista.setOnItemClickListener{
                adapterView, view, posicion,l ->
            dialogoEliminaActualiza(posicion)

        }
    }

    fun mensaje(m: String){
        AlertDialog.Builder(this)
            .setTitle("ATENCION")
            .setMessage(m)
            .setPositiveButton("OK"){ d, i->}
            .show()
    }

    private fun alerta(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()
    }

    private fun dialogoEliminaActualiza(posicion: Int) {
        var id = listaId.get(posicion)

        AlertDialog.Builder(this)
            .setTitle("Modificar estatus de entrega")
            .setMessage("Statu ${datalista.get(posicion)} ")
            .setNeutralButton("No entregado"){d,i->
                status(id,2)   }
                .setPositiveButton("Entregado"){d,i->
                    status(id,1)
                }
                .setNegativeButton("Cancelar"){d,i->
                    d.dismiss()
            }
            .show()
    }

    private fun status(id: String, i: Int) {
        if (i==1){
            baseRemota.collection("pedidos")
                    .document(id)
                    .update("entrega",true)
                    .addOnSuccessListener { alerta("Exito se actualizo")
                    }
                    .addOnFailureListener { mensaje("Error no se pudo actualizar") }

        }else
        {
            baseRemota.collection("pedidos")
                    .document(id)
                    .update("entrega",false)
                    .addOnSuccessListener { alerta("Exito se actualizo")
                    }
                    .addOnFailureListener { mensaje("Error no se pudo actualizar") }

        }

    }


}
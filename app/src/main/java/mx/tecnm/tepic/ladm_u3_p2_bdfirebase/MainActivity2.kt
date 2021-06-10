package mx.tecnm.tepic.ladm_u3_p2_bdfirebase

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.protobuf.Internal
import java.util.*
import kotlin.collections.HashMap

class MainActivity2 : AppCompatActivity() {
    var baseRemota = FirebaseFirestore.getInstance()
    var item = 0
    var datalista = ArrayList<String>()
    var datospedidos = hashMapOf<String, Any>()
    var tprecio = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val btncliente = findViewById<Button>(R.id.agregarcliente)
        val btnpedido = findViewById<Button>(R.id.agrepedido)
        val btnre = findViewById<Button>(R.id.regresa)

        btncliente.setOnClickListener {
            crearpedido()
            cargalista()
        }

        btnpedido.setOnClickListener {
            ingresar()

        }

        btnre.setOnClickListener {
            finish()
        }

    }

    private fun crearpedido() {
        val cantidad = findViewById<EditText>(R.id.cantidad)
        val sumcant = cantidad.text.toString().toInt()
        val descp = findViewById<EditText>(R.id.descp)
        val precio = findViewById<EditText>(R.id.precio)
        val precioindi = precio.text.toString().toFloat()//precio invidual de productos
        val sumaprecios = precio.text.toString().toFloat()
        val produprecio = (sumcant*precioindi).toFloat()
        val producto = descp.text.toString()
        var cadena = ""
        item=item+1

        var datosproductos =hashMapOf(
                "cantidad" to cantidad.text.toString().toInt(),
                "descripcion" to descp.text.toString(),
                "precio" to produprecio
        )
        tprecio=tprecio+produprecio
        datospedidos.put("item${item}", datosproductos)
        limpiarpedido()
        cadena = "Producto: ${producto}, Cantidad: ${sumcant}," +
                " Precio ${produprecio}"
        datalista.add(cadena)
        cargalista()
    }

    private fun ingresar() {
        val date = Calendar.getInstance()
        val nombre = findViewById<EditText>(R.id.nombre)
        val celuar = findViewById<EditText>(R.id.celular)

        var datosInsertar = hashMapOf(
                "nombre" to nombre.text.toString(),
                "celuar" to celuar.text.toString(),
                "fecha" to date.time,
                "total" to tprecio,
                "entrega" to false,
                "pedido" to datospedidos
        )

        baseRemota.collection("pedidos")
            .add(datosInsertar as Any)
            .addOnSuccessListener {
                alerta("SE INSERTO CORRECTAMENTE EN LA NUBE")
            }
            .addOnFailureListener {
                mensaje("ERROR: ${it.message!!}")
            }
        limpiarCampos()
        datospedidos.clear()
        datalista.clear()
        item=0
        cargalista()
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


    private fun limpiarCampos() {
        val celular = findViewById<EditText>(R.id.celular)
        val nombre = findViewById<EditText>(R.id.nombre)

        celular.setText("")
        nombre.setText("")

    }

    private fun limpiarpedido() {
        val producto = findViewById<EditText>(R.id.cantidad)
        val precio = findViewById<EditText>(R.id.precio)
        val desp = findViewById<EditText>(R.id.descp)

        desp.setText("")
        precio.setText("")
        producto.setText("")
    }

    private fun cargalista() {
        val lista = findViewById<ListView>(R.id.listapedidopen)
        lista.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datalista)
        }
    }

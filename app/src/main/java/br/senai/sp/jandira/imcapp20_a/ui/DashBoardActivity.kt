package br.senai.sp.jandira.imcapp20_a.ui

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.senai.sp.jandira.imcapp20_a.R
import br.senai.sp.jandira.imcapp20_a.utils.calcularImc
import br.senai.sp.jandira.imcapp20_a.utils.converterBase64ParaBitmap
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_dash_board.*
import org.jetbrains.anko.toast

class DashBoardActivity : AppCompatActivity() {

    var id = 0
    var nome = ""
    var profissao = ""
    var peso = 0
    var altura = 0.0
    var idade = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        supportActionBar!!.title = "Perfil"
        supportActionBar!!.subtitle = "Bem-vindo"
        supportActionBar!!.setBackgroundDrawable(getDrawable(R.drawable.toolbar_background))
        supportActionBar!!.elevation = 0.0f

        preencherDashBoard()

        card_weight.setOnClickListener {
            atualizarBio()
        }

    }

    private fun atualizarBio() {
        val intent = Intent(this, CadastroBiometriaActivity::class.java)
        intent.putExtra("id_usuario", id)
        startActivity(intent)
    }

    private fun criarAlertDialog() {
        val dialog = AlertDialog.Builder(this)

        dialog.setMessage("\nVocê ainda não concluiu o seu cadastro. Vamos fazer isso agora?\n")
            .setCancelable(false)
            .setPositiveButton("Sim", DialogInterface.OnClickListener { dialog, which ->
                atualizarBio()
            })
            .setNegativeButton("Não", DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })

        val alert = dialog.create()

        alert.setTitle("\nPrecisamos de mais informações\n\n")

        alert.show()
    }

    private fun preencherDashBoard() {
        val dados = getSharedPreferences("dados_usuario", Context.MODE_PRIVATE)

        nome = dados.getString("nome", "")!!
        profissao = dados.getString("profissao", "")!!
        peso = dados.getInt("peso", 0)
        idade = dados.getString("idade", "")!!
        altura = dados.getFloat("altura", 0.0f).toDouble()

        tv_profile_name.text = nome
        tv_profile_occupation.text = profissao
        tv_weight.text = peso.toString()
        tv_age.text = idade

        val imagemBase64 = dados.getString("foto", "")
        val imagemBitmap = converterBase64ParaBitmap(imagemBase64)

        id = dados.getInt("id_usuario", 0)

        iv_profile.setImageBitmap(imagemBitmap)

        if (dados.getInt("peso", 0) == 0) {
            criarAlertDialog()
        }

        tv_imc.text = String.format("%.1f", calcularImc(peso.toDouble(), altura))

        // *** Colocar foto do Github no ImageView
//        val url = "https://avatars.githubusercontent.com/u/14265058?v=4"
//        Glide.with(this).load(url).into(iv_profile)

    }

    override fun onResume() {
        super.onResume()
        preencherDashBoard()
    }
}
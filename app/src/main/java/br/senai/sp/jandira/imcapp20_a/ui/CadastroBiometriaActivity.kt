package br.senai.sp.jandira.imcapp20_a.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Toast
import br.senai.sp.jandira.imcapp20_a.R
import br.senai.sp.jandira.imcapp20_a.dao.BiometriaDao
import br.senai.sp.jandira.imcapp20_a.model.Biometria
import kotlinx.android.synthetic.main.activity_cadastro_biometria.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CadastroBiometriaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_biometria)

        supportActionBar!!.title = "Biometria"
        supportActionBar!!.subtitle = "Cadastre seus dados"
        supportActionBar!!.setBackgroundDrawable(getDrawable(R.drawable.toolbar_background))
        supportActionBar!!.elevation = 0.0f

        val id = intent.getIntExtra("id_usuario", 0)

        tv_nivel_atividade.text = "0 - Nenhuma atividade"

        Toast.makeText(this, "$id", Toast.LENGTH_SHORT).show()

        val nivelAtividades = resources.getStringArray(R.array.atividades)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_nivel_atividade, nivelAtividades)
        autocomplete_nivel_atividade.setAdapter(arrayAdapter)

        val hoje = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

        edit_text_data_pesagem.setText(hoje)

        seek_atividades.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (progress == 0) {
                    tv_nivel_atividade.text = "$progress - Nenhuma atividade"
                } else if (progress == 1) {
                    tv_nivel_atividade.text = "$progress - Atividade leve"
                } else if (progress == 2) {
                    tv_nivel_atividade.text = "$progress - Atividade moderada"
                } else if (progress == 3){
                    tv_nivel_atividade.text = "$progress - Atividade Intensa"
                }

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_novo_usuario, menu)
        return true

    }

    private fun salvarBiometria(id: Int) {

        val biometria = Biometria(
            peso = edit_text_peso.text.toString().toDouble(),
            nivelAtiviade = 1,
            dataPesagem = edit_text_data_pesagem.text.toString(),
            usuario = id
        )

        val biometriaDao = BiometriaDao(this, biometria)
        biometriaDao.gravar()

        Toast.makeText(this, "Dados gravados com sucesso!!", Toast.LENGTH_SHORT).show()

        finish()

    }
}
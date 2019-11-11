package jp.ac.asojuku.mytoratora

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import androidx.core.content.edit
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        pref.edit{this.clear()}
    }

    override fun onResume() {
        super.onResume()
        tora.setOnClickListener{onToratoraButtonTapped(it)}
        oba.setOnClickListener{onToratoraButtonTapped(it)}
        kato.setOnClickListener{onToratoraButtonTapped(it)}
    }

    fun onToratoraButtonTapped(view: View?) {
        val intent = Intent(this,Result::class.java)
        intent.putExtra("MY_IMAGE",view?.id)
        startActivity(intent)
    }
}

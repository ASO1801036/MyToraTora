package jp.ac.asojuku.mytoratora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_result.*

class Result : AppCompatActivity() {
    val tora = 0
    val oba = 1
    val kato = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
    }

    override fun onResume() {
        super.onResume()
        val id = intent.getIntExtra("MY_IMAGE",0)

        val myImage: Int
        myImage = when(id) {
            R.id.tora -> {
                my.setImageResource(R.drawable.tora)
                tora
            }
            R.id.oba -> {
                my.setImageResource(R.drawable.oba)
                oba
            }
            R.id.kato -> {
                my.setImageResource(R.drawable.kato)
                kato
            }
            else -> tora
        }

        //コンピューターの手を決める
        val comImage = getHand()
        when(comImage){
            tora -> com.setImageResource(R.drawable.tora)
            oba -> com.setImageResource(R.drawable.oba)
            kato -> com.setImageResource(R.drawable.kato)
        }

        //勝敗判定
        val gameResult = (comImage - myImage + 3) % 3
        when(gameResult){
            0 -> resultLabel.setText(R.string.result_draw)
            1 -> resultLabel.setText(R.string.result_win)
            2 -> resultLabel.setText(R.string.result_lose)
        }

        backButton.setOnClickListener{finish()}

    }
    private fun saveData(myImage: Int, comImage: Int, gameResult: Int){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val gameCount = pref.getInt("GAME_COUNT", 0)
        val winningStreakCount = pref.getInt("WINNING_STREAK_COUNT", 0)
        val lastComImage = pref.getInt("LAST_COM_IMAGE", 0)
        val lastGameResult = pref.getInt("GAME_RESULT", -1)

        val edtWinningStreakCount: Int =
            when {
                lastGameResult == 2 && gameResult == 2 ->
                    winningStreakCount + 1
                else ->
                    0
            }
        val editor = pref.edit()
        editor.putInt("GAME_COUNT", gameCount + 1)
            .putInt("WINNING_STREAK_COUNT", edtWinningStreakCount)
            .putInt("LAST_MY_IMAGE", myImage)
            .putInt("LAST_COM_IMAGE", comImage)
            .putInt("BEFORE_LAST_COM_IMAGE", lastComImage)
            .putInt("GAME_RESULT", gameResult)
            .apply()
    }
    private fun getHand(): Int{
        var image = (Math.random() * 3).toInt()
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val gameCount = pref.getInt("GAME_COUNT", 0)
        val winningStreakCount = pref.getInt("WINNING_STREAK_COUNT", 0)
        val lastMyImage = pref.getInt("LAST_MY_IMAGE", 0)
        val lastComImage = pref.getInt("LAST_COM_IMAGE", 0)
        val beforeLastComImage = pref.getInt("BEFORE_LAST_COM_IMAGE", 0)
        val gameResult = pref.getInt("GAME_RESULT", -1)

        if(gameCount == 1){
            if(gameResult == 2){
                while (lastComImage == image){
                    image = (Math.random() * 3).toInt()
                }
            }else if(gameResult == 1){
                image = (lastMyImage - 1 + 3) % 3
            }
        }else if(winningStreakCount > 0){
            if(beforeLastComImage == lastComImage){
                while(lastComImage == image){
                    image = (Math.random() * 3).toInt()
                }
            }
        }
        return image
    }
}
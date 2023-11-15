package com.project.north_south

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView

class ScannerFragment : Fragment(), ZBarScannerView.ResultHandler {

    private lateinit var scanner: ZBarScannerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_scanner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scanner = view.findViewById(R.id.zbarScannerView)
        scanner.setResultHandler(this)

        val btn1 = view.findViewById<Button>(R.id.button2)
        val btn2 = view.findViewById<Button>(R.id.button3)
        btn1.setOnClickListener{
            val imageSuccess: View = view.findViewById(R.id.imageSuccess)
            animatingView(imageSuccess)
        }

        btn2.setOnClickListener{
            val imageCancel: View = view.findViewById(R.id.imageCancell)
            animatingView(imageCancel)
        }

    }

    override fun onResume() {
        super.onResume()
        scanner.startCamera()
    }

    override fun onPause() {
        super.onPause()
        scanner.stopCamera()
    }

    companion object {
        fun newInstance() = ScannerFragment()

    }

    override fun handleResult(result: Result?) {
        TODO("В result?.contents Придет то, что лежит в QR коде. Осталось сверить это " +
                "со списком данных, который придет в этот фрейм с Intent, после удачной проверки" +
                "запустить анимацию одобрения, после нудачной - отклонения. " +
                "В конце вызвать scanner.resumeCameraPreview(this), чтобы запустить скан следующего кода")

        Log.d("MyLog", "cool")

        scanner.resumeCameraPreview(this)
    }

    private fun animatingView(view: View){
        // Объединение анимаций в AnimatorSet
        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(ObjectAnimator.ofFloat(view, "alpha", 0f, 1f).setDuration(1000),
            ObjectAnimator.ofFloat(view, "alpha", 1f, 0f).setDuration(1000))
        animatorSet.start()
    }
}
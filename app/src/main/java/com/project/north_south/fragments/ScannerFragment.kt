package com.project.north_south.fragments

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.google.android.material.snackbar.Snackbar
import com.project.north_south.R
import com.project.north_south.ViewModels.ScannerFragmentViewModel
import com.project.north_south.ViewModels.TicketFragmentViewModel
import com.project.north_south.databinding.FragmentScannerBinding
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView

class ScannerFragment : Fragment(), ZBarScannerView.ResultHandler {

    private lateinit var scanner: ZBarScannerView
    private lateinit var binding: FragmentScannerBinding
    private lateinit var scannerFragmentViewModel: ScannerFragmentViewModel
    private lateinit var ticketFragmentViewModel: TicketFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScannerBinding.inflate(layoutInflater)
        scannerFragmentViewModel = ViewModelProvider(this)[ScannerFragmentViewModel::class.java]
        ticketFragmentViewModel = ViewModelProvider(this)[TicketFragmentViewModel::class.java]
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scanner = view.findViewById(R.id.zbarScannerView)
        scanner.setResultHandler(this)

//        val btn1 = view.findViewById<Button>(R.id.button2)
//        val btn2 = view.findViewById<Button>(R.id.button3)
        binding.supportButton.setOnClickListener{
            scannerFragmentViewModel.openSupport(binding, childFragmentManager)
        }

        scannerFragmentViewModel.ticketDate.observe(requireActivity()){
            scannerFragmentViewModel.savePassengerInfo(it)
        }
        ticketFragmentViewModel.ticketDate.observe(requireActivity()){
            scannerFragmentViewModel.savePassengerInfo(it)
        }


//        btn1.setOnClickListener{
//            doAnimate(R.id.success_anim)
//        }

//        btn2.setOnClickListener{
//            doAnimate(R.id.cancel_anim)
//            val snackbar = Snackbar.make(view, "Это билет на рейс ${null} в ${null}", 7000)
//            val textView = snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
//            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
//            snackbar.show()
//        }

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
        scannerFragmentViewModel.checkQR(result?.contents, requireView())
        scanner.resumeCameraPreview(this)
    }
}
package deu.cse.tos.activity.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import deu.cse.tos.R
import deu.cse.tos.activity.BrushActivity
import deu.cse.tos.activity.CameraActivity
import deu.cse.tos.data.LoginData
import kotlinx.android.synthetic.main.fragment_mode.*
import kotlinx.android.synthetic.main.fragment_mode.view.*


/**
 * A simple [Fragment] subclass.
 * Use the [ModeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ModeFragment : Fragment() {
    private lateinit var intent: Intent
    private lateinit var loginData: LoginData
    private lateinit var bundle: Bundle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_mode, container, false)

        bundle = requireArguments()
        loginData = bundle.getSerializable("LOGIN") as LoginData

        rootView.btn_bluetooth.setOnClickListener{
            intent = Intent(activity, BrushActivity::class.java)
            intent.putExtra("LOGIN", loginData)
            startActivity(intent)
        }

        rootView.btn_camera.setOnClickListener{
            intent = Intent(activity, CameraActivity::class.java)
            startActivity(intent)
        }

        return rootView
    }

    companion object {

    }
}
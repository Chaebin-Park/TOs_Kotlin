package deu.cse.tos.activity.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import deu.cse.tos.R
import deu.cse.tos.activity.MainActivity
import deu.cse.tos.data.LoginData
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment() {

    private lateinit var loginData: LoginData
    private lateinit var bundle: Bundle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_main, container, false)
        bundle = requireArguments()
        loginData = bundle.getSerializable("LOGIN") as LoginData

        rootView.main_user_name.text = "${loginData.name} 님"

        rootView.img_btn_brush.setOnClickListener{
            (activity as MainActivity).replaceFragment(ModeFragment())
        }

        return rootView
    }

    companion object {

    }
}
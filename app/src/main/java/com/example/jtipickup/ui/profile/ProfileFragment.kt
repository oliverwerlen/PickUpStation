package com.example.jtipickup.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.jtipickup.R
import com.example.jtipickup.ui.home.HomeFragment
import com.example.jtipickup.ui.login.SessionManager

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val USERNAME = "test"
private const val EMAIL = "test"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {


    private lateinit var v: View
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sessionManager = SessionManager(requireActivity().applicationContext)
        // Inflate the layout for this fragment
        this.v = inflater.inflate(R.layout.fragment_profile, container, false)
        v.findViewById<TextView>(R.id.textView2).text = "Please welcome " + arguments?.getString(USERNAME)
        v.findViewById<Button>(R.id.buttonLogout).setOnClickListener { this.logout() }
        return v
    }

    fun logout(){
        sessionManager.deleteAuthToken()
        Toast.makeText(context, "Logout", Toast.LENGTH_SHORT).show()
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, HomeFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(USERNAME, param1)
                    putString(EMAIL, param2)
                }
            }
    }
}
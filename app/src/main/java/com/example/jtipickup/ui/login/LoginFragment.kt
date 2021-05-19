package com.example.jtipickup.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.jtipickup.R
import com.example.jtipickup.requests.LoginRequest
import com.example.jtipickup.response.LoginResponse
import com.example.jtipickup.retrofit.ApiClient
import com.example.jtipickup.ui.products.ProductsFragment
import com.example.jtipickup.ui.profile.ProfileFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment(){
    // TODO: Rename and change types of parameters

    private val TAG = "Login"

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private lateinit var v: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiClient = ApiClient()
        sessionManager = SessionManager(requireActivity().applicationContext)
        Log.v(TAG, sessionManager.fetchAuthToken().toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if(sessionManager.fetchAuthToken() != null){
            findUserPageFragement()
        }
        this.v = inflater.inflate(R.layout.fragment_login, container, false)
        var loginButton: Button = v.findViewById<Button>(R.id.login)
        loginButton.setOnClickListener {
            this.login()
        }
        return this.v
    }

    fun login() {
        var editTextUsername = v.findViewById(R.id.username) as EditText
        var editTextPassword = v.findViewById(R.id.password) as EditText

        apiClient.getApiService(requireContext()).login(
            LoginRequest(
                username = editTextUsername.text.toString(),
                password = editTextPassword.text.toString()
            )
        )
            .enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.v(TAG, t.toString())
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val loginResponse = response.body()
                    Log.v(TAG, response.code().toString())
                    if (response.code() == 200 && loginResponse != null) {
                        sessionManager.saveAuthToken(loginResponse.jwt)
                        Log.v(TAG, response.code().toString())
                        goToUserPage(loginResponse)
                    } else {
                        Toast.makeText(context, "Wrong credentials", Toast.LENGTH_LONG).show()
                    }
                    Log.v(TAG, sessionManager.fetchAuthToken().toString())
                }
            })
    }

    fun goToUserPage(loginResponse: LoginResponse){
        Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, ProfileFragment.newInstance(loginResponse.username, loginResponse.username), "profile")
        fragmentTransaction.addToBackStack("profile")
        fragmentTransaction.commit()
    }

    fun findUserPageFragement(){
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragment: Fragment? = fragmentManager.findFragmentByTag("profile")
        if(fragment != null){
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, fragment)
            fragmentTransaction.commit()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}
package com.example.userdetail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import com.example.userdetail.R
import com.example.userdetail.databinding.FragmentUserDetailesBinding
import com.example.userdetail.viewmodel.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsFragment : Fragment() {

    private lateinit var binding: FragmentUserDetailesBinding
    private var userId = 0
    private val args: UserDetailsFragmentArgs by navArgs()
    private val viewModel: UsersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentUserDetailesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userId = args.userId
        if (userId > 0) {
            viewModel.loadDetailsUser(userId)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            viewModel.userDetails.observe(viewLifecycleOwner) { response ->
                val userPhotograph = response.image
                imgUser.load(userPhotograph) {
                    crossfade(true)
                    placeholder(R.drawable.poster_placeholder)
                    scale(Scale.FILL)
                }
                imgUserBg.load(userPhotograph) {
                    crossfade(true)
                    placeholder(R.drawable.poster_placeholder)
                    scale(Scale.FILL)
                }

                val name = response.firstName+" "+ if(response.maidenName.isNotBlank())response.maidenName+" " else{""}+ response.lastName
                val age = view.resources.getString(R.string.age)+" : "+response.age
                val bloodGroup =  view.resources.getString(R.string.blood_group)+" : "+response.bloodGroup
                val address = view.resources.getString(R.string.address)+" : "+response.address.address +", "+response.address.city+", "+response.address.state+", "+response.address.postalCode
                val birthDate =  view.resources.getString(R.string.birth_date)+" : "+response.birthDate
                val gender =  view.resources.getString(R.string.gender)+" : "+response.gender
                val height =  view.resources.getString(R.string.height)+" : "+response.height
                val weight =  view.resources.getString(R.string.weight)+" : "+response.weight
                val company =  view.resources.getString(R.string.company)+" : "+response.company.name
                val department =  view.resources.getString(R.string.department)+" : "+response.company.department
                val uni =  view.resources.getString(R.string.university)+" : "+response.university
                val phone =  view.resources.getString(R.string.phone)+" : "+response.phone
                val email =  view.resources.getString(R.string.email)+" : "+response.email

                tvUserName.text = name
                tvUserAge.text = age
                tvUserAddress.text = address
                tvBloodGroup.text = bloodGroup
                tvHeight.text = height
                tvWeight.text = weight
                tvGender.text = gender
                tvUserBirthDate.text = birthDate
                tvCompany.text = company
                tvDept.text = department
                tvUniversity.text = uni
                tvPhone.text = phone
                tvEmail.text = email
            }

            viewModel.loading.observe(viewLifecycleOwner) {
                if (it) {
                    prgBarUsers.visibility = View.VISIBLE
                } else {
                    prgBarUsers.visibility = View.INVISIBLE
                }
            }
        }
    }

}
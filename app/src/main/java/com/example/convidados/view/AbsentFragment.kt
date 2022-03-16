package com.example.convidados.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.convidados.R
import com.example.convidados.databinding.FragmentAbsentBinding
import com.example.convidados.service.constants.GuestConstants
import com.example.convidados.view.adapter.GuestAdapter
import com.example.convidados.view.listener.GuestListener
import com.example.convidados.viewmodel.GuestsViewModel

class AbsentFragment : Fragment() {

    private lateinit var mViewModel: GuestsViewModel
    private lateinit var mListener: GuestListener
    private val mAdapter: GuestAdapter = GuestAdapter()
    private var _binding: FragmentAbsentBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mViewModel =
            ViewModelProvider(this)[GuestsViewModel::class.java]

        _binding = FragmentAbsentBinding.inflate(inflater, container, false)


        val recycler = binding.root.findViewById<RecyclerView>(R.id.recycler_absents)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = mAdapter

        mListener = object : GuestListener {
            override fun onClick(id: Int) {
                val intent = Intent(context, GuestFormActivity::class.java)

                val bundle = Bundle()
                bundle.putInt(GuestConstants.GUESTID, id)

                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                mViewModel.delete(id)
                mViewModel.load(GuestConstants.FILTER.ABSENT)
            }
        }
        mAdapter.attachListener(mListener)

        observe()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        mAdapter.attachListener(mListener)
        mViewModel.load(GuestConstants.FILTER.ABSENT)
    }

    private fun observe() {
        mViewModel.guestList.observe(viewLifecycleOwner, {
            mAdapter.updateGuests(it)
        })
    }}
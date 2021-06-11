package com.coding.entertainmentlist.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.coding.entertainmentlist.R
import com.coding.entertainmentlist.databinding.FragmentListBinding
import com.coding.entertainmentlist.pojo.ErrorCode
import com.coding.entertainmentlist.pojo.Status
import com.coding.entertainmentlist.viewmodel.ListViewModel
import com.coding.entertainmentlist.viewmodel.ViewModelFactory

private const val TAG = "ListFragment"
class ListFragment : Fragment() {
    private var _binding:FragmentListBinding?=null
    private val binding get() = _binding!!
    lateinit var listViewModel:ListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listViewModel=activity?.run {
            ViewModelProvider(viewModelStore,ViewModelFactory(this))[ListViewModel::class.java]
        }!!

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding= FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listRecycler.apply {
            layoutManager=GridLayoutManager(activity,2)
            adapter=ListAdapterForView{
                findNavController().navigate(ListFragmentDirections.actionListFragmentToDetailFragment(it))
            }
        }
        listViewModel.getList.observe(viewLifecycleOwner, Observer {
            (binding.listRecycler.adapter as ListAdapterForView).submitList(it)
            if(it.isEmpty()){
                listViewModel.fetchFromNetwork()
            }
        })
        listViewModel.status.observe(viewLifecycleOwner, Observer { loadingStatus ->
            when (loadingStatus?.status) {
                (Status.LOADING) -> {
                    binding.progressBar.visibility = View.VISIBLE
                    Log.d(TAG, "onViewCreated: Status loading")
//                        binding.loadingStatusText.visibility=View.VISIBLE
//                        binding.loadingStatusText.text=loadingStatus.message
                }
                (Status.SUCCESS) -> {
//                    binding.loadingStatusText.visibility=View.GONE
                    binding.progressBar.visibility = View.GONE
                }
                (Status.ERROR) -> {
                    binding.loadingStatusText.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    showError(loadingStatus.error, loadingStatus.message)
                }
                else -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
            binding.swipeUp.isRefreshing=false
        })
        binding.swipeUp.setOnRefreshListener {
            listViewModel.deleteData()
        }


    }

    private fun showError(errorCode: ErrorCode?, message: String?) {
        Log.d(TAG, "showError: ")
        when (errorCode) {
            ErrorCode.NO_DATA -> binding.loadingStatusText.text = getString(R.string.error_no_data)
            ErrorCode.NETWORK_ERROR -> binding.loadingStatusText.text =
                getString(R.string.error_network)
            ErrorCode.UNKNOWN_ERROR -> binding.loadingStatusText.text =
                getString(R.string.error_unknown, message)
        }
    }


}
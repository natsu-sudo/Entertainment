package com.coding.entertainmentlist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.coding.entertainmentlist.R
import com.coding.entertainmentlist.databinding.FragmentDetailBinding
import com.coding.entertainmentlist.databinding.FragmentListBinding
import com.coding.entertainmentlist.network.TmdbService
import com.coding.entertainmentlist.viewmodel.DetailViewModel
import com.coding.entertainmentlist.viewmodel.ViewModelFactory


class DetailFragment : Fragment() {

    private var _binding:FragmentDetailBinding?=null
    private val binding get() = _binding!!
    lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailViewModel=activity?.run {
            ViewModelProvider(viewModelStore,ViewModelFactory(this))[DetailViewModel::class.java]
        }!!

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding=FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ids = DetailFragmentArgs.fromBundle(requireArguments()).id
        detailViewModel.setMovieId(ids)
        detailViewModel.movieDetail.observe(viewLifecycleOwner, Observer {
            binding.ratingBi.text=it.rating.toString()
            binding.movieName.text=it.title
            binding.movieOverview.text=it.overView
            activity.let {frag->
                Glide.with(view).load(TmdbService.BACKDROP_BASE_URL+it.backdropPath)
                        .error(R.drawable.ic_launcher_background)
                        .into(binding.backGround)
            }
            activity.let {frag->
                Glide.with(view).load(TmdbService.POSTER_BASE_URL+it.posterPath)
                        .error(R.drawable.ic_launcher_background)
                        .into(binding.poster)
            }

        })



        }


    }



package com.example.kurditing.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.kurditing.R
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search_category.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchCategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchCategoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_category, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val parFragment = parentFragment
        var etSearch = parFragment?.et_search

//        parFragment?.et_search?.setText("Hello")

//        val searchResultFragment = SearchResultFragment()
//        var bundle = Bundle()

        btn_dm.setOnClickListener {
//            bundle.putString("SEARCH_TERM", btn_dm.text.toString())
//            searchResultFragment.arguments = bundle
            etSearch?.setText(btn_dm.text.toString())
        }
        btn_cp.setOnClickListener {
            etSearch?.setText(btn_cp.text.toString())
//            bundle.putString("SEARCH_TERM", btn_cp.text.toString())
        }
        btn_mb.setOnClickListener {
            etSearch?.setText(btn_mb.text.toString())
//            bundle.putString("SEARCH_TERM", btn_mb.text.toString())
        }

//        searchResultFragment.arguments = bundle

        btn_lainnya.setOnClickListener {

        }
    }

//    fun setSearchText(text: String) {
//
//    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchCategoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                SearchCategoryFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
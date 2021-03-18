package com.example.kurditing.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kurditing.R
import com.example.kurditing.model.Course
import com.google.firebase.database.*

import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search_category.*
import kotlinx.android.synthetic.main.fragment_search_result.*

private const val EXTRA_STATUS = "EXTRA_STATUS"

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var database: DatabaseReference
    var dataList = ArrayList<Course>()
    private lateinit var resultList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState != null) {
            et_search.setText(savedInstanceState.getString(EXTRA_STATUS) ?: "Kosong")
//            Log.i("debug", status.text.toString())
        }

        database = FirebaseDatabase.getInstance().getReference("Course")
        getData()

        // pembentukan objek fragment dari SearchCategoryFragment
        val fragmentSearchCategory = SearchCategoryFragment()
        // pembentukan objek fragment dari SearchResultFragment
        val fragmentSearchResult = SearchResultFragment()

        // tampilkan fragmentSearchCategory pada awal tampilan SearchFragment
        setFragment(fragmentSearchCategory)

        // pembuatan objek bundle untuk pengiriman data ke fragmentSearchResult
        var bundle = Bundle()

        // menambahkan textchangedlistener pada edit text et_search yang bertujuan melacak perubahan
        // ketika mengetik pada search box
        et_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                // ambil string pada et_search dengan membuang spasi tambahan pada awal dan akhir string
                var ss = s!!.trim()
                // jika string empty maka sedang dalam keadaan tidak melakukan search maka tampilkan fragmentSearchCategory
                if (ss.isEmpty()) {
                    setFragment(fragmentSearchCategory)
                } else { // dalam keadaan search
                    // masukkan string search pada bundle dengan key "SEARCH_TERM"
                    bundle.putString("SEARCH_TERM", "Search result for $ss")
                    // berikan nilai arguments pada fragmentSearchResult dengan objek bundle
                    fragmentSearchResult.arguments = bundle

                    // kemudian tampilkan fragmentSearchResult
                    setFragment(fragmentSearchResult)

                    // query hanya untuk penamaan, sebenarnya sama saja nilainya dengan ss
                    val query = ss
                    // filteredList untuk menampung kursus yang tlah difilter berdasarkan string search
                    var filteredList = ArrayList<Course>()
                    // lakukan pengecekan
                    for (course in dataList) {
                        if (course.judul!!.contains(query, ignoreCase = true)) {
                            filteredList.add(course)
                        }
                    }

                    // fungsi search merupakan fungsi custom untuk mengupdate recycler view
                    fragmentSearchResult.search(filteredList)
                }
            }

        })

        btn_cancel.setOnClickListener {
            et_search.setText("")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_STATUS, et_search.text.toString())
    }

//    private fun fetch() {
//        val query: Query = database
//
//        val options: FirebaseRecyclerOptions<Course> = FirebaseRecyclerOptions.Builder<Course>()
//                .setQuery(query) { snapshot ->
//                    Course(
//                            snapshot.child("desc").value.toString(),
//                            snapshot.child("judl").value.toString(),
//                            snapshot.child("owner").value.toString(),
//                            snapshot.child("poster").value.toString(),
//                            snapshot.child("harga").value.toString(),
//                            snapshot.child("rating").value.toString(),
//                    )
//                }
//                .build()
//
//        val adapter = object : FirebaseRecyclerAdapter<Course, CourseViewHolder>(options) {
//            lateinit var contextAdapter: Context
//
//            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
//                val layoutInflater = LayoutInflater.from(parent.context)
//                contextAdapter = parent.context
//                val inflatedView: View = layoutInflater.inflate(com.example.kurditing.R.layout.row_item_search_course, parent, false)
//
////                val view: View = LayoutInflater.from(this).inflate(R.layout.row)
//                return CourseViewHolder(inflatedView)
//            }
//
//            override fun onBindViewHolder(holder: CourseViewHolder, position: Int, course: Course) {
//                holder.bindItem(course, )
//            }
//        }
//
//        rv_search_result.adapter = adapter
//        adapter
//    }

//    private fun firebaseCourseSearch(ss: String) {
//        val firebaseRecyclerAdapter: FirebaseRecyclerAdapter<Course, CourseViewHolder> = FirebaseRecyclerAdapter<Course, CourseViewHolder>()
//    }

//    class CourseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        private val tvTitle: TextView = view.findViewById(com.example.kurditing.R.id.tv_judul)
//        private val tvOwner: TextView = view.findViewById(R.id.tv_owner)
//        private val tvRating: TextView = view.findViewById(R.id.tv_rating)
//        private val tvHarga: TextView = view.findViewById(R.id.tv_harga)
//
//        private val ivPoster: ImageView = view.findViewById(R.id.iv_poster)
//
//        fun bindItem(data: Course, listener: (Course) -> Unit, context: Context, position: Int) {
//
//            tvTitle.text = data.judul
//            tvOwner.text = data.owner
//            tvRating.text = data.rating
//            tvHarga.text = data.harga
//
//            Glide.with(context)
//                    .load(data.poster)
//                    .into(ivPoster);
//
//            itemView.setOnClickListener {
//                listener(data)
//            }
//        }
//    }

    private fun setFragment(fragment: Fragment) {
        val fragmentManager = getFragmentManager()
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.layout_frame, fragment)
        fragmentTransaction?.commit()
    }

    private fun getData() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                dataList.clear()
                for (getdataSnapshot in dataSnapshot.children) {
                    val course = getdataSnapshot.getValue(Course::class.java)

                    dataList.add(course!!)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "" + error.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

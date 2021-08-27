package com.tahadroid.wing.ui.fragments.home.restaurants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.database.*
import com.tahadroid.wing.R
import com.tahadroid.wing.models.More
import com.tahadroid.wing.ui.fragments.home.restaurants.adapters.MoreAdapter
import kotlinx.android.synthetic.main.dialog_view_more.*


class ViewMoreDialog : DialogFragment() {

    private lateinit var moreAdapter: MoreAdapter
    private lateinit var refTitle: String
    var list: ArrayList<More>? = null
    private lateinit var popRef: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.dialog_view_more, container, false)
        return v
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
    }

    // get data of Popular from firebase
    private fun getData() {


        list = ArrayList()
        popRef = FirebaseDatabase.getInstance().reference.child("category").child("foods")
            .child("popular-food")

        popRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                list?.clear()
                for (n in snapshot.children) {
                    var pop = n.getValue(More::class.java)
                    list?.add(pop!!)
                }
                moreAdapter.notifyDataSetChanged()

            }

        })
        setupExploreRecyclerView()
    }

    // set data to recycler
    private fun setupExploreRecyclerView() {
        moreAdapter =
            MoreAdapter { view, Food, i ->


            }

        moreAdapter.swapData(list!!)
        viewMoreRecyclerView.apply {
            adapter = moreAdapter
        }
    }

}
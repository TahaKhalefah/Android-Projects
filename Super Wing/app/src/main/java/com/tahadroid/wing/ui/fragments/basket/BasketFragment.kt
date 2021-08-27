package com.tahadroid.wing.ui.fragments.basket

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.tahadroid.wing.R
import com.tahadroid.wing.data.local.OrderDatabase
import com.tahadroid.wing.models.Cart
import com.tahadroid.wing.models.Order
import com.tahadroid.wing.repository.Repository
import kotlinx.android.synthetic.main.fragment_basket.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class BasketFragment : Fragment(R.layout.fragment_basket), CalculateOrderPrice {
    private lateinit var cart: Cart
    private lateinit var builder: AlertDialog.Builder
    private lateinit var basketAdapter: BasketAdapter
    private lateinit var basketViewModel: BasketViewModel
    var listOrders: List<Order>? = null
    var subTotal: Double = 0.0
    val TAX = 0.0
    val deliveryFee = 30.0
    var ServiceFee = 5.0
    var total = 0.0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val window = requireActivity().window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(requireActivity(), R.color.white)


        textViewDeliveryFee.text = deliveryFee.toString()
        textViewServiceFee.text = ServiceFee.toString()
        textViewTax.text = TAX.toString()
        buttonAddItem.setOnClickListener {
            val action = BasketFragmentDirections.actionBasketFragmentToHomeFragment()
            view.findNavController().navigate(action)
        }
        initViewModel()
        observeData()
        listOrders = ArrayList()
        initDialog()

        button.setOnClickListener {
            builder.show()
        }
    }

    private fun setVisibilityOrder() {
        if (listOrders?.size == 0) {
            linearLayoutEmpty.visibility = View.VISIBLE
            constraintLayoutCart.visibility = View.GONE
        } else {
            linearLayoutEmpty.visibility = View.GONE
            constraintLayoutCart.visibility = View.VISIBLE
        }
    }

    private fun initDialog() {
        val dialogClickListener =
            DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE ->                         //Yes button clicked
                    {
                        addingToCartList()
                        basketViewModel.deleteAll()
                        Toast.makeText(requireContext(), "Done (;", Toast.LENGTH_SHORT).show()
                    }
                    DialogInterface.BUTTON_NEGATIVE ->                         //No button clicked
                        dialog.dismiss()
                }
            }
        builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Order Confirmation").setPositiveButton("Confirm", dialogClickListener)
            .setNegativeButton("Cancel", dialogClickListener)
            .setIcon(R.drawable.ic_orderconfirm)
    }

    private fun observeData() {
        basketViewModel.getAll().observe(viewLifecycleOwner, Observer { list ->

            listOrders = list
            setVisibilityOrder()
            calOrder()
            basketAdapter = BasketAdapter({ view, order, i ->
                basketViewModel.deleteOrder(order)
                calOrder()
            }, requireContext(), this)
            basketAdapter.swapData(list)
            basketAdapter.notifyDataSetChanged()
            setupRecyclerView()
        })
    }


    private fun setupRecyclerView() {
        checkoutRecyclerView.apply {
            adapter = basketAdapter
        }
    }

    private fun initViewModel() {
        val repository = Repository(OrderDatabase(requireContext()))
        val checkoutViewModelFactory = BasketViewModelFactory(repository)
        basketViewModel =
            ViewModelProvider(requireActivity(), checkoutViewModelFactory)
                .get(BasketViewModel::class.java)
    }

    fun addingToCartList() {
        val calForDate = Calendar.getInstance()

        val currentDate = SimpleDateFormat("MMM dd ,yyyy")
        var saveCurrentDate = currentDate.format(calForDate.time)

        val currentTime = SimpleDateFormat("HH:mm:ss a")
        var saveCurrentTime = currentTime.format(calForDate.time)

        val cartListRef = FirebaseDatabase.getInstance().reference
            .child("Orders")

        cart = Cart(
            subTotal, total, saveCurrentTime,
            deliveryFee, listOrders!!, "egypt,alex", saveCurrentDate, TAX
        )
        cartListRef.child(FirebaseAuth.getInstance().currentUser?.uid.toString()).push()
            .setValue(cart)

    }

    override fun calOrder() {
        subTotal = 0.0
        total = 0.0
        for (list in listOrders!!) {
            subTotal += (list.price * list.quantity)
        }
        textViewSubtotal.text = subTotal.toString()
        total = deliveryFee + ServiceFee + TAX + subTotal
        textViewTotal.text = total.toString()
    }

}
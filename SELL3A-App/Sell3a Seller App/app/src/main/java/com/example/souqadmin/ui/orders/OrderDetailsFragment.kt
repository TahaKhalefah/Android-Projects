package com.example.souqadmin.ui.orders

import ahmed.adel.sleeem.clowyy.souq.notifications.Notifications
import android.content.Intent
import android.net.Uri
import android.os.Binder
import android.os.Bundle
import android.telephony.BarringInfo
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.souqadmin.R
import com.example.souqadmin.databinding.FragmentOrderDetailsBinding
import com.example.souqadmin.pojo.ItemResponse
import com.example.souqadmin.pojo.ModifyOrderState
import com.example.souqadmin.pojo.OrderResponse
import com.example.souqadmin.pojo.OrderResponseItem
import com.example.souqadmin.ui.orders.adapter.OrderDetailsAdapter
import com.example.souqadmin.utils.Resource
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONException
import org.json.JSONObject


class OrderDetailsFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentOrderDetailsBinding
    lateinit var adapter: OrderDetailsAdapter
    val args: OrderDetailsFragmentArgs by navArgs()
    lateinit var order: OrderResponseItem
    lateinit var viewModel: OrderDetailsViewModel
    lateinit var itemsList: MutableList<ItemResponse>
    private val tax = 40
    private lateinit var mySocket: Socket;
    private lateinit var notification: Notifications;
    private lateinit var email: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)

        //Notification
        notification = Notifications(requireContext())

        //Socket Io
        mySocket = IO.socket("https://souqitigraduationproj.herokuapp.com")
        mySocket.open()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val types = resources.getStringArray(R.array.status_list)
        var arrayAdapter = ArrayAdapter(
            requireContext(),
            R.layout.status_dropdown_item, types
        )
        binding.autoCompleteTextView.setAdapter(arrayAdapter)

        order = args.orderResponse
        initViewModel()
        itemsList = mutableListOf()
        initProductsRecyclerView()

        var userInfo = order.userId.split("@")
        binding.userName.text = userInfo[0]

        email = userInfo[3]
        if(userInfo[1] != null){
            binding.phoneNo.text = userInfo[1]
        } else{
            binding.phoneNo.text = ""
        }
        binding.orderDateTv.text = order.orderDate
        binding.orderIdTv.text = order._id
        binding.address.text = order.Address

        var itemsCount = 0.0
        var itemsPrice = 0.0
        for (itm in order.itemIds) {
            itemsCount += itm.count
            itemsPrice += itm.count
        }

        val total = order.totalPrice + tax
        binding.taxTv.text = "\$$tax"
        binding.itemPriceTv.text = "\$${order.totalPrice}"
        binding.paymentMethod.text = "Visa"

        binding.totalPriceTv.text = "\$${total}"

        binding.fbWhatsapp.setOnClickListener(this)
        binding.fbMessage.setOnClickListener(this)
        binding.updateBtn.setOnClickListener(this)

    }

    private fun initProductsRecyclerView() {
        adapter = OrderDetailsAdapter({ view, item, i ->
        }, requireContext())
        getItemById()
        adapter.changeData(itemsList)
        binding.ordersRv.adapter = adapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(OrderDetailsViewModel::class.java)
        for (odr in order.itemIds)
            viewModel.getItemsById(odr.id)
    }

    private fun getItemById() {
        viewModel.item.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Log.e("TAG", "getAllOrders: LOADING")
                    binding.itemProgressBar.visibility = View.VISIBLE
                }
                Resource.Status.ERROR -> {
                    Log.e("TAG", "getAllOrders: ERROR" + it.message)
                    binding.itemProgressBar.visibility = View.VISIBLE
                }
                Resource.Status.SUCCESS -> {
                    it.data.let {
                        binding.itemProgressBar.visibility = View.GONE
                        Log.e("TAG", "getAllOrders: ERROR" + it?.size)
                        itemsList.add(it!!)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    private fun chooseState() {
        val state = binding.autoCompleteTextView.text.toString()
        when (state) {
            "Packing", "Shipped", "Delivered", "Canceled" -> {
                Toast.makeText(requireContext(), "Updated $state", Toast.LENGTH_LONG).show()
                changeState()
                viewModel.modifyState(ModifyOrderState(order._id, state))
                binding.updateBtn.startLoading()
                val action =
                    OrderDetailsFragmentDirections.actionOrderDetailsFragmentToOrdersFragment()
                view?.findNavController()?.navigate(action)
            }
        }
    }

    private fun changeState() {
        viewModel.modifyStateLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Log.e("modifyState", "Loading........")
                }
                Resource.Status.SUCCESS -> {
                    Log.e("modifyState", "success........")
                }
                Resource.Status.ERROR -> {
                    Log.e("modifyState", it.message.toString())
                }

            }
        })
    }

    private fun whatsApp() {
        var userInfo = order.userId.split("@")
        var num = userInfo[1]
        val url = "https://api.whatsapp.com/send?phone=$num"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun message() {
        var userInfo = order.userId.split("@")
        var num = "+20" + userInfo[1]
        val sendIntent = Intent(Intent.ACTION_VIEW)
        sendIntent.data = Uri.parse("sms:")
        sendIntent.putExtra("address", num)
        startActivity(sendIntent)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.fbWhatsapp -> {
                whatsApp()
            }
            binding.fbMessage -> {
                message()
            }
            binding.updateBtn -> {
                val state = binding.autoCompleteTextView.text.toString()
                var jsonString = "{message: " + "'" + state + "'" + "},";
                jsonString += "{email: " + "'" + email + "'" + "}";
                try {
                    val jsonData = JSONObject(jsonString)
                    mySocket.emit("onShipStatusChange", jsonData)
                } catch (e: JSONException) {
                    Log.d("me", "error send message " + e.message)
                }
                chooseState()
            }
        }
    }
}
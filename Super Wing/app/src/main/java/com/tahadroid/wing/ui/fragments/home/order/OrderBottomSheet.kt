package com.tahadroid.wing.ui.fragments.home.order

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tahadroid.wing.R
import com.tahadroid.wing.data.local.OrderDatabase
import com.tahadroid.wing.models.Order
import com.tahadroid.wing.repository.Repository
import kotlinx.android.synthetic.main.bottom_sheet_order.*

class OrderBottomSheet : BottomSheetDialogFragment() {
    private lateinit var order: Order
    private lateinit var builder: AlertDialog.Builder
    private lateinit var bottomSheetViewModel: OrderBottomSheetViewModel
    val args: OrderBottomSheetArgs by navArgs()
    var count = 1
    var size = 0
    var placeKey: String? = null
    var flagFav = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_order, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        foodPriceTextView.text = args.menuOrder.price.toString()
        foodNameTextView.text = args.menuOrder.name
        shopNameTextView.text = args.menuOrder.shopName
        Glide.with(requireContext()).load(args.menuOrder.image).into(mealImage)

        initViewModel()

        determineSize()

        setupObserverAllOrders()

        plusView.setOnClickListener {
            count++
            countTextView.setText(count.toString())
        }

        minusView.setOnClickListener {
            if (count == 1) {
                Toast.makeText(requireContext(), "please add more than one", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            } else {
                count--
                countTextView.setText(count.toString())
            }

        }

        imageViewFav.setOnClickListener {
            if (flagFav == false) {
                imageViewFav.setBackground(resources.getDrawable(R.drawable.ic_favorite))
                flagFav = true
            } else {
                imageViewFav.setBackground(resources.getDrawable(R.drawable.ic_baseline_favorite_24))
                flagFav = false
            }
        }

        buttonAddToCart.setOnClickListener {
            setupObserverAddOrder()
        }

        showPopupMenu()

    }

    private fun setupObserverAddOrder() {
        order = Order(
            args.menuOrder.placeKey!!,
            args.menuOrder.shopName!!,
            args.menuOrder.name!!,
            args.menuOrder.image!!,
            args.menuOrder.price.toDouble(),
            count,
            size,
            tagTextView.text.toString()
        )
        if (placeKey == null ||placeKey.equals(args.menuOrder.placeKey)) {
            Toast.makeText(
                requireContext(),
                "added to your cart successfully",
                Toast.LENGTH_SHORT
            ).show()
            buttonAddToCart.isEnabled = false
            bottomSheetViewModel.upsert(order)
        } else {
            initDialog()
            builder.show()
        }
    }

    private fun setupObserverAllOrders() {
        bottomSheetViewModel.getAllOrders().observe(viewLifecycleOwner, Observer { list ->
            if (list != null && !(list.isEmpty())) {
                placeKey = list.get(0).placeKey
            }
        })
    }

    private fun initDialog() {
        val dialogClickListener =
            DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE ->                         //Yes button clicked
                    {
                        bottomSheetViewModel.deleteAllOrders()
                        bottomSheetViewModel.upsert(order)
                        Toast.makeText(requireContext(), "Done (;", Toast.LENGTH_SHORT).show()
                    }
                    DialogInterface.BUTTON_NEGATIVE ->                         //No button clicked
                        dialog.dismiss()
                }
            }
        builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Be careful,You will Discard the old Orders on Your Cart!!")
            .setIcon(R.drawable.ic_warning)
            .setPositiveButton("Yes", dialogClickListener)
            .setNegativeButton("No", dialogClickListener)
    }

    private fun initViewModel() {
        val repository = Repository(OrderDatabase(requireContext()))
        val orderBottomSheetViewModelFactory = OrderBottomSheetViewModelFactory(repository)
        bottomSheetViewModel =
            ViewModelProvider(requireActivity(), orderBottomSheetViewModelFactory)
                .get(OrderBottomSheetViewModel::class.java)
    }

    private fun showPopupMenu() {
        tagView.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), tagView)
            popupMenu.menuInflater.inflate(R.menu.order_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.handMade -> {
                        Toast.makeText(
                            requireContext(),
                            "You Clicked : " + item.title,
                            Toast.LENGTH_SHORT
                        ).show()
                        tagTextView.text = item.title
                    }
                    R.id.handTossed -> {

                        Toast.makeText(
                            requireContext(),
                            "You Clicked : " + item.title,
                            Toast.LENGTH_SHORT
                        ).show()
                        tagTextView.text = item.title
                    }
                    R.id.thinCrust -> {
                        Toast.makeText(
                            requireContext(),
                            "You Clicked : " + item.title,
                            Toast.LENGTH_SHORT
                        ).show()
                        tagTextView.text = item.title
                    }
                }
                true
            })
            popupMenu.show()
        }
    }

    private fun determineSize() {
        oneView.setOnClickListener {
            size = size20TV.text.toString().toInt()
            checkView(oneView)
            checkViewOff(twoView, threeView, fourView)
        }
        twoView.setOnClickListener {
            size = size16TV.text.toString().toInt()
            checkView(twoView)
            checkViewOff(oneView, threeView, fourView)
        }
        threeView.setOnClickListener {
            size = size14TV.text.toString().toInt()
            checkView(threeView)
            checkViewOff(twoView, oneView, fourView)
        }
        fourView.setOnClickListener {
            size = size12TV.text.toString().toInt()
            checkView(fourView)
            checkViewOff(twoView, threeView, oneView)
        }
    }

    private fun checkView(oneView: View) {
        oneView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
    }

    private fun checkViewOff(oneView: View, twoView: View, threeView: View) {
        oneView.setBackground(resources.getDrawable(R.drawable.background_choose_item))
        twoView.setBackground(resources.getDrawable(R.drawable.background_choose_item))
        threeView.setBackground(resources.getDrawable(R.drawable.background_choose_item))
    }

}
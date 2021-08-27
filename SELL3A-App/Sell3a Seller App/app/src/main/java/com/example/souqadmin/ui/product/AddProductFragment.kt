package com.example.souqadmin.ui.product

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.souqadmin.R
import com.example.souqadmin.databinding.FragmentAddProductBinding
import com.example.souqadmin.pojo.CategoryResponse
import com.example.souqadmin.pojo.ProductResponse
import com.example.souqadmin.ui.product.buttomDialog.SelectColorFragment
import com.example.souqadmin.utils.Constants
import com.example.souqadmin.utils.ProuductData
import com.example.souqadmin.utils.Resource
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.OnColorSelectedListener
import com.flask.colorpicker.builder.ColorPickerClickListener
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import java.util.*
import kotlin.collections.ArrayList


class AddProductFragment : Fragment() {
    private var currentBackgroundColor: Int = 0xfffffff
    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProductViewModel
    var colorList: Array<String>? = null
    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var imageUri: String = ""
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private var Categoris = arrayListOf<String>()
    var Categories = CategoryResponse()
    val args by navArgs<AddProductFragmentArgs>()
    private var productData: ProductResponse.Item? = null
    private lateinit var arrayAdapter: ArrayAdapter<String>
    var product = ProuductData.product
    lateinit var selectColorFragment: SelectColorFragment
    var sizeList: Array<String>? = null
    var flag = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectColorFragment = SelectColorFragment()
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().getReference()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)
        changeBackgroundColor(currentBackgroundColor);

        viewModel = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllCategories()
        binding.companyName.setText(getCompanyName())

        SelectColorFragment.setOnColorDelete = object : SelectColorFragment.OncolorDeleted {
            override fun onDelete(colorList: ArrayList<String>) {
                this@AddProductFragment.colorList = colorList.toTypedArray()
            }
        }

        if (args.product != null) {
            productData = args.product!!
        } else {
            ProuductData.sizeItems.clear()
        }
        binding.amouneEt.setText("0")
        binding.durationEt.setText("0")
        editObject()

        //image action
        binding.imageView2.setOnClickListener {
            launchGallery()
        }
        binding.titleTv.setOnClickListener {
        }

        arrayAdapter = ArrayAdapter(
            requireContext(),
            R.layout.category_dropdown_item, Categoris
        )

        //color action
        binding.colorTv.setOnClickListener {
            if (colorList == null || colorList!!.size == 0) {
                colorDialog()
            } else {
                val action =
                    AddProductFragmentDirections.actionAddProductFragmentToSelectColorFragment(
                        colorList = colorList
                    )
                view?.findNavController()?.navigate(action)
            }
        }

        //size action
        binding.sizeTv.setOnClickListener {
            val action = AddProductFragmentDirections.actionAddProductFragmentToSelectSizeFragment(
                _binding?.categoryTv?.text.toString(),
                sizeList = sizeList
            )
            view?.findNavController()?.navigate(action)
        }


        //add product action
        binding.addProduct.setOnClickListener {
            val isvalid = validation()
            if (isvalid) {
                // product object
                uploadImage()
                if (imageUri != "") {
                    subscribeToLiveData(it)
                } else {
                    binding.addProduct.startLoading()
                    Toast.makeText(requireContext(), "Uploading", Toast.LENGTH_SHORT).show()
                }

            }
        }


        // update product action
        binding.updateProduct.setOnClickListener {
            addProductObject()
            if (validation()) {
                viewModel.modifyProduct(product)
                subscribeToLiveData(null)
                if (!flag) {
                    Toast.makeText(requireContext(), "upload now", Toast.LENGTH_SHORT).show()
                    binding.updateProduct.startLoading()
                    binding.updateProduct.loadingFailed()
                } else {
                    binding.updateProduct.loadingSuccessful()
                    Navigation.findNavController(it).navigateUp()
                }
            }
        }

        subscribeToLiveData(null)

    }

    fun editObject() {
        if (productData != null) {
            binding.updateProduct.visibility = View.VISIBLE
            binding.addProduct.visibility = View.GONE
            Glide.with(requireActivity())
                .load(productData!!.image)
                .fitCenter()
                .into(binding.imageView2)
            product.id = productData!!.id
            product.image = productData!!.image
            binding.titleTv.setText(productData!!.title)
            binding.priceTv.setText(productData!!.price.toString())
            binding.countTv.setText(productData!!.quantity.toString())
            binding.categoryTv.setText(productData!!.category.name)
            binding.descriptionTv.setText(productData!!.description)
            binding.companyName.setText(productData!!.companyName)
            binding.prandName.setText(productData!!.brand)
            if (productData!!.sale != null) {
                binding.saleTv.setText(productData!!.sale.type)
                binding.amouneEt.setText(productData!!.sale.amount.toString())
                binding.durationEt.setText(productData!!.sale.duration.toString())
            }
            if (productData!!.size != null && productData!!.size.size != 0) {
                sizeList = productData!!.size.toTypedArray()
            } else {
                sizeList = null
            }

            if (productData!!.color != null && productData!!.color.size != 0) {
                colorList = productData!!.color.toTypedArray()
            } else {
                colorList = null
            }
        }
    }

    private fun colorDialog() {
        ColorPickerDialogBuilder
            .with(context)
            .setTitle("Choose color")
            .initialColor(currentBackgroundColor)
            .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
            .density(12)
            .setOnColorSelectedListener(object : OnColorSelectedListener {
                override fun onColorSelected(selectedColor: Int) {
                    toast("onColorSelected: 0x" + Integer.toHexString(selectedColor))
                }
            })
            .setPositiveButton("ok", object : ColorPickerClickListener {
                override fun onClick(
                    dialog: DialogInterface?,
                    selectedColor: Int,
                    allColors: Array<Int?>?
                ) {
                    ProuductData.colorItems.add("#" + Integer.toHexString(selectedColor))
                    Log.e("ssss", "selected color : " + ProuductData.colorItems.toString())
                    changeBackgroundColor(selectedColor)
                }
            })
            .setNegativeButton("cancel", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    dialog!!.dismiss()
                }
            })
            .showColorEdit(true)
            .setColorEditTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    android.R.color.holo_blue_bright
                )
            )
            .build()
            .show();
    }

    private fun changeBackgroundColor(selectedColor: Int) {
        currentBackgroundColor = selectedColor
    }

    private fun toast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }


    private fun showConfirmDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Add Product")
            .setMessage("Add Product Details ?")
            .setPositiveButton("Yes") { dialogInterface, which ->
            }
            .setNegativeButton("No") { dialogInterface, which ->
                Toast.makeText(requireContext(), "Add Product Canceled.", Toast.LENGTH_LONG).show()
            }.show()
    }


    fun addProductObject() {
        product.title = binding.titleTv.text.toString().trim()
        ProuductData.category = binding.categoryTv.text.toString()
        for (item in Categories) {
            if (item.name == binding.categoryTv.text.toString()) {
                product.category.name = item.name
                product.category.url = item.url
            }
        }
        product.title = binding.titleTv.text.toString()
        product.description = binding.descriptionTv.text.toString()
        product.price = binding.priceTv.text.toString().toDouble()
        product.quantity = binding.countTv.text.toString().toInt()
        product.brand = binding.prandName.text.toString()
        product.color = ProuductData.colorItems
        product.size = ProuductData.sizeItems
        product.companyName = binding.companyName.text.toString()
        if (binding.saleTv.text.toString() != "") {
            product.sale.type = binding.saleTv.text.toString()
            product.sale.amount = binding.amouneEt.text.toString().toInt()
            product.sale.duration = binding.durationEt.text.toString()
        }
        if (productData == null) {
            product.image = imageUri
        } else {
            product.image = productData!!.image

        }
    }


    fun subscribeToLiveData(view: View? = null) {
        viewModel.addProductsLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Log.e("sssss", "Loading........")
                }
                Resource.Status.SUCCESS -> {
                    Log.e("ssss", "list : " + it.data)
                    binding.addProduct.loadingSuccessful()
                    Navigation.findNavController(requireView())
                        .navigate(R.id.action_addProductFragment_to_productFragment)
                }
                Resource.Status.ERROR -> {
                    Log.e("ssss", it.message.toString())
                }

            }
        })

        viewModel.categoryLiveData.observe(requireActivity(), Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Log.e("sssss", "Loading........")
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()

                }
                Resource.Status.SUCCESS -> {
                    Categories = it.data!!
                    for (item in it.data!!) {
                        Categoris.add(item.name)
                    }
                    // Category Action

                    binding.categoryTv.setAdapter(arrayAdapter)
                }
            }
        })

        viewModel.modifyProductLiveData.observe(requireActivity(), Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Log.e("sssss", "Loading........")
                }
                Resource.Status.ERROR -> {
                    Log.e("ssss", it.message.toString())
                }
                Resource.Status.SUCCESS -> {
                    Log.e("ssss", it.data!!.toString())
                    flag = true
//                                    val action = AddProductFragmentDirections.actionAddProductFragmentToProductFragment()
//                                    requireView().findNavController().navigate(action)
//                                Navigation.findNavController(requireView())
//                                        .navigate(R.id.action_addProductFragment_to_productFragment)


                }
            }
        })

    }

    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }


    private fun uploadImage() {
        if (filePath != null) {
            val ref = storageReference?.child("uploads/" + UUID.randomUUID().toString())
            val uploadTask = ref?.putFile(filePath!!)

            val urlTask =
                uploadTask?.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    return@Continuation ref.downloadUrl
                })?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        imageUri = downloadUri.toString()
                        Log.e("ssss", imageUri)
                        addProductObject()
                        viewModel.addProduct(product)
//                    Toast.makeText(requireContext(), "Upload an Image", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Choose another image", Toast.LENGTH_SHORT)
                            .show()
                        Log.e("sss", task.exception!!.localizedMessage.toString())
                    }
                }?.addOnFailureListener {
                    Toast.makeText(requireContext(), "Choose another image", Toast.LENGTH_SHORT)
                        .show()
                    Log.e("sss", it.localizedMessage)
                }
        } else {
            Toast.makeText(requireContext(), "Please Upload an Image", Toast.LENGTH_SHORT).show()

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_REQUEST) {
            filePath = data?.data
            binding.imageView2.setImageURI(filePath)
        }
    }


    fun validation(): Boolean {
        var isvalide = false
        if (!isvalide) {
            if (binding.titleTv.text.isEmpty()) binding.titleTv.setError("Title is Requierd")
            if (binding.priceTv.text.isEmpty()) binding.priceTv.setError("Price is Requierd")
            if (binding.countTv.text.isEmpty()) binding.countTv.setError("Count is Requierd")
            if (binding.categoryTv.text.isEmpty()) binding.categoryTv.setError("Category is Requierd")
            if (binding.companyName.text.isEmpty()) binding.companyName.setError("Company name is Requierd")
            if (binding.prandName.text.isEmpty()) binding.prandName.setError("Prand is Requierd")
            else isvalide = true
        } else isvalide = true

        return isvalide
    }

    override fun onStop() {
        super.onStop()
        ProuductData.colorItems.clear()
        if (productData != null) {
            if (!productData!!.size.isEmpty()) {
                ProuductData.sizeItems.clear()
                ProuductData.sizeItems.addAll(productData!!.size)
            }
            if (!productData!!.color.isEmpty()) {
                ProuductData.colorItems.clear()
                ProuductData.colorItems.addAll(productData!!.color)
            }
        }
    }



    fun getCompanyName(): String {
        val sharedPreferences = requireContext().getSharedPreferences(
            Constants.USER_SHARED_PREF,
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getString("name", "")!!
    }

}
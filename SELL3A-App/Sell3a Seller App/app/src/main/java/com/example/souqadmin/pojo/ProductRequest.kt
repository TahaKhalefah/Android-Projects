package com.example.souqadmin.pojo

data class ProductRequest(
        var brand: String = "",
        var category: Category = Category(),
        var color: List<String>? = null,
        var companyName: String = "",
        var description: String = "",
        var id: Int = 0,
        var image: String = " ",
        var price: Double = 0.0,
        var quantity: Int = 0,
        var sale: Sale = Sale() ,
        var size: List<String>? = null,
        var title: String = ""
) {
    data class Category(
        var name: String = "",
        var url: String = ""
    )

    data class Sale(
            var amount: Int = 0,
            var duration: String = "",
            val image : List<String> = listOf<String>(),
            var type: String = ""
    )
}
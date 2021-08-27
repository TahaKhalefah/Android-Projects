package ahmed.adel.sleeem.clowyy.souq.api


import ahmed.adel.sleeem.clowyy.souq.pojo.request.LoginRequest
import ahmed.adel.sleeem.clowyy.souq.pojo.request.RegisterRequest
import ahmed.adel.sleeem.clowyy.souq.pojo.response.LoginResponse
import ahmed.adel.sleeem.clowyy.souq.pojo.response.RegisterResponse
import com.example.souqadmin.pojo.*
import retrofit2.Response
import retrofit2.http.*

interface ItemWebServices {

    @GET("products/getbycompanyName")
    suspend fun getItemsByCompanyName(@Query("companyName") companyName: String): Response<ProductResponse>

    @GET("products/getcategories")
    suspend fun getCategory(): Response<CategoryResponse>

    @POST("products/add")
    suspend fun addProduct(@Body orderRequest: ProductRequest): Response<ProductResponse.Item>

    @PUT("products/modifyproduct")
    suspend fun modifyProduct(@Body modifyProductRequest: ProductRequest): Response<ProductResponse.Item>

    @HTTP(method = "DELETE", path = "products/delete", hasBody = true)
    suspend fun deleteReview(@Body deleteProductRequest: DeleteProductRequest): Response<DeleteProductResponce>

    @GET("order/orderbycompanyname")
    suspend fun getOrdersByCompanyName(@Query("companyName") companyName: String): Response<List<OrderResponseItem>>

    @POST("order/modifyorder")
    suspend fun modifyState(@Body modifyOrderState: ModifyOrderState): Response<OrderResponseItem>

    @GET("products/getitembyid")
    suspend fun getItemsById(@Query("id") id: String): Response<ItemResponse>

    @GET("products/soldout")
    suspend fun getItemsByCount(@Query("companyName")companyName:String , @Query("quantity")count : String): Response<ProductResponse>

    @POST("admin/login")
    suspend fun loginAdmin(
        @Body request: LoginRequest,
        // @Header("x-auth-token") token: String
    ): Response<LoginResponse>

    @POST("admin/signup")
    suspend fun registerAdmin(
        @Body request: RegisterRequest,
    ): Response<RegisterResponse>
}
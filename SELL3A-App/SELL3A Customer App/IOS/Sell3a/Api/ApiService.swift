//
//  ApiService.swift
//  Sell3a
//
//  Created by Hager Samir on 17/06/2021.
//

import Foundation
import Alamofire

class ApiService {
    static let shared = ApiService()
    private let BASE_URL = "https://souqitigraduationproj.herokuapp.com/api/"
    private init(){}
    
    func addReview(request:AddReviewRequest,compilation:@escaping(ReviewResponse? ,Error?)->Void) {
        let endPoint = "review/addreview"
        
        AF.request(BASE_URL+endPoint, method: .post , parameters: request, encoder: JSONParameterEncoder.default, headers: nil, interceptor: nil, requestModifier: nil)
            .validate()
            .responseDecodable(of: ReviewResponse.self) { (response) in
                switch response.result{
                case .success(let data):
                    compilation(data,nil)
                case .failure(let error):
                    compilation(nil,error)
                }
                
            }
    }
    
    
    
    func addOrder(request:AddOrderRequest,compilation:@escaping(OrderResponse? ,Error?)->Void) {
        let endPoint = "order/add"
        AF.request(BASE_URL+endPoint, method: .post , parameters: request, encoder: JSONParameterEncoder.default, headers: nil, interceptor: nil, requestModifier: nil)
            .validate()
            .responseDecodable(of: OrderResponse.self) { (response) in
                switch response.result{
                case .success(let data):
                    compilation(data,nil)
                case .failure(let error):
                    compilation(nil,error)
                }
                
            }
    }
    
    
    func getReviews(itemId:Int, rate:Int=0,compilation:@escaping([ReviewResponse]? ,Error?)->Void) {
        let endPoint = "review/reviewbyrating"
        let parameters:Parameters = ["rating":"\(rate)" ,
                                     "itemId":"\(itemId)"]
        
        AF.request(BASE_URL+endPoint, method: .get, parameters: parameters, encoding: URLEncoding.queryString, headers: nil, interceptor: nil, requestModifier: nil)
            .validate()
            .responseDecodable(of: [ReviewResponse].self) { (response) in
                switch response.result{
                case .success(let data):
                    compilation(data,nil)
                case .failure(let error):
                    if error.responseCode == 400 {
                        compilation([],nil)
                    }
                    compilation(nil,error)
                }
                
            }
    }
    

    
    func getProductsByCategoryName(categoryName:String, compilation:@escaping([ProductResponse]? ,Error?)->Void) {
        let endPoint = "products/getbycategoryname"
        let parameters:Parameters = ["category":"\(categoryName)"]
        
        AF.request(BASE_URL+endPoint, method: .get, parameters: parameters, encoding: URLEncoding.queryString, headers: nil, interceptor: nil, requestModifier: nil)
            .validate()
            .responseDecodable(of: [ProductResponse].self) { (response) in
                switch response.result{
                case .success(let data):
                    compilation(data,nil)
                case .failure(let error):
                    if error.responseCode == 400 {
                        compilation([],nil)
                    }
                    compilation(nil,error)
                }
            }
    }
    
    
    func getOrderById(userId :String, compilation:@escaping([OrderResponse]? ,Error?)->Void) {
        let endPoint = "order/orderbyuserid"
        let parameters:Parameters = ["id":"\(userId)"]
        
        AF.request(BASE_URL+endPoint, method: .get, parameters: parameters, encoding: URLEncoding.queryString, headers: nil, interceptor: nil, requestModifier: nil)
            .validate()
            .responseDecodable(of: [OrderResponse].self) { (response) in
                switch response.result{
                case .success(let data):
                    compilation(data,nil)
                    print("Data")
                case .failure(let error):
                    if error.responseCode == 400 {
                        compilation([],nil)
                    }
                    compilation(nil,error)
                }
            }
    }
    
    func filterProducts(min:Int?=nil,max:Int?=nil,price:Int=0,category:String?=nil,sale:Int=0,brand:String?=nil,title:String?,page:Int = 1, compilation:@escaping([ProductResponse]? ,Error?)->Void) {
            let endPoint = "products/filter"
            let parameters:Parameters = ["min":min , "max":max,"category":category,"sale":sale,"brand":brand,"title":title,"price":price,"page":page]
          
            AF.request(BASE_URL+endPoint, method: .get, parameters: parameters, encoding: URLEncoding.queryString, headers: nil, interceptor: nil, requestModifier: nil)
                .validate()
                .responseDecodable(of: [ProductResponse].self) { (response) in
                    switch response.result{
                    case .success(let data):
                        compilation(data,nil)
                        print("taha data ==> \(data)")
                    case .failure(let error):
                        if error.responseCode == 400 {
                            compilation([],nil)
                        }
                        compilation(nil,error)
                    }
                }
        }
    
    func getProduct<T : Decodable> (url : String , complition:@escaping(T?, Error?) -> Void){
        
        AF.request(url).responseJSON{(response) in
            guard let data = response.data else {return}
            switch response.result{
            case .success(_):
                do{
                    let product = try! JSONDecoder().decode(T.self, from: data)
                    complition(product, nil)
                    
                }catch let jsonError{
                    complition(nil, jsonError)
                    print(jsonError)
                }
            case .failure(let error):
                complition(nil, error)
                print(error)
            }
            
        }
    }
    
    
    
    
    func getSaleProduct(compilation: @escaping ([ProductResponse]? , Error?) -> Void ){
        let endPoint = "products/getsales"
        AF.request(BASE_URL+endPoint, method: .get, parameters: nil , encoding: URLEncoding.queryString, headers: nil , interceptor: nil , requestModifier: nil).validate().responseDecodable (of: [ProductResponse].self) { (response)in
            
            switch response.result{
            case .success(let data):
                compilation(data,nil)
 
            case .failure(let error):
                compilation(nil,error)
            }
            
        }
    }
    
    
       func modifyAccount(request:AccountRequest ,compilation:@escaping(AccountResponse? ,Error?)->Void) {
           let endPoint = ("users/modifyaccount")
           AF.request(BASE_URL+endPoint, method: .put , parameters: request, encoder: JSONParameterEncoder.default, headers: nil, interceptor: nil, requestModifier: nil)
               .validate()
               .responseDecodable(of: AccountResponse.self) { (response) in
                   switch response.result{
                   case .success(let data):
                       compilation(data,nil)
                   case .failure(let error):
                       compilation(nil,error)
                   }
               }
       }
       
       func changePassword(request:PasswordRequest ,compilation:@escaping(PasswordResponse? ,Error?)->Void) {
           let endPoint = "users/changepassword"
           AF.request(BASE_URL+endPoint, method: .put , parameters: request, encoder: JSONParameterEncoder.default, headers: nil, interceptor: nil, requestModifier: nil)
               .validate()
               .responseDecodable(of: PasswordResponse.self) { (response) in
                   switch response.result{
                   case .success(let data):
                       compilation(data,nil)
                   case .failure(let error):
                       compilation(nil,error)
                   }
               }
       }
       
    
       
       func callingRegisterAPI(register : RegisterModel, completionHandler : @escaping(Bool)->()){
           let headers: HTTPHeaders = [
               .contentType("application/json")
           ]
           let endPoint = "users"
           
           AF.request(BASE_URL+endPoint,method: .post, parameters: register, encoder: JSONParameterEncoder.default , headers: headers).response { response in
               debugPrint(response)
               switch response.result {
               case .success(let data):
                   do {
                       let json = try JSONSerialization.jsonObject(with: data!, options: [])
                       print(json)
                       if response.response?.statusCode == 200 {
                           completionHandler(true)
                           
                       }else{
                           completionHandler(false)
                       }
                   }catch{
                       print(error.localizedDescription)
                   }
               case .failure(let err):
                   print(err.localizedDescription)
                   
               }
           }
       }
       
       func callingLoginAPI(login : LoginModel, completionHandler : @escaping(Bool)->()){
           let headers: HTTPHeaders = [
               .contentType("application/json")
           ]
           let endPoint = "auth"
           
           AF.request(BASE_URL+endPoint,method: .post, parameters: login, encoder: JSONParameterEncoder.default , headers: headers).response { response in
               debugPrint(response)
               switch response.result {
               case .success(let data):
                   do {
                       let json = try JSONSerialization.jsonObject(with: data!, options: [])
                       print(json)
                       if response.response?.statusCode == 200 {
                           completionHandler(true)
                       }else{
                           completionHandler(false)
                       }
                   }catch{
                       print(error.localizedDescription)
                   }
               case .failure(let err):
                   print(err.localizedDescription)
               }
               
           }
       }
    
    
}

//
//  ReviewsListView.swift
//  Sel3a
//
//  Created by Mahmoud Mousa on 20/06/2021.
//

import SwiftUI

struct ReviewsListView: View {
    //MARK: - PROPERTY
    @State var reviews:[ReviewResponse]
    
    var body: some View {
        VStack{
            List{
                ForEach(0..<reviews.count){ i in
                    ItemReviewListView(review: reviews[i]).padding(.vertical,5)
                }
            }
        }
    }
}

//struct ReviewsListView_Previews: PreviewProvider {
//    static var previews: some View {
//        ReviewsListView()
//    }
//}

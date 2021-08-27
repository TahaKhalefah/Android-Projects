package com.tahadroid.quraanapp.adapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DividerItemDecorationn extends RecyclerView.ItemDecoration {
    private Drawable mDivider;

    public DividerItemDecorationn(Drawable divider) {
        mDivider = divider;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView
            parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view) == 0) {
            return;
        }
        outRect.top = mDivider.getIntrinsicHeight();
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int left = 32;
        int right = parent.getWidth() - 32;

        for (int i = 0; i < parent.getChildCount(); i++) {
            //This is done so that at the bottom of the last child
            //We don't want a divider there.
            if (i != parent.getChildCount() - 1) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                //Calculating the distance of the divider to be drawn from top
                int dividerTop = child.getBottom() + params.bottomMargin;
                int dividerBottom = dividerTop + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, dividerTop, right, dividerBottom);
                mDivider.draw(c);
            }

        }

    }
}
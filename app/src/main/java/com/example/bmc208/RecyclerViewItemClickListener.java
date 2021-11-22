package com.example.bmc208;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewItemClickListener extends RecyclerView.SimpleOnItemTouchListener {

    OnItemClickListener clickListener;
    GestureDetectorCompat gestureDetector;

    public RecyclerViewItemClickListener(RecyclerView recyclerView, OnItemClickListener listener) {
        this.clickListener = listener;
        gestureDetector = new GestureDetectorCompat(recyclerView.getContext(), new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e){
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        View childView = rv.findChildViewUnder(e.getX(), e.getY());
        if(childView != null && clickListener != null && gestureDetector.onTouchEvent(e)){
            clickListener.onItemClick(childView, rv.getChildAdapterPosition(childView));
            return true;
        }
        return false;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
}

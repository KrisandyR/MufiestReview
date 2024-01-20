package com.example.mufiest.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.mufiest.R;
import com.example.mufiest.models.Review;
import com.example.mufiest.models.ReviewWithDetail;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ReviewFormFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "formType";
    private static final String ARG_PARAM2 = "movieId";
    private static final String ARG_PARAM3 = "userId";
    private static final String ARG_PARAM4 = "exitBtnClickListener";
    private static final String ARG_PARAM5 = "submitBtnClickListener";
    private static final String ARG_PARAM6 = "existingReview";


    private String formType;
    private String movieId;
    private String userId;
    private ReviewWithDetail reviewDetail;
    private onExitButtonClickedListener exitBtnClickListener;
    private onSubmitButtonClickedListener submitBtnClickListener;
    private ImageButton exitBtn;
    private TextView reviewFormTitle, reviewErrorTv;
    private RatingBar reviewRatingRb;
    private EditText reviewDescEt;
    private Button reviewSubmitBtn;

    public ReviewFormFragment() {
        // Required empty public constructor
    }

    public static ReviewFormFragment newInstance(String formType, String movieId, String userId,
                                                 onExitButtonClickedListener exitBtnClickListener,
                                                 onSubmitButtonClickedListener submitBtnClickListener
                                                 ) {
        ReviewFormFragment fragment = new ReviewFormFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, formType);
        args.putString(ARG_PARAM2, movieId);
        args.putString(ARG_PARAM3, userId);
        args.putSerializable(ARG_PARAM4, exitBtnClickListener);
        args.putSerializable(ARG_PARAM5, submitBtnClickListener);
        fragment.setArguments(args);
        return fragment;
    }

    public static ReviewFormFragment newInstance(String formType, String movieId, String userId,
                                                 onExitButtonClickedListener exitBtnClickListener,
                                                 onSubmitButtonClickedListener submitBtnClickListener,
                                                 ReviewWithDetail existingReview) {
        ReviewFormFragment fragment = new ReviewFormFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, formType);
        args.putString(ARG_PARAM2, movieId);
        args.putString(ARG_PARAM3, userId);
        args.putSerializable(ARG_PARAM4, exitBtnClickListener);
        args.putSerializable(ARG_PARAM5, submitBtnClickListener);
        if(existingReview != null){
            args.putParcelable(ARG_PARAM6, existingReview);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            formType = getArguments().getString(ARG_PARAM1);
            movieId = getArguments().getString(ARG_PARAM2);
            userId = getArguments().getString(ARG_PARAM3);
            exitBtnClickListener = (onExitButtonClickedListener) getArguments().getSerializable(ARG_PARAM4);
            submitBtnClickListener = (onSubmitButtonClickedListener) getArguments().getSerializable(ARG_PARAM5);
            if (getArguments().containsKey(ARG_PARAM6)) {
                reviewDetail = getArguments().getParcelable(ARG_PARAM6);
            } else {
                reviewDetail = null;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review_form, container, false);

        reviewFormTitle = view.findViewById(R.id.review_form_title_tv_rf);
        reviewErrorTv= view.findViewById(R.id.review_error_message_tv);
        reviewRatingRb = view.findViewById(R.id.review_form_rating_rb_rf);
        reviewDescEt = view.findViewById(R.id.review_form_edit_et_rf);
        reviewSubmitBtn = view.findViewById(R.id.review_submit_btn_rf);

        if(formType.equalsIgnoreCase("add")){
            setUpAddReviewForm();
        } else if(formType.equalsIgnoreCase("edit")){
            setUpEditReviewForm();
        }

        reviewRatingRb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingBar.setRating(rating);
            }
        });

        exitBtn = view.findViewById(R.id.review_form_exit_btn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitBtnClickListener.onExitButtonClicked();
            }
        });

        reviewSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateReviewData();
            }
        });

        return view;
    }

    private void setUpEditReviewForm() {
        reviewFormTitle.setText("Edit Review");
        reviewRatingRb.setRating(reviewDetail.getRating().floatValue());
        reviewDescEt.setText(reviewDetail.getDescription());
    }

    private void setUpAddReviewForm() {
        reviewFormTitle.setText("Add Review");
        reviewRatingRb.setRating(0);
        reviewDescEt.setText("");
    }

    private void validateReviewData() {

        Double rating = (double) reviewRatingRb.getRating();
        String reviewDesc = String.valueOf(reviewDescEt.getText());

        if(reviewDesc.length() <= 0){
            reviewErrorTv.setVisibility(View.VISIBLE);
            reviewErrorTv.setText("Please insert a review description");
            return;
        }

        reviewErrorTv.setVisibility(View.GONE);
        reviewErrorTv.setText("");

        uploadReviewData(rating, reviewDesc);
    }

    private void uploadReviewData(Double rating, String reviewDesc) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateAsString = simpleDateFormat.format(System.currentTimeMillis());

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://mufiest-e2f6c-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference();
        DatabaseReference reviewCloudEndPoint = myRef.child("reviews");
        String reviewId;

        if(reviewDetail == null && formType.equalsIgnoreCase("add")){
            reviewId = reviewCloudEndPoint.push().getKey();
        } else {
            reviewId = reviewDetail.getReviewId();
        }

        if(reviewId == null){
            return;
        }

        reviewCloudEndPoint.child(reviewId).child("reviewId").setValue(reviewId);
        reviewCloudEndPoint.child(reviewId).child("movieId").setValue(movieId);
        reviewCloudEndPoint.child(reviewId).child("userId").setValue(userId);
        reviewCloudEndPoint.child(reviewId).child("description").setValue(reviewDesc);
        reviewCloudEndPoint.child(reviewId).child("rating").setValue(rating);
        reviewCloudEndPoint.child(reviewId).child("date").setValue(currentDateAsString);

        submitBtnClickListener.onSubmitButtonClicked();
    }

    public interface onExitButtonClickedListener extends Serializable {
        void onExitButtonClicked();
    }

    public interface onSubmitButtonClickedListener extends Serializable {
        void onSubmitButtonClicked();

    }
}
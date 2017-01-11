/*
    Assignment HW3
    Group#19_HW03.zip
    Shashank Ramdohkar, James Budday, Jeffrey Snow
 */

package com.example.group19_hw3;

import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by shash on 21/02/2016.
 */
public class QuizDetails implements Parcelable{
    int quizId;
    String quizText;
    ArrayList<String> answerText = new ArrayList<String>();
    ArrayList<String> answerValue = new ArrayList<String>();
    String quizUrl;
    QuizDetails(int quizId, String quizText, ArrayList<String> answerText, ArrayList<String> answerValue, String quizUrl)
    {
        super();
        this.quizId = quizId;
        this.quizText = quizText;
        this.answerText = answerText;
        this.answerValue = answerValue;
        this.quizUrl = quizUrl;

    }

    QuizDetails()
    {
        this.quizId = quizId;
        this.quizText = quizText;
        this.answerText = answerText;
        this.answerValue = answerValue;
        this.quizUrl = quizUrl;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(quizId);
        dest.writeString(quizText);
        dest.writeStringList(answerText);
        dest.writeStringList(answerValue);
        dest.writeString(quizUrl);
    }

    public static final Parcelable.Creator<QuizDetails> CREATOR
            = new Parcelable.Creator<QuizDetails>() {
        public QuizDetails createFromParcel(Parcel in) {
            return new QuizDetails(in);
        }

        public QuizDetails[] newArray(int size) {
            return new QuizDetails[size];
        }
    };

    private QuizDetails(Parcel in) {
        this.quizId = in.readInt();
        this.quizText = in.readString();
        //this.answerText = in.readStringList();
        //this.answerValue = in.readStringList();
        answerText = in.createStringArrayList();
        answerValue = in.createStringArrayList();
        this.quizUrl = in.readString();
    }
}

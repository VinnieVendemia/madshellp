package com.mobileappdevelopersclub.shellp.models;

import java.util.Arrays;
import java.util.Collections;

import android.os.Parcel;
import android.os.Parcelable;

public class Questions implements Parcelable {
    private String question;
    private String[] answers;
    private String correctAns;
    private boolean isAnswerd, answerdCorrectly;
    private String userAnswer;

    public Questions(String[] temp){

            this.question= temp[0];

            answers= new String[4];
            this.answers[0]= temp[1];
            this.answers[1]= temp[2];
            this.answers[2]= temp[3];
            this.answers[3]= temp[4];

            this.correctAns= temp[4];
    }

    public String getQuestion() {
            return question;
    }

    /**
     *
     * @return Shuffled array of answers
     */
    public String[] getAnswers() {

            Collections.shuffle(Arrays.asList(answers));
            return answers;
    }

    public String getCorrectAns() {
            return correctAns;
    }

    public boolean isAnswerd() {
            return isAnswerd;
    }

    public void setIsAnswerd(boolean answerd) {
            this.isAnswerd = answerd;
    }

    /**
     * @return the userAnswer
     */
    public String getUserAnswer() {
            return userAnswer;
    }

    /**
     * @param userAnswer the userAnswer to set
     */
    public void setUserAnswer(String userAnswer) {
            this.userAnswer = userAnswer;
    }

    /**
     * @return the answerdCorrectly
     */
    public boolean isAnswerdCorrectly() {
            return answerdCorrectly;
    }

    /**
     * @param answerdCorrectly the answerdCorrectly to set
     */
    public void setAnswerdCorrectly(boolean answerdCorrectly) {
            this.answerdCorrectly = answerdCorrectly;
    }

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public Questions(Parcel in) {
		question = in.readString();
		correctAns = in.readString();
		userAnswer = in.readString();
		in.readStringArray(answers);
		isAnswerd = in.readByte() == 1 ? true : false;
		answerdCorrectly = in.readByte() == 1 ? true : false;
	}

	@Override
	public void writeToParcel(Parcel out, int arg1) {

		out.writeString(question);
		out.writeString(correctAns);
		out.writeString(userAnswer);
		out.writeStringArray(answers);
		out.writeByte((byte) (isAnswerd ? 1 : 0));
		out.writeByte((byte) (answerdCorrectly ? 1 : 0));
	}

}


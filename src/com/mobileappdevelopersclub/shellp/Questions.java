package com.mobileappdevelopersclub.shellp;

import java.util.Arrays;
import java.util.Collections;

public class Questions {
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

}


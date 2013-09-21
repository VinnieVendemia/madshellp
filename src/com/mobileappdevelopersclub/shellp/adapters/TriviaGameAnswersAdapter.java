package com.mobileappdevelopersclub.shellp.adapters;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mobileappdevelopersclub.shellp.R;
import com.mobileappdevelopersclub.shellp.models.Questions;

public class TriviaGameAnswersAdapter extends ArrayAdapter<Questions>{
	
	
	private final int ANSWER_CORRECT_FLAG = 1;
	private final int ANSWER_INCORRECT_FLAG = -1;
	
	private Context context;
	private LayoutInflater mInflater;
	private List<Questions> questionsList;
	
	public TriviaGameAnswersAdapter(Context context, int textViewResourceId, List<Questions> objects ) {
		super(context, textViewResourceId, objects);
		this.context = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.questionsList = objects;
		//		imageLoader = new ImageLoader();

	}





	@Override
	public int getCount() {
		return (questionsList.size() > 0) ? questionsList.size() : 0;
	}


	private class WeatherViewHolder {
		LinearLayout itemBackground;
		TextView question;
		TextView userAnswer;
		TextView answerStatus;		
	}



	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		WeatherViewHolder mViewHolder;

		if(convertView == null) {
			mViewHolder = new WeatherViewHolder();
			convertView = View.inflate(context, R.layout.question_item_layout, null);
			mViewHolder.question = (TextView)convertView.findViewById(R.id.question);
			mViewHolder.userAnswer = (TextView) convertView.findViewById(R.id.user_answer);
			mViewHolder.itemBackground = (LinearLayout)convertView.findViewById(R.id.question_item_layout);
			mViewHolder.answerStatus = (TextView) convertView.findViewById(R.id.answer_status);
		
			convertView.setTag(mViewHolder);
		}
		else {
			mViewHolder = (WeatherViewHolder) convertView.getTag();
		}
		
		Questions question = questionsList.get(position);
		
		mViewHolder.question.setText(question.getQuestion());
		mViewHolder.userAnswer.setText(question.getUserAnswer());
		
		if(question.getUserAnswer().equals(question.getCorrectAns())) {
			mViewHolder.answerStatus.setText("Correct!");
			setQuestionBackground(mViewHolder.itemBackground, ANSWER_CORRECT_FLAG);
		} else {
			mViewHolder.answerStatus.setText("Incorrect!");
			setQuestionBackground(mViewHolder.itemBackground, ANSWER_INCORRECT_FLAG);
		}
		
//		if(position == 0) {
//			mViewHolder.header.setVisibility(View.VISIBLE);
//		}

		return convertView;
	}
	
	private void setQuestionBackground(LinearLayout layout, int answerStatusFlag) {
		
		if(answerStatusFlag == ANSWER_CORRECT_FLAG) {
			layout.setBackgroundColor(context.getResources().getColor(R.color.correct_green));
		} else {
			layout.setBackgroundColor(context.getResources().getColor(R.color.incorrect_red));
		} 
	}
	
}


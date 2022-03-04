package com.jcarlosprofesor.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimerPickerFragment extends DialogFragment {
	private static final String ARG_TIME = "time";
	private TimePicker mTimePicker;
	public static final String EXTRA_TIME = "time_new";

	public static TimerPickerFragment newInstance(Date date){
		Bundle args = new Bundle();
		args.putSerializable(ARG_TIME,date);
		TimerPickerFragment fragment = new TimerPickerFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
		Date date = (Date) getArguments().getSerializable(ARG_TIME);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time,null);
		mTimePicker = (TimePicker) view.findViewById(R.id.dialog_time_picker);
		mTimePicker.setCurrentHour(hour);
		mTimePicker.setCurrentMinute(minute);
		return new AlertDialog.Builder(getActivity())
				.setView(view)
				.setTitle(R.string.data_picker_title)
				//.setPositiveButton(android.R.string.ok, null)
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						int hour = mTimePicker.getCurrentHour();
						int minute = mTimePicker.getCurrentMinute();
						Date date = new GregorianCalendar(Calendar.YEAR,
								Calendar.MONTH,
								Calendar.DAY_OF_WEEK,
								hour,
								minute).getTime();
						sendResult(Activity.RESULT_OK,date);
					}
				})
				.create();
	}

	private void sendResult(int resultCode, Date date){
		if(getTargetFragment() == null){
			return;
		}
		Intent intent = new Intent();
		intent.putExtra(EXTRA_TIME,date);
		getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);
	}
}

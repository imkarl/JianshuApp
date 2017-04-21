package com.copy.jianshuapp.common;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 键盘相关
 * @author imkarl 2017-03
 * @see <a href="https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/KeyboardUtils.java">utilcode/KeyBoardUtils</a>
 */
public class KeyboardUtils {

	/** 
	 * 打开软键盘
	 */
	public static void showSoftInput() {
		Activity activity = ActivityLifcycleManager.get().current();
		View view = activity.getCurrentFocus();
		if (view == null) {
			view = activity.findViewById(android.R.id.content);
		}
		showSoftInput(view);
	}
	/**
	 * 打开软键盘
	 */
	public static void showSoftInput(View view) {
		if (view instanceof EditText) {
			EditText editText = (EditText) view;
			editText.setFocusable(true);
			editText.setFocusableInTouchMode(true);
			editText.requestFocus();
		}
		InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm == null) {
			return;
		}
		//imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
		imm.showSoftInput(view, 0);
	}


	/** 
	 * 关闭软键盘 
	 */
	public static void hideSoftInput(View view) {
		InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm == null) {
			return;
		}
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
	/**
	 * 关闭软键盘
	 */
	public static void hideSoftInput() {
		Activity activity = ActivityLifcycleManager.get().current();
		View view = activity.getCurrentFocus();
		if (view == null) {
			view = activity.findViewById(android.R.id.content);
		}
		hideSoftInput(view);
	}


	/**
	 * 切换键盘显示状态
	 */
	public static void toggleSoftInput(View view) {
		InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
	}


	/**
	 * 获取键盘显示状态
	 * @param view 输入框
	 */
	public static boolean isActive(View view) {
		InputMethodManager inputMethod = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		return inputMethod.isActive(view);
	}

//	/**
//	 * 获取键盘显示状态
//	 */
//	public static boolean isVisible(Activity activity) {
//		return KeyboardVisibilityEvent.isKeyboardVisible(activity);
//	}

}

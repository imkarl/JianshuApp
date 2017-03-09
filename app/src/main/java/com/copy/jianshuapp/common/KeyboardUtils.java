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
	 * @param editText 输入框
	 */
	public static void showSoftInput(EditText editText) {
		editText.setFocusable(true);
		editText.setFocusableInTouchMode(true);
		editText.requestFocus();
		InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm == null) {
			return;
		}
		imm.showSoftInput(editText, 0);
	}

	/** 
	 * 关闭软键盘 
	 */
	public static void hideSoftInput(View view) {
		view.clearFocus();
		InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm == null) {
			return;
		}
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
	/**
	 * 关闭软键盘
	 * @param activity 所在Activity
	 */
	public static void hideSoftInput(Activity activity) {
		View view = activity.getCurrentFocus();
		if (view != null) {
			view.clearFocus();
		} else {
			view = new View(activity);
		}
		hideSoftInput(view);
	}

	/**
	 * 切换键盘显示状态
	 * @param editText 输入框
	 */
	public static void toggleSoftInput(EditText editText) {
		editText.setFocusable(true);
		editText.setFocusableInTouchMode(true);
		editText.requestFocus();
		InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
	}

	/**
	 * 获取键盘显示状态
	 * @param editText 输入框
	 */
	public static boolean isActive(EditText editText) {
		InputMethodManager inputMethod = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		return inputMethod.isActive(editText);
	}

//	/**
//	 * 获取键盘显示状态
//	 */
//	public static boolean isVisible(Activity activity) {
//		return KeyboardVisibilityEvent.isKeyboardVisible(activity);
//	}

}

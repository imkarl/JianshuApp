package jianshu.widget;

import android.os.Parcel;
import android.text.TextPaint;
import android.text.style.URLSpan;
import android.view.View;

import com.copy.jianshuapp.uilayer.browser.BrowserActivity;

import jianshu.api.MatchJianShuUrl;

/**
 * @version JianShu 2017-03
 */
public class URLSpanNoUnderline extends URLSpan {
    public URLSpanNoUnderline(String p_Url) {
        super(p_Url);
    }

    public void updateDrawState(TextPaint p_DrawState) {
        super.updateDrawState(p_DrawState);
        p_DrawState.setUnderlineText(false);
    }

    public void onClick(View widget) {
        if (!MatchJianShuUrl.matchUrl(getURL(), widget.getContext())) {
            BrowserActivity.launch(getURL());
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }
    protected URLSpanNoUnderline(Parcel in) {
        super(in);
    }
    public static final Creator<URLSpanNoUnderline> CREATOR = new Creator<URLSpanNoUnderline>() {
        @Override
        public URLSpanNoUnderline createFromParcel(Parcel source) {
            return new URLSpanNoUnderline(source);
        }
        @Override
        public URLSpanNoUnderline[] newArray(int size) {
            return new URLSpanNoUnderline[size];
        }
    };
}

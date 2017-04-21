package jianshu.widget;

import android.content.Context;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.vanniktech.emoji.EmojiTextView;

/**
 * @version JianShu 2017-03
 * @see <a href="http://stackoverflow.com/questions/8558732/listview-textview-with-linkmovementmethod-makes-list-item-unclickable">LocalLinkMovementMethod</a>
 */
public class TextViewFixTouchConsume extends EmojiTextView {
    boolean dontConsumeNonUrlClicks = true;
    boolean linkHit;

    public TextViewFixTouchConsume(Context context) {
        super(context);
    }
    public TextViewFixTouchConsume(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean onTouchEvent(MotionEvent event) {
        this.linkHit = false;
        boolean res = super.onTouchEvent(event);
        if (this.dontConsumeNonUrlClicks) {
            return this.linkHit;
        }
        return res;
    }

    public void setTextViewHTML(String html) {
        SpannableStringBuilder strBuilder = new SpannableStringBuilder(Html.fromHtml(html));
        removeUnderlines(strBuilder);
        setText(strBuilder);
    }

    public void setTextViewHTML(String html, Html.ImageGetter imgGetter) {
        SpannableStringBuilder strBuilder = new SpannableStringBuilder(Html.fromHtml(html, imgGetter, null));
        removeUnderlines(strBuilder);
        setText(strBuilder);
    }

    public Spannable removeUnderlines(Spannable text) {
        for (URLSpan span : text.getSpans(0, text.length(), URLSpan.class)) {
            int start = text.getSpanStart(span);
            int end = text.getSpanEnd(span);
            text.removeSpan(span);
            text.setSpan(new URLSpanNoUnderline(span.getURL()), start, end, 0);
        }
        return text;
    }
}

package jianshu.widget;

import android.content.Context;
import android.text.Html;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.method.Touch;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.vanniktech.emoji.EmojiTextView;

/**
 * @version JianShu 2017-03
 * @see <a href="http://stackoverflow.com/questions/8558732/listview-textview-with-linkmovementmethod-makes-list-item-unclickable">LocalLinkMovementMethod</a>
 */
public class TextViewFixTouchConsume extends EmojiTextView {
    boolean dontConsumeNonUrlClicks = true;
    boolean linkHit;

    public static class LocalLinkMovementMethod extends LinkMovementMethod {
        static LocalLinkMovementMethod sInstance;

        public static LocalLinkMovementMethod getInstance() {
            if (sInstance == null) {
                sInstance = new LocalLinkMovementMethod();
            }
            return sInstance;
        }

        public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
            int action = event.getAction();
            if (action != MotionEvent.ACTION_UP && action != MotionEvent.ACTION_DOWN) {
                return Touch.onTouchEvent(widget, buffer, event);
            }

            int x = (((int) event.getX()) - widget.getTotalPaddingLeft()) + widget.getScrollX();
            int y = (((int) event.getY()) - widget.getTotalPaddingTop()) + widget.getScrollY();
            Layout layout = widget.getLayout();
            int off = layout.getOffsetForHorizontal(layout.getLineForVertical(y), (float) x);
            ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);
            if (link.length != 0) {
                if (action == MotionEvent.ACTION_UP) {
                    link[0].onClick(widget);
                } else {
                    Selection.setSelection(buffer, buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]));
                }
                if (!(widget instanceof TextViewFixTouchConsume)) {
                    return true;
                }
                ((TextViewFixTouchConsume) widget).linkHit = true;
                return true;
            }
            Selection.removeSelection(buffer);
            Touch.onTouchEvent(widget, buffer, event);
            return false;
        }
    }

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

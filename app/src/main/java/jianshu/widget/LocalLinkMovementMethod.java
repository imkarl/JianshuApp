package jianshu.widget;

import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.Touch;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * @version JianShu 2017-03
 */
public class LocalLinkMovementMethod extends LinkMovementMethod {
    private static LocalLinkMovementMethod sInstance;

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

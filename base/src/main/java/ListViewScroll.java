import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 无限高的ListView
 *
 * @author yangfei
 */
public class ListViewScroll extends ListView {

    public ListViewScroll(Context context) {
        super(context);
    }
    public ListViewScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ListViewScroll(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, height);
    }

}
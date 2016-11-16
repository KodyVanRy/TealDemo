package kopperkow.soccerstats.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import kopperkow.soccerstats.R;

public class SoccerPlayDrawingView extends View {

    private static final float MOVEMENT_TOLERANCE = 4;
    Context context;
    private ArrayList<MotionEvent> motionEventArrayList = new ArrayList<>();
    private Bitmap bitmap;
    private Canvas canvas;
    private Path path;
    private Paint bitmapPaint;
    private Paint circlePaint;
    private Path circlePath;
    private Paint paint;
    private float mX, mY;

    public SoccerPlayDrawingView(Context context) {
        super(context);
        this.context = context;
        path = new Path();
        bitmapPaint = new Paint(Paint.DITHER_FLAG);
        circlePaint = new Paint();
        circlePath = new Path();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(getResources().getColor(R.color.colorAccent));
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeJoin(Paint.Join.MITER);
        circlePaint.setStrokeWidth(3f);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(12);
    }

    public SoccerPlayDrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        path = new Path();
        bitmapPaint = new Paint(Paint.DITHER_FLAG);
        circlePaint = new Paint();
        circlePath = new Path();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(getResources().getColor(R.color.colorAccent));
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeJoin(Paint.Join.MITER);
        circlePaint.setStrokeWidth(3f);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(12);
    }

    public SoccerPlayDrawingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        path = new Path();
        bitmapPaint = new Paint(Paint.DITHER_FLAG);
        circlePaint = new Paint();
        circlePath = new Path();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(getResources().getColor(R.color.colorAccent));
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeJoin(Paint.Join.MITER);
        circlePaint.setStrokeWidth(3f);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(12);
    }

    public void clear() {
        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        invalidate();
    }

    /**
     * Must override to keep the signature if screen is rotated!
     * @param w new width
     * @param h new height
     * @param oldw old width
     * @param oldh old height
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        try {
            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap);
            for (MotionEvent event : getMotionEventArrayList()) {
                performTouchEvent(event);
            }
        } catch (IllegalArgumentException e) { // TODO Kept getting a weird exception sometimes where width was equal to 0 which is illegal. Should probably look into a proper fix not just "it broke I'll add a catch"
            e.printStackTrace();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
        canvas.drawPath(path, paint);
        canvas.drawPath(circlePath, circlePaint);
    }

    private void do_touch_start(float x, float y) {
        path.reset();
        path.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void do_touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= MOVEMENT_TOLERANCE || dy >= MOVEMENT_TOLERANCE) {
            path.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;

            circlePath.reset();
            circlePath.addCircle(mX, mY, 30, Path.Direction.CW);
        }
    }

    private void do_touch_up() {
        path.lineTo(mX, mY);
        circlePath.reset();
        canvas.drawPath(path, paint);
        path.reset();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                performTouchEvent(event);
        }
        getMotionEventArrayList().add(MotionEvent.obtain(event));
        return true;
    }

    private void performTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                do_touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                do_touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                do_touch_up();
                invalidate();
                break;
        }
    }

    private ArrayList<MotionEvent> getMotionEventArrayList() {
        if (motionEventArrayList == null) {
            motionEventArrayList = new ArrayList<>();
        }
        return motionEventArrayList;
    }

    public Bitmap getBitmap() {
        View v = this;
        Bitmap b = Bitmap.createBitmap( v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        c.drawColor(Color.WHITE);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        return b;
    }
}
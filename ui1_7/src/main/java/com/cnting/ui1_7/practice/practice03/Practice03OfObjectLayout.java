package com.cnting.ui1_7.practice.practice03;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.cnting.ui1_7.R;

public class Practice03OfObjectLayout extends RelativeLayout {
    Practice03OfObjectView view;
    Button animateBt;
    int clickIndex;

    public Practice03OfObjectLayout(Context context) {
        super(context);
    }

    public Practice03OfObjectLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice03OfObjectLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        view = (Practice03OfObjectView) findViewById(R.id.objectAnimatorView);
        animateBt = (Button) findViewById(R.id.animateBt);

        animateBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator;
                if (clickIndex % 2 == 0) {
                    animator = ObjectAnimator.ofObject(view, "position",
                            new PointFEvaluator(), new PointF(0, 0), new PointF(1, 1));
                } else {
                    animator = ObjectAnimator.ofObject(view, "position",
                            new PointFEvaluator(), new PointF(1, 1), new PointF(0, 0));
                }
                animator.setInterpolator(new LinearInterpolator());
                animator.setDuration(1000);
                animator.start();

                clickIndex++;
            }
        });
    }

    private class PointFEvaluator implements TypeEvaluator<PointF> {

        PointF pointF = new PointF();

        // 重写 evaluate() 方法，让 PointF 可以作为属性来做动画
        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            pointF.x = startValue.x + (endValue.x - startValue.x) * fraction;
            pointF.y = startValue.y + (endValue.y - startValue.y) * fraction;
            return pointF;
        }
    }
}

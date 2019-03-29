package com.example.user.myapplication.extension;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 延展组件
 *
 * @author by syp on 2019/1/16.
 */
public class ExtensionView extends FrameLayout {

    /** 展开item下标 */
    private int extensionIndex = 0;

    private LeftExtensionLayoutView leftSpecialShapedView;
    private CenterExtensionLayoutView centerSpecialShapedView1;
    private CenterExtensionLayoutView centerSpecialShapedView2;
    private CenterExtensionLayoutView centerSpecialShapedView3;
    private RightExtensionLayoutView rightSpecialShapedView;

    private List<BaseExtensionLayout> mListSpecialShaped;

    public ExtensionView(Context context) {
        this(context, null);
    }

    public ExtensionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExtensionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 不裁切子view
        setClipChildren(false);
//        // 设置可以获取焦点
//        setFocusable(true);
//        setFocusableInTouchMode(true);
//        // 抢占子view焦点
//        setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_extension, this, false);
        leftSpecialShapedView = view.findViewById(R.id.layout_left);
        centerSpecialShapedView1 = view.findViewById(R.id.layout_center1);
        centerSpecialShapedView2 = view.findViewById(R.id.layout_center2);
        centerSpecialShapedView3 = view.findViewById(R.id.layout_center3);
        rightSpecialShapedView = view.findViewById(R.id.layout_right);

        leftSpecialShapedView.setOnFocusChanged(focusChangedCallback);
        centerSpecialShapedView1.setOnFocusChanged(focusChangedCallback);
        centerSpecialShapedView2.setOnFocusChanged(focusChangedCallback);
        centerSpecialShapedView3.setOnFocusChanged(focusChangedCallback);
        rightSpecialShapedView.setOnFocusChanged(focusChangedCallback);

        mListSpecialShaped = new ArrayList<>();
        mListSpecialShaped.add(leftSpecialShapedView);
        mListSpecialShaped.add(centerSpecialShapedView1);
        mListSpecialShaped.add(centerSpecialShapedView2);
        mListSpecialShaped.add(centerSpecialShapedView3);
        mListSpecialShaped.add(rightSpecialShapedView);

        addView(view);
    }

    private BaseExtensionLayout.IFocusChangedCallback focusChangedCallback = new BaseExtensionLayout.IFocusChangedCallback() {
        @Override
        public void onFocusChanged(View view, boolean gainFocus, int direction) {
            extensionIndex = mListSpecialShaped.indexOf(view);
            if (gainFocus) {
                updateView();
            } else {
                ((BaseExtensionLayout) view).scaleView(view, 1, 1);
            }
        }
    };

    /**
     * 刷新view
     */
    public void updateView() {
        int size = mListSpecialShaped.size();
        for (int i = 0; i < size; i++) {
            if (i == extensionIndex) {
                mListSpecialShaped.get(i).setExtension(true);
            } else {
                mListSpecialShaped.get(i).setExtension(false);
            }
        }
    }

}

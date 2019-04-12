package com.example.fogfly.smarter.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 使用方式：
 * --在调用setViewPager之前使用setxxx方法进行样式设置，支持链式调用
 * --调用setViewPager方法将viewPager绑定到simpleViewpagerIndicator(viewPager的adapter必须实现getPageTitle方法)
 * --调用setOnPageChangeListener来设置viewPager的页面切换监听
 */
public class SimpleViewpagerIndicator extends HorizontalScrollView {

//配置属性 START-------------------------------------------------------------------------------------

    /*
     * true:每个tab宽度为平分父控件剩余空间
     * false:每个tab宽度为包裹内容
     */
    private boolean expand = false;

    /*
     * 指示器（被选中的tab下的短横线）
     */
    private boolean indicatorWrapText = true;//true：indicator与文字等长；false：indicator与整个tab等长
    private int indicatorColor = Color.parseColor("#ff666666");
    private int indicatorHeight = 2;//dp

    /*
     * 底线（指示器的背景滑轨）
     */
    private boolean showUnderline = false;//是否展示底线
    private int underlineColor;
    private int underlineHeight;//dp

    /*
     * tab之间的分割线
     */
    private boolean showDivider = false;//是否展示分隔线
    private int dividerColor;
    private int dividerPadding;//分隔线上下的padding,dp
    private int dividerWidth;//分隔线宽度,dp

    /*
     * tab
     */
    private int tabTextSize = 16;//tab字号,dp
    private int tabTextColor = Color.parseColor("#ff999999");//tab字色
    private Typeface tabTypeface = null;//tab字体
    private int tabTypefaceStyle = Typeface.NORMAL;//tab字体样式
    private int tabBackgroundResId = 0;//每个tab的背景资源id
    private int tabPadding = 24;//每个tab的左右内边距,dp

    /*
     * 被选中的tab
     */
    private int selectedTabTextSize = 16;//dp
    private int selectedTabTextColor = Color.parseColor("#ff666666");
    private Typeface selectedTabTypeface = null;
    private int selectedTabTypefaceStyle = Typeface.BOLD;

    /*
     * scrollView整体滚动的偏移量,dp
     */
    private int scrollOffset = 100;

//配置属性 End---------------------------------------------------------------------------------------

    private LinearLayout.LayoutParams wrapTabLayoutParams;
    private LinearLayout.LayoutParams expandTabLayoutParams;

    private Paint rectPaint;
    private Paint dividerPaint;
    private Paint measureTextPaint;//测量文字宽度用的画笔

    private final PageListener pageListener = new PageListener();
    private OnPageChangeListener userPageListener;

    private LinearLayout tabsContainer;//tab的容器
    private ViewPager viewPager;

    private int currentPosition = 0;//viewPager当前页面
    private float currentPositionOffset = 0f;//viewPager当前页面的偏移百分比（取值：0~1）
    private int selectedPosition = 0;//viewPager当前被选中的页面

    private int tabCount;
    private int lastScrollX = 0;

    public SimpleViewpagerIndicator(Context context) {
        this(context, null);
    }

    public SimpleViewpagerIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleViewpagerIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFillViewport(true);
        setWillNotDraw(false);
    }

    public SimpleViewpagerIndicator setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        if (viewPager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }

        viewPager.setOnPageChangeListener(pageListener);

        init();
        initView();

        return this;
    }

    public SimpleViewpagerIndicator setOnPageChangeListener(OnPageChangeListener listener) {
        this.userPageListener = listener;
        return this;
    }

    private void init() {
        /*
         * 将dp换算为px
         */
        float density = getContext().getResources().getDisplayMetrics().density;
        indicatorHeight = (int) (indicatorHeight * density);
        underlineHeight = (int) (underlineHeight * density);
        dividerPadding = (int) (dividerPadding * density);
        dividerWidth = (int) (dividerWidth * density);
        tabTextSize = (int) (tabTextSize * density);
        tabPadding = (int) (tabPadding * density);
        selectedTabTextSize = (int) (selectedTabTextSize * density);
        scrollOffset = (int) (scrollOffset * density);

        /*
         * 创建tab的容器（LinearLayout）
         */
        tabsContainer = new LinearLayout(getContext());
        tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        tabsContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(tabsContainer);

        /*
         * 创建画笔
         */
        rectPaint = new Paint();
        rectPaint.setAntiAlias(true);
        rectPaint.setStyle(Style.FILL);

        dividerPaint = new Paint();
        dividerPaint.setAntiAlias(true);
        dividerPaint.setStrokeWidth(dividerWidth);

        measureTextPaint = new Paint();
        measureTextPaint.setTextSize(selectedTabTextSize);

        /*
         * 创建两个Tab的LayoutParams，一个为宽度包裹内容，一个为宽度等分父控件剩余空间
         */
        wrapTabLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);//宽度包裹内容
        expandTabLayoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);//宽度等分
    }

    private void initView() {
        //注意：currentPosition和selectedPosition的含义并不相同，它们分别在onPageScroll和onPageSelected中被赋值
        //在从tab1往tab2滑动的过程中,selectedPosition会比currentPosition先由1变成2
        currentPosition = viewPager.getCurrentItem();
        selectedPosition = viewPager.getCurrentItem();

        tabsContainer.removeAllViews();
        tabCount = viewPager.getAdapter().getCount();

        //创建tab并添加到tabsContainer中
        for (int i = 0; i < tabCount; i++) {
            addTab(i, viewPager.getAdapter().getPageTitle(i).toString());
        }

        //遍历tab，设置tab文字大小和样式
        updateTextStyle();

        //滚动scrollView
        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }

                scrollToChild(currentPosition, 0);//滚动scrollView
            }
        });
    }

    /**
     * 添加tab
     */
    private void addTab(final int position, String title) {
        TextView tab = new TextView(getContext());

        tab.setGravity(Gravity.CENTER);
        tab.setSingleLine();
        tab.setText(title);
        if (tabBackgroundResId != 0) {
            tab.setBackgroundResource(tabBackgroundResId);
        }
        tab.setPadding(tabPadding, 0, tabPadding, 0);
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(position);
            }
        });

        tabsContainer.addView(tab, position, expand ? expandTabLayoutParams : wrapTabLayoutParams);
    }

    /**
     * 遍历tab，设置tab文字大小和样式
     */
    private void updateTextStyle() {
        for (int i = 0; i < tabCount; i++) {
            TextView tvTab = (TextView) tabsContainer.getChildAt(i);

            if (i == selectedPosition) {//被选中的tab
                tvTab.setTextSize(TypedValue.COMPLEX_UNIT_PX, selectedTabTextSize);
                tvTab.setTypeface(selectedTabTypeface, selectedTabTypefaceStyle);
                tvTab.setTextColor(selectedTabTextColor);
            } else {//未被选中的tab
                tvTab.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSize);
                tvTab.setTypeface(tabTypeface, tabTypefaceStyle);
                tvTab.setTextColor(tabTextColor);
            }
        }
    }

    /**
     * 滚动scrollView
     * <p>
     * 注意：当普通文字字号（tabTextSize）与被选中的文字字号（selectedTabTextSize）相差过大，且tab的宽度模式为包裹内容（expand = false）时，
     * 由于文字选中状态切换时文字宽度突变，造成tab宽度突变，可能导致scrollView在滚动时出现轻微抖动。
     * 因此，当普通文字字号（tabTextSize）与被选中的文字字号（selectedTabTextSize）相差过大时，应避免使tab宽度包裹内容（expand = false）。
     */
    private void scrollToChild(int position, int offset) {
        if (tabCount == 0) return;

        //getLeft():tab相对于父控件，即tabsContainer的left
        int newScrollX = tabsContainer.getChildAt(position).getLeft() + offset;

        //附加一个偏移量，防止当前选中的tab太偏左
        //可以去掉看看是什么效果
        if (position > 0 || offset > 0) {
            newScrollX -= scrollOffset;
        }

        if (newScrollX != lastScrollX) {
            lastScrollX = newScrollX;
            scrollTo(newScrollX, 0);
        }
    }

    /**
     * 绘制indicator、underline和divider
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isInEditMode() || tabCount == 0) return;

        final int height = getHeight();

        /*
         * 绘制divider
         */
        if (showDivider) {
            dividerPaint.setColor(dividerColor);
            for (int i = 0; i < tabCount - 1; i++) {
                View tab = tabsContainer.getChildAt(i);
                canvas.drawLine(tab.getRight(), dividerPadding, tab.getRight(), height - dividerPadding, dividerPaint);
            }
        }

        /*
         * 绘制underline(indicator的背景线)
         */
        if (showUnderline) {
            rectPaint.setColor(underlineColor);
            canvas.drawRect(0, height - underlineHeight, tabsContainer.getWidth(), height, rectPaint);
        }

        /*
         * 绘制indicator
         */
        if (indicatorWrapText) {//indicator与文字等长
            rectPaint.setColor(indicatorColor);
            getTextLocation(currentPosition);
            float lineLeft = textLocation.left;
            float lineRight = textLocation.right;
            if (currentPositionOffset > 0f && currentPosition < tabCount - 1) {
                getTextLocation(currentPosition + 1);
                final float nextLeft = textLocation.left;
                final float nextRight = textLocation.right;

                lineLeft = lineLeft + (nextLeft - lineLeft) * currentPositionOffset;
                lineRight = lineRight + (nextRight - lineRight) * currentPositionOffset;
            }
            canvas.drawRect(lineLeft, height - indicatorHeight, lineRight, height, rectPaint);
        } else {//indicator与tab等长
            rectPaint.setColor(indicatorColor);
            View currentTab = tabsContainer.getChildAt(currentPosition);
            float lineLeft = currentTab.getLeft();
            float lineRight = currentTab.getRight();
            if (currentPositionOffset > 0f && currentPosition < tabCount - 1) {
                View nextTab = tabsContainer.getChildAt(currentPosition + 1);
                final float nextLeft = nextTab.getLeft();
                final float nextRight = nextTab.getRight();

                lineLeft = lineLeft + (nextLeft - lineLeft) * currentPositionOffset;
                lineRight = lineRight + (nextRight - lineRight) * currentPositionOffset;
            }
            canvas.drawRect(lineLeft, height - indicatorHeight, lineRight, height, rectPaint);
        }
    }

    /**
     * 获得指定tab中，文字的left和right
     */
    private void getTextLocation(int position) {
        View tab = tabsContainer.getChildAt(position);
        String tabText = viewPager.getAdapter().getPageTitle(position).toString();

        float textWidth = measureTextPaint.measureText(tabText);
        int tabWidth = tab.getWidth();
        textLocation.left = tab.getLeft() + (int) ((tabWidth - textWidth) / 2);
        textLocation.right = tab.getRight() - (int) ((tabWidth - textWidth) / 2);
    }

    private LeftRight textLocation = new LeftRight();

    class LeftRight {
        int left, right;
    }

    private class PageListener implements OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            currentPosition = position;
            currentPositionOffset = positionOffset;

            //scrollView滚动
            scrollToChild(position, (int) (positionOffset * tabsContainer.getChildAt(position).getWidth()));

            invalidate();//invalidate后onDraw会被调用,绘制indicator、divider等

            if (userPageListener != null) {
                userPageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                scrollToChild(viewPager.getCurrentItem(), 0);//scrollView滚动
            }

            if (userPageListener != null) {
                userPageListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(int position) {
            selectedPosition = position;
            updateTextStyle();//更新tab文字大小和样式

            if (userPageListener != null) {
                userPageListener.onPageSelected(position);
            }
        }
    }

//setter--------------------------------------------------------------------------------------------

    public SimpleViewpagerIndicator setExpand(boolean expand) {
        this.expand = expand;
        return this;
    }

    public SimpleViewpagerIndicator setIndicatorWrapText(boolean indicatorWrapText) {
        this.indicatorWrapText = indicatorWrapText;
        return this;
    }

    public SimpleViewpagerIndicator setIndicatorColor(int indicatorColor) {
        this.indicatorColor = indicatorColor;
        return this;
    }

    public SimpleViewpagerIndicator setIndicatorHeight(int indicatorHeight) {
        this.indicatorHeight = indicatorHeight;
        return this;
    }

    public SimpleViewpagerIndicator setShowUnderline(boolean showUnderline, int underlineColor, int underlineHeight) {
        this.showUnderline = showUnderline;
        this.underlineColor = underlineColor;
        this.underlineHeight = underlineHeight;
        return this;
    }

    public SimpleViewpagerIndicator setShowDivider(boolean showDivider, int dividerColor, int dividerPadding, int dividerWidth) {
        this.showDivider = showDivider;
        this.dividerColor = dividerColor;
        this.dividerPadding = dividerPadding;
        this.dividerWidth = dividerWidth;

        return this;
    }

    public SimpleViewpagerIndicator setTabTextSize(int tabTextSize) {
        this.tabTextSize = tabTextSize;
        return this;
    }

    public SimpleViewpagerIndicator setTabTextColor(int tabTextColor) {
        this.tabTextColor = tabTextColor;
        return this;
    }

    public SimpleViewpagerIndicator setTabTypeface(@Nullable Typeface tabTypeface) {
        this.tabTypeface = tabTypeface;
        return this;
    }

    public SimpleViewpagerIndicator setTabTypefaceStyle(int tabTypefaceStyle) {
        this.tabTypefaceStyle = tabTypefaceStyle;
        return this;
    }

    public SimpleViewpagerIndicator setTabBackgroundResId(int tabBackgroundResId) {
        this.tabBackgroundResId = tabBackgroundResId;
        return this;
    }

    public SimpleViewpagerIndicator setTabPadding(int tabPadding) {
        this.tabPadding = tabPadding;
        return this;
    }

    public SimpleViewpagerIndicator setSelectedTabTextSize(int selectedTabTextSize) {
        this.selectedTabTextSize = selectedTabTextSize;
        return this;
    }

    public SimpleViewpagerIndicator setSelectedTabTextColor(int selectedTabTextColor) {
        this.selectedTabTextColor = selectedTabTextColor;
        return this;
    }

    public SimpleViewpagerIndicator setSelectedTabTypeface(@Nullable Typeface selectedTabTypeface) {
        this.selectedTabTypeface = selectedTabTypeface;
        return this;
    }

    public SimpleViewpagerIndicator setSelectedTabTypefaceStyle(int selectedTabTypefaceStyle) {
        this.selectedTabTypefaceStyle = selectedTabTypefaceStyle;
        return this;
    }

    public SimpleViewpagerIndicator setScrollOffset(int scrollOffset) {
        this.scrollOffset = scrollOffset;
        return this;
    }
}
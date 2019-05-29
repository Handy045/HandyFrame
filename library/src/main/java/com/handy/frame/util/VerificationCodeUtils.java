package com.handy.frame.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.blankj.utilcode.util.ConvertUtils;

import java.util.HashMap;
import java.util.Random;

/**
 * 图形验证码生成工具
 *
 * @author LiuJie https://github.com/Handy045
 * @description File Description
 * @date Created in 2019-04-24 15:43
 * @modified By liujie
 */
public class VerificationCodeUtils {
    private static final char[] CHARS = {
            '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm',
            'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M',
            'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    private static VerificationCodeUtils codeUtils;

    private int paddingLeft, paddingTop;
    private Random random = new Random();
    private StringBuilder stringBuilder = new StringBuilder();

    /**
     * 验证码的长度  这里是6位
     */
    private static final int DEFAULT_CODE_LENGTH = 4;
    /**
     * 多少条干扰线
     */
    private static final int DEFAULT_LINE_NUMBER = 3;
    /**
     * 默认背景颜色值
     */
    private static final int DEFAULT_COLOR = 0xEF;
    /**
     * 字体大小
     */
    private static final int DEFAULT_FONT_SIZE = ConvertUtils.sp2px(15);
    /**
     * /左边距
     */
    private static final int BASE_PADDING_LEFT = DEFAULT_FONT_SIZE * 2 / 3;
    /**
     * 左边距范围值
     */
    private static final int RANGE_PADDING_LEFT = DEFAULT_FONT_SIZE / 3;
    /**
     * 上边距
     */
    private static final int BASE_PADDING_TOP = DEFAULT_FONT_SIZE;
    /**
     * 上边距范围值
     */
    private static final int RANGE_PADDING_TOP = DEFAULT_FONT_SIZE / 2;
    /**
     * 默认宽度.图片的总宽
     */
    private static final int DEFAULT_WIDTH = DEFAULT_FONT_SIZE * 5;
    /**
     * 默认高度.图片的总高
     */
    private static final int DEFAULT_HEIGHT = DEFAULT_FONT_SIZE * 2;

    private String code;

    public static VerificationCodeUtils getInstance() {
        if (codeUtils == null) {
            codeUtils = new VerificationCodeUtils();
        }
        return codeUtils;
    }

    public HashMap<String, Object> getVerificationCode() {
        Bitmap bitmap = createBitmap();
        String code = getCode();
        return new HashMap<String, Object>() {{
            put("bitmap", bitmap);
            put("code", code);
        }};
    }

    /**
     * 生成验证码图片
     */
    private Bitmap createBitmap() {
        paddingLeft = 0;
        paddingTop = 0;

        Bitmap bitmap = Bitmap.createBitmap(DEFAULT_WIDTH, DEFAULT_HEIGHT, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        code = createCode();
        canvas.drawColor(Color.rgb(DEFAULT_COLOR, DEFAULT_COLOR, DEFAULT_COLOR));
        Paint paint = new Paint();
        paint.setTextSize(DEFAULT_FONT_SIZE);

        for (int i = 0; i < code.length(); i++) {
            randomTextStyle(paint);
            randomPadding();
            canvas.drawText(code.charAt(i) + "", paddingLeft, paddingTop, paint);
        }
        //干扰线
        for (int i = 0; i < DEFAULT_LINE_NUMBER; i++) {
            drawLine(canvas, paint);
        }
        canvas.save();
        canvas.restore();
        return bitmap;
    }

    /**
     * 得到图片中的验证码字符串
     */
    private String getCode() {
        return code;
    }

    /**
     * 生成验证码
     */
    private String createCode() {
        stringBuilder.delete(0, stringBuilder.length());
        for (int i = 0; i < DEFAULT_CODE_LENGTH; i++) {
            stringBuilder.append(CHARS[random.nextInt(CHARS.length)]);
        }
        return stringBuilder.toString();
    }

    /**
     * 生成干扰线
     */
    private void drawLine(Canvas canvas, Paint paint) {
        int color = randomColor();
        int startX = random.nextInt(DEFAULT_WIDTH);
        int startY = random.nextInt(DEFAULT_HEIGHT);
        int stopX = random.nextInt(DEFAULT_WIDTH);
        int stopY = random.nextInt(DEFAULT_HEIGHT);
        paint.setStrokeWidth(1);
        paint.setColor(color);
        canvas.drawLine(startX, startY, stopX, stopY, paint);
    }

    /**
     * 随机颜色
     */
    private int randomColor() {
        //使用之前首先清空内容
        stringBuilder.delete(0, stringBuilder.length());
        String haxString;
        for (int i = 0; i < 3; i++) {
            haxString = Integer.toHexString(random.nextInt(0xFF));
            if (haxString.length() == 1) {
                haxString = "0" + haxString;
            }
            stringBuilder.append(haxString);
        }
        return Color.parseColor("#" + stringBuilder.toString());
    }

    /**
     * 随机文本样式
     */
    private void randomTextStyle(Paint paint) {
        int color = randomColor();
        paint.setColor(color);
        // true为粗体，false为非粗体
        paint.setFakeBoldText(random.nextBoolean());
        float skewX = random.nextInt(11) / 10f;
        skewX = random.nextBoolean() ? skewX : -skewX;
        // float类型参数，负数表示右斜，整数左斜
        paint.setTextSkewX(skewX);
        // true为下划线，false为非下划线
        // paint.setUnderlineText(true);
        // true为删除线，false为非删除线
        // paint.setStrikeThruText(true);
    }

    /**
     * 随机间距
     */
    private void randomPadding() {
        paddingLeft += BASE_PADDING_LEFT + random.nextInt(RANGE_PADDING_LEFT);
        paddingTop = BASE_PADDING_TOP + random.nextInt(RANGE_PADDING_TOP);
    }
}

/*
 * Copyright © Yan Zhenjie. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.handy.frame.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.handy.frame.R;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumLoader;

/**
 * Glit图片加载类
 *
 * @author LiuJie https://github.com/Handy045
 * @description 在Application中初始化，用于图片选择器加载图片
 * @date Created in 2018/12/3 16:21
 * @modified By LiuJie
 */
public class GlideAlbumLoader implements AlbumLoader {

    @Override
    public void load(ImageView imageView, AlbumFile albumFile) {
        load(imageView, albumFile.getPath());
    }

    @Override
    public void load(ImageView imageView, String url) {
        RequestOptions myOptions = new RequestOptions()
                .error(R.drawable.frame_icon_imgerror)
                .placeholder(R.drawable.frame_icon_imgsearch)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        Glide.with(imageView.getContext())
                .load(url)
                .apply(myOptions)
                .into(imageView);
    }
}
